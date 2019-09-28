package Program;

import DataBaseEntity.Objects;
import DataBaseEntity.Purchase;
import DataBaseEntity.Sale;
import DataBaseEntity.Store;
import Hibernate.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Rules {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static Objects validationNewProduct(String[] argumentsArray, Service service){

        if( argumentsArray.length == 2 ){

            IDao dao = new ObjectsDaoImpl();
            List<Objects> products = service.findByName( dao, argumentsArray[1] );

            if( products.size() == 0 ){

                System.out.println("Success");
                Objects product = new Objects(argumentsArray[1]);
                return product;

            }else{

                System.out.println("This product already exists.");
                return null;

            }

        }else{

            System.out.println("Error in numbers arguments in command.");
        }

        return null;
    }

    public static Purchase validationPurchase( String[] argumentsArray, Service service){

        Purchase purchase = null;
        String productName = "";
        Date date = null;
        Timestamp timestampDate = null;

        int countProducts = 0;
        double productPrice = 0.0;

        if( argumentsArray.length == 5 ){

            IDao dao = new ObjectsDaoImpl();
            List<Objects> products = service.findByName( dao, argumentsArray[1] );

            if( products.size() > 0 ){

                productName = argumentsArray[1];

                countProducts = getInt(argumentsArray[2]);
                productPrice = getDouble(argumentsArray[3]);
                date = getDate(argumentsArray[4]);

                if( countProducts > 0 && productPrice >= 0.0 && date != null ){

                    System.out.println("Success validation");

                    timestampDate = new Timestamp(date.getTime());
                    purchase = new Purchase(productName, countProducts, productPrice, timestampDate);

                    return purchase;

                }else{

                    System.out.println("Error validation");

                    return null;
                }

            }else{

                System.out.println("This product does not exist.");
                return null;

            }

        }else{

            System.out.println("Error in numbers arguments in command.");

            return null;

        }

    }
    public static Sale validationDemand(String[] argumentsArray, Service service){

        Sale sale = null;
        String productName = "";
        Date date = null;
        Timestamp timestampDate = null;

        int countProducts = 0;
        double productPrice = 0.0;

        if( argumentsArray.length == 5 ){

            IDao dao = new StoreDaoImpl();
            List<Store> products = service.findByName( dao, argumentsArray[1] );

            if( products.size() > 0 ){

                productName = argumentsArray[1];

                countProducts = getInt(argumentsArray[2]);
                productPrice = getDouble(argumentsArray[3]);
                date = getDate(argumentsArray[4]);

                if( countProducts > 0 && countProducts <= products.size()  && productPrice >= 0.0 && date != null  ){

                    System.out.println("Success validation");

                    timestampDate = new Timestamp(date.getTime());
                    sale = new Sale(productName, countProducts, productPrice, timestampDate);

                    return sale;

                }else{
                    System.out.println("Error validation");
                    return null;
                }

            }else{

                System.out.println("This product does not exist.");
                return null;
            }

        }else{

            System.out.println("Error in numbers arguments in command.");
            return null;

        }

    }

    public static Date validationSalesReport(String[] argumentsArray){

        Date date = null;
        if( argumentsArray.length == 3 ){

            date = getDate(argumentsArray[2]);

            if( date != null ){

                return date;

            }else{
                System.out.println("Error validation");
                return null;

            }

        }else{
            return null;
        }

    }

    public static int getInt(String str){

        int countProducts = 0;

        try{

            countProducts = 0;
            countProducts = Integer.parseInt(str);

        }catch(Exception ex){

            countProducts = -1;

        }finally {

            if( countProducts < 1 ) {

                System.out.println("Error in [3] argument - count products in command.");

                return -1;

            }
        }

        return countProducts;
    }

    public static double getDouble(String str){

        double productPrice = 0.0;

        try{

            productPrice = 0.0;
            productPrice = Double.parseDouble(str);

        }catch(Exception ex){

            productPrice = -1.0;

        }finally {

            if( productPrice <= 0.0 ){

                System.out.println("Error in [4] argument - products price in command.");

                return -1.0;

            }
        }

        return productPrice;

    }

    public static Date getDate(String str){

        Date date = null;

        try{

            date = null;
            date = dateFormat.parse(str);

        }catch( Exception ex ){

            date = null;

        }finally {

            if( date == null ){

                System.out.println("Error in [5] argument - date in command.");

                return null;

            }

        }

        return date;

    }
}
