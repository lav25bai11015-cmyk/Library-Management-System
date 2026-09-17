package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static void saveData(String fileName, List<String> data) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static List<String> loadData(String fileName) throws IOException {

        List<String> data = new ArrayList<>();

        File file = new File(fileName);

        if (!file.exists()) {
            return data;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        }

        return data;
    }
}
