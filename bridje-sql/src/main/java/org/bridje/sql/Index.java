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

/**
 * Represents an SQL index.
 */
public interface Index
{
    /**
     * The name of the index.
     * 
     * @return The name of the index.
     */
    String getName();

    /**
     * The table that the index belongs to.
     * 
     * @return The table that the index belongs to.
     */
    Table getTable();

    /**
     * The columns of the index.
     * 
     * @return The columns of the index.
     */
    Column<?, ?>[] getColumns();

    /**
     * If this index is unique.
     * 
     * @return true the index is unique, false otherwise.
     */
    boolean isUnique();

    /**
     * If index must be removed.
     *
     * @return true if index must be removed.
     */
    boolean mustRemove();
}
