package logic;


public class MainLogic {
    double[] supply = {45, 25};
    double[] demand = {30, 30};
    double[] purchasePrices = {6, 7};
    double[] sellPrices = {12, 13};
    double[][] transportCostsTable = {{7, 3}, {4, 5}};
    double[][] profitTable;
    double[][] reverseProfitTable;
    int n_suppliers, n_customers;

    public void test(){
        calc();
    }

    public void calc(){
        calcProfitTable();
    }

    private void calcProfitTable(){
        //dumbest approach ever
        double maxValue = Double.MIN_VALUE;
        for(int s = 0; s < n_suppliers; s++)
            for (int c = 0; c < n_customers; c++)
                maxValue = Math.max(profitTable[s][c],maxValue);
        for(int s = 0; s < n_suppliers; s++)
            for (int c = 0; c < n_customers; c++) {
                profitTable[s][c] = sellPrices[c] - profitTable[s][c] - purchasePrices[s];
                reverseProfitTable[s][c] = maxValue - profitTable[s][c];
            }
    }


}
