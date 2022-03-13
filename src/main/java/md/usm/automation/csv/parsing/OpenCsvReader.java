package md.usm.automation.csv.parsing;

import com.opencsv.CSVReader;
import md.usm.automation.student.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpenCsvReader {

    public static List<Student> read(String fileName) {
        List<Student> students = new ArrayList<>();
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader);
             CSVReader csvReader = new CSVReader(reader)) {

            List<String[]> rows = csvReader.readAll();
            // Skipping header row
            for (String[] row : rows.subList(1, rows.size())){

                Student student = new Student();

                student.setId(Integer.parseInt(row[0]));
                student.setFirstName(row[1]);
                student.setLastName(row[2]);

                String[] dateOfBirth = row[3].split("\\.");
                LocalDate date = LocalDate.of(Integer.parseInt(dateOfBirth[2]),
                        Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[0]));
                student.setDateOfBirth(date);

                student.setGroupName(row[4]);
                student.setFacultyName(row[5]);

                students.add(student);
            }

        } catch (Exception e) {
            System.out.println("Cannot read csv file " + fileName);
        }

        return students;
    }

}
