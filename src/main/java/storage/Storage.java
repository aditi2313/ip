package storage;

import exception.DukeException;
import notes.Note;
import notes.NoteList;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;
import task.TaskList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface that deals with pushing or pulling tasks from the storage.
 */
public class Storage {

    String taskPath;
    String notePath;

    public Storage(String taskPath, String notePath) {
        this.taskPath = taskPath;
        this.notePath = notePath;
    }

    /**
     * Retrieves tasks from the storage (if any) and adds them to a new task list.
     * @return list of tasks retrieved from the storage, else an empty list.
     * @throws DukeException If data file does not exist.
     */
    public ArrayList<Task> setUpTaskData() throws DukeException {
        try {
            FileReader myObj = new FileReader(this.taskPath);
            BufferedReader br = new BufferedReader(myObj);
            String line;
            ArrayList<Task> tasks = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] lineArray = line.split("/");
                Task task;
                switch (lineArray[0]) {
                case "T":
                    task = new Todo(lineArray[2]);
                    break;
                case "E":
                    task = new Event(lineArray[2], lineArray[3]);
                    break;
                default:
                    task = new Deadline(lineArray[2], lineArray[3]);
                }
                if (lineArray[1].equals("X")) {
                    task.setAsDone();
                }
                tasks.add(task);
            }
            return tasks;
        } catch (IOException e) {
            throw new DukeException("File does not exist.");
        }
    }

    /**
     * Retrieves notes from the storage (if any) and adds them to a new note list.
     * @return list of notes retrieved from the storage, else an empty list.
     * @throws DukeException If data file does not exist.
     */
    public ArrayList<Note> setUpNoteData() throws DukeException {
        try {
            FileReader myObj = new FileReader(this.notePath);
            BufferedReader br = new BufferedReader(myObj);
            String line;
            ArrayList<Note> notes = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                Note newNote = new Note(line);
                notes.add(newNote);
            }
            return notes;
        } catch (IOException e) {
            throw new DukeException("File does not exist.");
        }
    }

    /**
     * Adds or removes tasks from storage according to changes made by user.
     * @param taskList updated list of tasks.
     */
    public void updateTaskData(TaskList taskList) {
        try {
            FileWriter taskPath = new FileWriter(this.taskPath);
            taskPath.flush();
            for (int i = 0; i < taskList.tasks.size(); i++) {
                taskPath.write(taskList.tasks.get(i).symbol() + "/" + taskList.tasks.get(i).getStatusIcon() +
                        "/" + taskList.tasks.get(i).toString() + "\n");
            }
            taskPath.close();
        } catch (IOException e) {
            System.out.println("File does not exist.");
        }
    }

    /**
     * Adds or removes notes from storage according to changes made by user.
     * @param noteList updated list of notes.
     */
    public void updateNoteData(NoteList noteList) {
        try {
            FileWriter notePath = new FileWriter(this.notePath);
            notePath.flush();
            for (int i = 0; i < noteList.notes.size(); i++) {
                notePath.write(noteList.notes.get(i).toString() + "\n");
            }
            notePath.close();
        } catch (IOException e) {
            System.out.println("File does not exist.");
        }
    }
}
