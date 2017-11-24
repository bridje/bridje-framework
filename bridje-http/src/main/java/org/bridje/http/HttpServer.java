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

package org.bridje.http;

import java.io.PrintWriter;
import org.bridje.vfs.VFile;

/**
 * Http server service, you can inject this interface to control HTTP server.
 */
public interface HttpServer
{
    
    /**
     * Default configuration file for the HTTP Server.
     */
    public static VFile CONFIG_FILE = new VFile("/etc/http.xml");

    /**
     * Starts the HTTP server, this method does not wait for the server to start
     * it returns inmediatly. The HTTP server is started in a diferent thread.
     */
    void start();

    /**
     * Stops the HTTP server
     */
    void stop();

    /**
     * Joins the HTTP thread, and waits until the server its shutdown.
     */
    void join();

    /**
     * Prints the full list of HttpBridlets that are in the class path and its
     * priorities.
     *
     * @param writer The writer to print to.
     */
    void printBridlets(PrintWriter writer);

}
