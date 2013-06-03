package acm.dao;

import acm.model.User;

public interface UserDAO {

    User getUserByName(String username);

    User getUserById(Long id);

}
