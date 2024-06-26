package labopi.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;

import labopi.db.DAOFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import labopi.management.DotsCount;
import labopi.management.FigureArea;


@Named
@ApplicationScoped
@Data
public class ResultsListBean implements Serializable {
    @Inject
    private XBean XBean;
    
    @Inject
    private YBean YBean;
    
    @Inject
    private RBean RBean;

    @Inject
    private DotsCount dotsCount;

    @Inject
    private FigureArea figureArea;

    private LinkedList<ResultBean> results;

    public ResultsListBean () {
        super();
        try {
            results = new LinkedList<>(DAOFactory.getInstance().getResultDAO().getAll());
        } catch (SQLException exception) {
            results = new LinkedList<>();
            // throw new RuntimeException(exception);
        }
    }

    public void newResult(final float x, final float y, final float r) {
        System.out.println("Send: " + x + " " + y + " " + r);

        final long startExec = System.nanoTime();
        final long endExec = System.nanoTime();
        final int executionTime = (int) (endExec - startExec);

        final ResultBean currentResult = new ResultBean(x, y, r);
        final boolean    result = currentResult.getIsHit();
        currentResult.setCurrentTime(LocalDateTime.now());
        currentResult.setExecutionTime(executionTime);
        try {
            DAOFactory.getInstance().getResultDAO().addNewObj(currentResult);
            String jsFunction = "drawIsHitPoint(" + x + ", " + y + ", " + r +", " + result +")";
            FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add(jsFunction);
        } catch (SQLException exception) {
            System.err.println("Adding new object to DataBase failed with: " + exception.toString());
        }
        results.addLast(currentResult);
        dotsCount.newPoint(currentResult);
        figureArea.measureAreaOfFigure(this.results);
    }

    public int getResultListLength() {
        return results.size();
    }

    public void deleteAll() {
        try {
            DAOFactory.getInstance().getResultDAO().deleteAll();
            results.clear();
            dotsCount.setZeroDots();
        } catch (SQLException exception) {
            System.err.println("Deleting all records in DataBase failed with: " + exception.toString());
        }
    }
}