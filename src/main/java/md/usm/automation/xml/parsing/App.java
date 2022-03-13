package md.usm.automation.xml.parsing;

import md.usm.automation.student.Student;

import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("XML DOM parsing:");
        List<Student> domStudents = XmlDomParser.parse("students.xml");
        System.out.println(domStudents);

        System.out.println("XML SAX parsing:");
        List<Student> saxStudents = XmlSaxParser.parse("students.xml");
        System.out.println(saxStudents);

        System.out.println("XML Xpath parsing:");
        List<Student> xpathStudents = XmlXpathParser.parse("students.xml");
        System.out.println(xpathStudents);
    }
}
