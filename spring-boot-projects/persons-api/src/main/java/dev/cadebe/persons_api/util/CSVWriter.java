package dev.cadebe.persons_api.util;

import dev.cadebe.persons_api.model.Person;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

@NoArgsConstructor
@Slf4j
public class CSVWriter {

    private static final String DELIMITER = FileData.DELIMETER.toString();

    public static void writeCSVFile(List<Person> list) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(FileData.FILE_NAME.toString()));
        } catch (FileNotFoundException e) {
            log.error("Exception '{}' occurred trying to write the revised input file data", e.getMessage());
        }

        StringBuilder builder = new StringBuilder();
        for (Person p : list) {
            builder.append(p.getLastName());
            builder.append(DELIMITER);

            builder.append(p.getFirstName());
            builder.append(DELIMITER);

            builder.append(p.getZipCode());
            builder.append(" ");
            builder.append(p.getCity());
            builder.append(DELIMITER);

            builder.append(p.getColorCode());
            builder.append(DELIMITER);
            builder.append("\n");
        }
        assert writer != null;
        writer.write(builder.toString());
        writer.close();
    }
}
