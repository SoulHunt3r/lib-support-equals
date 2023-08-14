/*
 *    Copyright 2021 Konstantin Fedorov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ru.dfkzbt.support.equals.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import ru.dfkzbt.support.equals.EqualsPartial;

/**
 * errorCollector.checkThat(expected, CustomMatcherEqualsPartialReverseOrder.equalsPartial(actual));
 * <p>
 * deprecation candidate
 * all use it
 * deprecate
 * all use normal one - errorCollector.checkThat(actual, CustomMatcherEqualsPartialReverseOrder.equalsPartial(expected));
 *
 * @param <T>
 */
public class CustomMatcherEqualsPartialReverseOrder<T extends EqualsPartial> extends TypeSafeMatcher<T> {
    private T match;

    public CustomMatcherEqualsPartialReverseOrder(T match) {
        this.match = match;
    }

    public static <T extends EqualsPartial> Matcher<T> equalsPartial(T match) {
        return new CustomMatcherEqualsPartialReverseOrder<>(match);
    }

    @Override
    protected boolean matchesSafely(T t) {
        return t.equalsPartial(match);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("reverse order compare to: " + (match != null ? match.toString() : "null"));
    }
}
