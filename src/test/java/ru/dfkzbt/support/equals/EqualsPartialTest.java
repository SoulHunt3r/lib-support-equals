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

package ru.dfkzbt.support.equals;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dfkzbt.support.equals.matchers.CustomMatcherEqualsPartialReverseOrder;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Generic description
 *
 * @author Fedorov Konstantin (k.fedorov@axitech.ru)
 * @version 0.1.0 [MAJOR.MINOR.PATCH]
 * Created on 09.11.2021.
 */
public class EqualsPartialTest {
    private final static Logger logger = LoggerFactory.getLogger(EqualsPartialTest.class);

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();


    @Test
    public void equalsPartial() {
        SampleObject expected = new SampleObject(null, "text");
        SampleObject actual = new SampleObject("some", "text");

        assertNotEquals("preset", expected, actual);

        //
        assertTrue("partial equality", expected.equalsPartial(actual));
    }

    @Test
    public void equalsPartialComposition() {
        SampleCompositionObject expected = new SampleCompositionObject(new SampleObject(null, "text"), new SampleObject("some", null));
        SampleCompositionObject actual = new SampleCompositionObject(new SampleObject("some", "text"), new SampleObject("some", "text"));

        assertNotEquals("preset", expected, actual);

        //
        assertTrue("partial equality", expected.equalsPartial(actual));
    }

    @Test
    public void equalsPartialCustomMatcher() {
        SampleObject expected = new SampleObject(null, "text");
        SampleObject actual = new SampleObject("some", "text");

        assertNotEquals("preset", expected, actual);

        //
        errorCollector.checkThat(expected, CustomMatcherEqualsPartialReverseOrder.equalsPartial(actual));
    }

    @Test
    public void equalsPartialCustomMatcherFail() {
        SampleObject expected = new SampleObject(null, "text");
        SampleObject actual = new SampleObject("some", "text");

        assertNotEquals("preset", expected, actual);

        //
        errorCollector.checkThat(actual, not(CustomMatcherEqualsPartialReverseOrder.equalsPartial(expected)));
    }

    /**
     * проверка что для expected == null можно создать строку описания проблемы
     */
    @Test
    public void equalsPartialCustomMatcherNull() {
        Matcher<EqualsPartial> matcher = CustomMatcherEqualsPartialReverseOrder.equalsPartial(null);
        matcher.describeTo(new Description.NullDescription());
    }

    /**
     * проверка что для expected == null можно создать строку описания проблемы
     */
    @Test
    @Ignore("require manual sample run")
    public void equalsPartialCustomMatcherNullManual() {
        SampleObject expected = null;
        SampleObject actual = new SampleObject("some", "text");

        assertNotEquals("preset", expected, actual);

        //
        errorCollector.checkThat(actual, CustomMatcherEqualsPartialReverseOrder.equalsPartial(expected));
    }
}

class SampleObject implements EqualsPartial {
    String a;
    String b;

    public SampleObject(String a, String b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "SampleObject{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleObject)) return false;
        SampleObject that = (SampleObject) o;
        return Objects.equals(a, that.a) && Objects.equals(b, that.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}

class SampleCompositionObject implements EqualsPartial{
    SampleObject objA;
    SampleObject objB;

    public SampleCompositionObject(SampleObject objA, SampleObject objB) {
        this.objA = objA;
        this.objB = objB;
    }

    @Override
    public String toString() {
        return "SampleCompositionObject{" +
                "objA=" + objA +
                ", objB=" + objB +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleCompositionObject)) return false;
        SampleCompositionObject that = (SampleCompositionObject) o;
        return Objects.equals(getObjA(), that.getObjA()) && Objects.equals(getObjB(), that.getObjB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObjA(), getObjB());
    }

    public SampleObject getObjA() {
        return objA;
    }

    public SampleObject getObjB() {
        return objB;
    }
}