public class Task {
    private int id;
    private String description;
    private int priority;
    private boolean isCompleted;

    Task(String description, int priority, boolean isCompleted)
    {
        this.setDescription(description);
        this.setPriority(priority);
        this.isCompleted = false;
    }

    public void setDescription(String description){
        if(description != null && !description.isEmpty()){
            this.description = description;
        }
    }

    public void setPriority(int priority){
        if(priority >= 0 && priority <= 10){
            this.priority = priority;
        } else {
            System.out.println("Sorry but you have to set the priority between 1 and 10");
        }
    }

    public void setCompleted(){
        this.isCompleted = !this.isCompleted;
    }

    public void setId(int id){

        this.id = id;
    }

    public String getDescription(){
        return this.description;
    }

    public int getPriority(){
        return this.priority;
    }

    public boolean getIsCompleted(){
        return this.isCompleted;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String toString(){
        return STR."\{this.id} | \{this.description} | \{this.isCompleted ? "[X]" : "[]"}";
    }
}
