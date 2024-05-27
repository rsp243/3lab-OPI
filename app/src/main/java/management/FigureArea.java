package management;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.management.NotificationBroadcasterSupport;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.event.SystemEvent;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import model.ResultBean;
import model.ResultsListBean;

@Named
@ApplicationScoped
@Getter
@Slf4j
public class FigureArea implements FigureAreaMBean {
    private float figureArea = 0;

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

    public void measureAreaOfFigure(List<ResultBean> resultsList) {
        if (resultsList.size() > 0 && resultsList.size() % triggerPointCount == 0) {
            List<ResultBean> figurePoints = new LinkedList<ResultBean>(resultsList.subList(resultsList.size() - 5, resultsList.size()));
            List<ResultBean> figurePointsCopy = new LinkedList<ResultBean>(figurePoints);            

            ResultBean masterPoint = figurePoints.get(0);

            // get non-adjacent from zero node
            List<ResultBean> nonAjacentPoints0 = getNonAdjacentPointsArray(figurePoints, masterPoint); // size = 2

            // area of first triangle
            float diagonal1 = getDistanceBetweenPoints(figurePoints.get(0), nonAjacentPoints0.get(0));
            float diagonal2 = getDistanceBetweenPoints(figurePoints.get(0), nonAjacentPoints0.get(1));

            List<ResultBean> middlePoints = new LinkedList<ResultBean>(nonAjacentPoints0);
            float thirdSide0 = getDistanceBetweenPoints(nonAjacentPoints0.get(0), nonAjacentPoints0.get(1));

            float firstTriangleArea = getTriangleArea(diagonal1, diagonal2, thirdSide0);

            List<ResultBean> nonAjacentPoints1 = getNonAdjacentPointsArray(figurePoints, figurePoints.get(1)); // size = 2
            nonAjacentPoints1.add(masterPoint); // add zero point

            figurePointsCopy.removeAll(nonAjacentPoints1); // get second adjacent point to first point (first adjacent point is zero point)
            
            ResultBean firstPoint = figurePointsCopy.get(0);
            System.out.println("FIRST POINT " + firstPoint);
            System.out.println("MASTER POINT " + masterPoint);


            float firstSide1 = getDistanceBetweenPoints(firstPoint, nonAjacentPoints0.get(0));
            float secondSide1 = getDistanceBetweenPoints(firstPoint, masterPoint);
            float thirdSide1 = Math.min(getDistanceBetweenPoints(firstPoint, middlePoints.get(0)), getDistanceBetweenPoints(firstPoint, middlePoints.get(1))); 
            System.out.println(firstSide1);
            System.out.println(secondSide1);
            System.out.println(thirdSide1);

            float secondTriangleArea = getTriangleArea(firstSide1, secondSide1, thirdSide1);

            middlePoints.add(masterPoint);
            middlePoints.add(firstPoint);
            System.out.println("MIDDLE POINTS " + middlePoints);

            figurePoints.removeAll(middlePoints);
            System.out.println("FIGURE POINTS " + figurePoints);
            ResultBean secondPoint = figurePoints.get(0);

            float firstSide2 = getDistanceBetweenPoints(secondPoint, nonAjacentPoints0.get(0));
            float secondSide2 = getDistanceBetweenPoints(secondPoint, masterPoint);
            float thirdSlide2 = Math.min(getDistanceBetweenPoints(secondPoint, middlePoints.get(0)), getDistanceBetweenPoints(secondPoint, middlePoints.get(1))); 
            float thirdTriangleArea = getTriangleArea(firstSide2, secondSide2, thirdSlide2);

            System.out.println(firstTriangleArea);
            System.out.println(secondTriangleArea);
            System.out.println(thirdTriangleArea);
            this.figureArea = firstTriangleArea + secondTriangleArea + thirdTriangleArea;
            System.out.println("NEW FIGURE AREA" + this.figureArea);
        }
    }

    public List<ResultBean> getNonAdjacentPointsArray(List<ResultBean> pointsList, ResultBean point) {
        pointsList.remove(point);
        List<ResultBean> nonAdjacentPoints = new LinkedList<>(pointsList);
        float prevDistance = 20000000;
        float minDistance = 10000000;
        for (ResultBean forPoint : pointsList) {
            float distance = getDistanceBetweenPoints(forPoint, point);
            if (minDistance > distance) {
                prevDistance = minDistance;
                minDistance = distance;
            }
        }
        for (ResultBean forPoint : pointsList) {
            float distance = getDistanceBetweenPoints(forPoint, point);
            if (minDistance == distance || prevDistance == distance) {
                nonAdjacentPoints.remove(forPoint);
            }
        }

        return nonAdjacentPoints;
    }

    public float getDistanceBetweenPoints(ResultBean point1, ResultBean point2) {
        return (float) Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2) + Math.pow(point2.getY() - point1.getY(), 2));
    }

    public float getTriangleArea(float firstSide, float secondSide, float thirdSide) {
        var pValue = 0.5 * (firstSide + secondSide + thirdSide); 
        var area = Math.sqrt(pValue * (pValue - firstSide) * (pValue - secondSide) * (pValue - thirdSide));

        return (float) area;
    }
}
