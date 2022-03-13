package md.usm.automation.csv.parsing;

import com.opencsv.CSVWriter;
import md.usm.automation.student.Student;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OpenCsvWriter {

    private static final String[] HEADERS = new String[]{"ID", "FIRST_NAME", "LAST_NAME", "DATE_OF_BIRTH", "GROUP_NAME", "FACULTY_NAME"};

    public static void write(List<Student> students, String fileName) {
        Path directory = null;
        try {
            directory = Paths.get(OpenCsvWriter.class.getResource("/").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File file = new File(directory.toFile(), fileName);
        try (FileWriter writer = new FileWriter(file);
             CSVWriter csvWriter = new CSVWriter(writer,
                     CSVWriter.DEFAULT_SEPARATOR,
                     CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END)) {

            csvWriter.writeNext(HEADERS);

            for (Student student : students){
                csvWriter.writeNext(new String[]{
                        student.getId() + "",
                        student.getFirstName(),
                        student.getLastName(),
                        localDateToString(student.getDateOfBirth()),
                        student.getGroupName(),
                        student.getFacultyName()
                        });
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
