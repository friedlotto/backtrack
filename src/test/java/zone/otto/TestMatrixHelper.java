package zone.otto;

import org.junit.Assert;
import org.junit.Test;


/**
 * <p>
 * The <code>TestMatrixHelper</code> class tests the functionality of the <code>MatrixHelper</code> class.
 * </p>
 */
public class TestMatrixHelper {

    /**
     * <p>
     * Test that <code>testMatrixReflectHorizontal()</code> transforms an int[][] correctly.
     * </p>
     */
    @Test
    public void testMatrixDeepCopy() {

        int[][] expected = new int[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        };

        int[][] actual = MatrixHelper.matrixDeepCopy(expected);

        Assert.assertFalse(expected == actual);

        Assert.assertArrayEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixReflectHorizontal()</code> transforms an int[][] correctly.
     * </p>
     */
    @Test
    public void testMatrixReflectHorizontal() {

        int[][] input = new int[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        };

        int[][] expected = new int[][]{
                {2, 1},
                {4, 3},
                {6, 5},
                {8, 7}
        };

        int[][] actual = MatrixHelper.matrixReflectHorizontal(input);

        Assert.assertArrayEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixReflectVertical()</code> transforms an int[][] correctly.
     * </p>
     */
    @Test
    public void testMatrixReflectVertical() {

        int[][] input = new int[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        };

        int[][] expected = new int[][]{
                {7, 8},
                {5, 6},
                {3, 4},
                {1, 2}
        };

        int[][] actual = MatrixHelper.matrixReflectVertical(input);

        Assert.assertArrayEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixTranspose()</code> transforms an int[][] correctly.
     * </p>
     */
    @Test
    public void testMatrixTranspose() {

        int[][] input = new int[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        };

        int[][] expected = new int[][]{
                {1, 3, 5, 7},
                {2, 4, 6, 8}
        };

        int[][] actual = MatrixHelper.matrixTranspose(input);

        Assert.assertArrayEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixRotateQuarterCW()</code> transforms an int[][] correctly.
     * </p>
     */
    @Test
    public void testMatrixRotateQuarterCW() {

        int[][] input = new int[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        };

        int[][] expected = new int[][]{
                {7, 5, 3, 1},
                {8, 6, 4, 2}
        };

        int[][] actual = MatrixHelper.matrixRotateQuarterCW(input);
        ;

        Assert.assertArrayEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixRotateQuarterAC()</code> transforms an int[][] correctly.
     * </p>
     */
    @Test
    public void testMatrixRotateQuarterAC() {

        int[][] input = new int[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        };

        int[][] expected = new int[][]{
                {2, 4, 6, 8},
                {1, 3, 5, 7}
        };

        int[][] actual = MatrixHelper.matrixRotateQuarterAC(input);

        Assert.assertArrayEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixRotateHalf()</code> transforms an int[][] correctly.
     * </p>
     */
    @Test
    public void testMatrixRotateHalf() {

        int[][] input = new int[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        };

        int[][] expected = new int[][]{
                {8, 7},
                {6, 5},
                {4, 3},
                {2, 1}
        };

        int[][] actual = MatrixHelper.matrixRotateHalf(input);

        Assert.assertArrayEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixToString_NoLabels()</code> renders int[][] with no labels.
     * </p>
     */
    @Test
    public void testMatrixToString_NoLabels() {

        int[][] input = new int[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        };

        String actual = MatrixHelper.renderMatrix(false, input);

        String expected = "+---+---+\n" +
                "| 1 | 2 |\n" +
                "+---+---+\n" +
                "| 3 | 4 |\n" +
                "+---+---+\n" +
                "| 5 | 6 |\n" +
                "+---+---+\n" +
                "| 7 | 8 |\n" +
                "+---+---+\n";

        Assert.assertEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixToString_NoLabels()</code> renders int[][] with no
     * labels and value-scaled cell-width.
     * </p>
     */
    @Test
    public void testMatrixToString_NoLabelsBigValues() {

        int[][] input = new int[][]{
                {10_000_000, 1_000_000},
                {100_000, 10_000},
                {1_000, 100},
                {10, 1}
        };

        String actual = MatrixHelper.renderMatrix(false, input);

        String expected = "+----------+----------+\n" +
                "| 10000000 |  1000000 |\n" +
                "+----------+----------+\n" +
                "|   100000 |    10000 |\n" +
                "+----------+----------+\n" +
                "|     1000 |      100 |\n" +
                "+----------+----------+\n" +
                "|       10 |        1 |\n" +
                "+----------+----------+\n";

        Assert.assertEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixToString_NoLabels()</code> renders int[][] with labels.
     * </p>
     */
    @Test
    public void testMatrixToString_Labels() {

        int[][] input = new int[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        };

        String actual = MatrixHelper.renderMatrix(true, input);

        String expected = "     0   1\n" +
                "   +---+---+\n" +
                " 0 | 1 | 2 |\n" +
                "   +---+---+\n" +
                " 1 | 3 | 4 |\n" +
                "   +---+---+\n" +
                " 2 | 5 | 6 |\n" +
                "   +---+---+\n" +
                " 3 | 7 | 8 |\n" +
                "   +---+---+\n";

        Assert.assertEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixToString_NoLabels()</code> renders int[][] with labels
     * that are wider than the maximum width of the data.
     * </p>
     */
    @Test
    public void testMatrixToString_LabelsBiggerLabels() {

        int[][] input = new int[4][12];

        String actual = MatrixHelper.renderMatrix(true, input);

        String expected = "      00   01   02   03   04   05   06   07   08   09   10   11\n" +
                "    +----+----+----+----+----+----+----+----+----+----+----+----+\n" +
                " 00 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |\n" +
                "    +----+----+----+----+----+----+----+----+----+----+----+----+\n" +
                " 01 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |\n" +
                "    +----+----+----+----+----+----+----+----+----+----+----+----+\n" +
                " 02 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |\n" +
                "    +----+----+----+----+----+----+----+----+----+----+----+----+\n" +
                " 03 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |  0 |\n" +
                "    +----+----+----+----+----+----+----+----+----+----+----+----+\n";

        Assert.assertEquals(expected, actual);

    }

    /**
     * <p>
     * Test that <code>testMatrixToString_NoLabels()</code> renders int[][] with data
     * that are wider than the maximum width of the labels.
     * </p>
     */
    @Test
    public void testMatrixToString_LabelsBiggerValues() {

        int[][] input = new int[][]{
                {10_000_000, 1_000_000},
                {100_000, 10_000},
                {1_000, 100},
                {10, 1}
        };

        String actual = MatrixHelper.renderMatrix(true, input);

        String expected = "            0          1\n" +
                "   +----------+----------+\n" +
                " 0 | 10000000 |  1000000 |\n" +
                "   +----------+----------+\n" +
                " 1 |   100000 |    10000 |\n" +
                "   +----------+----------+\n" +
                " 2 |     1000 |      100 |\n" +
                "   +----------+----------+\n" +
                " 3 |       10 |        1 |\n" +
                "   +----------+----------+\n";

        Assert.assertEquals(expected, actual);

    }

}
