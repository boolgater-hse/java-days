import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");

        MapIterator<Integer, String> it = new MapIterator<>(map);

        it.next();
        it.remove();
        while (it.hasNext())
        {
            System.out.println(it.next());
        }
        it.remove();
    }
}
