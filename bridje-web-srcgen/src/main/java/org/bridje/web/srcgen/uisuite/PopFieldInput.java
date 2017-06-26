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

package org.bridje.web.srcgen.uisuite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Pops the value of the given field out of the request.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PopFieldInput implements ReadInputAction
{
    @XmlAttribute
    private String fieldName;

    /**
     * The name of the field to pop the value.
     * 
     * @return The name of the field to pop the value.
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * The name of the field to pop the value.
     * 
     * @param fieldName The name of the field to pop the value.
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }
}
