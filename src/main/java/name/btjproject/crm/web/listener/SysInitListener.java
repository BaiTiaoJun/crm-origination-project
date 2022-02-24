package name.btjproject.crm.web.listener;

import name.btjproject.crm.settings.service.DicService;
import name.btjproject.crm.settings.service.impl.DicServiceImpl;
import name.btjproject.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        ServletContext application = event.getServletContext();
        loadDictionary(application);
        stageToPossibility(application);
    }

    private void loadDictionary(ServletContext application) {
        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, Object> map = dicService.getAll();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            application.setAttribute(key , map.get(key));
        }
    }

    private void stageToPossibility(ServletContext application) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Stage2Possibility");
        Map<String, String> map = new HashMap<>();
        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, resourceBundle.getString(key));
        }
        application.setAttribute("stpMap", map);
    }
}