package DataBaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sale")
public class Sale extends DataBaseEntity {

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

    @Column(name = "profit")
    private double sale_profit = 0.0;

    public Sale(){

    }

    public Sale( String name, int count, double price, Timestamp date ){

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

    public void setProfit(double sale_profit){
        this.sale_profit = sale_profit;
    }

    public double getProfit(){
        return sale_profit;
    }

    @Override
    public void display() {
        System.out.println("Product name - " + name + ", count - " + count + ", price - " + price + ", date - " + date + ", profit - " + sale_profit);
    }

}
