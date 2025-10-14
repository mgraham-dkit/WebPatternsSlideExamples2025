package databases.D_introducingLombok.persistence;

import java.sql.Connection;

public interface Connector {
    public Connection getConnection();
    public void freeConnection();
}
