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

package org.bridje.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.maven.plugin.MojoExecutionException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 */
public class DataFile
{
    private String path;

    private Node[] nodes;

    public String getPath()
    {
        return path;
    }

    public Node[] getNodes()
    {
        return nodes;
    }

    public void generate(GenerateMojo mojo) throws MojoExecutionException
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(mojo.getDataFilesBasePath() + "/" + path));
            for (Node node : nodes)
            {
                node.generate(mojo, doc);
            }
        }
        catch (ParserConfigurationException | SAXException | IOException ex)
        {
            throw new MojoExecutionException(ex.getMessage(), ex);
        }
    }
}