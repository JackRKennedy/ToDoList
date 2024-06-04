public class Task {
    private int id;
    private String description;
    private int priority;
    private boolean isCompleted;

    // Constructor without id (for creating new tasks)
    public Task(String description, int priority, boolean isCompleted) {
        this.setDescription(description);
        this.setPriority(priority);
        this.isCompleted = isCompleted;
    }

    // Constructor with id (for tasks retrieved from the database)
    public Task(int id, String description, int priority, boolean isCompleted) {
        this.setId(id);
        this.setDescription(description);
        this.setPriority(priority);
        this.isCompleted = isCompleted;
    }

    // Setters and Getters
    public void setDescription(String description) {
        if (description != null && !description.isEmpty()) {
            this.description = description;
        }
    }

    public void setPriority(int priority) {
        if(priority >= 0 && priority <= 10) {
            this.priority = priority;
        } else {
            System.out.println("Priority must be between 0 and 10.");
        }
    }

    public void toggleCompleted() {
        this.isCompleted = !this.isCompleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean getIsCompleted() {
        return this.isCompleted;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id + " | " + this.description + " | " + (this.isCompleted ? "[X]" : "[]");
    }
}