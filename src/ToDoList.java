import java.util.ArrayList;
public class ToDoList {

    ArrayList<Task> taskList = new ArrayList<>();

    public void addTask(Task task) {
        if (task != null) {
            this.taskList.add(task);
        } else {
            throw new IllegalArgumentException("Task to add cannot be null");
        }
    }

    public void removeTask(int ID){
        Task taskToRemove = findTask(ID);
        if (taskToRemove != null) {
            taskList.remove(taskToRemove);
        } else {
            throw new IllegalArgumentException(STR."Task with ID \{ID} does not exist");
        }
    }

    private Task findTask(int ID) {
        for (Task task : taskList) {
            if (task.getId() == ID) {
                return task;
            }
        }
        return null;
    }

    public void toggleTaskCompleted(int ID) {
        Task taskToToggleCompleted = findTask(ID);
        if (taskToToggleCompleted != null) {
            taskToToggleCompleted.toggleCompleted();
        } else {
            throw new IllegalArgumentException(STR."Task with ID \{ID} does not exist");
        }
    }

    public void showTasks(){
        for (Task task : taskList) {
            System.out.println(task);
        }
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<Task>(this.taskList);
    }
}