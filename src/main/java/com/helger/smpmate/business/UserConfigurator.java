/*
 * Copyright (C) 2022 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.smpmate.business;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.annotation.Nonnull;

import com.helger.smpmate.args.ESPArgOption;
import com.helger.smpmate.config.SPPaths;
import com.helger.smpmate.config.SPTask;
import com.helger.smpmate.log.MyLog;

/**
 * Represents a tool for editing user configurations.
 *
 * @author Philip Helger
 */
public final class UserConfigurator
{
  private static final int HTTP_OK = 200;

  private final boolean m_bCallSMP;
  private final SmpService m_aSmp;
  private final Path m_aCsvFailOutput;
  private final Statistics m_aStats = new Statistics ();

  /**
   * Initializes a new instance.
   */
  public UserConfigurator (@Nonnull final SPTask aTask) throws IOException
  {
    final SPPaths aPaths = aTask.getPaths ();

    m_aCsvFailOutput = aPaths.getCsvFailOutput ();
    m_aSmp = new SmpService (aTask);
    m_bCallSMP = !aTask.getOptions ().contains (ESPArgOption.DRY_RUN);
  }

  @Nonnull
  public Statistics getStats ()
  {
    return m_aStats;
  }

  /**
   * Updates the associated user configuration based on a given {@link String}.
   */
  public void update (final String sParticipantId)
  {
    m_aStats.incTotalParticipants ();

    if (m_bCallSMP)
      _tryAddToSMP (sParticipantId);
    else
      MyLog.info ( () -> "DRY_RUN: no provisioning of participant " + sParticipantId + " to SMP");
  }

  public void update (final String sParticipantId, final Path bcPath)
  {
    m_aStats.incTotalParticipants ();

    if (m_bCallSMP)
    {
      _tryAddToSMP (sParticipantId);
      _tryAddBcToSMP (sParticipantId, bcPath);
    }
    else
    {
      MyLog.info ( () -> "DRY_RUN: no provisioning of participant " + sParticipantId + " to SMP");
    }
  }

  private static boolean _allOK (@Nonnull final List <Integer> aResults)
  {
    return aResults.stream ().allMatch (x -> x.equals (Integer.valueOf (HTTP_OK)));
  }

  private boolean _tryAddToSMP (final String sParticipantID)
  {
    try
    {
      final int nRegisterUserResult = m_aSmp.registerParticipant (sParticipantID);
      if (HTTP_OK == nRegisterUserResult)
      {
        final List <Integer> aAddDocumentIDsResult = m_aSmp.addDocumentIDs (sParticipantID);
        if (_allOK (aAddDocumentIDsResult))
        {
          MyLog.info ( () -> "SMP: added participant " + sParticipantID);
          m_aStats.incAddToSMP ();
          return true;
        }
        MyLog.info ( () -> "SMP:addDocumentID: failed adding participant " +
                           sParticipantID +
                           " with result " +
                           aAddDocumentIDsResult);
        m_aStats.addAddDocumentIdFailed ();
      }
      else
      {
        MyLog.info ( () -> "SMP:registerUser: failed adding participant " +
                           sParticipantID +
                           " with result " +
                           nRegisterUserResult);
        m_aStats.addRegisterUserFailed ();
      }
    }
    catch (final IOException ex)
    {
      MyLog.error ( () -> "An error occurred while trying to submit the participant " + sParticipantID + " to the SMP.",
                    ex);
      m_aStats.addSmpFail (sParticipantID);
    }
    return false;
  }

  private boolean _tryAddBcToSMP (final String sParticipantId, final Path bcPath)
  {
    try
    {
      final byte [] content = Files.readAllBytes (bcPath);
      final int result = m_aSmp.putBusinessCard (sParticipantId, content);
      if (result == HTTP_OK)
      {
        MyLog.info ( () -> "SMP: Set business card content of " + sParticipantId);
        m_aStats.incrementBusinessCardSuccessCount ();
        return true;
      }
      MyLog.warning ( () -> "SMP: Cannot set business card content of " + sParticipantId + ". Error " + result);
      m_aStats.incrementBusinessCardFailCount ();
    }
    catch (final Exception e)
    {
      MyLog.error ( () -> "An error occurred while trying to submit business card of participant " +
                          sParticipantId +
                          " to the SMP.", e);
      m_aStats.incrementBusinessCardFailCount ();
    }
    return false;
  }

  /**
   * Writes the associated user configuration into a given XML file.
   */
  public void saveAllFailedOnes () throws IOException
  {
    if (!m_aStats.getSmpFails ().isEmpty ())
    {
      // Write all failed entries into the provided line
      Files.createDirectories (m_aCsvFailOutput.getParent ());
      MyLog.info ( () -> "Writing failed participants to " + m_aCsvFailOutput.toString ());

      try (final BufferedWriter aBW = Files.newBufferedWriter (m_aCsvFailOutput,
                                                               StandardCharsets.UTF_8,
                                                               StandardOpenOption.CREATE,
                                                               StandardOpenOption.TRUNCATE_EXISTING))
      {
        for (final String sLine : m_aStats.getSmpFails ())
        {
          aBW.write (sLine);
          aBW.newLine ();
        }
        aBW.flush ();
      }
    }
  }
}
