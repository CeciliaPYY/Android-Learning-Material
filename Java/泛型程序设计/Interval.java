import java.io.Serializable;
import java.time.LocalDate;

public class Interval implements Serializable {
    private Comparable lower;
    private Comparable upper;

    public Interval(Comparable first, Comparable second) {
        if (first.compareTo(second) <= 0) {
            lower = first;
            upper = second;
        } else {
            lower = second;
            upper = first;
        }
    }
}

class DateInterval extends Pair<LocalDate> {
    public void setSecond(LocalDate second) {
        if (second.compareTo(getFirst()) > 0 ) {
            super.setSecond(second);
        }
    }
}

class DateInterval extends Pair {
    public void setSecond(LocalDate second) {
    }
}

