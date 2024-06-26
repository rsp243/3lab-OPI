package labopi.management;

import java.lang.management.ManagementFactory;
import javax.management.ObjectName;

import lombok.extern.slf4j.Slf4j;

import javax.management.MalformedObjectNameException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;

@Slf4j
public class Registrator {

    public static boolean register(Object objBean) {
        var server = ManagementFactory.getPlatformMBeanServer();

        var beanClass = objBean.getClass();
        var name = "%s:type=basic,name=%s".formatted(beanClass.getPackageName(), beanClass.getSimpleName());

        log.info("Registering management bean");

        ObjectName objName;
        try {
            objName = new ObjectName(name);
        } catch (MalformedObjectNameException e) {
            log.error("Registration of bean has ended with exception", e);
            return false;
        }

        try {
            server.unregisterMBean(objName);
        } catch (MBeanRegistrationException | InstanceNotFoundException e) {} // ignore these exceptions

        try {
            server.registerMBean(objBean, objName);
        } catch (Exception e) {
            log.error("Registration of bean has ended with exception", e);
            return false;
        }
        return true;
    }

}