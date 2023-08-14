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

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * Generic description
 *
 * @author Fedorov Konstantin (k.fedorov@axitech.ru)
 * @version 0.1.0 [MAJOR.MINOR.PATCH]
 * Created on 27.01.2021.
 */
public interface EqualsPartial {
    /**
     * Indicates whether some other object is "partially equal to" this one,
     * comparing all not-null fields with dest object.
     *
     * @param dest
     * @return {@code true} if this object is the same as the dest
     * argument; or all not-NULL fields are equal to dest fields
     * {@code false} otherwise.
     */
    default boolean equalsPartial(Object dest) {
        if (this == dest) return true;
        if (dest == null || getClass() != dest.getClass()) return false;
        Field[] fields = dest.getClass().getDeclaredFields();
        boolean result = true;
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object srcValue = field.get(this);
                Object destValue = field.get(dest);

                result = result && equalsX(srcValue, destValue);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return result;
    }

    /**
     * null, X = true
     * NotNull, null = false
     * NotNull, NotNull = equality
     *
     * @param first
     * @param second
     * @return
     */
    default boolean equalsX(Object first, Object second) {
        if (first == null) return true;
        if (second == null) return false;
        if (first instanceof List && second instanceof List) {
            return ((List<?>) first).stream()
                    .map(f -> ((List<?>) second).stream().map(s -> equalsX(f, s)).count())
                    .allMatch(it -> it > 0);
            //return ((List<?>) first).containsAll((List<?>) second) && ((List<?>) second).containsAll((List<?>) first);
        } else if (first instanceof EqualsPartial && second instanceof EqualsPartial) {
            return ((EqualsPartial) first).equalsPartial(second);
        } else {
            return Objects.equals(first, second);
        }
    }

}
