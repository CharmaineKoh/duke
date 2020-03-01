package storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import commons.Duke;
import commons.FriendlierSyntax;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.TaskList;
import tasks.Todo;

/**
 * Handles the loading and saving of task lists to the hard disk.
 */
public class Storage {
    private Gson gson;
    private String userDirectory;
    final RuntimeTypeAdapterFactory<Task> typeFactory = RuntimeTypeAdapterFactory
            .of(Task.class, "type")
            .registerSubtype(Todo.class, "todo")
            .registerSubtype(Deadline.class, "deadline")
            .registerSubtype(Event.class, "event");

    /**
     * Initialises a storage object with gson and user directory.
     */
    public Storage() {
        gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();
        userDirectory = System.getProperty("user.dir");
    }

    /**
     * Searches for an existing file with a previous task list and alias store.
     * If no such file found, create a new task list.
     */
    public void readFromFile(Duke duke) {
        try {
            FileReader task = new FileReader(userDirectory + "/data.json");
            FileReader fileReader = new FileReader(userDirectory + "/alias.json");
            duke.setTaskList(gson.fromJson(task, TaskList.class));
            duke.setFriendlierSyntax(gson.fromJson(fileReader, FriendlierSyntax.class));
        } catch (FileNotFoundException e) {
            System.err.println(e);
            duke.setFriendlierSyntax(new FriendlierSyntax());
            duke.setTaskList(new TaskList());
        }
    }

    /**
     * Writes task list and alias into hard drive.
     */
    public void saveFile(TaskList taskList, FriendlierSyntax alias) {
        try {
            FileWriter fileWriter = new FileWriter(userDirectory + "/data.json");
            FileWriter aliasFile = new FileWriter(userDirectory + "/alias.json");
            gson.toJson(taskList, fileWriter);
            gson.toJson(alias, aliasFile);
            fileWriter.flush();
            aliasFile.flush();
            aliasFile.close();
            fileWriter.close();
        } catch (IOException io) {
            System.err.println(io);
        }
    }
}