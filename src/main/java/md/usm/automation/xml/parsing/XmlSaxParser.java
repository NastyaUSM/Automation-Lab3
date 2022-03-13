package md.usm.automation.xml.parsing;

import md.usm.automation.student.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XmlSaxParser {

    public static List<Student> parse(String fileName) {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName)) {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            StudentHandler handler = new StudentHandler();
            saxParser.parse(inputStream, handler);

            return handler.getStudents();

        } catch (Exception e) {
            System.out.println("Failed to parse file " + fileName);
            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    private static class StudentHandler extends DefaultHandler {

        private List<Student> students = new ArrayList<>();
        private Integer counter = 0;
        private String tag = "";

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("student")) {
                students.add(new Student());
            }

            if (qName.equalsIgnoreCase("id") ||
                    qName.equalsIgnoreCase("firstName") ||
                    qName.equalsIgnoreCase("lastName") ||
                    qName.equalsIgnoreCase("dateOfBirth") ||
                    qName.equalsIgnoreCase("groupName") ||
                    qName.equalsIgnoreCase("facultyName")) {
                tag = qName;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (tag.equalsIgnoreCase("id")) {
                int id = Integer.parseInt(new String(ch, start, length));
                students.get(counter).setId(id);
            }

            if (tag.equalsIgnoreCase("firstName")) {
                String firstName = new String(ch, start, length);
                students.get(counter).setFirstName(firstName);
            }

            if (tag.equalsIgnoreCase("lastName")) {
                String lastName = new String(ch, start, length);
                students.get(counter).setLastName(lastName);
            }

            if (tag.equalsIgnoreCase("dateOfBirth")) {
                String[] date = new String(ch, start, length).split("\\.");
                LocalDate dateOfBirth = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
                students.get(counter).setDateOfBirth(dateOfBirth);
            }

            if (tag.equalsIgnoreCase("groupName")) {
                String groupName = new String(ch, start, length);
                students.get(counter).setGroupName(groupName);
            }

            if (tag.equalsIgnoreCase("facultyName")) {
                String facultyName = new String(ch, start, length);
                students.get(counter).setFacultyName(facultyName);
            }

            tag = "";
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("student")) {
                counter++;
            }
        }

        public List<Student> getStudents() {
            return students;
        }
    }
}
