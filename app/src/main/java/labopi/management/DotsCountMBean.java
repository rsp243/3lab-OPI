package labopi.management;

public interface DotsCountMBean {
    public int getDotsCount();

    public int getDotsHitCount();

    public void setZeroDots();

    public int getTriggerPointCount();

    public void setTriggerPointCount(int triggerPointCount);
}