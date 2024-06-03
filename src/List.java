import java.util.ArrayList;
public class List{
    public int length = 0;

    ArrayList<Task> task_list = new ArrayList<>();

    public void addTask(Task task) {
        task.setId(task_list.size() + 1);
        this.task_list.add(task);
    }

    public void removeTask(int ID) {
        boolean isTaskFound = false;
        for (int i = 0; i < task_list.size(); i++) {
            // If the ID is found, remove the task and readjust IDs
            if (task_list.get(i).getId() == ID) {
                task_list.remove(i);
                isTaskFound = true;
                for (; i < task_list.size(); i++) {
                    task_list.get(i).setId(i + 1);
                }
                break;
            }
        }
        if (!isTaskFound) {
            System.out.println("This task does not exist");
        }
    }

    public void changeCompletion(int ID) {
        boolean isTaskFound = false;
        for (Task task : task_list) {
            if (task.getId() == ID) {
                task.setCompleted(); // assuming setCompleted() sets the task status to completed.
                isTaskFound = true;
                break;
            }
        }
        if (!isTaskFound) {
            System.out.println("This task does not exist");
        }
    }

    public void showTasks(){
        for (Task task : task_list) {
            System.out.println(task);
        }
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<Task>(this.task_list);
    }
}