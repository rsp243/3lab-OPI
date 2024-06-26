package labopi;

import labopi.model.AreaResultChecker;
import labopi.model.ResultBean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

class AreaResultCheckerTest {
    @Test
    void checkAreaResultValidationtTrueTest() {
        assertTrue(AreaResultChecker.validateXYR(-5, -5, 1));
        assertTrue(AreaResultChecker.validateXYR(-5, -5, 3));

        assertTrue(AreaResultChecker.validateXYR(-5, 5, 1));
        assertTrue(AreaResultChecker.validateXYR(-5, 5, 3));

        assertTrue(AreaResultChecker.validateXYR(3, -5, 1));
        assertTrue(AreaResultChecker.validateXYR(3, -5, 3));

        assertTrue(AreaResultChecker.validateXYR(3, 5, 1));
        assertTrue(AreaResultChecker.validateXYR(3, 5, 3));
    }

    @Test
    void checkAreaResultValidationtFalseTest() {
        assertFalse(AreaResultChecker.validateXYR(-6, -5, 3));
        assertFalse(AreaResultChecker.validateXYR(4, -5, 3));

        assertFalse(AreaResultChecker.validateXYR(-5, -6, 1));
        assertFalse(AreaResultChecker.validateXYR(-5, 6, 1));

        assertFalse(AreaResultChecker.validateXYR(-5, 5, 0));
        assertFalse(AreaResultChecker.validateXYR(3, -5, 4));
    }

    @ParameterizedTest
    @MethodSource("pointHitTrueFactory")
    void checkPointHitTrueTest(ResultBean result) {
        assertTrue(result.getIsHit());
    }
    
    @ParameterizedTest
    @MethodSource("pointHitFalseFactory")
    void checkPointHitFalseTest(ResultBean result) {
        assertFalse(result.getIsHit());
    }

    static Stream<ResultBean> pointHitTrueFactory() {
        return Stream.of(
            new ResultBean(0, 0, 1),
            new ResultBean(-1, 1, 3));
    }

    static Stream<ResultBean> pointHitFalseFactory() {
        return Stream.of(
            new ResultBean(-3, -3, 1),
            new ResultBean(500, 3, 1)
        );
    }
}
