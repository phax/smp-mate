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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.helger.smpmate.args.SPArgPaths;
import com.helger.smpmate.args.SPArgServiceMetadata;

public class SPPathsTest
{
  private static SPArgPaths _origin (final List <SPArgServiceMetadata> metadata)
  {
    return new SPArgPaths ("csvInput.csv", "csvFailOutput.csv", "groupTemplate.xml", metadata);
  }

  private static Path _absolutePath (final String name)
  {
    return Paths.get (name).toAbsolutePath ();
  }

  @Test (expected = ValidationException.class)
  public final void validateEmptyTemplates () throws ValidationException
  {
    new SPPaths (null, _origin (Collections.emptyList ()));
  }

  @Test
  public final void getServiceMetadataTemplates () throws ValidationException
  {
    final String metadataTemplate03Path = _absolutePath ("metadataTemplate03.xml").toString ();
    final SPPaths result = new SPPaths (_absolutePath ("input"),
                                        _origin (Arrays.asList (new SPArgServiceMetadata ("metadataTemplate01.xml",
                                                                                         "",
                                                                                         ""),
                                                               new SPArgServiceMetadata ("metadataTemplate02.xml",
                                                                                         "",
                                                                                         ""),
                                                               new SPArgServiceMetadata (metadataTemplate03Path,
                                                                                         "",
                                                                                         ""))));
    assertEquals (3, result.getServiceMetadata ().size ());
    for (final SPServiceMetadata meta : result.getServiceMetadata ())
    {
      assertTrue (meta.getTemplate ().isAbsolute ());
    }
  }
}
