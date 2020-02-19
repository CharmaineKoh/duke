package command;

import factory.DeadlineFactory;
import factory.EventFactory;
import factory.TodoFactory;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.TaskList;
import tasks.Todo;

/**
 * Parses user input and instruct corresponding classes to perform specified tasks.
 */
public class Controller {

    private DeadlineFactory deadlineFactory;
    private EventFactory eventFactory;
    private TodoFactory todoFactory;
    private Parser parser;

    /**
     * Constructor for the controller object which instantiates factory objects to create various tasks.
     */
    public Controller() {
        deadlineFactory = new DeadlineFactory();
        eventFactory = new EventFactory();
        todoFactory = new TodoFactory();
        parser = new Parser();
    }

    /**
     * Parses user input and determine specified instructions to execute.
     *
     * @param input input received from user.
     * @return output to be displayed to user.
     */
    public String readInput(String input, TaskList taskList, FriendlierSyntax friendlierSyntax) throws DukeException {
        input = input.toLowerCase();
        parser.validateInput(input);
        String command = parser.getCommand(input);
        String description = "";
        if (parser.hasDescription(input)) {
            description = parser.getDescription(input);
        }
        try {
            switch (command) {
                case "bye":
                    return UI.BYE;
                case "alias":
                    String[] alias = description.split(" ");
                    friendlierSyntax.addAlias(alias[0], alias[1]);
                    return UI.ALIAS;
                case "list":
                    if (taskList.isEmpty()) {
                        return UI.EMPTY_LIST;
                    }
                    return UI.LIST + taskList.printList();
                case "delete":
                    int deletedTaskNumber = Integer.parseInt(description);
                    Task deletedTask = taskList.getTask(deletedTaskNumber);
                    taskList.deleteTask(deletedTaskNumber);
                    return UI.REMOVE + deletedTask;
                case "clear":
                    taskList.clearAll();
                    return UI.CLEAR;
                case "done":
                    int doneTaskNumber = Integer.parseInt(description);
                    Task taskDone = taskList.getTask(doneTaskNumber);
                    taskList.markAsDone(doneTaskNumber);
                    return UI.DONE + taskDone;
                case "find":
                    String keyword = description;
                    return UI.LIST + taskList.findTaskContainingKeyword(keyword);
                case "tag":
                    String tag = description;
                    return UI.LIST + taskList.findTaskContainingTag(tag);
                case "todo":
                    String[] parse = description.split(" ", 2);
                    Todo newTodo = todoFactory.create(description);
                    taskList.addTask(newTodo);
                    return UI.ADD + "\t\t" + newTodo.toString() + taskList.printTotalTasks();
                case "deadline":
                    Deadline newDeadline = deadlineFactory.create(description);
                    taskList.addTask(newDeadline);
                    return UI.ADD + "\t\t" + newDeadline.toString() + taskList.printTotalTasks();
                case "event":
                    Event newEvent = eventFactory.create(description);
                    taskList.addTask(newEvent);
                    return UI.ADD + "\t\t" + newEvent.toString() + taskList.printTotalTasks();
                default:
                    throw new DukeException("\t☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (DukeException e) {
            System.err.println(e);
            return "Please try again.";
        }
    }
}
