package name.btjproject.crm.workbench.dao;

import name.btjproject.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer customer);

    List<String> getNameList(String name);
}
