import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlainSingerDao implements SingerDao {

    static {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/spring");
    }

    private void closeConnection(Connection connection){
        if (connection==null){
            return;
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public List<Singer> findAll() {

        List<Singer>  result = new ArrayList<Singer>();
        Connection connection=null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select  * from singer");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Singer singer = new Singer();
                singer.setId(resultSet.getLong("id"));
                singer.setFirstName(resultSet.getString("first_name"));
                singer.setLastName(resultSet.getString("last_name"));
                singer.setBirthDate(resultSet.getDate("birth_date"));
                result.add(singer);
            }
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
        return result;
    }

    public List<Singer> findByFirstName(String firstName) {
        return null;
    }

    public String findLastNameById(Long id) {
        return null;
    }

    public String findFirstNameById(Long id) {
        return null;
    }

    public void insert(Singer singer) {
        Connection connection = null;
        try {
            connection=getConnection();
            PreparedStatement statement =
                    connection.prepareStatement("insert into " +
                                    "singer(first_name,last_name,birth_date)values ( ?,?,? )",
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,singer.getFirstName());
            statement.setString(2,singer.getLastName());
            statement.setDate(3, singer.getBirthDate());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                singer.setId(generatedKeys.getLong(1));
            }
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
    }

    public void update(Singer singer) {

    }

    public void delete(Long singerId) {
        Connection connection=null;
        try {
        connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("delete from singer where id=?");
        statement.setLong(1,singerId);
        statement.execute();
        statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }


    }

    public List<Singer> findAllWithDetail() {
        return null;
    }

    public void insertWithDetail(Singer singer) {

    }
}
