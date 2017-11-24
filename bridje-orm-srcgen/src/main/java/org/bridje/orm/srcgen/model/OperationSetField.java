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

package org.bridje.orm.srcgen.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * This object represents a set field value for an operation.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationSetField
{
    @XmlAttribute(name = "field")
    private String field;

    @XmlAttribute(name = "value")
    private String value;

    /**
     * The field to set.
     * 
     * @return The field to set.
     */
    public String getField()
    {
        return field;
    }

    /**
     * The field to set.
     * 
     * @param field The field to set.
     */
    public void setField(String field)
    {
        this.field = field;
    }

    /**
     * The value to set
     * 
     * @return The value to set
     */
    public String getValue()
    {
        return value;
    }

    /**
     * The value to set
     * 
     * @param value The value to set
     */
    public void setValue(String value)
    {
        this.value = value;
    }
}
