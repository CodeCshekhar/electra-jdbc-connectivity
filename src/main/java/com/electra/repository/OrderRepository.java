package com.electra.repository;

import com.electra.model.Orders;
import com.electra.model.Product;
import com.electra.service.ConnectionService;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRepository {
    private static Connection connection = null;

    private void initConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = new ConnectionService().getConnection();
        }
    }
    public List<Orders> retrieveOrders() {
        List<Orders> Orders = new ArrayList<>();
        // Use the connection to execute SQL queries and interact with the database
        try {
            this.initConnection();

            // Your database operations here...
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");

            // Iterate over the result set
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String product = resultSet.getString("product_id ");
                String customer= resultSet.getString("customer");
                Date orderDate = resultSet.getDate("orderDate");

                // Do something with the data, e.g., print it
                Orders orders = new Orders(id , product , customer, orderDate);
                Orders.add(orders);
            }
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        } finally {
            // Close the connection when done
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
        return Orders;
    }
}
