package zone.otto;

import java.io.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * The <code>Sudoku</code> class contains a few static methods that solves a Sudoku puzzle using a
 * backtracking algorithm.
 * </p>
 * <p>
 * This implementation differs slightly from Bob Carpenter's in that it uses marginally less "brute force"
 * by a few optimisations (such as calculating valid options for a cell once and storing them in a set).
 * Initial benchmarking suggests an almost 20% speedup over that version.
 * </p>
 *
 * @see <a href="https://bob-carpenter.github.io/games/sudoku/java_sudoku.html">Bob Carpenter's Sudoku</a>
 * @see <a href="https://en.wikipedia.org/wiki/Backtracking">Wikipedia Backtracking</a>
 */
public class Sudoku {

    // The data array is the central data-structure on which everything rests.
    private static final int[][] data = new int[9][9];

    // The FULL_SET immutable set simply offers a starting template to copy when eliminating invalid choices.
    private static final Set<Integer> FULL_SET;

    // Who woulda thunk that I would find a legitimate use for a static initialiser block. ;)
    static {

        LinkedHashSet<Integer> tmpSet = new LinkedHashSet<>(10);

        tmpSet.add(1);
        tmpSet.add(2);
        tmpSet.add(3);
        tmpSet.add(4);
        tmpSet.add(5);
        tmpSet.add(6);
        tmpSet.add(7);
        tmpSet.add(8);
        tmpSet.add(9);

        FULL_SET = Collections.unmodifiableSet(tmpSet);

    }

    /**
     * The <code>main</code> method gets executed by Java.
     *
     * @param args Possibly contains a filename.
     */
    public static void main(String[] args) {

        args = new String[]{"src/test/resources/SudokuTest.dat"};

        boolean solved;
        long bgn, end;

        switch (args.length) {

            case 0:
                dataParse("");
                break;

            case 1:
                dataParse(args[0]);
                break;

            default:
                System.out.println(renderUsage());
                return;

        }

        System.out.println(dataRender());

        bgn = System.nanoTime();

        solved = dataSolve(0, 0);

        end = System.nanoTime();

        if (solved) {

            System.out.println(dataRender());

        } else {

            System.out.println("NO SOLUTION FOUND.");

        }

        System.out.printf("Processed in: %d ms%n%n", TimeUnit.NANOSECONDS.toMillis(end - bgn));

    }

    /**
     * The <code>dataSolve</code> method recurses (with backtracking) through the puzzle eliminating
     * legitimate choices for each available cell.
     *
     * @param r The starting row
     * @param c The starting column
     */
    static boolean dataSolve(int r, int c) {

        if (r == 9) {

            r = 0;

            if (++c == 9) {

                return true;

            }

        }

        if (data[r][c] != 0) {

            return dataSolve(r + 1, c);

        }

        for (int v : getValidSet(r, c)) {

            data[r][c] = v;

            if (dataSolve(r + 1, c)) {

                return true;

            }

        }

        data[r][c] = 0;

        return false;

    }

    /**
     * The <code>getValidSet</code> method simply calculates a set of legitimate options at row <code>r</code>
     * and column <code>c</code>.
     *
     * @param r The row
     * @param c The column
     */
    static Set<Integer> getValidSet(int r, int c) {

        Set<Integer> valid = new LinkedHashSet<>(FULL_SET);

        for (int i = 0; i < 9; i++) {

            if (i != c
                    && data[r][i] > 0) {

                valid.remove(data[r][i]);

            }

            if (i != r
                    && data[i][c] > 0) {

                valid.remove(data[i][c]);

            }

        }

        int ro = 3 * (r / 3);
        int co = 3 * (c / 3);

        for (int rc = 0; rc < 3; rc++) {

            for (int cc = 0; cc < 3; cc++) {

                if (co + cc != c
                        && ro + rc != r
                        && data[ro + rc][co + cc] > 0) {

                    valid.remove(data[ro + rc][co + cc]);

                }

            }

        }

        return valid;

    }

