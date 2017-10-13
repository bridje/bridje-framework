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

import java.sql.SQLException;

/**
 * 
 */
public interface SQLResultSet extends AutoCloseable
{
    /**
     * 
     * @return
     * @throws SQLException 
     */
    boolean next() throws SQLException;

    /**
     * 
     * @param <T> The final java type of the expression.
     * @param <E> The result set read java type of the expression.
     * @param expr
     * @return
     * @throws SQLException 
     */
    <T, E> T get(Expression<T, E> expr) throws SQLException;

    /**
     * 
     * @param <T> The final java type of the expression.
     * @param <E> The result set read java type of the expression.
     * @param column
     * @param type
     * @return
     * @throws SQLException 
     */
    <T, E> T get(int column, SQLType<T, E> type) throws SQLException;

    /**
     * 
     * @param <T> The final java type of the expression.
     * @param <E> The result set read java type of the expression.
     * @param expr
     * @param parser
     * @return
     * @throws SQLException 
     */
    <T, E> T get(Expression<T, E> expr, SQLValueParser<T, E> parser) throws SQLException;

    /**
     * 
     * @param <T> The final java type of the expression.
     * @param <E> The result set read java type of the expression.
     * @param column
     * @param type
     * @param parser
     * @return
     * @throws SQLException 
     */
    <T, E> T get(int column, SQLType<T, E> type, SQLValueParser<T, E> parser) throws SQLException;
}
