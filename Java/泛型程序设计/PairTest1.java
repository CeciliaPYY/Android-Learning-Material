public class PairTest1 {
    public static void main(String[] args) {
        String[] words = {"Mary", "had", "a", "little", "lamb"};
        Pair<String> mm = ArrayAlg.minmax(words);
        System.out.println(mm.getFirst());
        System.out.println(mm.getSecond());
        
    }

    
}

class ArrayAlg {

    public static Pair<String> minmax(String[] a) {
        if (a == null || a.length == 0) {
            return null;
        }

        String min = a[0];
        String max = a[0];
        for (int i = 0; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) {
                min = a[i];
            }
            if (max.compareTo(a[i]) < 0) {
                max = a[i];
            }
        }
        return new Pair<>(min, max);
    }

    public static <T> T getMiddle(T... a) {
        return a[a.length / 2];
    }

    public static <T> T min(T[] a) {
        if (a == null || a.length == 0 ) return null;
        T smallest = a[0];
        for (int i = 0; i < a.length ; i++) {
            if (smallest.compareTo(a[i]) > 0 ) {
                smallest = a[i];
            }
        }
        return smallest;
    }
}