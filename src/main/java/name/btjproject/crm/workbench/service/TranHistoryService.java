package name.btjproject.crm.workbench.service;

import name.btjproject.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryService {

    List<TranHistory> getTranHistory(String tranId);
}
