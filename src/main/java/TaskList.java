import java.util.ArrayList;

public class TaskList {

    public ArrayList<Task> tasks;

    TaskList() {
        this.tasks = new ArrayList<>();
    }

    TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void displayList() {
        String returnString = "";
        for (int i = 0; i < tasks.size(); i++) {
            returnString = returnString + (i + 1) + ". [" + tasks.get(i).symbol() + "][" + tasks.get(i).getStatusIcon()
                    + "] " + tasks.get(i).displayTime() + "\n";
        }
        System.out.println(returnString);
    }

    public void addToList(String[] stringToAdd) throws DukeException {
        if (stringToAdd.length < 2) {
            throw new DukeException("OOPS!! The description of a " + stringToAdd[0] + " cannot be empty.");
        } else {
            Task task;
            String returnString = "";
            boolean containsBy = false;
            for (int i = 1; i < stringToAdd.length; i++) {
                if (stringToAdd[i].equals("by")) {
                    containsBy = true;
                    break;
                } else {
                    returnString = returnString + stringToAdd[i] + " ";
                }
            }
            if (stringToAdd[0].equals("todo")) {
                if (containsBy) {
                    throw new DukeException("Todo cannot have a due date. Create an deadline or event instead :)");
                } else {
                    task = new Todo(returnString);
                    System.out.println(task);
                }
            } else if (stringToAdd[0].equals("deadline")) {
                if (!containsBy) {
                    throw new DukeException("A deadline needs a due date. Create a todo instead.");
                } else {
                    task = new Deadline(returnString, stringToAdd[stringToAdd.length - 1]);
                    System.out.println(task);
                }
            } else {
                if (!containsBy) {
                    throw new DukeException("An event needs a due date. Create a todo instead.");
                } else {
                    task = new Event(returnString, stringToAdd[stringToAdd.length - 1]);
                    System.out.println(task);
                }
            }
            tasks.add(task);
            System.out.println("Got it!! :D I've added this task:\n" + " [" + task.symbol() + "][] " + returnString +
                    "\nNow you have " + tasks.size() + " tasks in the list.");
        }
    }

    public void mark(String number) {
        int num = Integer.parseInt(number);
        tasks.get(num - 1).setAsDone();
        Task temp = tasks.get(num - 1);
        System.out.println("Nice! :P I've marked this task as done:\n [" + temp.symbol() + "][" +
                temp.getStatusIcon() + "] " + temp);
    }

    public void unmark(String number) {
        int num = Integer.parseInt(number);
        tasks.get(num - 1).setAsNotDone();
        Task temp = tasks.get(num - 1);
        System.out.println("OK ._. , I've marked this task as not done yet:\n [" + temp.symbol() + "][" +
                temp.getStatusIcon() + "] " + temp);
    }

    public void delete(String number) {
        int num = Integer.parseInt(number);
        Task temp = tasks.get(num - 1);
        System.out.println("Noted. I've removed this task:\n [" + temp.symbol() + "][" +
                temp.getStatusIcon() + "] " + temp +
                "\nNow you have " + (tasks.size() - 1) + " tasks left in this list");
        tasks.remove(num - 1);
    }

}
