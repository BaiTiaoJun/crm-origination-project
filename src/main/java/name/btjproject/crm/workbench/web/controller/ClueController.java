package name.btjproject.crm.workbench.web.controller;

import name.btjproject.crm.settings.domain.User;
import name.btjproject.crm.settings.service.UserService;
import name.btjproject.crm.settings.service.impl.UserServiceImpl;
import name.btjproject.crm.utils.DateTimeUtil;
import name.btjproject.crm.utils.PrintJsonUtil;
import name.btjproject.crm.utils.ServiceFactory;
import name.btjproject.crm.utils.UUIDUtil;
import name.btjproject.crm.workbench.domain.Activity;
import name.btjproject.crm.workbench.domain.Clue;
import name.btjproject.crm.workbench.domain.Tran;
import name.btjproject.crm.workbench.service.ActivityService;
import name.btjproject.crm.workbench.service.ClueService;
import name.btjproject.crm.workbench.service.impl.ActivityServiceImpl;
import name.btjproject.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(servletPath)) {
            getUserList(response);
        } else if ("/workbench/clue/save.do".equals(servletPath)) {
            save(response, request);
        } else if ("/workbench/clue/detail.do".equals(servletPath)) {
            detail(request, response);
        } else if ("/workbench/clue/getActivityListByClueId.do".equals(servletPath)) {
            getActivityListByClueId(request, response);
        } else if ("/workbench/clue/unband.do".equals(servletPath)) {
            unband(request, response);
        } else if ("/workbench/clue/getActivityBySearch.do".equals(servletPath)) {
            getActivityBySearch(request, response);
        } else if ("/workbench/clue/bund.do".equals(servletPath)) {
            bund(request, response);
        } else if ("/workbench/clue/getActivityByName.do".equals(servletPath)) {
            getActivityByName(request, response);
        } else if ("/workbench/clue/convert.do".equals(servletPath)) {
            convert(request, response);
        }
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String flag = request.getParameter("flag");
        String clueId = request.getParameter("clueId");

        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getCreateBy();

        Tran tran = null;
        if ("true".equals(flag)) {
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expDate = request.getParameter("expDate");
            String stage = request.getParameter("stage");
            String actId = request.getParameter("actId");

            tran = new Tran();
            tran.setId(clueId);
            tran.setMoney(money);
            tran.setName(name);
            tran.setExpectedDate(expDate);
            tran.setStage(stage);
            tran.setActivityId(actId);
            tran.setId(UUIDUtil.getUUID());
            tran.setCreateBy(createBy);
            tran.setCreateTime(createTime);
        }

        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Boolean flag1 = clueService.convert(tran, createBy, createTime, clueId);

        response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");
    }

    private void getActivityByName(HttpServletRequest request, HttpServletResponse response) {
        String aName = request.getParameter("aName");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> list = activityService.getActivityByName(aName);
        PrintJsonUtil.printObjectJson(response, list);
    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Boolean flag = clueService.bund(cid, aids);
        PrintJsonUtil.printBoolJson(response, flag);
    }

    private void getActivityBySearch(HttpServletRequest request, HttpServletResponse response) {
        String aName = request.getParameter("aName");
        String clueId = request.getParameter("clueId");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Map<String, Object> map = new HashMap<>();
        map.put("aName", aName);
        map.put("clueId", clueId);
        List<Activity> list = clueService.getActivityBySearch(map);
        PrintJsonUtil.printObjectJson(response, list);
    }

    private void unband(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Boolean flag = clueService.unband(id);
        PrintJsonUtil.printBoolJson(response, flag);
    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {
        String clueId = request.getParameter("clueId");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> list = activityService.getActivityListByClueId(clueId);
        PrintJsonUtil.printObjectJson(response, list);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue clue = clueService.detail(id);
        request.setAttribute("c", clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);
    }

    private void save(HttpServletResponse response, HttpServletRequest request) {
        String id = UUIDUtil.getUUID();
        String createOwner = request.getParameter("create-owner");
        String createCompany = request.getParameter("create-company");
        String createAppellation = request.getParameter("create-appellation");
        String createFullname = request.getParameter("create-fullname");
        String createJob = request.getParameter("create-job");
        String createEmail = request.getParameter("create-email");
        String createPhone = request.getParameter("create-phone");
        String createWebsite = request.getParameter("create-website");
        String createMphone = request.getParameter("create-mphone");
        String createState = request.getParameter("create-state");
        String createSource = request.getParameter("create-source");
        String createDescription = request.getParameter("create-description");
        String createContactSummary = request.getParameter("create-contactSummary");
        String createNextContactTime = request.getParameter("create-nextContactTime");
        String createAddress = request.getParameter("create-address");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();

        Clue clue = new Clue();
        clue.setId(id);
        clue.setOwner(createOwner);
        clue.setCompany(createCompany);
        clue.setAppellation(createAppellation);
        clue.setFullname(createFullname);
        clue.setJob(createJob);
        clue.setEmail(createEmail);
        clue.setPhone(createPhone);
        clue.setWebsite(createWebsite);
        clue.setMphone(createMphone);
        clue.setState(createState);
        clue.setSource(createSource);
        clue.setDescription(createDescription);
        clue.setContactSummary(createContactSummary);
        clue.setNextContactTime(createNextContactTime);
        clue.setAddress(createAddress);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);

        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Boolean falg = clueService.save(clue);
        PrintJsonUtil.printBoolJson(response, falg);
    }

    private void getUserList(HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> list = userService.getUserList();
        PrintJsonUtil.printObjectJson(response, list);
    }
}
