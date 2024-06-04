import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseManager dbManager;
        try {
            dbManager = new DatabaseManager(); // DatabaseManager() could throw SQLException
        } catch (SQLException ex) {
            ex.printStackTrace();
            return; // If we can't even establish a DBManager, we cannot further continue with our program.
        }

        Scanner scanner = new Scanner(System.in);
        String listName;
        System.out.println("Welcome to your To-Do List, Jack.");

        while (true) {
            System.out.println("Do you want to (1=Select a list), (2=Create new list), (3=EXIT):");
            int response = scanner.nextInt();
            scanner.nextLine();

            if (response == 1){
                System.out.println("Enter the name of the list you want to access:");
                listName = scanner.nextLine();
                break;
            } else if (response == 2){
                System.out.println("Enter a name for the new list:");
                listName = scanner.nextLine();
                dbManager.createListTable(listName);
                break;
            } else if (response == 3){
                System.out.println("Thanks for using To-Do List!");
                scanner.close();
            } else {
                System.out.println("Invalid response. Please try again.");
            }
        }

        boolean running = true;
        while(running){
            System.out.println("Your tasks: ");
            ToDoList toDoList = dbManager.findAllTasks(listName);
            toDoList.showTasks();
            System.out.println("\n");

            System.out.println("What would you like to do? (1=Add), (2=Remove), (3=Change completion status), (4=SELECT/CREATE LIST), (5=EXIT)");

            int result = scanner.nextInt();
            scanner.nextLine();

            if (result == 1){
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
                dbManager.addTaskToList(listName, newTask);

            } else if (result == 2){
                System.out.println("Please enter the ID for the task you want to remove: ");
                int remove_task_id = scanner.nextInt();
                scanner.nextLine();
                dbManager.removeTaskFromList(listName, remove_task_id);

            } else if (result == 3){
                System.out.println("Which task do you want to change the completion status for? Please enter the ID: ");
                int task_to_change = scanner.nextInt();
                scanner.nextLine();
                Task targetTask = dbManager.findTaskById(listName, task_to_change);

                if (targetTask != null) {
                    targetTask.toggleCompleted();
                    dbManager.updateTaskInList(listName, targetTask);
                } else {
                    System.out.println("Task not found.");
                }
            } else if (result == 4){
                System.out.println("Do you want to (1=Select a list), (2=Create new list), (3=EXIT):");
                int newResponse = scanner.nextInt();
                scanner.nextLine();

                if (newResponse == 1){
                    System.out.println("Enter the name of the list you want to access:");
                    listName = scanner.nextLine();
                } else if (newResponse == 2){
                    System.out.println("Enter a name for the new list:");
                    listName = scanner.nextLine();
                    dbManager.createListTable(listName);
                } else if (newResponse == 3){
                    System.out.println("Thanks for using To-Do List!");
                    scanner.close();
                } else {
                    System.out.println("Invalid response. Please try again.");
                }

            } else if(result == 5){
                System.out.println("Thanks for using To-Do List!");
                running = false;
            }
        }

        scanner.close();

        try {
            dbManager.closeConnection(); // Try to close the connection when finished.
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}