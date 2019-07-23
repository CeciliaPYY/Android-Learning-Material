public class Pair<Object> {
    private Object first;
    private Object second;

    public Pair() {
        first = null;
        second = null;
    }

    public Pair(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    public Object getFirst() {
        return first;
    }

    public Object getSecond() {
        return second;
    }

    public void setFirst(Object newValue) {
        first = newValue;
    }

    public void setSecond(Object newValue) {
        second = newValue;
    }
}