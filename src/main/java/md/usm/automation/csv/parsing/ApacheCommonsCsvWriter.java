package md.usm.automation.csv.parsing;

import md.usm.automation.student.Student;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ApacheCommonsCsvWriter {

    private static final String[] HEADERS = new String[]{"ID", "FIRST_NAME", "LAST_NAME", "DATE_OF_BIRTH", "GROUP_NAME", "FACULTY_NAME"};

    public static void write(List<Student> students, String fileName) {
        Path directory = null;
        try {
            directory = Paths.get(OpenCsvWriter.class.getResource("/").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File file = new File(directory.toFile(), fileName);
        try (FileWriter out = new FileWriter(file);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.Builder.create().setHeader(HEADERS).build())) {

            for (Student student : students){
                printer.printRecord(
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        localDateToString(student.getDateOfBirth()),
                        student.getGroupName(),
                        student.getFacultyName()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String localDateToString(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return localDate.format(formatter);
    }
}
