package name.btjproject.crm.workbench.service.impl;

import name.btjproject.crm.utils.SqlSessionUtil;
import name.btjproject.crm.workbench.dao.CustomerDao;
import name.btjproject.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getNameList(String name) {
        return customerDao.getNameList(name);
    }
}
