package dao;

import moodels.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersDaoJdbcTempkateImpl implements UsersDao {
    private JdbcTemplate template;
    public UsersDaoJdbcTempkateImpl(DriverManagerDataSource dataSource){
        this.template= new JdbcTemplate(dataSource);
    }
    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM fix_user";
    //language=SQL
    private final String SQL_SELECT_BY_FIRSTNAME =
            "SELECT * FROM fix_user WHERE firstName = ?";
    //language=SQL
    private final String SQL_USERS_CARS =
            "select * from fix_user left join fix_car on fix_user.id=fix_car.owner_id where fix_user.id=?";
    @Override
    public List<User> findAllByFirstName(String firstName) {
        return template.query(SQL_SELECT_BY_FIRSTNAME,userRowMapper,firstName);
    }

    @Override
    public Optional<User> find(Integer id) {
        return Optional.empty();
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
    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User (
                    resultSet.getInt("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName")
            );
        }
    };
    @Override
    public List<User> findAll() {
        return template.query(SQL_SELECT_ALL,userRowMapper);
    }
}
