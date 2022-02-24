package name.btjproject.crm.workbench.dao;

import name.btjproject.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> getTranHistory(String tranId);
}
