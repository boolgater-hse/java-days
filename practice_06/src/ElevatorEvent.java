public class ElevatorEvent {
    public enum Directions {
        UP, DOWN
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
