package name.btjproject;

import name.btjproject.crm.exception.LoginException;
import name.btjproject.crm.settings.dao.UserDao;
import name.btjproject.crm.settings.domain.User;
import name.btjproject.crm.settings.service.UserService;
import name.btjproject.crm.settings.service.impl.UserServiceImpl;
import name.btjproject.crm.utils.DateTimeUtil;
import name.btjproject.crm.utils.MD5Util;
import name.btjproject.crm.utils.ServiceFactory;
import name.btjproject.crm.utils.SqlSessionUtil;
import name.btjproject.crm.vo.PaginationVo;
import name.btjproject.crm.workbench.dao.ActivityDao;
import name.btjproject.crm.workbench.domain.Activity;
import name.btjproject.crm.workbench.service.ActivityService;
import name.btjproject.crm.workbench.service.impl.ActivityServiceImpl;
import org.junit.Test;

import javax.xml.ws.Service;
import java.util.*;

public class test {
    @Test
    public void testCompareTo() {
//        String time = "2022-01-30 19:59:10";
//        String time2 = DateTimeUtil.getSysTime();
//        System.out.println(time.compareTo(time2));
    }

    @Test
    public void testMD5Util() {
        String pass = "12ad3";
        System.out.println(MD5Util.getMD5(pass));
    }

    @Test
    public void test() throws LoginException {
//        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
//        User user = userService.login("dasd", "1321", "1535135");
//        System.out.println(user);
//        UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
//        Map<String, String> map = new HashMap<>();
//        map.put("userName", "ls");
//        map.put("pass", "202cb962ac59075b964b07152d234b70");
//        User user = userDao.login(map);
//        System.out.println(user);

    }

    @Test
    public void testActivityController() {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> list = userService.getUserList();
        list.forEach(System.out:: println);
    }

    @Test
    public void testUserServiceImpl() throws LoginException {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        User user = userService.login("ls", "123", "127.0.0.1");
        System.out.println(user);
    }

    @Test
    public void test1() {
        UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
        List<User> users = userDao.getUserList();
        Activity activity = activityDao.getActivity("87a1d309d8e14943be465a8ef52d4e88");
        Map<String, Object> map = new HashMap<>();
        map.put("uList", users);
        map.put("a", activity);
        System.out.println(map.get("uList"));
//        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
//        int skipCount = (1 - 1) * 2;
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "asd");
//        map.put("owner", "Avds");
//        map.put("startDate", "153");
//        map.put("endDate", "1355");
//        map.put("pageSize", 2);
//        map.put("skipCount", skipCount);
//
//        PaginationVo<Activity> paginationVo = activityService.pageList(map);
    }

    @Test
    public void testS2P() {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("Stage2Possibility");
//        Map<String, String> map = new HashMap<>();
//        Enumeration<String> keys = resourceBundle.getKeys();
//        while (keys.hasMoreElements()) {
//            String key = keys.nextElement();
//            map.put(key, resourceBundle.getString(key));
//        }
    }
}