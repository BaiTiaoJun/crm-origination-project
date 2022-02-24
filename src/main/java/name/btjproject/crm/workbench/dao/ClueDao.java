package name.btjproject.crm.workbench.dao;

import name.btjproject.crm.workbench.domain.Activity;
import name.btjproject.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {

    int save(Clue clue);

    Clue detail(String id);

    List<Activity> getActivityBySearch(Map<String, Object> map);

    Clue getById(String clueId);

    int delete(String clueId);
}
