package logic;

import java.util.Arrays;

public class MainLogic {
    double[] supply;
    double[] demand;
    double[] purchasePrices ;
    double[] sellPrices;
    double[][] transportCostsTable;
    double[][] profitTable;
    double[][] reverseProfitTable;
    double[][] resultTable;
    int n_suppliers, n_customers;

    double profit = 0., cost_purchase = 0., cost_transport = 0., income_from_sell = 0.;
    double[][] individual_profit, optimal_transport;
    public MainLogic(int n_suppliers_in, int n_customers_in, double[] supply, double[] demand, double[] purchasePrices, double[] sellPrices, double[][] transportCostsTable) {
        this.n_suppliers = n_suppliers_in + 1;
        this.n_customers = n_customers_in + 1;
        //init arrays
        this.supply = new double[n_suppliers];
        this.demand = new double[n_customers];
        this.purchasePrices = new double[n_suppliers];
        this.sellPrices = new double[n_customers];
        this.transportCostsTable = new double[n_suppliers][n_customers];
        this.profitTable = new double[n_suppliers][n_customers];
        this.reverseProfitTable = new double[n_suppliers][n_customers];
        this.resultTable = new double[n_suppliers][n_customers];
        //copy values - hard way
        for (int s = 0; s < n_suppliers; s++) {
            for (int c = 0; c < n_customers; c++) {
                this.resultTable[s][c] = 0.;
                if (s == this.n_suppliers-1 || c == this.n_customers-1) { //dummies
                    this.transportCostsTable[s][c] = 0;
                }
                else { //rest
                    this.transportCostsTable[s][c] = transportCostsTable[s][c];
                }
            }
        }
        for (int s = 0; s < n_suppliers; s++) {
            if (s == this.n_suppliers-1 ) { //dummies
                this.supply[s] = Arrays.stream(demand).sum();
                this.purchasePrices[s] = 0;
            }
            else { //rest
                this.supply[s] = supply[s];
                this.purchasePrices[s] = purchasePrices[s];
            }
        }
        for (int c = 0; c < n_customers; c++) {
            if (c == this.n_customers-1 ) { //dummies
                this.demand[c] =  Arrays.stream(supply).sum();;
                this.sellPrices[c] = 0;
            }
            else { //rest
                this.demand[c] = demand[c];
                this.sellPrices[c] = sellPrices[c];
            }
        }
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
        individual_profit = new double[n_suppliers-1][n_customers-1];
        optimal_transport = new double[n_suppliers-1][n_customers-1];
        System.out.println("individual profit= ");
        for (int s = 0; s < n_suppliers - 1; s++) { //without last
            for (int c = 0; c < n_customers - 1; c++) { //without last
                System.out.print(profitTable[s][c] + " \t");
                individual_profit[s][c] = profitTable[s][c];
            }
            System.out.println();
        }
        System.out.println("optimal transport= ");
        for (int s = 0; s < n_suppliers - 1; s++) { //without last
            for (int c = 0; c < n_customers - 1; c++) { //without last
                System.out.print(resultTable[s][c]+" \t");
                optimal_transport[s][c] = resultTable[s][c];
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

    public double getProfit() { return profit; }
    public double getCost_purchase() { return cost_purchase; }
    public double getCost_transport() { return cost_transport; }
    public double getIncome_from_sell() { return income_from_sell; }
    public double[][] getIndividual_profit() { return individual_profit; }
    public double[][] getOptimal_transport() { return optimal_transport; }
}
