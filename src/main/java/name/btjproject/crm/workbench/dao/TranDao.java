package name.btjproject.crm.workbench.dao;

import name.btjproject.crm.workbench.domain.Tran;

import java.util.List;

public interface TranDao {

    List<Integer> getStageCount();

    int save(Tran tran);

    Tran detail(String id);

    int changeStage(Tran t);

    List<String> getStageType();
}
