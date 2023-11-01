package terracraft;

public class TerraCraft {

    private static native String setup();

    private static native int mult(int a, int b);

    static {
        System.loadLibrary("terracraft");
    }

    public static void main(String[] args) {
        String output = setup();
        if (output.length() > 0) {
            Colors.error(output);
        } else {
            System.out.println("Setup completed successfully!");
        }

        System.out.println("Hello, TerraCraft, from the JVM!");
        System.out.println("1 + 2 = " + mult(1, 2));
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
