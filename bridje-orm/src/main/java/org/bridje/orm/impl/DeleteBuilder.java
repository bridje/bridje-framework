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
class DeleteBuilder
{
    private final StringBuilder sb;

    public DeleteBuilder()
    {
        sb = new StringBuilder();
    }

    public DeleteBuilder delete(String fields)
    {
        sb.append("DELETE FROM ");
        sb.append(fields);
        return this;
    }
    
    public DeleteBuilder where(String condition)
    {
        sb.append(" WHERE ");
        sb.append(condition);
        return this;
    }

    @Override
    public String toString()
    {
        sb.append(";");
        return sb.toString();
    }
}