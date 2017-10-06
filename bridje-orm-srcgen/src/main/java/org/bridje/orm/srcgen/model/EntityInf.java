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

package org.bridje.orm.srcgen.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class EntityInf
{
    @XmlAttribute
    private String name;

    @XmlTransient
    private ModelInf model;
    
    @XmlElementWrapper(name = "fields")
    @XmlElements(
    {
        @XmlElement(name = "field", type = FieldInf.class)
    })
    private List<FieldInf> fields;

    @XmlElementWrapper(name = "queries")
    @XmlElements(
    {
        @XmlElement(name = "query", type = QueryInf.class)
    })
    private List<QueryInf> queries;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getFullName()
    {
        return model.getPackage() + "." + this.getName();
    }

    public ModelInf getModel()
    {
        return model;
    }

    public void setModel(ModelInf model)
    {
        this.model = model;
    }
    
    public List<FieldInf> getFields()
    {
        return fields;
    }

    public void setFields(List<FieldInf> fields)
    {
        this.fields = fields;
    }

    public List<QueryInf> getQueries()
    {
        return queries;
    }

    public void setQueries(List<QueryInf> queries)
    {
        this.queries = queries;
    }
}
