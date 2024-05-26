package utils;

import javax.management.*;
import lombok.extern.slf4j.Slf4j;
import model.ResultBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Slf4j
@Named
@ApplicationScoped
class DotsCount extends NotificationBroadcasterSupport implements DotsCountMBean {
    private int dotsCount;
    private int dotsHitCount;
    private int sequenceNumber = 0;

    @PostConstruct
    private void init() {
        System.out.println("HELLLOOOOOO!!!");
        Registrator.register(this);
    }

    @Override
    public synchronized void newPoint(ResultBean result) {
        System.out.println("Registered new point");

        if (result.getIsHit() == true) {
            this.dotsHitCount++;
        }
        this.dotsCount++;
        
        if (this.dotsCount % 5 == 0) {
            System.out.println("Registered 5th point");
            Notification n = new Notification(
                            "INFO", 
                            this, 
                            sequenceNumber++, 
                            System.currentTimeMillis(),
                            "Reached count dividing by 5: %d".formatted(this.dotsCount));
            
            sendNotification(n);
        }
    }

    @Override
    public int getDotsCount() {
        return dotsCount;
    }

    @Override
    public int getDotsHitCount() {
        return dotsHitCount;
    }

    @Override
    public void setDotsCount(int dotsCount) {
        this.dotsCount = dotsCount;
    }

    @Override
    public void setDotsHitCount(int dotsHitCount) {
        this.dotsHitCount = dotsHitCount;
    }
}