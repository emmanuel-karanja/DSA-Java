package Arrays;

//*You are given two arrays of positive integers. Gas and Cost. Gas[i] is the amount of gas
//  at station i, and Cost[i] is the cost in fuel to travel from station i to station i+1. The stations are
// organized in a circuit route. Determine if thereis a starting
// point station where you can complete the circuit.
// INTUTION:
//  Determine if it's possible to complete the circuit from each station i*/
public class CanCompleteCircuit {

    public static int getStartingStation(int[] gas,int[] cost){
        if(gas==null || gas.length==0 || cost==null || cost.length==0){
            throw new IllegalArgumentException("Gas or Costs array is either empty or null.");
        }

        if(gas.length!=cost.length){
            throw new IllegalArgumentException("Cost and Gas arrays must be of the same length.");
        }

        int currentTank=0;
        int totalTank=0;
        int startStation=0;

        for(int i=0;i<gas.length;i++){
            //calculate net fuel for the station
            int netFuel=gas[i]-cost[i];

            //update tanks
            currentTank+=netFuel;
            totalTank+=netFuel;

            //if at this station i, the currentTank is less than 0, we can't proceed to station i+1, 
            // if it was 0 we could since we get to fuel here
            if(currentTank < 0){  
                startStation=i+1; //try the next station
                currentTank=0; //reset the currenTank    
            }
        }

        return totalTank >= 0 ? startStation: -1; 
    }
    
    public static void main(String[] args){
        int[] gas={1,2,3,4,5};
        int[] cost={3,4,5,1,2};

        System.out.println(getStartingStation(gas, cost));
    }
}
