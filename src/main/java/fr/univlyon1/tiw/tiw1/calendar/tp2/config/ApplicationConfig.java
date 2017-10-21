package fr.univlyon1.tiw.tiw1.calendar.tp2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/17.
 */
public class ApplicationConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);
    private Document document;

    public ApplicationConfig() throws IOException, SAXException, ParserConfigurationException {

        try {
            File xmlFile = new File(this.getClass().getResource("/application-config.xml").getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.document = dBuilder.parse(xmlFile);
            this.document.getDocumentElement().normalize();

        } catch (NullPointerException e) {
            LOG.error(e.getMessage());
            throw new IOException(e);
        }
    }


    public List<Class> getAllBusinessComponentsClass() throws ClassNotFoundException {
        List<Class> components = new ArrayList<>();

        NodeList list = document.getElementsByTagName("business");
        NodeList nodesComponent = list.item(0).getChildNodes();
        for (int temp = 0; temp < nodesComponent.getLength(); temp++) {
            Node node = nodesComponent.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String className = element.getElementsByTagName("class-name").item(0).getTextContent();
                components.add(Class.forName(className));
            }
        }

        return components;
    }

    public Class getDAOClass() throws ClassNotFoundException {
        Class classDAO;

        NodeList list = document.getElementsByTagName("persistence");
        Element element = (Element) list.item(0);
        String className = element.getElementsByTagName("class-name").item(0).getTextContent();
        classDAO =  Class.forName(className);

        return classDAO;
    }
}
