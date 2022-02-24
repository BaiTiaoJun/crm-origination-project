package name.btjproject.crm.settings.service.impl;

import name.btjproject.crm.exception.LoginException;
import name.btjproject.crm.settings.dao.UserDao;
import name.btjproject.crm.settings.domain.User;
import name.btjproject.crm.settings.service.UserService;
import name.btjproject.crm.utils.DateTimeUtil;
import name.btjproject.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String userName, String pass, String ip) throws LoginException {
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("pass", pass);
        User user = userDao.login(map);
        if (user == null) {
            throw new LoginException("账号或密码错误");
        }
        String expireTime = user.getExpireTime();
        if (expireTime.compareTo(DateTimeUtil.getSysTime()) < 0) {
            throw new LoginException("账号已失效");
        }
        String lockState = user.getLockState();
        if ("0".equals(lockState)) {
            throw new LoginException("账号已被锁定");
        }
        String allowIps = user.getAllowIps();
        System.out.println(allowIps);
        if (!allowIps.contains(ip)) {
            throw new LoginException("ip地址受限");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }
}