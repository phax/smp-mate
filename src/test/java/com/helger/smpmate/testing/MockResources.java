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
package com.helger.smpmate.testing;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.annotation.Nonnull;

public final class MockResources
{
  private final Class <?> m_aRelClass;

  private MockResources (@Nonnull final Class <?> aRelClass)
  {
    m_aRelClass = aRelClass;
  }

  @Nonnull
  public MockResources copy (@Nonnull final String sResourcePath, @Nonnull final Path aTargetPath) throws IOException
  {
    Files.createDirectories (aTargetPath.getParent ());
    try (final InputStream aIS = m_aRelClass.getResourceAsStream (sResourcePath))
    {
      Files.copy (aIS, aTargetPath, StandardCopyOption.REPLACE_EXISTING);
    }
    return this;
  }

  @Nonnull
  public static MockResources using (@Nonnull final Class <?> relClass)
  {
    return new MockResources (relClass);
  }
}
