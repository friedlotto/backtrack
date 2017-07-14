package zone.otto;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * The <code>MatrixHelper</code> class is a utility class that provides functionality
 * in the form of transforms on int[][] as well as rendering to multi-line String.
 * </p>
 */
class MatrixHelper {

    /**
     * <p>
     * The <code>matrixDeepCopy()</code> creates a new instance of int[][] which
     * is an exact replica of the one provided.
     * </p>
     *
     * @param data The source array.
     * @return The resulting replicated array.
     */
    static int[][] matrixDeepCopy(int[][] data) {

        int rowSize = data.length;
        int colSize = data[0].length;

        int[][] result = new int[rowSize][colSize];

        for (int r = 0; r < rowSize; r++) {

            for (int c = 0; c < colSize; c++) {

                result[r][c] = data[r][c];

            }

        }

        return result;

    }

    /**
     * <p>
     * The <code>matrixReflectHorizontal()</code> reflects the COLUMNS of int[][].
     * i.e. int[k][i] swaps with int[k][n - i]
     * </p>
     *
     * @param data The source array.
     * @return The resulting reflected array.
     */
    static int[][] matrixReflectHorizontal(int[][] data) {

        int rowSize = data.length;
        int colSize = data[0].length;

        int[][] result = new int[rowSize][colSize];

        for (int r = 0; r < rowSize; r++) {

            for (int c = 0; c < colSize; c++) {

                result[r][c] = data[r][colSize - c - 1];

            }

        }

        return result;

    }

    /**
     * <p>
     * The <code>matrixReflectVertical()</code> reflects the ROWS of int[][].
     * i.e. int[i][k] swaps with int[m - i][k]
     * </p>
     *
     * @param data The source array.
     * @return The resulting reflected array.
     */
    static int[][] matrixReflectVertical(int[][] data) {

        int rowSize = data.length;
        int colSize = data[0].length;

        int[][] result = new int[rowSize][colSize];

        for (int r = 0; r < rowSize; r++) {

            for (int c = 0; c < colSize; c++) {

                result[r][c] = data[rowSize - r - 1][c];

            }

        }

        return result;

    }

    /**
     * <p>
     * The <code>matrixTranspose()</code> transposes the ROWS with the COLUMNS of int[][].
     * i.e. int[i][k] swaps with int[k][i]
     * </p>
     * <p>
     * NOTA BENE! In the case of non-square inputs, the shape will be rotated,
     * i.e. int[m][n] results in int[n][m]
     * </p>
     *
     * @param data The source array.
     * @return The resulting transposed array.
     */
    static int[][] matrixTranspose(int[][] data) {

        int rowSize = data.length;
        int colSize = data[0].length;

        int[][] result = new int[colSize][rowSize];

        for (int r = 0; r < rowSize; r++) {

            for (int c = 0; c < colSize; c++) {

                result[c][r] = data[r][c];

            }

        }

        return result;

    }

    /**
     * <p>
     * The <code>matrixRotateQuarterCW()</code> rotates the values of the int[][] 90° CLOCKWISE.
     * </p>
     * <p>
     * This is mathematically equivalent to:
     * <code>matrixReflectHorizontal(matrixTranspose(int[][]))</code>,
     * but quicker to implement in one step.
     * </p>
     * <p>
     * NOTA BENE! In the case of non-square inputs, the shape will be rotated,
     * i.e. int[m][n] results in int[n][m]
     * </p>
     *
     * @param data The source array.
     * @return The resulting rotated array.
     */
    static int[][] matrixRotateQuarterCW(int[][] data) {

        int rowSize = data.length;
        int colSize = data[0].length;

        int[][] result = new int[colSize][rowSize];

        for (int r = 0; r < rowSize; r++) {

            for (int c = 0; c < colSize; c++) {

                result[c][rowSize - r - 1] = data[r][c];

            }

        }

        return result;

    }

    /**
     * <p>
     * The <code>matrixRotateQuarterAC()</code> rotates the values of the int[][] 90° ANTI-CLOCKWISE.
     * </p>
     * <p>
     * This is mathematically equivalent to:
     * <code>matrixReflectVertical(matrixTranspose(int[][]))</code>,
     * but quicker to implement in one step.
     * </p>
     * <p>
     * NOTA BENE! In the case of non-square inputs, the shape will be rotated,
     * i.e. int[m][n] results in int[n][m]
     * </p>
     *
     * @param data The source array.
     * @return The resulting rotated array.
     */
    static int[][] matrixRotateQuarterAC(int[][] data) {

        int rowSize = data.length;
        int colSize = data[0].length;

        int[][] result = new int[colSize][rowSize];

        for (int r = 0; r < rowSize; r++) {

            for (int c = 0; c < colSize; c++) {

                result[c][r] = data[r][colSize - c - 1];

            }

        }

        return result;

    }

