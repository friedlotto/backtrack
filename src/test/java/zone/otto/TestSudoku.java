package zone.otto;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * <p>
 * The <code>TestSudoku</code> class tests the functionality of the <code>Sudoku</code> class.
 * </p>
 */
public class TestSudoku {

    /**
     * <p>
     * Set up exception rule since JUnit4 <code>expects</code> cannot respond to messages."
     * </p>
     */
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    /**
     * <p>
     * Set up path to test resources
     * </p>
     */
    private static final String testResourcePath = "src/test/resources/";

    /**
     * <p>
     * Tests that <code>Sudoku.dataParse()</code> handles invalid filenames.
     * </p>
     */
    @Test
    public void testDataParse_BadFileName() {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("ERROR: The file (");
        Sudoku.dataParse("NonExistentFile.dat");

    }

    /**
     * <p>
     * Tests that <code>Sudoku.dataParse()</code> handles invalid input with insufficient lines.
     * </p>
     */
    @Test
    public void testDataParse_MissingLines() {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("ERROR: Insufficient number of valid input lines: ");
        Sudoku.dataParse(testResourcePath + "testDataParse_MissingLines.dat");

    }

    /**
     * <p>
     * Tests that <code>Sudoku.dataParse()</code> handles invalid input with invalid lines,
     * specifically invalid characters (i.e. <code>[^_ 1-9]</code>).
     * </p>
     */
    @Test
    public void testDataParse_InvalidCharacter() {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("ERROR: Line #");
        Sudoku.dataParse(testResourcePath + "testDataParse_InvalidCharacter.dat");

    }

    /**
     * <p>
     * Tests that <code>Sudoku.dataParse()</code> handles invalid input with invalid lines,
     * specifically duplicate digits.
     * </p>
     */
    @Test
    public void testDataParse_DuplicateDigit() {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("ERROR: Line #");
        Sudoku.dataParse(testResourcePath + "testDataParse_DuplicateDigit.dat");

    }

    /**
     * <p>
     * Tests that the backtracking recursive algorithm <code>Sudoku.dataSolve()</code> actually works. :P
     * </p>
     */
    @Test
    public void testDataSolve() {

        String expected = "     0 1 2   3 4 5   6 7 8\n" +
                "   +-------+-------+-------+\n" +
                " 0 | 3 4 7 | 1 8 2 | 5 6 9 |\n" +
                " 1 | 5 1 9 | 6 7 4 | 2 3 8 |\n" +
                " 2 | 2 8 6 | 3 5 9 | 1 4 7 |\n" +
                "   +-------+-------+-------+\n" +
                " 3 | 1 2 8 | 7 4 5 | 3 9 6 |\n" +
                " 4 | 4 7 3 | 9 2 6 | 8 5 1 |\n" +
                " 5 | 6 9 5 | 8 3 1 | 7 2 4 |\n" +
                "   +-------+-------+-------+\n" +
                " 6 | 7 3 2 | 4 6 8 | 9 1 5 |\n" +
                " 7 | 8 6 1 | 5 9 3 | 4 7 2 |\n" +
                " 8 | 9 5 4 | 2 1 7 | 6 8 3 |\n" +
                "   +-------+-------+-------+\n";

        Sudoku.dataParse(testResourcePath + "SudokuTest.dat");
        Assert.assertTrue(Sudoku.dataSolve(0, 0));

        String actual = Sudoku.dataRender();

        Assert.assertEquals(expected, actual);

    }

