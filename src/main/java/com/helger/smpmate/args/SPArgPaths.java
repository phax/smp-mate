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
package com.helger.smpmate.args;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Contains the paths of a task configuration.
 *
 * @author Philip Helger
 * @see com.helger.smpmate.config.SPPaths
 */
public final class SPArgPaths
{
  private final String m_sCsvInput;
  private final String m_sCsvFailOutput;
  private final String m_sServiceGroupTemplate;
  private final List <SPArgServiceMetadata> m_aServiceMetadata;

  /**
   * Initializes a new instance by its properties.
   */
  public SPArgPaths (final String sCsvInput,
                     final String sCsvFailOutput,
                     final String sServiceGroupTemplate,
                     @Nonnull final List <SPArgServiceMetadata> aServiceMetadata)
  {
    m_sCsvInput = sCsvInput;
    m_sCsvFailOutput = sCsvFailOutput;
    m_sServiceGroupTemplate = sServiceGroupTemplate;
    m_aServiceMetadata = new ArrayList <> (aServiceMetadata);
  }

  /**
   * Retrieves the path of the CSV input file
   */
  public String getCsvInput ()
  {
    return m_sCsvInput;
  }

  /**
   * Retrieves the path of the CSV fail output file or {@code null}
   */
  public String getCsvFailOutput ()
  {
    return m_sCsvFailOutput;
  }

  /**
   * Retrieves the path of the ServiceGroup template
   */
  public String getServiceGroupTemplate ()
  {
    return m_sServiceGroupTemplate;
  }

  /**
   * Retrieves the path of the ServiceMetadata template
   */
  @Nonnull
  public List <SPArgServiceMetadata> getServiceMetadata ()
  {
    return Collections.unmodifiableList (m_aServiceMetadata);
  }
}
