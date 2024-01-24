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

import java.util.EnumSet;

import javax.annotation.Nullable;

/**
 * Represents a task configuration from json input point of view.
 *
 * @author Philip Helger
 * @see com.helger.smpmate.config.SPTask
 */
public final class SPArgTask
{
  private static final EnumSet <ESPArgOption> EMPTY_OPTIONS = EnumSet.noneOf (ESPArgOption.class);
  private final SPArgPaths m_aPaths;
  private final SPArgSMP m_aSmp;
  private final SPArgProxy m_aProxy;
  private final EnumSet <ESPArgOption> m_aOptions;

  /**
   * Initializes a new instance by its properties.
   */
  public SPArgTask (final SPArgPaths aPaths,
                    final SPArgSMP aSmp,
                    @Nullable final SPArgProxy aProxy,
                    @Nullable final EnumSet <ESPArgOption> aOptions)
  {
    m_aPaths = aPaths;
    m_aSmp = aSmp;
    m_aProxy = aProxy;
    m_aOptions = aOptions != null && !aOptions.isEmpty () ? EnumSet.copyOf (aOptions) : EMPTY_OPTIONS;
  }

  /**
   * Retrieves the paths for this task
   */
  public SPArgPaths getPaths ()
  {
    return m_aPaths;
  }

  /**
   * Retrieves the SMP configuration for this task
   */
  public SPArgSMP getSmp ()
  {
    return m_aSmp;
  }

  /**
   * Retrieves the proxy configuration for this task or {@code null}
   */
  @Nullable
  public SPArgProxy getProxy ()
  {
    return m_aProxy;
  }

  /**
   * Retrieves the options for this task. Never <code>null</code>.
   */
  @Nullable
  public EnumSet <ESPArgOption> getOptions ()
  {
    return m_aOptions;
  }
}
