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

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

public final class CharsetTest
{
  @Test
  public void testBasic ()
  {
    assertEquals ("UTF-8", StandardCharsets.UTF_8.name ());
    assertEquals ("UTF-8", StandardCharsets.UTF_8.displayName ());
    assertEquals ("UTF-8", StandardCharsets.UTF_8.toString ());
  }
}
