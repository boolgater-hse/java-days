public class BuildingLogger implements Runnable {
    private final Building building;

    BuildingLogger(final Building building) {
        this.building = building;
    }

    private int maxWaiting = Integer.MIN_VALUE;

    public void run() {
        while (!Thread.interrupted()) {
            for (int i = building.getNumberOfFloors() - 1; i >= 0; --i) {
                System.out.print("Floor: " + i + " ");
                System.out.print("Wait: " + building.getFloor(i).getFloorQueueSize() + " ");
                maxWaiting = Math.max(building.getFloor(i).getFloorQueueSize(), maxWaiting);
                System.out.print("Here:");
                for (int j = 0; j < building.getNumberOfElevators(); ++j) {
                    if (building.getElevator(j).getCurrentFloor() == i) {
                        System.out.print(" " + j);
                    }
                }
                System.out.println();
            }
            for (int i = 0; i < building.getNumberOfElevators(); ++i) {
                System.out.print("Elevator: " + i + " ");
                System.out.print("Load: " + building.getElevator(i).getPassengers());
                System.out.println();
            }
            System.out.println("Max number of waiters was " + maxWaiting);

            System.out.println("Press 'q' + Enter to stop simulation");
            System.out.print("\033[H\033[2J");
        }
        System.out.print("\033[H\033[2J");
    }
}
