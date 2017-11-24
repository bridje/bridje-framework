/*
 * Copyright 2016 Bridje Framework.
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

package org.bridje.orm.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bridje.orm.Column;
import org.bridje.orm.Condition;
import org.bridje.orm.OrderBy;
import org.bridje.orm.OrderByType;
import org.bridje.orm.TableColumn;

/**
 *
 */
abstract class AbstractColumn<T> implements Column<T>
{
    @Override
    public OrderBy asc()
    {
        return new OrderByImpl(OrderByType.ASC, this);
    }

    @Override
    public OrderBy desc()
    {
        return new OrderByImpl(OrderByType.DESC, this);
    }

    @Override
    public Condition eq(T value)
    {
        return new BinaryCondition(this, Operator.EQ, value);
    }

    @Override
    public Condition eq(TableColumn<?, ?> value)
    {
        return new BinaryCondition(this, Operator.EQ, value);
    }
    
    @Override
    public Condition ne(T value)
    {
        return new BinaryCondition(this, Operator.NE, value);
    }

    @Override
    public Condition isNotNull()
    {
        return new UnaryCondition(null, this, "IS NOT NULL");
    }

    @Override
    public Condition isNull()
    {
        return new UnaryCondition(null, this, "IS NULL");
    }

    /**
     * 
     * @param value
     * @return 
     */
    public abstract T readValue(int index, ResultSet rs, EntityContextImpl ctx) throws SQLException;

    /**
     * 
     * @param value
     * @return 
     */
    public abstract T unserialize(Object value);
    
    /**
     * 
     * @param value
     * @return 
     */
    public abstract Object serialize(T value);
}