    /**
     * <p>
     * The <code>matrixRotateHalf()</code> rotates the values of the int[][] 180°.
     * </p>
     * <p>
     * This is mathematically equivalent to:
     * <code>matrixReflectHorizontal(matrixReflectVertical(int[][]))</code>,
     * but quicker to implement in one step.
     * </p>
     *
     * @param data The source array.
     * @return The resulting rotated array.
     */
    static int[][] matrixRotateHalf(int[][] data) {

        int rowSize = data.length;
        int colSize = data[0].length;

        int[][] result = new int[rowSize][colSize];

        for (int r = 0; r < rowSize; r++) {

            for (int c = 0; c < colSize; c++) {

                result[rowSize - r - 1][colSize - c - 1] = data[r][c];

            }

        }

        return result;

    }

    /**
     * <p>
     * The <code>renderMatrix()</code> renders an int[][] to a String with:
     * </p><p><ul>
     * <li>
     * Optional row/column labels.
     * </li>
     * <li>
     * Dynamically scaled column widths (based on data/column label width).
     * </li>
     * <li>
     * Dynamically zero padded labels (based on dimension sizes).
     * </li>
     * </ul></p>
     *
     * @param data The source array.
     * @return The string representation of the provided array.
     */
    static String renderMatrix(boolean labels, int[][] data) {

        int rowCount = data.length;
        int colCount = data[0].length;
        int maxLabelChars = 1 + (int) Math.log10(Integer.max(rowCount - 1, colCount - 1));
        int maxDataChars = array2dMaxStringLength(data);
        String labelFormat = "%0" + maxLabelChars + "d";
        String dataFormat = "%" + maxDataChars + "d";
        StringBuilder output = new StringBuilder(rowCount * colCount * maxDataChars * 30);

        // Dynamically calculate separator based on maximum column label/cell width.
        String rowSeparator = IntStream.range(0, Integer.max(maxLabelChars, maxDataChars) + 2)
                .mapToObj(x -> "-")
                .collect(Collectors.joining());

        // Dynamically calculate row label whitespace padding based on maximum column label/cell width.
        String rowLabelPad = IntStream.range(0, Integer.max(maxLabelChars, maxDataChars) - maxLabelChars)
                .mapToObj(x -> " ")
                .collect(Collectors.joining());

        // Dynamically calculate column label whitespace padding based on cell width.
        // (if maximum cell width is wider than column header width).
        String colLabelPad = IntStream.range(0, maxLabelChars + 2)
                .mapToObj(x -> " ")
                .collect(Collectors.joining());

        // Dynamically calculate data cell whitespace padding based on cell width
        // (if column header width is wider than maximum cell width).
        String dataPad = IntStream.range(0, Integer.max(maxLabelChars, maxDataChars) - maxDataChars)
                .mapToObj(x -> " ")
                .collect(Collectors.joining());

        // If labels are required, render column header labels.
        if (labels) {

            output.append(IntStream.range(0, colCount)
                    .mapToObj(e -> rowLabelPad + String.format(labelFormat, e))
                    .collect(
                            Collectors.joining("   ",
                                    colLabelPad + "  ",
                                    "\n")
                    )
            );

        }

        // Render opening separator.
        output.append(IntStream.range(0, colCount)
                .mapToObj(e -> rowSeparator)
                .collect(
                        Collectors.joining("+",
                                labels ? colLabelPad + "+" : "+",
                                "+\n")
                )
        );

        for (int r = 0; r < rowCount; r++) {

            // Render data line, optionally prefixed with row header labels if required.
            output.append(Arrays.stream(data[r])
                    .mapToObj(e -> dataPad + String.format(dataFormat, e))
                    .collect(
                            Collectors.joining(
                                    " | ",
                                    labels ? " " + String.format(labelFormat, r) + " | " : "| ",
                                    " |\n")
                    )
            );

            // Render separator.
            output.append(IntStream.range(0, colCount)
                    .mapToObj(e -> rowSeparator)
                    .collect(
                            Collectors.joining(
                                    "+",
                                    labels ? colLabelPad + "+" : "+",
                                    "+\n")
                    )
            );

        }

        return output.toString();

    }

    /**
     * <p>
     * The <code>array2dMaxStringLength()</code> is used to determine the maximum string width
     * of of values in the int[][]. This is useful in <code>renderMatrix()</code> to render
     * the int[][] in a dynamic way.
     * </p>
     *
     * @param data The source array.
     * @return The number of characters in the widest value.
     */
    static int array2dMaxStringLength(int[][] data) {

        OptionalInt max = Arrays.stream(data)
                .flatMapToInt(Arrays::stream)
                .map(e -> (Integer.toString(e)).length())
                .max();

        return max.isPresent() ? max.getAsInt() : 1;

    }


}
