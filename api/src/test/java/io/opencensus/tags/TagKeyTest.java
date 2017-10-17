/*
 * Copyright 2017, OpenCensus Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opencensus.tags;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.testing.EqualsTester;
import java.util.Arrays;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for {@link TagKey}. */
@RunWith(JUnit4.class)
public final class TagKeyTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void testGetName() {
    assertThat(TagKey.create("foo").getName()).isEqualTo("foo");
  }

  @Test
  public void create_AllowTagKeyNameWithMaxLength() {
    char[] key = new char[TagKey.MAX_LENGTH];
    Arrays.fill(key, 'k');
    TagKey.create(new String(key));
  }

  @Test
  public void create_DisallowTagKeyNameOverMaxLength() {
    char[] key = new char[TagKey.MAX_LENGTH + 1];
    Arrays.fill(key, 'k');
    thrown.expect(IllegalArgumentException.class);
    TagKey.create(new String(key));
  }

  @Test
  public void create_DisallowUnprintableChars() {
    thrown.expect(IllegalArgumentException.class);
    TagKey.create("\2ab\3cd");
  }

  @Test
  public void testTagKeyEquals() {
    new EqualsTester()
        .addEqualityGroup(TagKey.create("foo"), TagKey.create("foo"))
        .addEqualityGroup(TagKey.create("bar"))
        .testEquals();
  }
}
