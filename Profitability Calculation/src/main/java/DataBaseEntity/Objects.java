package DataBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "objects")
public class Objects extends DataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String name = "";

    public Objects(){

    }

    public Objects( String name ){

        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }


    public String getName(){
        return name;
    }

    @Override
    public void display() {
        System.out.println( "Product name - " + name);
    }

}
