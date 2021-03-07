package dao;

import moodels.User;
import moodels.car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsersDaoJdbcTempkateImpl implements UsersDao {
    private JdbcTemplate template;
    public UsersDaoJdbcTempkateImpl(DriverManagerDataSource dataSource){
        this.template= new JdbcTemplate(dataSource);
    }
    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT fix_user.*, fix_car.id as car_id, fix_car.model FROM fix_user LEFT JOIN fix_car on fix_user.id=fix_car.owner_id";
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
        template.query(SQL_SELECT_USER_WITH_CARS,userRowMapper,id);
        if (usersMap.containsKey(id)) return Optional.of(usersMap.get(id));
        else return Optional.empty();
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
    private Map<Integer,User> usersMap = new HashMap<>();
    //language=SQL
    private final String SQL_SELECT_USER_WITH_CARS =
            "SELECT fix_user.*, fix_car.id as car_id, fix_car.model FROM fix_user LEFT JOIN fix_car ON fix_user.id = fix_car.owner_id WHERE fix_user.id = ?";

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            Integer id = resultSet.getInt("id");
            if (!usersMap.containsKey(id)){
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                User user = new User(id,firstName,lastName, new ArrayList<>());
                usersMap.put(id,user);
            }
            car car1 = new car (resultSet.getInt("car_id"), resultSet.getString("model"), usersMap.get(id));
            usersMap.get(id).getCars().add(car1);
            return usersMap.get(id);
        }
    };
    @Override
    public List<User> findAll() {
        return template.query(SQL_SELECT_ALL,userRowMapper);
    }
}
