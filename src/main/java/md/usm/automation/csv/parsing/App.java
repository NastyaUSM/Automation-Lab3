package md.usm.automation.csv.parsing;

import md.usm.automation.student.Student;

import java.util.List;

public class App {

    public static void main(String[] args) {
        //Read by OpenCSV
        System.out.println("Open csv read:");
        List<Student> openCsvStudents = OpenCsvReader.read("students.csv");
        System.out.println(openCsvStudents);


        //Read by apache commons csv
        System.out.println("Apache commons csv read:");
        List<Student> apacheCommonsCsvStudents = ApacheCommonsCsvReader.read("students.csv");
        System.out.println(apacheCommonsCsvStudents);

        System.out.println();

        // Write by OpenCSV
        System.out.println("Printing open csv students to opencsv_students.csv file");
        OpenCsvWriter.write(openCsvStudents, "opencsv_students.csv");

        // Write by apache commons csv
        System.out.println("Printing apache commons csv students to opencsv_students.csv file");
        ApacheCommonsCsvWriter.write(apacheCommonsCsvStudents, "apache_commons_students.csv");
    }
}
