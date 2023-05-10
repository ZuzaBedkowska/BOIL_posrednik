package logic;
import java.util.LinkedList;
public class TransportationProblem {
    double []required;
    double []stock;
    double [][]cost;
    LinkedList<Variable> feasible = new LinkedList<Variable>();
    int stockSize;
    int requiredSize;

    public TransportationProblem(int stockSize, int requiredSize ){
        this.stockSize = stockSize;
        this.requiredSize = requiredSize;

        stock = new double[stockSize];
        required = new double[requiredSize];
        cost = new double[stockSize][requiredSize];

        for(int i=0; i < (requiredSize + stockSize -1); i++)
            feasible.add(new Variable());
    }

    public void setSupply(double value, int index) { stock[index] = value; }
    public void setDemand(double value, int index) { required[index] = value; }
    public void setCost(double value, int stock, int required) { cost[stock][required] = value; }
    public LinkedList<Variable> getFeasible() { return feasible; }

    public double northWestCorner() {
        long start = System.nanoTime();
        double min;
        int k = 0; //feasible solutions counter
        //isSet is responsible for annotating cells that have been allocated
        boolean [][]isSet = new boolean[stockSize][requiredSize];
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0;  i < stockSize; i++)
                isSet[i][j] = false;
        //the for loop is responsible for iterating in the 'north-west' manner
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0;  i < stockSize; i++)
                if(!isSet[i][j]){
                    //allocating stock in the proper manner
                    min = Math.min(required[j], stock[i]);
                    feasible.get(k).setRequired(j);
                    feasible.get(k).setStock(i);
                    feasible.get(k).setValue(min);
                    k++;

                    required[j] -= min;
                    stock[i] -= min;

                    //allocating null values in the removed row/column
                    if(stock[i] == 0)
                        for(int l = 0; l < requiredSize; l++)
                            isSet[i][l] = true;
                    else
                        for(int l = 0; l < stockSize; l++)
                            isSet[l][j] = true;
                }
        return (System.nanoTime() - start) * 1.0e-9;
    }
    public double leastCostRule() {
        long start = System.nanoTime();

        double min;
        int k = 0; //feasible solutions counter

        //isSet is responsible for annotating cells that have been allocated
        boolean [][]isSet = new boolean[stockSize][requiredSize];
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0;  i < stockSize; i++)
                isSet[i][j] = false;

        int i = 0, j = 0;
        Variable minCost = new Variable();

        //this will loop is responsible for candidating cells by their least cost
        while(k < (stockSize + requiredSize - 1)){

            minCost.setValue(Double.MAX_VALUE);
            //picking up the least cost cell
            for (int m = 0;  m < stockSize; m++)
                for (int n = 0; n < requiredSize; n++)
                    if(!isSet[m][n])
                        if(cost[m][n] < minCost.getValue()){
                            minCost.setStock(m);
                            minCost.setRequired(n);
                            minCost.setValue(cost[m][n]);
                        }

            i = minCost.getStock();
            j = minCost.getRequired();

            //allocating stock in the proper manner
            min = Math.min(required[j], stock[i]);

            feasible.get(k).setRequired(j);
            feasible.get(k).setStock(i);
            feasible.get(k).setValue(min);
            k++;

            required[j] -= min;
            stock[i] -= min;

            //allocating null values in the removed row/column
            if(stock[i] == 0)
                for(int l = 0; l < requiredSize; l++)
                    isSet[i][l] = true;
            else
                for(int l = 0; l < stockSize; l++)
                    isSet[l][j] = true;
        }
        return (System.nanoTime() - start) * 1.0e-9;
    }
}