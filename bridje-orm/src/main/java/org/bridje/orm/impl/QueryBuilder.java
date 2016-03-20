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

/**
 *
 */
public class QueryBuilder
{
    private final StringBuilder sb;

    public QueryBuilder()
    {
        sb = new StringBuilder();
    }

    public QueryBuilder select(String fields)
    {
        sb.append("SELECT ");
        sb.append(fields);
        return this;
    }
    
    public QueryBuilder from(String table)
    {
        sb.append(" FROM ");
        sb.append(table);
        return this;
    }
    
    public QueryBuilder where(String condition)
    {
        sb.append(" WHERE ");
        sb.append(condition);
        return this;
    }
    
    public QueryBuilder orderBy(String orderBy)
    {
        sb.append(" ORDER BY ");
        sb.append(orderBy);
        return this;
    }

    public QueryBuilder limit(int index, int size)
    {
        if(index >= 0 && size >= 0)
        {
            sb.append(" LIMIT ");
            sb.append(index);
            sb.append(", ");
            sb.append(size);
        }
        return this;
    }
    
    @Override
    public String toString()
    {
        sb.append(";");
        return sb.toString();
    }
}
