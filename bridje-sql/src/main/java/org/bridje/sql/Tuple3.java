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

package org.bridje.sql;

public class Tuple3<T1, T2, T3>
{
    private T1 value1;
    
    private T2 value2;
    
    private T3 value3;

    public Tuple3(T1 value1, T2 value2, T3 value3)
    {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public T1 getValue1()
    {
        return value1;
    }

    public void setValue1(T1 value1)
    {
        this.value1 = value1;
    }

    public T2 getValue2()
    {
        return value2;
    }

    public void setValue2(T2 value2)
    {
        this.value2 = value2;
    }

    public T3 getValue3()
    {
        return value3;
    }

    public void setValue3(T3 value3)
    {
        this.value3 = value3;
    }
}
