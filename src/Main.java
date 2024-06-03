import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        System.out.println("Welcome to your To-Do List, Jack");
        List incomplete_list = new List();
        List complete_list = new List();

        while(running){
            System.out.println("You're incomplete tasks: ");
            incomplete_list.showTasks();
            System.out.println("\n");
            System.out.println("You're complete tasks: ");
            complete_list.showTasks();

            System.out.println("What would you like to do? (1=Add), (2=Remove), (3=Change completion status), (4=EXIT)");
            while (!scanner.hasNextInt()){
                scanner.next();
                System.out.println("Please enter a number from 1 to 3...");
            }
            int result = scanner.nextInt();
            scanner.nextLine();

            if (result == 1) {
                System.out.println("Enter task description: ");
                String description = scanner.nextLine();

                int priority;
                do {
                    System.out.println("Enter task priority (1-10): ");
                    while (!scanner.hasNextInt()){
                        scanner.next();
                        System.out.println("Please enter a number from 1 to 10...");
                    }
                    priority = scanner.nextInt();
                    scanner.nextLine();
                } while(priority < 1 || priority > 10);

                Task newTask = new Task(description, priority, false);
                incomplete_list.addTask(newTask);
                System.out.println("Task Added Successfully!");
            } else if (result == 2) {
                System.out.println("Please enter the ID for the task you want to remove: ");
                while (!scanner.hasNextInt()){
                    scanner.next();
                    System.out.println("Please enter a number...");
                }
                int remove_task_id = scanner.nextInt();
                scanner.nextLine();

                incomplete_list.removeTask(remove_task_id);
            } else if (result == 3) {
                System.out.println("Which task do you want to change the completion status for? Please enter the ID: ");
                int task_to_change = scanner.nextInt();
                scanner.nextLine();

                Task foundTask = null;
                for (Task task : incomplete_list.getAllTasks()){
                    if(task.getId() == task_to_change){
                        foundTask = task;
                        break;
                    }
                }

                if(foundTask != null){
                    incomplete_list.changeCompletion(task_to_change);
                    complete_list.addTask(foundTask);
                    incomplete_list.removeTask(task_to_change);
                } else {
                    System.out.println(STR."Task with Id: \{task_to_change} not found.");
                }
            }
            else if(result == 4){
                System.out.println("Thanks for using To-Do List!");
                running = false;
            }
        }

        scanner.close();
    }
}