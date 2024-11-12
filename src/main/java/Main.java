import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        // Задача 1: CSV - JSON парсер
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName1 = "data.csv";
        List<Employee> list1 = CSVParser.parseCSV(columnMapping, fileName1);
        String json1 = listToJson(list1);
        if (writeString(json1, "data.json")) {
            System.out.println("File successfully written");
        } else {
            System.out.println("ERROR cannot write file");
        }


        // Задача 2: XML - JSON парсер
        String fileName2 = "data.xml";
        List<Employee> list2 = XMLParser.parseXML(fileName2);
        String json2 = listToJson(list2);
        if (writeString(json2, "data2.json")) {
            System.out.println("File successfully written");
        } else {
            System.out.println("ERROR cannot write file");
        }


        // Задача 3: JSON парсер (со звездочкой *)
        String json3 = readString("new_data.json");
        List<Employee> list3 = jsonToList(json3);
        list3.stream().forEach(System.out::println);
    }

    private static boolean writeString(String json, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(json);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static String listToJson(List<Employee> list) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        return gson.toJson(list, listType);
    }

    private static List<Employee> jsonToList(String json) {
        List<Employee> list = new ArrayList<>();
        JsonArray arr = JsonParser.parseString(json).getAsJsonArray();
        Gson gson = new GsonBuilder().create();
        for (var e : arr) {
            var emp = gson.fromJson(e, Employee.class);
            list.add(emp);
        }
        return list;
    }

    private static String readString(String fileName) {
        String res;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            res = br.lines().collect(Collectors.joining());
        } catch (IOException e) {
            return null;
        }
        return res;
    }
}
