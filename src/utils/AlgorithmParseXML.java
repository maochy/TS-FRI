package utils;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class AlgorithmParseXML
{
    public String[] parseXML() {
        String[] result = null;
        try {
            final File f = new File("resource\\Algorithm.xml");
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(f);
            final NodeList nl = doc.getElementsByTagName("algorithm");
            result = new String[nl.getLength()];
            for (int i = 0; i < nl.getLength(); ++i) {
                final String name = doc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue();
                result[i] = name;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
