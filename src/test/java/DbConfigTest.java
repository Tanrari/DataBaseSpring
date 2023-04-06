import config.DbConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DbConfigTest {
    @Test
    public void testOne() throws SQLException {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(DbConfig.class);
        DataSource dataSource = ctx.getBean("dataSource", DataSource.class);
        assertNotNull(dataSource);
//        System.out.println(dataSource.getConnection().getCatalog());
        testDataSource(dataSource);
        ctx.close();

    }

    public void testDataSource(DataSource dataSource) throws SQLException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            System.out.println(connection.getCatalog());
            PreparedStatement statement = connection.prepareStatement("select * from singer where id=1");
//            System.out.println(statement.execute());
            ResultSet resultSet = statement.executeQuery();
//            statement.execute();
//            System.out.println(resultSet);
            while (resultSet.next()){
//                System.out.println(resultSet.);
                 String mockVal = resultSet.getString("first_name");
                assertEquals("John", mockVal);
//                System.out.println(resultSet.getString("first_name"));
            }
//            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            if (connection!=null){
                connection.close();
            }
        }
    }
}
