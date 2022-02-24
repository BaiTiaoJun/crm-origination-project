package name.btjproject.crm.workbench.service;

import name.btjproject.crm.workbench.domain.Activity;
import name.btjproject.crm.workbench.domain.Clue;
import name.btjproject.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {

    Boolean save(Clue clue);

    Clue detail(String id);

    Boolean unband(String id);

    List<Activity> getActivityBySearch(Map<String, Object> map);

    Boolean bund(String cid, String[] aids);

    Boolean convert(Tran tran, String createBy, String createTime, String clueId);
}
