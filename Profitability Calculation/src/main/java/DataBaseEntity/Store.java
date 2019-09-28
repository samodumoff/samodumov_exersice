package DataBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "store")
public class Store extends DataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String name = "";

    private double price = 0.0;

    public Store(){

    }

    public Store( String name, double price ){

        this.name = name;
        this.price = price;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    @Override
    public void display(){
        System.out.println("Product name - " + name + ", price - " + price);
    }

}
