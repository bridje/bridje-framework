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

package org.bridje.web.view;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import org.bridje.web.view.widgets.Widget;

/**
 * Provides the hability to define 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Defines
{
    @XmlAttribute
    private String name;

    @XmlAnyElement(lax = true)
    private List<Widget> components;
    
    public String getName()
    {
        return name;
    }

    public List<Widget> getComponents()
    {
        if(components == null)
        {
            components = new ArrayList<>();
        }
        return components;
    }
    
    public Widget getComponent()
    {
        if(components != null && components.size() > 0)
        {
            return components.get(0);
        }
        return null;
    }
}
