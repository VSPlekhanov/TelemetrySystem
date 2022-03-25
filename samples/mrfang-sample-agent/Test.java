import java.util.Scanner;

class Test {
    public static void main(String[] args) {
        System.out.println("Test");
        Scanner scanner = new Scanner(System.in);
        var line = scanner.nextLine();

        while (!line.equals("exit")) {
            System.out.println(line);
            line = scanner.nextLine();
        }
    }
}
