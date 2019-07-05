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

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Result<E, T> {

    boolean isRight();

    <R> Result<E, R> map(Function<? super T, ? extends R> mapping);

    <R> Result<E, R> flatMap(Function<? super T, ? extends Result<E, R>> mapping); 

    <F> Result<F, T> errorMap(Function<? super E, ? extends F> mapping);

    T rescue(Function<? super E, ? extends T> errorMapping);

    Result<E, T> doOnLeft(Consumer<? super E> onLeftAction);

    Result<E, T> doOnRight(Consumer<? super T> onRightAction);

    static <E, T> Result<E, T> right(T t) {
        return new Result<>() {
            @Override
            public boolean isRight() {
                return true;
            }

            @Override
            public <R> Result<E, R> map(Function<? super T, ? extends R> mapping) {
                Function<? super T, ? extends R> fun = Objects.requireNonNull(mapping, "mapping function is null");
                R result = fun.apply(t);
                return Result.right(result);
            }

            @Override
            public <F> Result<F, T> errorMap(Function<? super E, ? extends F> mapping) {
                Objects.requireNonNull(mapping, "mapping function is null");
                return Result.right(t);
            }

            @Override
            public T rescue(Function<? super E, ? extends T> errorMapping) {
                Objects.requireNonNull(errorMapping, "mapping function is null");
                return t;
            }

            @Override
            public <R> Result<E, R> flatMap(Function<? super T, ? extends Result<E, R>> mapping) {
                Function<? super T, ? extends Result<E, R>> fun = Objects.requireNonNull(mapping, "mapping function is null");
                return fun.apply(t);
            }

            @Override
            public Result<E, T> doOnLeft(Consumer<? super E> onLeftAction) {
                Objects.requireNonNull(onLeftAction, "action is null");
                return this;
            }

            @Override
            public Result<E, T> doOnRight(Consumer<? super T> onRightAction) {
                Objects.requireNonNull(onRightAction, "action is null").accept(t);
                return this;
            }

            @Override
            public String toString() {
                return "right[" + t.toString() + "]";
            }
        };
    }

    static <E, T> Result<E, T> left(E e) {
        return new Result<>() {
            @Override
            public boolean isRight() {
                return false;
            }

            @Override
            public <R> Result<E, R> map(Function<? super T, ? extends R> mapping) {
                Objects.requireNonNull(mapping, "mapping function is null");
                return Result.left(e);
            }

            @Override
            public <F> Result<F, T> errorMap(Function<? super E, ? extends F> mapping) {
                Function<? super E, ? extends F> fun = Objects.requireNonNull(mapping, "mapping function is null");
                F result = fun.apply(e);
                return Result.left(result);
            }

            @Override
            public T rescue(Function<? super E, ? extends T> errorMapping) {
                Function<? super E, ? extends T> fun = Objects.requireNonNull(errorMapping, "mapping function is null");
                return fun.apply(e);
            }

            @Override
            public <R> Result<E, R> flatMap(Function<? super T, ? extends Result<E, R>> mapping) {
                Objects.requireNonNull(mapping, "mapping function is null");
                return Result.left(e);
            }

            @Override
            public Result<E, T> doOnLeft(Consumer<? super E> onLeftAction) {
                Objects.requireNonNull(onLeftAction, "action is null").accept(e);
                return this;
            }

            @Override
            public Result<E, T> doOnRight(Consumer<? super T> onRightAction) {
                Objects.requireNonNull(onRightAction, "action is null");
                return this;
            }

            @Override
            public String toString() {
                return "left[" + e.toString() + "]";
            }
        };
    }
}
