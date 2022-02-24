package name.btjproject.crm.workbench.dao;

import name.btjproject.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int unband(String id);

    Boolean bund(ClueActivityRelation clueActivityRelation);

    List<ClueActivityRelation> getListById(String clueId);

    int delete(String id);
}
