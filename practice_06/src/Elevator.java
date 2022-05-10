import java.util.*;

public class Elevator implements Runnable {
    private static int counter = 0;

    private enum States {
        CALM, UP, DOWN
    }

    private final int id;

    private final int timeToPassFloor = 1000; // milliseconds

    private final int capacity;
    private final Building building;

    private int passengers = 0;
    private int currentFloor = 0;
    private States state = States.CALM;
    private final PriorityQueue<ElevatorEvent> tasks;

    public Elevator(int capacity, Building building) {
        this.id = counter++;
        this.capacity = capacity;
        /*
          PQ stores future tasks for elevator.
          To select the next target floor we'll use greedy-like algorithm.
          Given current floor PQ stores at the top the closest floor to go.
          This way, every time we need to deliver next passenger we will pick top of the PQ (the closest floor).
         */
        this.tasks = new PriorityQueue<>((event1, event2) ->
                Math.abs(currentFloor - event1.getDestination()) - Math.abs(currentFloor - event2.getDestination()));
        this.building = building;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getPassengers() {
        return passengers;
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void moveElevator(int to) {
        if (to < currentFloor) {
            state = States.DOWN;
        } else if (to > currentFloor) {
            state = States.UP;
        }
        if (state == States.UP) {
            while (currentFloor != to) {
                sleep(timeToPassFloor);
                ++currentFloor;
                if (passengers != capacity) {
                    /*
                      On way to the target floor we will try to stake out every floor we're passing
                      If approachingFloor is -1 and Queue of staked floor isn't empty we will take passengers from
                      this floor.
                     */
                    if (building.getFloor(currentFloor).tryToStakeOutFloor(this.id)) {
                        Queue<ElevatorEvent> queue = building.getFloor(currentFloor).queue;
                        for (ElevatorEvent i : queue) {
                            if (i.getDirection() == ElevatorEvent.Directions.UP) {
                                this.tasks.add(i);
                                /*
                                  Mark taken ElevatorEvents in order to delete them in future
                                 */
                                i.setDirection(null);
                                ++passengers;
                                if (passengers == capacity) {
                                    break;
                                }
                            }
                        }
                        /*
                          Deleting marked earlier Events
                         */
                        queue.removeIf(value ->
                                value.getDirection() == null);
                        /*
                          Some of taken Events can be closer than the main target
                          So, we're checking our PQ to make sure that we'll visit the nearest floor
                         */
                        to = (this.tasks.isEmpty()) ? to : this.tasks.peek().getDestination();
                        building.getFloor(currentFloor).setApproachingElevator(-1);
                    }
                }
            }
        } else if (state == States.DOWN) {
            while (currentFloor != to) {
                sleep(timeToPassFloor);
                --currentFloor;
                if (passengers != capacity) {
                    if (building.getFloor(currentFloor).tryToStakeOutFloor(this.id)) {
                        Queue<ElevatorEvent> queue = building.getFloor(currentFloor).queue;
                        for (ElevatorEvent i : queue) {
                            if (i.getDirection() == ElevatorEvent.Directions.DOWN) {
                                this.tasks.add(i);
                                i.setDirection(null);
                                ++passengers;
                                if (passengers == capacity) {
                                    break;
                                }
                            }
                        }
                        queue.removeIf(value ->
                                value.getDirection() == null);
                        to = (this.tasks.isEmpty()) ? to : this.tasks.peek().getDestination();
                        building.getFloor(currentFloor).setApproachingElevator(-1);
                    }
                }
            }
        }
        state = States.CALM;
    }

    public void run() {
        while (!Thread.interrupted()) {
            if (tasks.isEmpty()) {
                int pickUpFloor = -1;
                /*
                  Elevator traverse all the floors in unusual way.
                  For example, if we have 5-storey building and currentFloor == 3
                  Elevator will search for the next task not like 0 1 2 3 4, but 3 4 0 1 2
                  It's done in order not to leave top floors unvisited long time
                 */
                {
                    int i = currentFloor;
                    do {
                        if (building.getFloor(i).tryToStakeOutFloor(this.id)) {
                            pickUpFloor = i;
                            break;
                        }
                        ++i;
                        i %= building.getNumberOfFloors();
                    } while (i != currentFloor);
                }
                /*
                  If nothing has found continue while
                 */
                if (pickUpFloor == -1) {
                    continue;
                }
                moveElevator(pickUpFloor);
                while (passengers != capacity && !building.getFloor(pickUpFloor).queue.isEmpty()) {
                    this.tasks.add(building.getFloor(pickUpFloor).queue.poll());
                    ++passengers;
                }
                building.getFloor(pickUpFloor).setApproachingElevator(-1);
            } else {
                int deliverFloor = tasks.peek().getDestination();
                while (!tasks.isEmpty() && (deliverFloor = tasks.peek().getDestination()) == currentFloor)
                {
                    tasks.poll();
                    --passengers;
                }
                moveElevator(deliverFloor);
                tasks.poll();
                --passengers;
            }
        }
    }
}
