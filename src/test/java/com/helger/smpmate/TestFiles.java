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
package com.helger.smpmate;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestFiles
{
  public static final String SAMPLE_TASK_RESOURCE = "/sample.task.json";
  public static final String SAMPLE_TASK_DRY_RUN_RESOURCE = "/sample.task-dry-run.json";
  public static final String SAMPLE_TASK_FAIL_SMP_RESOURCE = "/sample.task-fail-smp.json";
  public static final String SAMPLE_TASK_NO_OPTIONS_RESOURCE = "/sample.task-no-options.json";
  public static final String SAMPLE_TASK_NO_PROXY_RESOURCE = "/sample.task-no-proxy.json";
  public static final String SAMPLE_TASK_WITH_AUTH_RESOURCE = "/sample.task-with-auth.json";

  public static final Path TEST_PATH = Paths.get ("target", "testing", TestFiles.class.getCanonicalName ())
                                            .toAbsolutePath ()
                                            .normalize ();
  public static final Path SAMPLE_TASK_PATH = TEST_PATH.resolve ("task1.json");
  public static final Path SAMPLE_TASK_DRY_RUN_PATH = TEST_PATH.resolve ("task2.json");
  public static final Path SAMPLE_TASK_FAIL_SMP_PATH = TEST_PATH.resolve ("task3.json");
  public static final Path SAMPLE_TASK_NO_OPTIONS_PATH = TEST_PATH.resolve ("task4.json");
  public static final Path SAMPLE_TASK_NO_PROXY_PATH = TEST_PATH.resolve ("task5.json");
  public static final Path SAMPLE_TASK_WITH_AUTH_PATH = TEST_PATH.resolve ("task6.json");

  private TestFiles ()
  {}
}