    /**
     * <p>
     * Tests that <code>Sudoku.getValidSet()</code> eliminates row, column and
     * box values correctly from the <code>FULL_SET</code> (i.e. values 1 to 9).
     * </p><p>
     * EIGHT assertions are made (in four categories):
     * </p><p><ul>
     * <li>
     * All cells with fully populated ValidSet.<br>
     * <code>ValidSet.Size() == 9</code><br>
     * or <code>[1,2,3,4,5,6,7,8,9]</code><br>
     * (Only ONE candidate)
     * </li>
     * <li>
     * All cells with only a single elimination from the ValidSet.<br>
     * <code>ValidSet.Size() == 8</code><br>
     * or <code>[[1-9], [1-9], [1-9], [1-9], [1-9], [1-9], [1-9], [1-9]]</code><br>
     * (TWO candidates)
     * </li>
     * <li>
     * All cells with only two entry ValidSet.<br>
     * <code>ValidSet.Size() == 2</code><br>
     * or <code>[[1-9], [1-9]]</code><br>
     * (FOUR candidates)
     * </li>
     * <li>
     * All cells with single entry ValidSet.<br>
     * <code>ValidSet.Size() == 1</code><br>
     * or <code>[[1-9]]</code><br>
     * (Only ONE candidate)
     * </li>
     * </ul></p>
     */
    @Test
    public void testGetValidSet() {

        int[] actual;
        int[] expected;

        Sudoku.dataParse(testResourcePath + "SudokuTest.dat");

        // All cells with fully populated ValidSet.
        actual = Sudoku.getValidSet(0, 8).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Assert.assertArrayEquals(expected, actual);

        // All cells with only a single elimination from the ValidSet.
        actual = Sudoku.getValidSet(0, 7).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1, 2, 3, 4, 6, 7, 9};
        Assert.assertArrayEquals(expected, actual);
        actual = Sudoku.getValidSet(5, 8).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1, 2, 4, 6, 7, 8, 9};
        Assert.assertArrayEquals(expected, actual);

        // All cells with only two entry ValidSet.
        actual = Sudoku.getValidSet(6, 5).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1, 8};
        Assert.assertArrayEquals(expected, actual);
        actual = Sudoku.getValidSet(7, 0).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1, 8};
        Assert.assertArrayEquals(expected, actual);
        actual = Sudoku.getValidSet(7, 1).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{6, 8};
        Assert.assertArrayEquals(expected, actual);
        actual = Sudoku.getValidSet(8, 4).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1, 2};
        Assert.assertArrayEquals(expected, actual);

        // All cells with single entry ValidSet.
        actual = Sudoku.getValidSet(7, 2).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1};
        Assert.assertArrayEquals(expected, actual);

    }

    /**
     * </p>
     * Test that <code>Sudoku.renderUsage()</code> renders the usage screen correctly.
     * </p>
     */
    @Test
    public void testRenderUsage() {

        String expected = "\nUSAGE:\n" +
                "\n  java Sudoku <filename>\n" +
                "\n    OR\n" +
                "\n  cat <filename> | java Sudoku\n" +
                "\n";

        String actual = Sudoku.renderUsage();

        Assert.assertEquals(expected, actual);

    }

    /**
     * <p>
     * Test that the output of MatrixHelper.renderMatrix() is correctly "Sudokufied", i.e.:
     * </p><p><ul>
     * <li>
     * Data cell 0s replaced with " "
     * </li>
     * <li></li>
     * + Data cells grouped into 3 x 3 boxes.
     * <li>
     * </ul></p>
     */
    @Test
    public synchronized void testDataRender() {

        String expected = "     0 1 2   3 4 5   6 7 8\n" +
                "   +-------+-------+-------+\n" +
                " 0 |       |       |       |\n" +
                " 1 |   1   | 6   4 |       |\n" +
                " 2 | 2   6 |   5   |       |\n" +
                "   +-------+-------+-------+\n" +
                " 3 |     8 |   4   |       |\n" +
                " 4 | 4     | 9     |   5   |\n" +
                " 5 |     5 |   3   |       |\n" +
                "   +-------+-------+-------+\n" +
                " 6 | 7   2 |   6   |       |\n" +
                " 7 |   6   | 5 9 3 | 4     |\n" +
                " 8 |       |     7 |   8   |\n" +
                "   +-------+-------+-------+\n";

        Sudoku.dataParse(testResourcePath + "SudokuTest.dat");
        String actual = Sudoku.dataRender();

        Assert.assertEquals(expected, actual);

    }

}
