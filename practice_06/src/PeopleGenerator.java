import java.util.Random;

public class PeopleGenerator implements Runnable {
    Random random = new Random(System.nanoTime());

    Building building;
    private final int numberOfFloors;

    PeopleGenerator(final Building building) {
        this.building = building;
        this.numberOfFloors = building.getNumberOfFloors();
    }

    public void run() {
        while (!Thread.interrupted()) {
            int floor = (random.nextInt(0, numberOfFloors));
            int people = random.nextInt(1, 5);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            for (int i = 0; i < people; ++i) {
                int destination = random.nextInt(0, numberOfFloors);
                if (destination == floor) {
                    destination = ++destination % numberOfFloors;
                }
                ElevatorEvent.Directions direction = (destination > floor) ? ElevatorEvent.Directions.UP : ElevatorEvent.Directions.DOWN;
                building.getFloor(floor).queue.add(new ElevatorEvent(destination, direction));
            }
            random.setSeed(System.nanoTime());
        }
    }
}
