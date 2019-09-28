package DataBaseEntity;

public abstract class DataBaseEntity {

    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public abstract void display();

}
