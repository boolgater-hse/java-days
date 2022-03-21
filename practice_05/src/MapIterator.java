import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MapIterator <K, V> {
    private final HashMap<K, V> data;
    private final Iterator<K> it;

    private boolean isNextCalled = false;
    private boolean isRemoveCalled = false;

    public MapIterator(HashMap<K, V> map) {
        this.data = map;
        this.it = map.keySet().iterator();
    }

    public String next() {
        if (!it.hasNext()) {
            throw new NoSuchElementException("No such an element");
        }

        K temp = it.next();
        isNextCalled = true;
        isRemoveCalled = false;

        return data.get(temp).toString();
    }

    public boolean hasNext() {
        return it.hasNext();
    }

    public void remove() {
        if (!isNextCalled || isRemoveCalled) {
            throw new IllegalStateException("Illegal state of iterator");
        }

        it.remove();
        isNextCalled = false;
        isRemoveCalled = true;
    }
}
