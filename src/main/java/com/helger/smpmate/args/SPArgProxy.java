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
package com.helger.smpmate.args;

import javax.annotation.Nullable;

/**
 * Contains the proxy configuration for a task.
 *
 * @author Philip Helger
 */
public final class SPArgProxy
{
  private final String m_sHost;
  private final String m_sPort;
  private final SPArgAuthority m_aAuthority;

  /**
   * Initializes a new instance by its properties.
   */
  public SPArgProxy (final String sHost, final String sPort, @Nullable final SPArgAuthority aAuthority)
  {
    m_sHost = sHost;
    m_sPort = sPort;
    m_aAuthority = aAuthority;
  }

  /**
   * Retrieves the proxy host name (or ip address)
   */
  public String getHost ()
  {
    return m_sHost;
  }

  /**
   * Retrieves the proxy's port
   */
  public String getPort ()
  {
    return m_sPort;
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
