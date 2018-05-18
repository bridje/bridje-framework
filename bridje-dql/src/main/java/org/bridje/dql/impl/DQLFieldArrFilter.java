/*
 * Copyright 2018 Bridje Framework.
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

package org.bridje.dql.impl;

import org.bridje.dql.DQLDialect;
import org.bridje.dql.DQLFieldExpr;
import org.bridje.dql.DQLFilter;

class DQLFieldArrFilter implements DQLFilter
{
    private final DQLOperators operator;

    private final DQLFieldExpr field;

    private final Object[] value;

    public DQLFieldArrFilter(DQLOperators operator, DQLFieldExpr field, Object[] value)
    {
        this.operator = operator;
        this.field = field;
        this.value = value;
    }

    public DQLOperators getOperator()
    {
        return operator;
    }

    public DQLFieldExpr getField()
    {
        return field;
    }

    public Object[] getValue()
    {
        return value;
    }

    @Override
    public void writeFilter(StringBuilder sb, DQLDialect dialect)
    {
        dialect.writeFieldArrFilter(sb, operator, field, value);
    }
}
