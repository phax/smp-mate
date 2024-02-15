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

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.Test;

import com.helger.smpmate.args.SPArgAuthority;
import com.helger.smpmate.args.SPArgPaths;
import com.helger.smpmate.args.SPArgProxy;
import com.helger.smpmate.args.SPArgSMP;
import com.helger.smpmate.args.SPArgServiceMetadata;
import com.helger.smpmate.args.SPArgTask;

public class SPTaskTest
{
  private static final Path REL_ABSOLUTE = _anyRoot ().resolve ("rel");
  private static final Path REL_RELATIVE = Paths.get ("rel");
  private static final String CSV_INPUT_PATH = "input.csv";
  private static final String CSV_FAIL_OUTPUT_PATH = "output.csv";
  private static final String SERVICE_GROUP_PATH = "ServiceGroup.xml";
  private static final SPArgServiceMetadata SERVICE_METADATA = new SPArgServiceMetadata ("ServiceMetadata.xml",
                                                                                         "anyDocId",
                                                                                         "anyProcId");
  private static final List <SPArgServiceMetadata> SERVICE_META_LIST = Collections.singletonList (SERVICE_METADATA);
  private static final String URL = "url";
  private static final String NAME = "name";
  private static final String PASSWORD = "password";
  private static final String HOST = "host";
  private static final String PORT = "port";

  @Nonnull
  private static Path _anyRoot ()
  {
    // noinspection resource
    return FileSystems.getDefault ().getRootDirectories ().iterator ().next ();
  }

  @Nonnull
  private static SPArgTask _newTask (@Nullable final String csvOutputPath)
  {
    return new SPArgTask (new SPArgPaths (CSV_INPUT_PATH, csvOutputPath, SERVICE_GROUP_PATH, SERVICE_META_LIST),
                          new SPArgSMP (URL, new SPArgAuthority (NAME, PASSWORD)),
                          new SPArgProxy (HOST, PORT, null),
                          null);
  }

  @Test
  public final void getCsvOutputPathRelRelative () throws ValidationException
  {
    final SPTask task = new SPTask (REL_RELATIVE, _newTask (CSV_FAIL_OUTPUT_PATH));
    final Path expected = REL_RELATIVE.resolve (CSV_FAIL_OUTPUT_PATH).toAbsolutePath ().normalize ();
    assertEquals (task.getPaths ().getCsvFailOutput (), expected);
  }

  @Test
  public final void getCsvOutputPathRelAbsolute () throws ValidationException
  {
    final SPTask task = new SPTask (REL_ABSOLUTE, _newTask (CSV_FAIL_OUTPUT_PATH));
    final Path expected = REL_ABSOLUTE.resolve (CSV_FAIL_OUTPUT_PATH);
    assertEquals (task.getPaths ().getCsvFailOutput (), expected);
  }

  @Test
  public final void getCsvOutputPathNull () throws ValidationException
  {
    final SPTask task = new SPTask (REL_RELATIVE, _newTask (null));
    final Path expected = REL_RELATIVE.resolve ("input.fail.csv").toAbsolutePath ().normalize ();
    assertEquals (task.getPaths ().getCsvFailOutput (), expected);
  }
}
