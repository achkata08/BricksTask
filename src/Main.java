import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Engine engine = new Engine();
        engine.run();
    }

}

class Engine implements Runnable{

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String[] sizesMatrix = scanner.nextLine().split(" ");
        int rows = Integer.parseInt(sizesMatrix[0]);
        int cols = Integer.parseInt(sizesMatrix[1]);

        if (rows % 2 == 1 || cols % 2 == 1){
            System.out.println("Error in matrix input!");
            return;
        }

        List<Bricks> oddBricksList = new ArrayList<>();
        List<Bricks> evenBricksList = new ArrayList<>();
        List<Bricks> evenLeftBricksList = new ArrayList<>();
        List<Bricks> evenRightBricksList = new ArrayList<>();

        try {
            createOddAndEvenLists(scanner, sizesMatrix[0], oddBricksList, evenBricksList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        SeparateOnLeftAndRightEvenLists(evenBricksList, evenLeftBricksList, evenRightBricksList);
        int[][] matrix = new int[rows][cols];
        createLeftSide(evenLeftBricksList, matrix);
        createRightSite(evenRightBricksList, matrix);
        createCenterSide(oddBricksList, matrix);

        printMatrix(matrix);
    }

    private static void createOddAndEvenLists(Scanner scanner, String sizesMatrix, List<Bricks> oddBricksList,
                                              List<Bricks> evenBricksList) throws Exception {
        for (int i = 0; i < Integer.parseInt(sizesMatrix); i++) {
            String inputLine = scanner.nextLine();
            String[] inputLineArr = inputLine.split(" ");
            if (inputLineArr.length % 2 == 1) {
                throw new Exception("Error!");
            }
            for (int j = 0; j < inputLineArr.length; j += 2) {
                if (Integer.parseInt(inputLineArr[j]) % 2 == 1) {
                    oddBricksList.add(new Bricks(Integer.parseInt(inputLineArr[j])));
                } else if (Integer.parseInt(inputLineArr[j]) % 2 == 0) {
                    evenBricksList.add(new Bricks(Integer.parseInt(inputLineArr[j])));
                }
            }
        }
    }

    private static void SeparateOnLeftAndRightEvenLists(List<Bricks> evenBricksList, List<Bricks> evenLeftBricksList,
                                                        List<Bricks> evenRightBricksList) {
        for (int j = 0; j < evenBricksList.size(); j++) {
            if (j % 2 == 0) {
                evenLeftBricksList.add(evenBricksList.get(j));
            } else {
                evenRightBricksList.add(evenBricksList.get(j));
            }
        }
    }

    private static void createCenterSide(List<Bricks> oddBricksList, int[][] matrix) {
        int index;
        index = 0;
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][1] = oddBricksList.get(index).getBricksValue();
            matrix[i][2] = oddBricksList.get(index).getBricksValue();
            index++;
        }
    }

    private static void createRightSite(List<Bricks> evenRightBricksList, int[][] matrix) {
        int index;
        index = 0;
        for (int i = 0; i < matrix.length; i += 2) {
            matrix[i][matrix[i].length-1] = evenRightBricksList.get(index).getBricksValue();
            matrix[i + 1][matrix[i].length-1] = evenRightBricksList.get(index).getBricksValue();
            index++;
        }
    }

    private static void createLeftSide(List<Bricks> evenLeftBricksList, int[][] matrix) {
        int index = 0;
        for (int i = 0; i < matrix.length; i += 2) {
            matrix[i][0] = evenLeftBricksList.get(index).getBricksValue();
            matrix[i + 1][0] = evenLeftBricksList.get(index).getBricksValue();
            index++;
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class Bricks {
    private int bricksValue;

    public Bricks(int bricksValue) {
        this.bricksValue = bricksValue;
    }

    public int getBricksValue() {
        return bricksValue;
    }

}

/*
4 4
1 1 2 2
3 3 4 4
5 5 6 6
7 7 8 8

2 4
1 1 2 2
3 3 4 4
* */