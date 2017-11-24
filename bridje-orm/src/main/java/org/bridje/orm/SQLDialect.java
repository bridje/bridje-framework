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

package org.bridje.orm;

import javax.sql.DataSource;

/**
 * The interface that every SQLDialect must implement to be supported into the
 * orm.
 */
public interface SQLDialect
{
    /**
     * Determines if this SQL dialect can handle the given DataSource object.
     *
     * @param dataSource The JDBC DataSource to be tested.
     * @return true this dialect can handle the given DataSource, false
     * otherwise.
     */
    boolean canHandle(DataSource dataSource);

    /**
     * Creates the SQL statement needed to create the given table into a
     * database.
     *
     * @param table The data for the table that needs to be created.
     * @return The SQL create statement for the table.
     */
    String createTable(Table<?> table);

    /**
     * Creates the SQL statement needed to create the given column into the
     * database.
     *
     * @param column The data for the column that needs to be created.
     * @return The SQL create statement for the column.
     */
    String createColumn(TableColumn<?, ?> column);

    /**
     * Creates the SQL statement needed to create the an index on the given
     * column into the database.
     *
     * @param column The data for the column whos index needs to be created.
     * @return The SQL create statement for the index.
     */
    String createIndex(TableColumn<?, ?> column);

    /**
     * Get the name of the identifier for the table
     *
     * @param name The name of the object.
     * @return The correct object string to be writen to the sql statement.
     */
    public String identifier(String name);
    
    /**
     * Creates the limit statement for this dialect.
     * 
     * @param index The index of the limit.
     * @param size The size of the limit.
     * @return The limit statement.
     */
    public String limit(int index, int size);

    /**
     * This method will be called by the framework when a value has being readed 
     * from the ResultSet in order to convert it from especific SQL vendor class 
     * to a more standard java class.
     * 
     * @param sqlValue The SQL value readed from the ResultSet.
     * @return The converted value, if necesary, or the same value if not.
     */
    public Object parseSQLValue(Object sqlValue);
}
