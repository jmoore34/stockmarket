/**
 * -- DankBankers -- 
 */

import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;

class SimulateTrade {

    Connection connect;
    Statement query;
    ArrayList<Integer> brokerids;
    ArrayList<Integer> userids;
    
    public static void main(String[] args) {
        new SimulateTrade();
    }

    public SimulateTrade() {
        try {
            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
            connect = DriverManager.getConnection("jdbc:mariadb://localhost:3306/StockMarket", "root", "");
            System.out.println("connected");
            query = connect.createStatement();
            // load broker ids
            ResultSet result = query.executeQuery("select NationalTaxID from Person where BrokerFlag = 1;");
            brokerids = new ArrayList<>();
            while (result.next()) {
                brokerids.add(result.getInt("NationalTaxID"));
            }
            // load user ids
            result = query.executeQuery("select NationalTaxID from Person where UserFlag = 1;");
            userids = new ArrayList<>();
            while (result.next()) {
                userids.add(result.getInt("NationalTaxID"));
            }

            // begin simulation
            for (int n = 0; n < 200; ++n) {
                Random randgen = new Random();
                int broker = brokerids.get(randgen.nextInt(brokerids.size()));
                result = query.executeQuery(String.format("select Assets from Person where NationalTaxID = %d;", broker));
                result.next();
                double brokerAssets = result.getDouble("Assets");
                int buyer = userids.get(randgen.nextInt(userids.size()));
                result = query.executeQuery(String.format("select Assets from Person where NationalTaxID = %d;", buyer));
                result.next();
                double buyerAssets = result.getDouble("Assets");
                int seller = buyer;
                while (seller == buyer) { // make sure buyer and seller are not the same;
                    seller = userids.get(randgen.nextInt(userids.size()));
                }
                result = query.executeQuery(String.format("select Assets from Person where NationalTaxID = %d;", seller));
                result.next();
                double sellerAssets = result.getDouble("Assets");
                result = query.executeQuery(String.format("select ID from Stock where Person_FK = %d;", seller));
                ArrayList<Integer> stockids = new ArrayList<>();
                while (result.next()) {
                    stockids.add(result.getInt("ID"));
                }
                if (stockids.size() == 0) { // this "seller" owns no stocks
                    --n;
                    continue;                    
                }
                int productid = stockids.get(randgen.nextInt(stockids.size()));
                result = query
                    .executeQuery(String.format("select Price from Stock where ID = %d;", productid));
                result.next();
                double stockPrice = result.getDouble("Price");
                double price = stockPrice;
                result = query
                    .executeQuery(String.format("select PercentProfit, PricePerTransaction from Person where NationalTaxID = %d", broker));
                result.next();
                price += price * result.getDouble("PercentProfit");
                price += result.getDouble("PricePerTransaction");
                // update ownership of stock
                query.executeQuery(String.format("update Stock set Person_FK = %d where ID = %d;", buyer, productid));
                // update Assets of buyer
                query.executeQuery(String.format("update Person set Assets = %f where NationalTaxID = %d;", buyerAssets - price, buyer));
                // update Assets of seller
                query.executeQuery(String.format("update Person set Assets = %f where NationalTaxID = %d;", sellerAssets + stockPrice, seller));
                // update Assets of broker
                query.executeQuery(String.format("update Person set Assets = %f where NationalTaxID = %d;", brokerAssets + price - stockPrice, broker));
                query.executeQuery(String.format("insert into Hires (BuyerUser_FK, SellerUser_FK, Broker_FK, Stock_FK, `Time`, BrokerProfit) values (%d, %d, %d, %d, '%s', %f);",
                                                 buyer, seller, broker, productid, new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()), price - stockPrice));
            }
            connect.close();
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("not connected");
            System.exit(-1);
        }
        
    }
    
}