    /**
     * <p>
     * The <code>dataParse</code> method simply parses a puzzle, either from
     * <code>stdin</code> or from a file specified by the user in <code>args[0]</code>.
     * </p>
     * <p>
     * If the source file does not exist, or none is specified and no stdin is available, the program
     * aborts, throwing a RuntimeException.
     * </p>
     * <p>
     * However, if there are no I/O problems, the format works thus:
     * <ul>
     * <li>Blank lines (<code>""</code>) and lines prefixed with hash characters (<code>#</code>) are ignored.</li>
     * <li>ALL whitespace (<code>\s</code>, or <code>[ \t\n\x0B\f\r]</code>) is stripped out.</li>
     * <li>Lines are verified using a regular expression (<code>^(?!.*([1-9]).*\1)[_1-9]{9}$</code>) which
     * confirms that there are only at most nine <i>unique</i> digits, and underscores representing missing
     * digits. If an invalid line is read, the program aborts, throwing a RuntimeException.
     * <li>If input terminates before nine valid lines are read, the program aborts, throwing a RuntimeException.
     * </ul>
     * </p>
     *
     * @ param fileName The filename, <code>stdin</code> is assumed for <code>""</code>.
     */
    static void dataParse(String fileName) {

        Reader reader = new InputStreamReader(System.in);

        if (!fileName.equals("")) {

            try {

                reader = new FileReader(fileName);

            } catch (FileNotFoundException fnfe) {

                fnfe.printStackTrace();
                throw new RuntimeException("ERROR: The file (" + fileName + ") does not exist.");

            }

        }

        try (BufferedReader br = new BufferedReader(reader)) {

            Pattern p = Pattern.compile(("^(?!.*([1-9]).*\\1)[_1-9]{9}$"));
            String rawLine;
            String srcLine;
            int linesAdded = 0;
            int linesRead = 0;

            while ((rawLine = br.readLine()) != null
                    && linesAdded < 9) {

                ++linesRead;

                if (!rawLine.equals("") && !rawLine.startsWith("#")) {

                    srcLine = rawLine.replaceAll("\\s", "");
                    Matcher m = p.matcher(srcLine);

                    if (m.matches()) {

                        for (int c = 0; c < srcLine.length(); c++) {

                            if (srcLine.charAt(c) != '_') {

                                data[linesAdded][c] = Integer.parseInt(Character.toString(srcLine.charAt(c)));

                            }

                        }

                        ++linesAdded;

                    } else {

                        throw new RuntimeException("ERROR: Line #" + linesAdded + " (line #" + linesRead + " in input) is invalid: " + rawLine);

                    }

                }

            }

            if (linesAdded < 9) {

                throw new RuntimeException("ERROR: Insufficient number of valid input lines: " + linesAdded);

            }

            reader.close();
            br.close();

        } catch (IOException io) {

            io.printStackTrace();
            throw new RuntimeException("ERROR: An I/O Exception has occured.");

        }

    }

    /**
     * The <code>dataRender</code> method simply displays the <code>data</code> array.
     */
    static String dataRender() {

        String tmp = MatrixHelper.matrixToString(true, data);
        StringBuilder output = new StringBuilder(1000);

        // Reformat column headers into clusters of three.
        tmp = tmp.replaceAll("([0-9]) {3}([0-9]) {3}([0-9])", "$1 $2 $3");

        // Reformat data cells into clusters of three.
        tmp = tmp.replaceAll("\\| ([0-9]) \\| ([0-9]) \\| ([0-9]) ", "| $1 $2 $3 ");

        // Replace data cells with 0 values with " ".
        tmp = tmp.replaceAll("(?<=(?:\\||[0-9]) )0", " ");

        // Reformat reformat separators into clusters of three.
        tmp = tmp.replaceAll("\\+---\\+---\\+---", "+-------");

        int i = 0;
        for (String line : tmp.split("\n")) {

            // Reformat rows into clusters of three.
            if (i % 2 == 0
                    || (i - 1) % 6 == 0) {

                output.append(line + "\n");

            }

            i++;

        }

        return output.toString();

    }

    /**
     * The <code>renderUsage</code> method simply displays usage information.
     */
    static String renderUsage() {

        StringBuilder output = new StringBuilder();

        output.append("\n");
        output.append("USAGE:\n");
        output.append("\n");
        output.append("  java Sudoku <filename>\n");
        output.append("\n");
        output.append("    OR\n");
        output.append("\n");
        output.append("  cat <filename> | java Sudoku\n");
        output.append("\n");

        return output.toString();

    }

}