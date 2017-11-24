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

package org.bridje.web.view.controls;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * An XmlAdapter for UIInputExpression
 */
@XmlTransient
public class UIInputExpressionAdapter extends XmlAdapter<String, UIInputExpression>
{
    @Override
    public String marshal(UIInputExpression v)
    {
        return v.getExpression();
    }

    @Override
    public UIInputExpression unmarshal(String v)
    {
        return new UIInputExpression(v);
    }
}
