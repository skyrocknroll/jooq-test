/**
 * Created by uva on 31/12/15.
 */

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import utils.DbConnectionPool;

import javax.sql.DataSource;

import static jooq.generated.tables.Author.AUTHOR;

;

public class JooqTest {

    public static void main(String[] args) {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl("jdbc:mysql://localhost:3306/library");
//        config.setUsername("root");
//        config.setPassword("sddsds");
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        DataSource ds = DbConnectionPool.getConnection();
//        HikariDataSource ds = new HikariDataSource(config);
//        String userName = "root";
//        String password = "minjar";
//        String url = "jdbc:mysql://localhost:3306/library";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (DSLContext create = DSL.using(ds, SQLDialect.MYSQL)) {
            Result<Record> result = create.select().from(AUTHOR).fetch();
            for (Record r : result) {
                Integer id = r.getValue(AUTHOR.ID);
                String firstName = r.getValue(AUTHOR.FIRST_NAME);
                String lastName = r.getValue(AUTHOR.LAST_NAME);

                System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
            }
//            create.select().from(AUTHOR).where(AUTHOR.ID.equal(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
