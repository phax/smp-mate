/*
 * Copyright (C) 2022-204 Philip Helger (www.helger.com)
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
package com.helger.smpmate.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.smpmate.args.SPArgPaths;

/**
 * Contains the paths of a task configuration.
 *
 * @author Philip Helger
 * @see com.helger.smpmate.config.SPPaths
 */
public final class SPPaths
{
  private final Path m_aCsvInput;
  private final Path m_aCsvFailOutput;
  private final Path m_aServiceGroupTemplate;
  private final List <SPServiceMetadata> m_aServiceMetadata;

  @Nonnull
  public static Path toPath (@Nullable final Path aRel, @Nonnull final String sPath)
  {
    final Path aResult = Paths.get (sPath);
    return (aResult.isAbsolute () || aRel == null ? aResult : aRel.resolve (aResult)).toAbsolutePath ().normalize ();
  }

  @Nonnull
  private static Path _toPath (@Nullable final Path aRel,
                               @Nullable final String sPrimePath,
                               @Nonnull final String sAltPath)
  {
    return toPath (aRel, sPrimePath == null ? sAltPath : sPrimePath);
  }

  @Nonnull
  private static String _insert (final String sFilename, final String sFragment)
  {
    final int nPos = sFilename.lastIndexOf ('.');
    if (nPos < 0)
      return sFilename + sFragment;

    return sFilename.substring (0, nPos) + sFragment + sFilename.substring (nPos);
  }

  /*
   * Initializes a task configuration.
   */
  public SPPaths (@Nullable final Path aRel, @Nonnull final SPArgPaths aOrigin) throws ValidationException
  {
    m_aCsvInput = toPath (aRel, aOrigin.getCsvInput ());
    m_aCsvFailOutput = _toPath (aRel, aOrigin.getCsvFailOutput (), _insert (aOrigin.getCsvInput (), ".fail"));
    m_aServiceGroupTemplate = toPath (aRel, aOrigin.getServiceGroupTemplate ());
    m_aServiceMetadata = Collections.unmodifiableList (aOrigin.getServiceMetadata ()
                                                              .stream ()
                                                              .map (meta -> new SPServiceMetadata (aRel, meta))
                                                              .collect (Collectors.toList ()));
    // Validate
    final List <String> aProblems = new ArrayList <> ();
    if (m_aServiceMetadata.isEmpty ())
      aProblems.add ("\"serviceMetadata\" are empty");
    if (!aProblems.isEmpty ())
      throw new ValidationException (aProblems);
  }

  /**
   * Retrieves the path of the CSV input file
   */
  @Nonnull
  public Path getCsvInput ()
  {
    return m_aCsvInput;
  }

  /**
   * Retrieves the path of the CSV fail output file
   */
  @Nonnull
  public Path getCsvFailOutput ()
  {
    return m_aCsvFailOutput;
  }

  /**
   * Retrieves the path of the ServiceGroup template
   */
  @Nonnull
  public Path getServiceGroupTemplate ()
  {
    return m_aServiceGroupTemplate;
  }

  /**
   * Retrieves the path of the ServiceMetadata template
   */
  @Nonnull
  public List <SPServiceMetadata> getServiceMetadata ()
  {
    return m_aServiceMetadata;
  }
}
