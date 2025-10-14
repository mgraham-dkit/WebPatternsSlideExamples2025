package databases.C_refactoredDaoPattern.persistence;


import databases.C_refactoredDaoPattern.business.Customer;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomerDaoImpl implements CustomerDao {
    private Connector connector;

    public CustomerDaoImpl(Connector connector){
        this.connector = connector;
    }

    public static void main(String[] args) {
        Connector connector = new MySqlConnector("database.properties");
        CustomerDao customerDao = new CustomerDaoImpl(connector);
        List<Customer> customers = customerDao.getAllCustomers();
        connector.freeConnection();
        System.out.println(customers);

        System.out.println("------------------------------");
        System.out.println("Customer with id 119: " + customerDao.getById(119));
        connector.freeConnection();
    }

    @Override
    public Customer getById(int id) {
        Customer customer = null;

        // Get a connection using the connector class injected when the DAO was created
        Connection conn = connector.getConnection();
        // TRY to get a statement from the connection
        // When you are parameterizing the query, remember that you need
        // to use the ? notation (so you can fill in the blanks later)
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers where customerNumber = ?")) {

            // Fill in the blanks, i.e. parameterize the query
            ps.setInt(1, id);

            // TRY to execute the query
            try (ResultSet rs = ps.executeQuery()) {
                // Extract the information from the result set
                // Use extraction method to avoid code repetition!
                if(rs.next()) {
                    customer = mapRow(rs);
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception occurred when executing SQL or processing results.");
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred when attempting to prepare SQL for execution");
            System.out.println("Error: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        // Create variable to hold the customer info from the database
        List<Customer> customers = new ArrayList<>();

        // Get a connection using the connector class injected when the DAO was created
        Connection conn = connector.getConnection();
        // Get a statement from the connection
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers")) {
            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {
                // Repeatedly try to get a customer from the resultset
                while (rs.next()) {
                    Customer c = mapRow(rs);
                    customers.add(c);
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception occurred when executing SQL or processing results.");
                //System.out.println("Error: " + e.getMessage());
                log.error("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred when attempting to prepare SQL for execution");
            System.out.println("Error: " + e.getMessage());
        }

        return customers;
    }

    @Override
    public boolean deleteById(int id) {
        int rowsAffected = 0;

        Connection conn = connector.getConnection();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM customers where customerNumber = ?")) {
            // Fill in the blanks, i.e. parameterize the query
            ps.setInt(1, id);

            // Execute the operation
            // Remember that when you are doing an update, a delete or an insert,
            // your only result will be a number indicating how many rows were affected
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred when attempting to prepare/execute SQL.");
            System.out.println("Error: " + e.getMessage());
        }

        return rowsAffected > 0;
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        // Get the pieces of a customer from the resultset and create a new Customer
        Customer c = new Customer(
                rs.getInt("customerNumber"),
                rs.getString("customerName"),
                rs.getString("contactLastName"),
                rs.getString("contactFirstName"),
                rs.getString("phone"),
                rs.getString("addressLine1"),
                rs.getString("addressLine2"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("postalCode"),
                rs.getString("country"),
                rs.getInt("salesRepEmployeeNumber"),
                rs.getDouble("creditLimit")
        );
        // Return the extracted Customer (or null if the resultset was empty)
        return c;
    }
}
