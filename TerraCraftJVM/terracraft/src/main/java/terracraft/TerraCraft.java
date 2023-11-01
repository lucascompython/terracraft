package terracraft;

public class TerraCraft {

    private static native String setup();

    private static native int addRust(int a, int b);

    static {
        System.loadLibrary("terracraft");
        // System.loadLibrary("terracraftnet");
    }

    public static void main(String[] args) {
        String output = setup();
        if (output.length() > 0) {
            Colors.error(output);
        } else {
            System.out.println("Setup completed successfully!");
        }

        System.out.println("Hello, TerraCraft, from the JVM!");
        System.out.println("1 + 2 = " + addRust(1, 2));
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
