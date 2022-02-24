package name.btjproject.crm.workbench.dao;

import name.btjproject.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getListById(String clueId);

    int delete(String id);
}
