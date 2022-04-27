public class ElevatorEvent {
    public enum Directions {
        UP, DOWN, NULL
        /* NULL is needed to mark
           already checked Events
           in order to remove them
           in future */
    }

    private final int destination;

    private Directions direction;

    public ElevatorEvent(int destination, Directions direction) {
        this.destination = destination;
        this.direction = direction;
    }

    public int getDestination() {
        return destination;
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }
}
