/*
 * Copyright 2019 Shinya Mochida
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example;

public final class Utils {

    private Utils() {}

    private static final Result<String, Void> right = Result.right(null);

    public static <T> Validation<String> notNull(T object, String errorMessage) {
        return () -> {
            if (object == null) {
                return Result.left(errorMessage);
            }
            return right;
        };
    }

    public static Validation<String> not0(long value, String errorMessage) {
        return () -> {
            if (value == 0) {
                return Result.left(errorMessage);
            }
            return right;
        };
    }

    public static Validation<String> notEmpty(String value, String errorMessage) {
        return () -> notNull(value, errorMessage).validate().flatMap(v -> {
            if (value.isEmpty()) {
                return Result.left(errorMessage);
            }
            return right;
        });
    }
}
