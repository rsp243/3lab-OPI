package utils;

import lombok.extern.slf4j.Slf4j;
import java.lang.management.ManagementFactory;
import javax.management.ObjectName;
import javax.management.MalformedObjectNameException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;

@Slf4j
public class Registrator {

    public static boolean register(Object mBean) {
        var server = ManagementFactory.getPlatformMBeanServer();

        var cls = mBean.getClass();
        var name = "%s:name=%s".formatted(cls.getPackageName(), cls.getSimpleName());
        log.warn("Registering MBean {}", name);

        System.out.println("REGISTERING MBEAN");

        ObjectName objName;
        try {
            objName = new ObjectName(name);
        } catch (MalformedObjectNameException e) {
            return false;
        }

        try {
            server.unregisterMBean(objName);
        } catch (InstanceNotFoundException | MBeanRegistrationException e) {
        }

        try {
            server.registerMBean(mBean, objName);
        } catch (Exception e) {
            log.error("MBean registration error", e);
            return false;
        }
        return true;
    }

}