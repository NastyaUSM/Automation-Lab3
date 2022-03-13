package md.usm.automation.csv.parsing;

import com.opencsv.CSVReader;
import md.usm.automation.student.Student;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApacheCommonsCsvReader {

    private static final String[] HEADERS = new String[]{"ID", "FIRST_NAME", "LAST_NAME", "DATE_OF_BIRTH", "GROUP_NAME", "FACULTY_NAME"};

    public static List<Student> read(String fileName) {
        List<Student> students = new ArrayList<>();
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {

            Iterable<CSVRecord> records = CSVFormat.Builder.create()
                    .setSkipHeaderRecord(true)
                    .setHeader(HEADERS)
                    .build()
                    .parse(reader);

            for (CSVRecord record : records) {
                Student student = new Student();

                student.setId(Integer.parseInt(record.get("ID")));
                student.setFirstName(record.get("FIRST_NAME"));
                student.setLastName(record.get("LAST_NAME"));

                String[] dateOfBirth = record.get("DATE_OF_BIRTH").split("\\.");
                LocalDate date = LocalDate.of(Integer.parseInt(dateOfBirth[2]),
                        Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[0]));
                student.setDateOfBirth(date);

                student.setGroupName(record.get("GROUP_NAME"));
                student.setFacultyName(record.get("FACULTY_NAME"));

                students.add(student);
            }

        } catch (Exception e) {
            System.out.println("Cannot read csv file " + fileName);
        }

        return students;
    }
}
