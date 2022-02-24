package name.btjproject.crm.workbench.service.impl;

import name.btjproject.crm.settings.dao.UserDao;
import name.btjproject.crm.settings.domain.User;
import name.btjproject.crm.utils.SqlSessionUtil;
import name.btjproject.crm.vo.PaginationVo;
import name.btjproject.crm.workbench.dao.ActivityDao;
import name.btjproject.crm.workbench.dao.ActivityRemarkDao;
import name.btjproject.crm.workbench.domain.Activity;
import name.btjproject.crm.workbench.domain.ActivityRemark;
import name.btjproject.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private final UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private final ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private final ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

    @Override
    public boolean save(Activity activity) {
        return activityDao.save(activity) == 1;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        int total = activityDao.getTotalByCondition(map);
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        PaginationVo<Activity> paginationVo = new PaginationVo<>();
        paginationVo.setTotal(total);
        paginationVo.setDataList(dataList);
        return paginationVo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = false;
        int countActivityRemark = activityRemarkDao.getCountActivityRemark(ids);
        int countActivityRemarkDeleted = activityRemarkDao.deletedActivityRemark(ids);
        if (countActivityRemarkDeleted == countActivityRemark) {
            flag = true;
        }
        int countActivityDeleted = activityDao.deletedActivity(ids);
        if (countActivityDeleted == ids.length) {
            flag = true;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> users = userDao.getUserList();
        Activity activity = activityDao.getActivity(id);
        Map<String, Object> map = new HashMap<>();
        map.put("uList", users);
        map.put("a", activity);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        return activityDao.update(activity) == 1;
    }

    @Override
    public Activity detail(String id) {
        return activityDao.detail(id);
    }

    @Override
    public List<ActivityRemark> getActivityRemarkByAid(String aid) {
        return activityRemarkDao.getActivityRemarkByAid(aid);
    }

    @Override
    public boolean deleteActivityRemark(String id) {
        return activityRemarkDao.deleteActivityRemark(id) == 1;
    }

    @Override
    public boolean saveActivityRemark(ActivityRemark ar) {
        return activityRemarkDao.saveActivityRemark(ar) == 1;
    }

    @Override
    public boolean updateActivityRemark(ActivityRemark ar) {
        return activityRemarkDao.updateActivityRemark(ar) == 1;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        return activityDao.getActivityListByClueId(clueId);
    }

    @Override
    public List<Activity> getActivityByName(String aName) {
        return activityDao.getActivityByName(aName);
    }
}
