package Program;

import DataBaseEntity.Objects;
import DataBaseEntity.Purchase;
import DataBaseEntity.Sale;
import DataBaseEntity.Store;
import Hibernate.*;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {

    private static final String NEWPRODUCT = "NEWPRODUCT";
    private static final String PURCHASE = "PURCHASE";
    private static final String DEMAND = "DEMAND";
    private static final String SALESREPORT = "SALESREPORT";

    public static String[] settings = new String[6];

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);

        String input;

        int port = 0;

        while( true ){

            System.out.println("Please, input port:");

            input = in.nextLine();
            port = Rules.getInt(input);

            if( port < 1 ){

                System.out.println("Error number!");

            }else{
                break;
            }
        }

        settings[0] = "org.postgresql.Driver";
        settings[1] = "jdbc:postgresql://localhost:" + String.valueOf(port) + "/samodumov_db";
        settings[2] = "user_samodumov";
        settings[3] = "user_samodumov";
        settings[4] = "org.hibernate.dialect.PostgreSQL10Dialect";
        settings[5] = "false";

        SessionFactory sessionFactory = HibernateSession.getSessionFactory(settings);

        ObjectsDaoImpl.sessionFactory = sessionFactory;
        PurchaseDaoImpl.sessionFactory = sessionFactory;
        SaleDaoImpl.sessionFactory = sessionFactory;
        StoreDaoImpl.sessionFactory = sessionFactory;

        work();

    }

    public static void work(){

        Scanner in = new Scanner(System.in);

        String input;
        String[] argumentsArray;

        double plusMoney = 0.0;
        double minusMoney = 0.0;

        Service service = new Service();
        IDao dao;


        while( true ){

            System.out.println("Please, input command:");

            input = in.nextLine();

            if( input.charAt(input.length() - 1) == ' ' ){
                input = input.substring(0, input.length() - 1);
            }

            if( input != null && !input.equals("") ){

                argumentsArray = input.split(" ");

                if( argumentsArray.length > 0 ){

                    switch( argumentsArray[0] ){

                        case NEWPRODUCT:

                            Objects product = Rules.validationNewProduct(argumentsArray, service);

                            if( product != null ){

                                dao = new ObjectsDaoImpl();
                                service.saveObj(dao, product);

                            }else{
                                continue;
                            }

                            break;

                        case PURCHASE:

                            Purchase purchase = Rules.validationPurchase( argumentsArray, service );

                            if( purchase != null ){

                                dao = new PurchaseDaoImpl();
                                service.saveObj(dao, purchase);

                                dao = new StoreDaoImpl();
                                for( int i = 0; i < purchase.getCount(); i++ ){

                                    Store store = new Store(purchase.getName(), purchase.getPrice());
                                    service.saveObj(dao, store);
                                }

                            }else{

                                continue;

                            }

                            break;

                        case DEMAND:

                            Sale sale = Rules.validationDemand(argumentsArray, service);

                            if( sale != null ){

                                dao = new StoreDaoImpl();
                                List<Store> products = service.findByName(dao, sale.getName());

                                plusMoney = sale.getCount() * sale.getPrice();
                                minusMoney = 0.0;

                                for( int i = 0; i < sale.getCount(); i++ ){

                                    minusMoney += products.get(i).getPrice();
                                    service.deleteObj(dao, products.get(i));
                                }

                                sale.setProfit( plusMoney - minusMoney );

                                dao = new SaleDaoImpl();
                                service.saveObj(dao, sale);

                            }else{

                                continue;
                            }

                            break;

                        case SALESREPORT:

                            Date date = Rules.validationSalesReport(argumentsArray);

                            if( date != null ){

                                dao = new SaleDaoImpl();
                                double profit = 0.0;
                                List<Sale> sales = service.findByName(dao, argumentsArray[1]);

                                for( Sale s: sales ){

                                    if( s.getDate().getTime() <= date.getTime() ){

                                        profit += s.getProfit();

                                    }
                                }

                                System.out.println("PROFIT = " + profit);

                            }else{
                                continue;
                            }

                            break;

                        default:
                            System.out.println("ERROR in COMMAND");
                            continue;

                    }
                }
            }
        }

    }
}
