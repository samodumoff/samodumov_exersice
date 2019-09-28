package DataBaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "purchase")
public class Purchase extends DataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String name = "";
    @Column(name = "product_count")
    private int count = 0;
    private double price = 0.0;
    @Column(name = "date_purchase")
    private Timestamp date = null;

    public Purchase(){

    }

    public Purchase( String name, int count, double price, Timestamp date ){

        this.name = name;
        this.count = count;
        this.price = price;
        this.date = date;

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCount(int count){
        this.count = count;
    }

    public int getCount(){
        return count;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public void setDate(Timestamp date){
        this.date = date;
    }

    public Timestamp getDate(){
        return date;
    }

    @Override
    public void display(){
        System.out.println("Product name - " + name + ", count - " + count + ", price - " + price + ", date - " + date);
    }

}
