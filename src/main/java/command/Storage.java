package command;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import tasks.Task;
import tasks.TaskList;

/**
 * Handles the loading and saving of task lists to the hard disk.
 */
public class Storage {
    static Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create();

    static String userDirectory = System.getProperty("user.dir");

    /**
     * Searches for an existing file with a previous task list store.
     * If no such file found, create a new task list.
     */
    public static TaskList readFromFile() {
        try {
            FileReader fileReader = new FileReader(userDirectory + "/data.json");
            return new TaskList(gson.fromJson(fileReader, new TypeToken<List<Task>>() {
            }.getType()));
        } catch (FileNotFoundException e) {
            return new TaskList(new ArrayList<Task>());
        }
    }

    /**
     * Writes task list into hard drive.
     */
    public static void saveFile(TaskList taskList) {
        try {
            FileWriter fileWriter = new FileWriter(userDirectory + "/data.json");
            gson.toJson(taskList.getList(), fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException io) {
            System.err.println(io);
        }
    }
}
