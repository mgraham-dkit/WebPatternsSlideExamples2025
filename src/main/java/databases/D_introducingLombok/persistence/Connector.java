package databases.D_introducingLombok.persistence;

import java.sql.Connection;

public interface Connector {
    public void setConnection(Connection conn);
    public Connection getConnection();
    public void freeConnection();
}
