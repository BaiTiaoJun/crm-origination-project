package name.btjproject.crm.workbench.service.impl;

import name.btjproject.crm.utils.SqlSessionUtil;
import name.btjproject.crm.workbench.dao.TranHistoryDao;
import name.btjproject.crm.workbench.domain.TranHistory;
import name.btjproject.crm.workbench.service.TranHistoryService;

import java.util.List;

public class TranHistoryServiceImpl implements TranHistoryService {
    private final TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);


    @Override
    public List<TranHistory> getTranHistory(String tranId) {
        return tranHistoryDao.getTranHistory(tranId);
    }
}
