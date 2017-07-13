package zone.otto;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class MatrixHelper {

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

    static int array2dMaxStringLength(int[][] data) {

        OptionalInt max = Arrays.stream(data)
                .flatMapToInt(Arrays::stream)
                .map(e -> (Integer.toString(e)).length())
                .max();

        return max.isPresent() ? max.getAsInt() : 1;

    }

    static String matrixToString(boolean labels, int[][] data) {

        int rowCount = data.length;
        int colCount = data[0].length;
        int maxLabelChars = 1 + (int) Math.log10(Integer.max(rowCount - 1, colCount - 1));
        int maxDataChars = array2dMaxStringLength(data);
        String labelFormat = "%0" + maxLabelChars + "d";
        String dataFormat = "%" + maxDataChars + "d";
        StringBuilder output = new StringBuilder(rowCount * colCount * maxDataChars * 30);

        String rowSeparator = IntStream.range(0, Integer.max(maxLabelChars, maxDataChars) + 2)
                .mapToObj(x -> "-")
                .collect(Collectors.joining());

        String rowLabelPad = IntStream.range(0, Integer.max(maxLabelChars, maxDataChars) - maxLabelChars)
                .mapToObj(x -> " ")
                .collect(Collectors.joining());

        String colLabelPad = IntStream.range(0, maxLabelChars + 2)
                .mapToObj(x -> " ")
                .collect(Collectors.joining());

        String dataPad = IntStream.range(0, Integer.max(maxLabelChars, maxDataChars) - maxDataChars)
                .mapToObj(x -> " ")
                .collect(Collectors.joining());

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

        output.append(IntStream.range(0, colCount)
                .mapToObj(e -> rowSeparator)
                .collect(
                        Collectors.joining("+",
                                labels ? colLabelPad + "+" : "+",
                                "+\n")
                )
        );

        for (int r = 0; r < rowCount; r++) {

            output.append(Arrays.stream(data[r])
                    .mapToObj(e -> dataPad + String.format(dataFormat, e))
                    .collect(
                            Collectors.joining(
                                    " | ",
                                    labels ? " " + String.format(labelFormat, r) + " | " : "| ",
                                    " |\n")
                    )
            );

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

}
