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

package org.bridje.sql.impl;

import org.bridje.sql.Column;
import org.bridje.sql.Index;
import org.bridje.sql.Table;

class IndexImpl implements Index
{
    private String name;

    private Table table;

    private final Column<?, ?>[] columns;

    private final boolean unique;

    private final boolean mustRemove;

    public IndexImpl(String name, Table table, Column<?, ?>[] columns,
                     boolean unique, boolean mustRemove)
    {
        this.name = name;
        this.table = table;
        this.columns = columns;
        this.unique = unique;
        this.mustRemove = mustRemove;
    }
    
    @Override
    public String getName()
    {
        if(name == null) name = createName();
        return name;
    }

    @Override
    public Table getTable()
    {
        return table;
    }

    void setTable(Table table)
    {
        this.table = table;
    }

    @Override
    public Column<?, ?>[] getColumns()
    {
        return columns;
    }

    @Override
    public boolean isUnique()
    {
        return unique;
    }

    @Override
    public boolean mustRemove()
    {
        return mustRemove;
    }

    private String createName()
    {
        if(table == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("idx_");
        sb.append(table.getName());
        for (Column<?, ?> column : columns)
        {
            sb.append("_");
            sb.append(column.getName());
        }
        return sb.toString();
    }
}
