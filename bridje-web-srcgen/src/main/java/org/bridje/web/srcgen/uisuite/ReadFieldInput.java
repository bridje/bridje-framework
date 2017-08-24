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
 * Read the value of the given field from the request.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ReadFieldInput implements ControlFlowAction
{
    @XmlAttribute
    private String fieldName;

    /**
     * Read the value of the given field from the request.
     * 
     * @return Read the value of the given field from the request.
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * Read the value of the given field from the request.
     * 
     * @param fieldName Read the value of the given field from the request.
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }
}
