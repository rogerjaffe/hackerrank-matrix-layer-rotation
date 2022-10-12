import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;

class Result {
    
    public enum Direction {EAST, SOUTH, NORTH, WEST};
    
    public static int[][] convert(List<List<Integer>> matrix) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();
        int[][] arr = new int[rows][cols];
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                arr[r][c] = matrix.get(r).get(c);
            }
        }
        return arr;        
    }
    
    public static int[][] rotate(int[][] arr, int offset, int r) {
        
        int rows = arr.length;
        int cols = arr[0].length;        
        int steps = (2*rows) + (2*cols) - (8*offset) - 4;
        steps = r % steps;
            for (int loop = 0; loop < steps; loop++) {
            int row1 = offset;
            int col1 = offset;
            int row2 = rows - offset - 1;
            int col2 = cols - offset - 1;
            int thisRow = row1;
            int thisCol = col1+1;
            int lastRow = row1;
            int lastCol = col1;
            int saved = arr[lastRow][lastCol];
            Direction direction = Direction.EAST;
            do {
                arr[lastRow][lastCol] = arr[thisRow][thisCol];
                lastRow = thisRow;
                lastCol = thisCol;
                if (thisCol == col2 && thisRow == row1) {
                    direction = Direction.SOUTH;
                } else if (thisCol == col1 && thisRow == row2) {
                    direction = Direction.NORTH;
                } else if (thisCol == col2 && thisRow == row2) {
                    direction = Direction.WEST;
                } else if (thisCol == col1 && thisRow == row1) {
                    direction = Direction.EAST;
                }
                if (direction == Direction.EAST) {
                    thisCol++;
                } else if (direction == Direction.SOUTH) {
                    thisRow++;
                } else if (direction == Direction.WEST) {
                    thisCol--;
                } else {
                    thisRow--;
                }
            } while (thisRow != row1 || thisCol != col1);
            arr[lastRow][lastCol] = saved;
        }
        return arr;
    }
    
    /*
     * Complete the 'matrixRotation' function below.
     *
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY matrix
     *  2. INTEGER r
     */

    public static void matrixRotation(List<List<Integer>> matrix, int r) {
    // Write your code here
        int min = Math.min(matrix.size(), matrix.get(0).size());
        int[][] arr = Result.convert(matrix);

        for (int offset = 0; offset < min/2; offset++) {
            arr = Result.rotate(arr, offset, r);
        }

        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr[0].length; j++) {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(firstMultipleInput[0]);

        int n = Integer.parseInt(firstMultipleInput[1]);

        int r = Integer.parseInt(firstMultipleInput[2]);

        List<List<Integer>> matrix = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                matrix.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Result.matrixRotation(matrix, r);

        bufferedReader.close();
    }
}
