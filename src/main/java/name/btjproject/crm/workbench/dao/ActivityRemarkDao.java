package name.btjproject.crm.workbench.dao;

import name.btjproject.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int deleteActivityRemark(String id);

    int getCountActivityRemark(String[] ids);

    int deletedActivityRemark(String[] ids);

    List<ActivityRemark> getActivityRemarkByAid(String aid);

    int saveActivityRemark(ActivityRemark ar);

    int updateActivityRemark(ActivityRemark ar);
}
