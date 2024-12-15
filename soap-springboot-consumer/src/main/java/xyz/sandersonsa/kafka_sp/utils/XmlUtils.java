package xyz.sandersonsa.kafka_sp.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class XmlUtils {

    private XmlUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getValue(final String payload) {
        StringBuilder result = new StringBuilder();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8));

            Document doc = db.parse(stream);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName(IpvaNode.PARTE_VARIAVEL.getValue());

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList listString = doc.getElementsByTagName(IpvaNode.STRING.getValue());
                    for(int i = 0; i < listString.getLength(); i++){
                        result.append(listString.item(i).getTextContent());
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
