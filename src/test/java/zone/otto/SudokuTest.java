package zone.otto;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SudokuTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private static final String testResourcePath = "target/test-classes/";

    @Test
    public void testDataGetBadFileName() {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("ERROR: The file (");
        Sudoku.dataParse("NonExistentFile.dat");

    }

    @Test
    public void testDataGetMissingLines() {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("ERROR: Insufficient number of valid input lines: ");
        Sudoku.dataParse(testResourcePath + "SudokuTestMissingLines.dat");

    }

    @Test
    public void testDataGetInvalidLine() {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("ERROR: Line #");
        Sudoku.dataParse(testResourcePath + "SudokuTestInvalidLine.dat");

    }

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

    @Test
    public void testGetValidSet() {

        int[] actual;
        int[] expected;

        Sudoku.dataParse(testResourcePath + "SudokuTest.dat");
        Sudoku.dataRender();

        // All cells with a fully populated valid set.
        actual = Sudoku.getValidSet(0, 8).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Assert.assertArrayEquals(expected, actual);

        // All cells with only a single elimination from the valid set.
        actual = Sudoku.getValidSet(0, 7).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1, 2, 3, 4, 6, 7, 9};
        Assert.assertArrayEquals(expected, actual);
        actual = Sudoku.getValidSet(5, 8).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1, 2, 4, 6, 7, 8, 9};
        Assert.assertArrayEquals(expected, actual);

        // All cells with only two entry valid sets.
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

        // All cells with only two entry valid sets.
        actual = Sudoku.getValidSet(7, 2).stream().mapToInt(Integer::intValue).sorted().toArray();
        expected = new int[]{1};
        Assert.assertArrayEquals(expected, actual);

    }

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
