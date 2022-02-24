package name.btjproject.crm.workbench.web.controller;

import name.btjproject.crm.settings.domain.User;
import name.btjproject.crm.settings.service.UserService;
import name.btjproject.crm.settings.service.impl.UserServiceImpl;
import name.btjproject.crm.utils.DateTimeUtil;
import name.btjproject.crm.utils.PrintJsonUtil;
import name.btjproject.crm.utils.ServiceFactory;
import name.btjproject.crm.utils.UUIDUtil;
import name.btjproject.crm.workbench.domain.Tran;
import name.btjproject.crm.workbench.domain.TranHistory;
import name.btjproject.crm.workbench.service.CustomerService;
import name.btjproject.crm.workbench.service.TranHistoryService;
import name.btjproject.crm.workbench.service.TranService;
import name.btjproject.crm.workbench.service.impl.CustomerServiceImpl;
import name.btjproject.crm.workbench.service.impl.TranHistoryServiceImpl;
import name.btjproject.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionController extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/workbench/transaction/getUserList.do".equals(servletPath)) {
            getUserList(request, response);
        } else if ("/workbench/transaction/getCustomerName.do".equals(servletPath)) {
            getCustomerName(request, response);
        } else if ("/workbench/transaction/save.do".equals(servletPath)) {
            save(request, response);
        } else if ("/workbench/transaction/detail.do".equals(servletPath)) {
            detail(request, response);
        } else if ("/workbench/transaction/getTranHistory.do".equals(servletPath)) {
            getTranHistory(request, response);
        } else if ("/workbench/transaction/changeStage.do".equals(servletPath)) {
            changeStage(request, response);
        } else if ("/workbench/transaction/getTranCharts.do".equals(servletPath)) {
            getTranCharts(response);
        }
    }

    private void getTranCharts(HttpServletResponse response) {
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Map<String, Object> map = tranService.getTranCharts();
        PrintJsonUtil.printObjectJson(response, map);
    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();

        Tran t = new Tran();
        t.setId(id);
        t.setStage(stage);
        t.setMoney(money);
        t.setExpectedDate(expectedDate);
        t.setEditBy(editBy);
        t.setEditTime(editTime);

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag =tranService.changeStage(t);

        Map<String, String> stpMap = (Map<String, String>) getServletConfig().getServletContext().getAttribute("stpMap");
        t.setPossibility(stpMap.get(stage));

        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("t", t);

        PrintJsonUtil.printObjectJson(response, map);
    }

    private void getTranHistory(HttpServletRequest request, HttpServletResponse response) {
        String tranId = request.getParameter("tranId");
        Map<String, String> stpMap = (Map<String, String>) this.getServletContext().getAttribute("stpMap");

        TranHistoryService tranHistoryService = (TranHistoryService) ServiceFactory.getService(new TranHistoryServiceImpl());
        List<TranHistory> thList = tranHistoryService.getTranHistory(tranId);
        for (TranHistory tranHistory:thList) {
            String stage = tranHistory.getStage();
            String possibility = stpMap.get(stage);
            tranHistory.setPossibility(possibility);
        }
        PrintJsonUtil.printObjectJson(response, thList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        ServletContext application = this.getServletContext();
        Map<String, String> stpMap = (Map<String, String>) application.getAttribute("stpMap");

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Tran tran = tranService.detail(id, stpMap);

        request.setAttribute("tran", tran);

        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran tran = new Tran();
        tran.setId(id);
        tran.setOwner(owner);
        tran.setMoney(money);
        tran.setName(name);
        tran.setExpectedDate(expectedDate);
        tran.setStage(stage);
        tran.setType(type);
        tran.setSource(source);
        tran.setActivityId(activityId);
        tran.setContactsId(contactsId);
        tran.setCreateBy(createBy);
        tran.setCreateTime(createTime);
        tran.setDescription(description);
        tran.setContactSummary(contactSummary);
        tran.setNextContactTime(nextContactTime);

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Boolean flag = tranService.save(tran, customerName);
        if (flag) {
            response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");
        }
    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<String> nameList = customerService.getNameList(name);
        PrintJsonUtil.printObjectJson(response, nameList);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = userService.getUserList();
        request.setAttribute("uList", uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request, response);
    }
}
