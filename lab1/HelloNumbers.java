public class HelloNumbers {
    public static void main(String[] args) {
        int i = 0;
        while (i < 10) {
            int sum = 0;
            for (int x = 0; x <= i; x++) {
                sum += x;
            }
            i++;
            System.out.println(sum);
        }
    }
}
