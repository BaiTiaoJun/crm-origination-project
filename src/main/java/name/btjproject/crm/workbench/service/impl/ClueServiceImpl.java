package name.btjproject.crm.workbench.service.impl;

import name.btjproject.crm.utils.SqlSessionUtil;
import name.btjproject.crm.utils.UUIDUtil;
import name.btjproject.crm.workbench.dao.*;
import name.btjproject.crm.workbench.domain.*;
import name.btjproject.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {
    private final ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private final ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private final ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    private final CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private final CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    private final ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private final ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private final ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    private final TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private final TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public Boolean save(Clue clue) {
        return clueDao.save(clue) == 1;
    }

    @Override
    public Clue detail(String id) {
        return clueDao.detail(id);
    }

    @Override
    public Boolean unband(String id) {
        return clueActivityRelationDao.unband(id) == 1;
    }

    @Override
    public List<Activity> getActivityBySearch(Map<String, Object> map) {
        return clueDao.getActivityBySearch(map);
    }

    @Override
    public Boolean bund(String cid, String[] aids) {
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        for (String aid:aids) {
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(cid);
            clueActivityRelation.setActivityId(aid);
        }
        return clueActivityRelationDao.bund(clueActivityRelation);
    }

    @Override
    public Boolean convert(Tran tran, String createBy, String createTime, String clueId) {
        boolean flag = true;

        Clue clue = clueDao.getById(clueId);
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setContactSummary(clue.getContactSummary());
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(company);
            customer.setOwner(clue.getOwner());
            customer.setDescription(clue.getDescription());
            int n = customerDao.save(customer);
            if (n != 1) {
                flag = false;
            }
        }

        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(clue.getId());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());
        int n2 = contactsDao.save(contacts);
        if (n2 != 1) {
            flag = false;
        }

        List<ClueRemark> getClueRemarkList = clueRemarkDao.getListById(clueId);
        for (ClueRemark clueRemark:getClueRemarkList) {
            String contents = clueRemark.getNoteContent();
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setNoteContent(contents);
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(clueRemark.getId());
            int n3 = customerRemarkDao.save(customerRemark);
            if (n3 != 1) {
                flag = false;
            }

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setNoteContent(contents);
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(clueRemark.getId());
            int n4 = contactsRemarkDao.save(contactsRemark);
            if (n4 != 1) {
                flag = false;
            }
        }

        List<ClueActivityRelation> getClueActivityRelationList = clueActivityRelationDao.getListById(clueId);
        for (ClueActivityRelation clueActivityRelation:getClueActivityRelationList) {
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
            int n5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (n5 != 1) {
                flag = false;
            }
        }

        if (tran != null) {
            tran.setSource(clue.getSource());
            tran.setOwner(clue.getOwner());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setContactsId(contacts.getId());
            int n6 = tranDao.save(tran);
            if (n6 != 1) {
                flag = false;
            }
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(createBy);
        tranHistory.setCreateTime(createTime);
        if (tran != null) {
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setStage(tran.getStage());
            tranHistory.setTranId(tran.getId());
        }
        int n7 = tranHistoryDao.save(tranHistory);
        if (n7 != 1) {
            flag = false;
        }

        for (ClueRemark clueRemark:getClueRemarkList) {
            int n8 = clueRemarkDao.delete(clueRemark.getId());
            if (n8 != 1) {
                flag = false;
            }
        }

        for (ClueActivityRelation clueActivityRelation:getClueActivityRelationList) {
            int n9 = clueActivityRelationDao.delete(clueActivityRelation.getId());
            if (n9 != 1) {
                flag = false;
            }
        }

        int n9 = clueDao.delete(clueId);
        if (n9 != 1) {
            flag = false;
        }

        return flag;
    }
}