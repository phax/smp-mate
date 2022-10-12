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
package com.helger.smpmate.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.concurrent.Immutable;

/**
 * Contains the version number details
 *
 * @author Philip Helger
 */
@Immutable
public final class SMPMateVersion
{
  /** Current version - from properties file */
  public static final String BUILD_VERSION;
  /** Build timestamp - from properties file */
  public static final String BUILD_TIMESTAMP;

  static
  {
    String sProjectVersion = null;
    String sProjectTimestamp = null;
    final String sFilename = "smp-mate-version.properties";
    final Properties p = new Properties ();
    try (final InputStream aIS = SMPMateVersion.class.getClassLoader ().getResourceAsStream (sFilename))
    {
      p.load (aIS);
    }
    catch (final IOException ex)
    {
      throw new IllegalStateException ("Failed to load version properties file");
    }
    sProjectVersion = p.getProperty ("version");
    if (sProjectVersion == null)
      sProjectVersion = "undefined";
    BUILD_VERSION = sProjectVersion;

    sProjectTimestamp = p.getProperty ("timestamp");
    if (sProjectTimestamp == null)
      sProjectTimestamp = "undefined";
    BUILD_TIMESTAMP = sProjectTimestamp;
  }

  private SMPMateVersion ()
  {}
}
