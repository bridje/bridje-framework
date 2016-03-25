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
package org.bridje.orm.impl.dialects;

import org.bridje.orm.dialects.SQLDialect;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.bridje.ioc.Component;
import org.bridje.orm.dialects.ColumnData;
import org.bridje.orm.dialects.TableData;
import org.bridje.orm.impl.sql.DDLBuilder;

/**
 *
 */
@Component
class MySQLDialect implements SQLDialect
{
    private static final Logger LOG = Logger.getLogger(MySQLDialect.class.getName());

    @Override
    public boolean canHandle(DataSource dataSource)
    {
        try(Connection conn = dataSource.getConnection())
        {
            return conn.getMetaData().getDriverName().contains("MySQL");
        }
        catch (SQLException ex)
        {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }


    @Override
    public <T> String createTable(TableData table)
    {
        DDLBuilder b = new DDLBuilder("`");
        b.createTable(table.getTableName());
        table.getColumns().stream()
                .map((f) -> buildColumnStmt(f, b))
                .forEach(b::column);
        b.primaryKey(table.getKeyColumn().getColumnName());
        return b.toString();
    }

    @Override
    public <T> String createColumn(ColumnData column)
    {
        DDLBuilder b = new DDLBuilder("`");
        b.alterTable(column.getTableData().getTableName())
                .addColumn(buildColumnStmt(column, b));
        
        return b.toString();
    }

    @Override
    public <T> String createIndex(ColumnData column)
    {
        DDLBuilder b = new DDLBuilder("`");
        return b.createIndex(column.getTableData().getTableName(), column.getColumnName());
    }

    public String buildColumnStmt(ColumnData column, DDLBuilder b)
    {
        return b.buildColumnStmt(column.getColumnName(), 
                column.getSqlType().getName(), 
                column.getLength(), 
                column.getPrecision(), 
                column.isKey(), 
                column.isAutoIncrement(), 
                column.getDefaultValue());
    }
}
