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

public class XMLParser {
    public static List<Employee> parseXML(String s) {
        List<Employee> employees = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        NodeList nodeList;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(s));
            Node root = doc.getDocumentElement();
            nodeList = root.getChildNodes();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            // return empty list in case any error occurs
            return employees;
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Employee emp = getEmployeeFromElement((Element) node);
                if (emp != null) {
                    employees.add(emp);
                }
            }
        }

        return employees;
    }

    private static Employee getEmployeeFromElement(Element e) {
        long id;
        String firstName;
        String lastName;
        String country;
        int age;

        try {
            id = Long.parseLong(e.getElementsByTagName("id").item(0).getTextContent());
            firstName = e.getElementsByTagName("firstName").item(0).getTextContent();
            lastName = e.getElementsByTagName("lastName").item(0).getTextContent();
            country = e.getElementsByTagName("country").item(0).getTextContent();
            age = Integer.parseInt(e.getElementsByTagName("age").item(0).getTextContent());
        } catch (NullPointerException | NumberFormatException ex) {
            // quick-and-dirty validation
            return null;
        }

        return new Employee(id, firstName, lastName, country, age);
    }
}
