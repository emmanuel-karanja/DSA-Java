package Arrays;
import java.util.Arrays;

/**There are cars travelling to a given destination target on a single lane road. A car's starting position is given by
 * position[i] and its speed speed[i].
 * 
 * Afleet is a group of cars moving together at the same speed where a faster car has vaught up with the slower car.
 * 
 * Given a positions and speed array, find the number of fleets that get to the target destination.
 * 
 * INTUTION:
 * 
 * Look at the time for the last fleet to reach the destination. 
 * 
 * Arrange the cars by distance from the target. i.e. descending order.
 * Calculate the time each car takes to reach the target, keep track of the time for the previous
 *  car. if currentCarTime > previousTargetTime it forms a new fleet.  Otherwise, it's part of the fleet
 *  that will arriveat previousTargetTime
 * 
 * This is because cars that will arrive together have a fleetTime less or equal to the current fleet time.
 * i.e. faster ones will catch up with slower ones and slow down to that speed.
 */

class Car{
    public int position;
    public int speed;

    public Car(int position, int speed){
        this.position=position;
        this.speed=speed;
    }
}

public class CarFleet {
    
    public static int getFleetCount(int[] distance, int[] speed, int target){
        if(distance==null || distance.length==0){
            throw new IllegalArgumentException("Distance can't be empty or null.");
        }

        if(speed==null || speed.length==0){
            throw new IllegalArgumentException("Distance can't be empty or null.");
        }

        if(speed.length !=distance.length){
            throw new IllegalArgumentException("Speed and distance lengths must match.");
        }

        //combine
        Car[] cars=new Car[speed.length];
        for(int i=0;i<speed.length;i++){
            cars[i]=new Car(distance[i],speed[i]);
        }

        //sort descending order
        Arrays.sort(cars, (a, b) -> Integer.compare(b.position, a.position));

        int fleetCount=0;
        int currentFleetTime=0;
        for(Car car: cars){
            //get time
            int time=(target-car.position)/car.speed;

            //do we need a new fleet?
            if(time > currentFleetTime){
                fleetCount++;
                currentFleetTime=time;
            }
        }

        return fleetCount;
    }

    public static void main(String[] args){
        int target=12;
        int[] positions={10,8,0,5,3};
        int[] speed={2,4,1,1,3};

        System.out.println(getFleetCount(positions, speed, target));
    }
}
