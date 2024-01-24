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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.smpmate.args.SPArgServiceMetadata;

public final class SPServiceMetadata
{
  private final Path m_aTemplate;
  private final String m_sDocumentIdentifier;
  private final String m_sProcessIdentifier;

  public SPServiceMetadata (@Nullable final Path aRel, @Nonnull final SPArgServiceMetadata aOrigin)
  {
    m_aTemplate = SPPaths.toPath (aRel, aOrigin.getTemplate ());
    m_sDocumentIdentifier = aOrigin.getDocumentIdentifier ();
    m_sProcessIdentifier = aOrigin.getProcessIdentifier ();
  }

  public Path getTemplate ()
  {
    return m_aTemplate;
  }

  public String getDocumentIdentifier ()
  {
    return m_sDocumentIdentifier;
  }

  public String getProcessIdentifier ()
  {
    return m_sProcessIdentifier;
  }
}
