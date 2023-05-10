package logic;
public class MainLogic {
    double[] supply = {45, 25, 60};
    double[] demand = {30, 30, 70};
    double[] purchasePrices = {6, 7, 0};
    double[] sellPrices = {12, 13, 0};
    double[][] transportCostsTable = {{7, 4, 0}, {3, 5, 0}, {0, 0, 0}};
    double[][] profitTable;
    double[][] reverseProfitTable;
    double[][] resultTable;
    int n_suppliers, n_customers;
    public MainLogic() {
        n_suppliers=2 + 1;
        n_customers=2 + 1;
        profitTable = new double[n_suppliers][n_customers];
        reverseProfitTable = new double[n_suppliers][n_customers];
        resultTable = new double[n_suppliers][n_customers];
        for (int s = 0; s < n_suppliers; s++)
            for (int c = 0; c <n_customers; c++)
                resultTable[s][c]=0.;
    }
    public void test(){
        calc();
    }
    public void calc(){
        calcProfitTable();
        TransportationProblem transportationProblem = new TransportationProblem(n_suppliers, n_customers); //init
        //add supplies
        for (int s = 0; s < n_suppliers; s++)
            transportationProblem.setSupply(supply[s], s);
        //add demands
        for (int c = 0; c < n_customers; c++)
            transportationProblem.setDemand(demand[c], c);
        //add transportation costs
        for (int s = 0; s < n_suppliers; s++)
            for (int c = 0; c < n_customers; c++)
                transportationProblem.setCost(reverseProfitTable[s][c], s, c);
        //calc this thing
        //transportationProblem.northWestCorner();
        transportationProblem.leastCostRule();
        //get solution, save to resultTable
        for (Variable f: transportationProblem.feasible){
            resultTable[f.getStock()][f.getRequired()] = f.getValue();
        }
        //calc profit
        System.out.println("individual profit= ");
        for (int s = 0; s < n_suppliers - 1; s++) { //without last
            for (int c = 0; c < n_customers - 1; c++) { //without last
                System.out.print(profitTable[s][c] + " \t");
            }
            System.out.println();
        }
        System.out.println("optimal transport= ");
        double profit = 0., cost_transport = 0., cost_purchase = 0., income_from_sell = 0.;
        for (int s = 0; s < n_suppliers - 1; s++) { //without last
            for (int c = 0; c < n_customers - 1; c++) { //without last
                System.out.print(resultTable[s][c]+" \t");
                profit += resultTable[s][c] * profitTable[s][c];
                cost_purchase += resultTable[s][c] * purchasePrices[s];
                income_from_sell += resultTable[s][c] * sellPrices[c];
                cost_transport += resultTable[s][c] * transportCostsTable[s][c];
            }
            System.out.println();
        }
        System.out.println("cost_purchase    = "+cost_purchase);
        System.out.println("cost_transport   = "+cost_transport);
        System.out.println("income_from_sell = "+income_from_sell);
        System.out.println("profit           = "+profit);
    }
    private void calcProfitTable(){
        //dumbest approach ever
        for(int s = 0; s < n_suppliers; s++)
            for (int c = 0; c < n_customers; c++)
                if (s == n_suppliers-1 || c == n_customers-1) //dummy suppliers and customers
                    profitTable[s][c] = 0.;
                else
                    profitTable[s][c] = sellPrices[c] - transportCostsTable[s][c] - purchasePrices[s];

        double maxValue = Double.MIN_VALUE;
        for(int s = 0; s < n_suppliers; s++)
            for (int c = 0; c < n_customers; c++)
                maxValue = Math.max(profitTable[s][c],maxValue);

        for(int s = 0; s < n_suppliers; s++)
            for (int c = 0; c < n_customers; c++)
                reverseProfitTable[s][c] = maxValue - profitTable[s][c];
    }
}
