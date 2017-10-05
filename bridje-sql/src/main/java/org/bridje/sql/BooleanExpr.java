/*
 * Copyright 2017 Bridje Framework.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bridje.sql;

public interface BooleanExpr<T, E> extends Expression<T, E>
{
    BooleanExpr<T, E> and(BooleanExpr<T, E> operand);

    BooleanExpr<T, E> or(BooleanExpr<T, E> operand);

    BooleanExpr<T, E> and(T operand);

    BooleanExpr<T, E> or(T operand);

    BooleanExpr<T, E> not();
}
