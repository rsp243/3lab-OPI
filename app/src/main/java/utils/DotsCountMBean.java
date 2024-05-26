package utils;

import model.ResultBean;

public interface DotsCountMBean {
    public void newPoint(ResultBean result);

    public int getDotsCount();

    public int getDotsHitCount();

    public void setDotsCount(int dotsCount);

    public void setDotsHitCount(int dotsHitCount);

}