package name.btjproject.crm.workbench.service;

import name.btjproject.crm.workbench.domain.Tran;

import java.util.Map;

public interface TranService {
    Boolean save(Tran tran, String customerName);

    Tran detail(String id, Map<String, String> stpMap);

    boolean changeStage(Tran t);

    Map<String, Object> getTranCharts();
}
