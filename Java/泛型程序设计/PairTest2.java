import java.time.LocalDate;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.LocalAttribute;

public class PairTest2 {
    public static void main(String[] args) {
        LocalDate[] birthdays  = {
            LocalDate.of(1906, 12, 9),
            LocalDate.of(1815, 12, 10),
            LocalDate.of(1903, 12, 3),
            LocalDate.of(1910, 6, 22)
        };

    }
}

class ArrayAlg {
    public static <T extends Comparable> T minmax(T[] a) {
        if (a == null || a.length == 0 ) return null;
        T min = a[0];
        T max = a[0];
        for (int i = 0 ; i < a.length ; i ++) {
            if (min.compareTo(a[i]) > 0) min = a[i];
            if (max.compareTo(a[i]) < 0 ) max = a[i];
        }
        return new Pair<>(min, max);

    }
}