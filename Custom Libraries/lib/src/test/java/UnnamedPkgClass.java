import java.util.Arrays;

public class UnnamedPkgClass {
    public static void main(String[] args) {
        System.out.print(sum(1,3,4,7,3,4,3));
        System.out.print(fib(10));
    }
    public static int fib(int num) {
        return num == 0 || num == 1 ? num : (num == 2 ? 1 : fib(num-1)
                                                          + fib(num-2));
    }
    public static int add(int a, int b) {
        return a + b;
    }
    public static int sum(int... args) {
        int sum = 0;
        if (args.length > 0) {
            if (args.length > 1) {
                sum = add(args[0], sum(Arrays.copyOfRange(args, 1, args.length)));
            } else {
                sum = args[0];
            }
        }
        return sum;
    }
}
