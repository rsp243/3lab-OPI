package management;


import javax.management.*;
import model.ResultBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Named
@ApplicationScoped
@Getter
@Slf4j
public class DotsCount extends NotificationBroadcasterSupport implements DotsCountMBean {

    private int dotsCount = 0;

    private int dotsHitCount = 0;
    
    private int sequenceNumber = 0;

    @Setter    
    private int triggerPointCount = 5;

    @PostConstruct
    public void init() {
        if (Registrator.register(this)) {
            log.info("Successfully registered %s MBean.".formatted(this.getClass().getSimpleName()));
        } else {
            log.warn("Registration of %s MBean has ended with nonzero return code.".formatted(this.getClass().getSimpleName()));
        }
    }

    public synchronized void newPoint(ResultBean result) {
        log.info("Registered new point");

        if (result.getIsHit() == true) {
            this.dotsHitCount++;
        }
        this.dotsCount++;
        
        if (this.dotsCount % this.triggerPointCount == 0) {
            log.info("Reached count of setted points dividing by %d".formatted(this.triggerPointCount));
            Notification n = new Notification(
                            "INFO",
                            this,
                            sequenceNumber++,
                            System.currentTimeMillis(),
                            "Reached count dividing by %d: %d".formatted(this.triggerPointCount, this.dotsCount));
            
            sendNotification(n);
        }
    }

    @Override
    public synchronized void setZeroDots() {
        this.dotsCount = 0;
        this.dotsHitCount = 0;
    }
}