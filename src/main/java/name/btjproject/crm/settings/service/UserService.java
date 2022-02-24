package name.btjproject.crm.settings.service;

import name.btjproject.crm.exception.LoginException;
import name.btjproject.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String userName, String pass, String ip) throws LoginException;

    List<User> getUserList();
}
