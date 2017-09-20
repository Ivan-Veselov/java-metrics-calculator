public class PrintMe {
    public static int gcd (final int a, final int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
}
