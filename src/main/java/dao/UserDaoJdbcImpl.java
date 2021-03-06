package dao;

import moodels.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.activation.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJdbcImpl implements UsersDao {
    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM fix_user";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT * FROM fix_user WHERE id = ?";

    private Connection connection;

    public UserDaoJdbcImpl(DriverManagerDataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return null;
    }

    @Override
    public Optional<User> find(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                return Optional.of(new User(id, firstName, lastName));

            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User model) {

    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<User> findAll() {
        try {
            List<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");

                User user = new User(id, firstName, lastName);

                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

