package name.btjproject.crm.workbench.dao;

import name.btjproject.crm.workbench.domain.Activity;
import name.btjproject.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity activity);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int deletedActivity(String[] ids);

    Activity getActivity(String id);

    int update(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityByName(String aName);
}
