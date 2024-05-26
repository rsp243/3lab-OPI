package utils;

import javax.management.*;

class DotsCount implements DotsCountMBean {
    private int dotsCount;
    private int dotsHitCount;

    private void isCountMultipleByFive(int resultsCount) {
        // if (resultsCount % 5 == 0) {
        //     Notification n = new Notification("INFO",
        //                 sequenceNumber++,
        //                 "User set 5 dots");
            
        //     sendNotification(n);
        // }
        System.out.println("Hello JSX");
    }

    @Override
    public void isCountMultipleByFive() {
        isCountMultipleByFive(this.dotsCount);
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