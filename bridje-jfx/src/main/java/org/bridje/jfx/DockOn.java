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

package org.bridje.jfx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be use to dock a {@link WorkspacePanel} 
 * on a {@link Workspace}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DockOn
{
    /**
     * The main position of the {@link WorkspacePanel} in the {@link Workspace}.
     * 
     * @return One of the {@link Position} values.
     */
    Position position();

    /**
     * The secondary position of the {@link WorkspacePanel} in the main position.
     * 
     * @return One of the {@link Position} values.
     */
    Position subPosition();
}
