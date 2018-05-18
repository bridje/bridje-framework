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

package org.bridje.dql;

public interface DQLCollection
{
    DQLQuery delete(DQLFilter filter);

    DQLQuery distinct(String field, DQLFilter filter);

    DQLQuery find(DQLFilter query, DQLDocument fields);

    DQLQuery findOne(DQLFilter query, DQLDocument fields);

    DQLQuery insertOne(DQLDocument document);

    DQLQuery insertMany(DQLDocument[] documents);

    DQLQuery updateOne(DQLFilter filter, DQLDocument update);

    DQLQuery updateMany(DQLFilter filter, DQLDocument update);

    DQLQuery createIndex(DQLDocument keys);

    DQLQuery drop();

    DQLQuery dropIndex(String index);

    DQLQuery dropIndexes();
}
