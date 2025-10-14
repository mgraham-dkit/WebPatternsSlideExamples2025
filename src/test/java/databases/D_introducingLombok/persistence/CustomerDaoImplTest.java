package databases.D_introducingLombok.persistence;

import databases.D_introducingLombok.business.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CustomerDaoImplTest {
    // Establish which database properties to use - link to the test database, not live!
    private final static String PROPERTIES_FILE = "database_test.properties";
    private Connector connectionSource;
    private Connection conn;

    @BeforeEach
    void setUpConnection() throws SQLException {
        System.out.println("Setting up connection and disabling auto-commit");
        // Set up connection source to be used
        // This includes establishing a connection so we can control its auto-commit settings
        connectionSource = new MySqlConnector(PROPERTIES_FILE);
        conn = connectionSource.getConnection();

        // Turn off auto-commit - the connection will no longer store actions by default
        conn.setAutoCommit(false);
    }

    @AfterEach
    public void shutdownConnection() throws SQLException {
        System.out.println("Rolling back action and shutting down connection");
        // Roll back the action to prevent saving
        conn.rollback();

        // Close the database connection
        connectionSource.freeConnection();
    }

    @Test
    void getById() throws SQLException {
        // Create dao to test
        CustomerDao custDao = new CustomerDaoImpl(connectionSource);

        // Set test input
        int custId = 103;
        // Create expected result - make a Customer object matching what we expect to get back
        Customer expResult = Customer.builder()
                .customerNumber(103)
                .customerName("Atelier graphique")
                .contactLastName("Schmitt")
                .contactFirstName("Carine ")
                .phone("40.32.2555")
                .addressLine1("54, rue Royale")
                .addressLine2(null)
                .city("Nantes")
                .state(null)
                .postalCode("44000")
                .country("France")
                .salesRepEmployeeNumber(1370)
                .creditLimit(21000)
                .build();

        // Run the method under test
        Customer result = custDao.getById(103);
        // Confirm the result is as expected
        assertEquals(expResult, result);
    }

    @Test
    void getById_invalidID() throws SQLException {
        // Create dao to test
        CustomerDao custDao = new CustomerDaoImpl(connectionSource);

        // Set test input
        int custId = 103;

        // Run the method under test
        Customer result = custDao.getById(11);
        // Confirm the result is as expected
        assertNull(result);
    }

    @Test
    void deleteById() {
    }
}