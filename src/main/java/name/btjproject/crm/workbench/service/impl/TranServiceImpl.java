package name.btjproject.crm.workbench.service.impl;

import name.btjproject.crm.utils.DateTimeUtil;
import name.btjproject.crm.utils.SqlSessionUtil;
import name.btjproject.crm.utils.UUIDUtil;
import name.btjproject.crm.workbench.dao.CustomerDao;
import name.btjproject.crm.workbench.dao.TranDao;
import name.btjproject.crm.workbench.dao.TranHistoryDao;
import name.btjproject.crm.workbench.domain.Customer;
import name.btjproject.crm.workbench.domain.Tran;
import name.btjproject.crm.workbench.domain.TranHistory;
import name.btjproject.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {
    private final TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private final TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    private final CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public Boolean save(Tran tran, String customerName) {
        boolean flag = true;
        Customer customer = customerDao.getCustomerByName(customerName);
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setName(customerName);
            customer.setDescription(tran.getDescription());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setCreateTime(tran.getCreateTime());
            customer.setCreateBy(tran.getCreateBy());
            customer.setContactSummary(tran.getContactSummary());
            int n1 = customerDao.save(customer);
            if (n1 != 1) {
                flag = false;
            }
        }

        tran.setCustomerId(customer.getId());
        int n2 = tranDao.save(tran);
        if (n2 != 1) {
            flag = false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(tran.getCreateTime());
        int n3 = tranHistoryDao.save(tranHistory);
        if (n3 != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Tran detail(String id, Map<String, String> stpMap) {
        Tran tran = tranDao.detail(id);
        String possibility = stpMap.get(tran.getStage());
        tran.setPossibility(possibility);
        return tran;
    }

    @Override
    public boolean changeStage(Tran t) {
        boolean flag = true;
        int n1 = tranDao.changeStage(t);
        if (n1 != 1) {
            flag = false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setStage(t.getStage());
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(t.getCreateBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setMoney(t.getMoney());
        tranHistory.setTranId(t.getId());
        int n2 = tranHistoryDao.save(tranHistory);
        if (n2 != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getTranCharts() {
        List<Integer> countList = tranDao.getStageCount();
        List<String> list = tranDao.getStageType();
        Map<String, Object> map = new HashMap<>();
        map.put("nameList", list);
        map.put("valueList", countList);
        return map;
    }
}
