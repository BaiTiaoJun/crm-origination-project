package name.btjproject.crm.settings.dao;

import name.btjproject.crm.settings.domain.User;
import name.btjproject.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User login(Map<String, String> map);

    List<User> getUserList();
}
