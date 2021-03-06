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

package org.bridje.web.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.bridje.ioc.Component;
import org.bridje.ioc.Priority;

@Component
@Priority(Integer.MAX_VALUE)
class MemorySessionProvider implements WebSessionProvider
{
    private final Map<String, Map<String, String>> sessions;

    public MemorySessionProvider()
    {
        sessions = new ConcurrentHashMap<>();
    }

    @Override
    public String find(String sessionId, String name)
    {
        Map<String, String> session = sessions.get(sessionId);
        if(session != null)
        {
            return session.get(name);
        }
        return null;
    }

    @Override
    public void save(String sessionId, String name, String value)
    {
        Map<String, String> session = sessions.get(sessionId);
        if(session == null)
        {
            session = new ConcurrentHashMap<>();
            sessions.put(sessionId, session);
        }
        if(value == null)
        {
            session.remove(name);
        }
        else
        {
            session.put(name, value);
        }
    }
}
