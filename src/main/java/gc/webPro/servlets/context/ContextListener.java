package gc.webPro.servlets.context;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import gc.webPro.utils.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @Author: GC
 * @Description: TODO
 * @Version: 1.0
 */
public class ContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {

            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while(drivers.hasMoreElements()) {
                DriverManager.deregisterDriver(drivers.nextElement());
            }
            AbandonedConnectionCleanupThread.checkedShutdown();
            JDBCUtil.getSource().close();
        } catch (SQLException e) {
            logger.info("注销驱动失败");
        }

    }
}
