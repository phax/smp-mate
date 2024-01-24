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

import javax.annotation.Nullable;

/**
 * Contains the SMP server specific details of a task configuration.
 *
 * @author Philip Helger
 */
public final class SPArgSMP
{
  private final String m_sUrl;
  private final SPArgAuthority m_aAuthority;

  /**
   * Initializes a new instance by its properties.
   */
  public SPArgSMP (final String sUrl, @Nullable final SPArgAuthority aAuthority)
  {
    m_sUrl = sUrl;
    m_aAuthority = aAuthority;
  }

  /**
   * Retrieves the url of the smp server.
   */
  public String getUrl ()
  {
    return m_sUrl;
  }

  /**
   * Retrieves the proxy's authorization parameters - if any - or {@code null}
   */
  @Nullable
  public SPArgAuthority getAuthority ()
  {
    return m_aAuthority;
  }
}
