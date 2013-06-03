package acm.service;

import acm.model.User;

public interface UserService {

    User getUserByName(String username);

    User getUserByID(Long userid);

}
