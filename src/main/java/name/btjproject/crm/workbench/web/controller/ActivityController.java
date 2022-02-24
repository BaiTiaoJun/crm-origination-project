package name.btjproject.crm.workbench.web.controller;

import name.btjproject.crm.settings.domain.User;
import name.btjproject.crm.settings.service.UserService;
import name.btjproject.crm.settings.service.impl.UserServiceImpl;
import name.btjproject.crm.utils.DateTimeUtil;
import name.btjproject.crm.utils.PrintJsonUtil;
import name.btjproject.crm.utils.ServiceFactory;
import name.btjproject.crm.utils.UUIDUtil;
import name.btjproject.crm.vo.PaginationVo;
import name.btjproject.crm.workbench.domain.Activity;
import name.btjproject.crm.workbench.domain.ActivityRemark;
import name.btjproject.crm.workbench.service.ActivityService;
import name.btjproject.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(servletPath)) {
            getUserList(response);
        } else if ("/workbench/activity/save.do".equals(servletPath)) {
            save(request, response);
        } else if ("/workbench/activity/pageList.do".equals(servletPath)) {
            pageList(request, response);
        } else if ("/workbench/activity/delete.do".equals(servletPath)) {
            delete(request, response);
        } else if ("/workbench/activity/getUserListAndActivity.do".equals(servletPath)) {
            getUserListAndActivity(request, response);
        } else if ("/workbench/activity/update.do".equals(servletPath)) {
            update(request, response);
        } else if ("/workbench/activity/detail.do".equals(servletPath)) {
            detail(request, response);
        } else if ("/workbench/activity/getActivityRemarkByAid.do".equals(servletPath)) {
            getActivityRemarkByAid(request, response);
        } else if ("/workbench/activity/deleteActivityRemark.do".equals(servletPath)) {
            deleteActivityRemark(request, response);
        } else if ("/workbench/activity/saveActivityRemark.do".equals(servletPath)) {
            saveActivityRemark(request, response);
        } else if ("/workbench/activity/updateActivityRemark.do".equals(servletPath)) {
            updateActivityRemark(request, response);
        }
    }

    private void updateActivityRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        String editFlag = "1";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setEditTime(editTime);
        ar.setEditBy(editBy);
        ar.setNoteContent(noteContent);
        ar.setEditFlag(editFlag);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.updateActivityRemark(ar);

        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", ar);
        PrintJsonUtil.printObjectJson(response, map);
    }

    private void saveActivityRemark(HttpServletRequest request, HttpServletResponse response) {
        String noteContent = request.getParameter("noteContent");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String activityId = request.getParameter("aid");
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setNoteContent(noteContent);
        ar.setActivityId(activityId);
        ar.setEditFlag(editFlag);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.saveActivityRemark(ar);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("arList", ar);
        PrintJsonUtil.printObjectJson(response, map);
    }

    private void deleteActivityRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.deleteActivityRemark(id);
        PrintJsonUtil.printBoolJson(response, flag);
    }

    private void getActivityRemarkByAid(HttpServletRequest request, HttpServletResponse response) {
        String aid = request.getParameter("aid");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> list = activityService.getActivityRemarkByAid(aid);
        PrintJsonUtil.printObjectJson(response, list);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = activityService.detail(id);
        request.setAttribute("a", activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setEndDate(endDate);
        activity.setStartDate(startDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        boolean flag = activityService.update(activity);
        PrintJsonUtil.printBoolJson(response, flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> map = activityService.getUserListAndActivity(id);
        PrintJsonUtil.printObjectJson(response, map);
    }

    private void delete (HttpServletRequest request, HttpServletResponse response) {
        String[] ids = request.getParameterValues("value");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.delete(ids);
        PrintJsonUtil.printBoolJson(response, flag);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("search-name");
        String owner = request.getParameter("search-owner");
        String startDate = request.getParameter("search-startDate");
        String endDate = request.getParameter("search-endDate");
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int skipCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("pageSize", pageSize);
        map.put("skipCount", skipCount);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginationVo<Activity> paginationVo = activityService.pageList(map);
        PrintJsonUtil.printObjectJson(response, paginationVo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setEndDate(endDate);
        activity.setStartDate(startDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        boolean flag = activityService.save(activity);
        PrintJsonUtil.printBoolJson(response, flag);
    }

    private void getUserList (HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> list = userService.getUserList();
        PrintJsonUtil.printObjectJson(response, list);
    }
}
