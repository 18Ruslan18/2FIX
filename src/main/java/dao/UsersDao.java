package dao;

import moodels.User;

import java.util.List;

public interface UsersDao extends CrudDao<User> {
    List<User> findAllByFirstName(String firstName);
}
