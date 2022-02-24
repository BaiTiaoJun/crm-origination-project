package name.btjproject.crm.workbench.service;

import name.btjproject.crm.vo.PaginationVo;
import name.btjproject.crm.workbench.domain.Activity;
import name.btjproject.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] values);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getActivityRemarkByAid(String aid);

    boolean deleteActivityRemark(String id);

    boolean saveActivityRemark(ActivityRemark ar);

    boolean updateActivityRemark(ActivityRemark ar);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityByName(String aName);
}
