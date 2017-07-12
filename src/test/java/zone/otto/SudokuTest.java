package zone.otto;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

public class SudokuTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private ByteArrayOutputStream stdOut = new ByteArrayOutputStream();
    private ByteArrayOutputStream stdErr = new ByteArrayOutputStream();

    @Before
    public void streamsIntercept() {

        System.setOut(new PrintStream(stdOut));
        System.setErr(new PrintStream(stdErr));

    }

    private static final String testResourcePath = "target/test-classes/";

    @Test
    public void testDataGetBadFileName() {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("ERROR: The file (");
        Sudoku.dataGet("NonExistentFile.dat");

    }

    @Test
    public void testDataGetMissingLines() {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("ERROR: Insufficient number of valid input lines: ");
        Sudoku.dataGet(testResourcePath + "SudokuTestMissingLines.dat");

    }

    @Test
    public void testDataGetInvalidLine() {

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("ERROR: Line #");
        Sudoku.dataGet(testResourcePath + "SudokuTestInvalidLine.dat");

    }

    @Test
    public void testDataSolve() {

        String expected = "\n" +
                "     0 1 2   3 4 5   6 7 8\n" +
                "   +-------+-------+-------+\n" +
                " 0 | 3 4 7 | 1 8 2 | 5 6 9 | \n" +
                " 1 | 5 1 9 | 6 7 4 | 2 3 8 | \n" +
                " 2 | 2 8 6 | 3 5 9 | 1 4 7 | \n" +
                "   +-------+-------+-------+\n" +
                " 3 | 1 2 8 | 7 4 5 | 3 9 6 | \n" +
                " 4 | 4 7 3 | 9 2 6 | 8 5 1 | \n" +
                " 5 | 6 9 5 | 8 3 1 | 7 2 4 | \n" +
                "   +-------+-------+-------+\n" +
                " 6 | 7 3 2 | 4 6 8 | 9 1 5 | \n" +
                " 7 | 8 6 1 | 5 9 3 | 4 7 2 | \n" +
                " 8 | 9 5 4 | 2 1 7 | 6 8 3 | \n" +
                "   +-------+-------+-------+\n" +
                "\n";

        String actual;

        Sudoku.dataGet(testResourcePath + "SudokuTest.dat");
        Assert.assertTrue(Sudoku.dataSolve(0, 0));

        Sudoku.dataPut();

        actual = stdOut.toString();

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testGetValidSet() {

        int[] actual;
        int[] expected;

        Sudoku.dataGet(testResourcePath + "SudokuTest.dat");
        Sudoku.dataPut();

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
    public void testPrintUsage() {

        String expected = "\nUSAGE:\n" +
                "\n  java Sudoku <filename>\n" +
                "\n    OR\n" +
                "\n  cat <filename> | java Sudoku\n" +
                "\n";

        Sudoku.printUsage();

        Assert.assertEquals(expected, stdOut.toString());
        stdOut.reset();

    }

    @After
    public void streamsRestore() {

        System.setOut(null);
        System.setErr(null);
    }

}
