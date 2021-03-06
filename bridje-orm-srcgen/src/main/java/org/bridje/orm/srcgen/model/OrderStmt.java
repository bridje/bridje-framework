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

package org.bridje.orm.srcgen.model;

import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderStmt
{
    private QueryInf query;

    @XmlElements(
    {
        @XmlElement(name = "asc", type = OrderAscStmt.class),
        @XmlElement(name = "desc", type = OrderDescStmt.class)
    })
    private List<OrderBaseStmt> statements;

    public List<OrderBaseStmt> getStatements()
    {
        return statements;
    }

    public void setStatements(List<OrderBaseStmt> statements)
    {
        this.statements = statements;
    }

    public QueryInf getQuery()
    {
        return query;
    }

    public void setQuery(QueryInf query)
    {
        this.query = query;
    }

    void afterUnmarshal(Unmarshaller u, Object parent)
    {
        query = (QueryInf)parent;
    }

    public OrderStmt clone(QueryInf query)
    {
        OrderStmt stmt = new OrderStmt();
        stmt.query = query;
        stmt.statements = OrderBaseStmt.clone(statements, stmt);
        return stmt;
    }

    public static List<OrderStmt> clone(List<OrderStmt> lst, QueryInf query)
    {
        return lst.stream()
                    .map(f -> f.clone(query))
                    .collect(Collectors.toList());
    }
}
