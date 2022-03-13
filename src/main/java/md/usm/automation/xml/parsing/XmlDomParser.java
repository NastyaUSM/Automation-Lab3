package md.usm.automation.xml.parsing;

import md.usm.automation.student.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XmlDomParser {

    public static List<Student> parse(String fileName) {
        List<Student> students = new ArrayList<>();
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName)) {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("student");

            for (int i = 0; i < list.getLength(); i++){
                Node studentNode = list.item(i);

                if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) studentNode;

                    int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                    String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();

                    String[] date = element.getElementsByTagName("dateOfBirth").item(0).getTextContent().split("\\.");
                    LocalDate dateOfBirth = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));

                    String groupName = element.getElementsByTagName("groupName").item(0).getTextContent();
                    String facultyName = element.getElementsByTagName("facultyName").item(0).getTextContent();

                    Student student = new Student();
                    student.setId(id);
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setDateOfBirth(dateOfBirth);
                    student.setGroupName(groupName);
                    student.setFacultyName(facultyName);

                    students.add(student);
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to parse file " + fileName);
            e.printStackTrace();
        }

        return students;
    }
}
