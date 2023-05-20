package logic;

import java.util.ArrayList;
import java.util.LinkedList;
public class TransportationProblem {
    double [] required;
    double [] stock;
    double [][] cost;
    double [][] transport;

    double[] alphas;
    double [] betas;
    double [][] deltas;
    LinkedList<Variable> feasible = new LinkedList<>();
    int stockSize;
    int requiredSize;

    double profit;

    public TransportationProblem(int stockSize, int requiredSize ){
        this.stockSize = stockSize;
        this.requiredSize = requiredSize;
        this.profit = 0;
        stock = new double[stockSize];
        required = new double[requiredSize];
        alphas = new double[stockSize];
        betas = new double[requiredSize];
        cost = new double[stockSize][requiredSize];
        transport = new double[stockSize][requiredSize];
        deltas = new double[stockSize][requiredSize];
        for(int i=0; i < (requiredSize + stockSize -1); i++)
            feasible.add(new Variable());
    }

    public void setSupply(double value, int index) { stock[index] = value; }
    public void setDemand(double value, int index) { required[index] = value; }
    public void setCost(double value, int stock, int required) { cost[stock][required] = value; }
    public double[][] getTransport() {
        return transport;
    }

    public void northWestCorner() {
        double min;
        int k = 0; //feasible solutions counter
        //isSet is responsible for annotating cells that have been allocated
        boolean [][]isSet = new boolean[stockSize][requiredSize];
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0;  i < stockSize; i++)
                isSet[i][j] = false;
        //the for loop is responsible for iterating in the 'north-west' manner
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0;  i < stockSize; i++) {
                if (!isSet[i][j]) {
                    //allocating stock in the proper manner
                    min = Math.min(required[j], stock[i]);
                    feasible.get(k).setRequired(j);
                    feasible.get(k).setStock(i);
                    feasible.get(k).setValue(min);
                    transport[i][j] = min;
                    k++;

                    required[j] -= min;
                    stock[i] -= min;

                    //allocating null values in the removed row/column
                    if (stock[i] == 0)
                        for (int l = 0; l < requiredSize; l++)
                            isSet[i][l] = true;
                    else
                        for (int l = 0; l < stockSize; l++)
                            isSet[l][j] = true;
                }
            }
        this.optimize();

        System.nanoTime();
    }
    public void maxProfitRule() {

        double max;
        int k = 0; //feasible solutions counter
        double newProfit = 0;
        //isSet is responsible for annotating cells that have been allocated
        boolean [][]isSet = new boolean[stockSize][requiredSize];
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0;  i < stockSize; i++)
                isSet[i][j] = false;

        int i, j;
        Variable maxCost = new Variable();

        //this will loop is responsible for candidating cells by their least cost
        while(k < (stockSize + requiredSize - 1)){

            maxCost.setValue((-1)*Double.MAX_VALUE);
            //picking up the biggest profit cell
            boolean changer = false;
            //first priority - real cells
            for (int m = 0;  m < stockSize - 1; m++)
                for (int n = 0; n < requiredSize - 1; n++)
                    if(!isSet[m][n]) { //first priority - real cells
                        if (cost[m][n] > maxCost.getValue()) {
                            maxCost.setStock(m);
                            maxCost.setRequired(n);
                            maxCost.setValue(cost[m][n]);
                            changer=true;
                        }
                    }
            //second priority - fake cells
            if (!changer) {
                for (int m = 0;  m < stockSize; m++)
                    for (int n = 0; n < requiredSize; n++)
                        if(!isSet[m][n]) { //first priority - real cells
                            if (cost[m][n] > maxCost.getValue()) {
                                maxCost.setStock(m);
                                maxCost.setRequired(n);
                                maxCost.setValue(cost[m][n]);
                            }
                        }
            }

            i = maxCost.getStock();
            j = maxCost.getRequired();

            //allocating stock in the proper manner
            max = Math.min(required[j], stock[i]);

            feasible.get(k).setRequired(j);
            feasible.get(k).setStock(i);
            feasible.get(k).setValue(max);
            transport[i][j] = max;
            newProfit+= cost[i][j]*transport[i][j];
            k++;

            required[j] -= max;
            stock[i] -= max;

            //allocating null values in the removed row/column
            if(stock[i] == 0)
                for(int l = 0; l < requiredSize; l++)
                    isSet[i][l] = true;
            else if (required[j] == 0)
                for(int l = 0; l < stockSize; l++)
                    isSet[l][j] = true;
        }
        profit = newProfit;
        this.optimize();
        System.nanoTime();
    }

    private void optimize() {
        while(true) {
            //0. remove previous alphas, betas and deltas
            deltas = new double[stockSize][requiredSize];
            alphas = new double[stockSize];
            betas = new double[requiredSize];
            //1. calculate betas and alphas
            calculateAlphasAndBetas();
            // 2. calculate remaining deltas
            int detlaCheckerX = -1;
            int deltaCheckerY = -1;
            for (int i = 0; i < transport.length; ++i) {
                for (int j = 0; j < transport[0].length; ++j) {
                    if (transport[i][j] == 0) {
                        deltas[i][j] = cost[i][j] - alphas[i] - betas[j];
                        if (deltas[i][j] > 0) {
                            detlaCheckerX = j;
                            deltaCheckerY = i;
                        }
                    } else {
                        deltas[i][j] = 0;
                    }
                }
            }
            // 3. check if solution can be optimized
            if (detlaCheckerX == -1) { //is optimal, no deltas >= 0
                break;
            }
            //4. optimize solution = move transport form cell to cell
            //if there was no movement, solution cant be found
            if(!moveTransport(detlaCheckerX, deltaCheckerY)) {
                break;
            }
        }
    }

    private void calculateAlphasAndBetas() {
        // 0. Tables to store which alphas and betas where calculated
        boolean [] calculatedA = new boolean[alphas.length];
        boolean [] calculatedB = new boolean[betas.length];
        // 1. last alpha = 0;
        alphas[alphas.length - 1] = 0;
        calculatedA[alphas.length - 1] = true;
        calculateBeta(alphas.length - 1, calculatedA, calculatedB); //calculate all betas that are using this alpha
    }

    private void calculateBeta(int index, boolean[] cA, boolean[] cB) {
        for (int i = 0; i < transport[index].length; ++i) {
            if (transport[index][i] > 0 && !cB[i]) {
                betas[i] = cost[index][i] - alphas[index];
                cB[i] = true;
                calculateAlpha(i, cA, cB); //calculate all alphas that are using this beta
            }
        }
    }

    private void calculateAlpha(int index, boolean[] cA, boolean[] cB) {
        for (int i = 0; i < transport.length; ++i) {
            if (transport[i][index] > 0 && !cA[i]) {
                alphas[i] = cost[i][index] - betas[index];
                cA[i] = true;
                calculateBeta(i, cA, cB); //calculate all alphas that are using this alpha
            }
        }
    }
    private boolean moveTransport(int x, int y) {
        //1. look for 2 cells with transport - one in the same row and one in the same column
        ArrayList<int[]> cellsInRow = new ArrayList<>();
        ArrayList<int[]> cellsInCollumn = new ArrayList<>();
        int[] cellInRow = new int[]{-1, -1};
        int[] cellInCollumn = new int[]{-1, -1};
        //looking in the same row
        //if there are more than one cell suitable, check quality of solution of each suitable cell
        for (int i = 0; i < transport[0].length; ++i) {
            if (transport[y][i] > 0) {
                cellInRow[0] = y;
                cellInRow[1] = i;
                cellsInRow.add(cellInRow);
            }
        }
        //looking in a column
        for (int i = 0; i < transport.length; ++i) {
            if (transport[i][x] > 0) {
                cellInCollumn[0] = i;
                cellInCollumn[1] = x;
                cellsInCollumn.add(cellInCollumn);
            }
        }
        //if there are no available cells - no change possible
        if (cellInRow[0] == -1|| cellInCollumn[0] == -1) {
            return false;
        }
        //check solutions
        for (int[] ints : cellsInCollumn) {
            for (int[] value : cellsInRow) {
                if(checkSolution(ints, value, x, y)) { //if change was made, return change
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkSolution(int[] cellInCollumn, int[] cellInRow, int x, int y) {
        double [][] transportCopy = new double[transport.length][transport[0].length];
        for (int i = 0; i < transportCopy.length; i++) {
            System.arraycopy(transport[i], 0, transportCopy[i], 0, transportCopy[0].length);
        }
        //2. check max value to move
        double max = Math.min(transportCopy[y][cellInRow[1]], transportCopy[cellInCollumn[0]][x]);
        //3. move the value = substract the value from cell in row and cell in column, and add value to the desired cell, and opposing cell
        transportCopy[y][cellInRow[1]] -= max;
        transportCopy[cellInCollumn[0]][x] -= max;
        transportCopy[y][x] += max;
        transportCopy[cellInCollumn[0]][cellInRow[1]] += max;
        double newProfit = 0;
        for (int i = 0; i < transportCopy.length; ++i) {
            for (int j = 0; j < transportCopy[0].length; ++j) {
                newProfit+= cost[i][j]*transportCopy[i][j];
            }
        }
        //if new profit>profit = successful change, save transport
        if (newProfit>profit) {
            profit = newProfit;
            transport = transportCopy;
            return true;
        }
        return false;
    }
}