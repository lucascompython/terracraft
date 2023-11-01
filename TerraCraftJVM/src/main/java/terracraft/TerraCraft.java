package terracraft;

public class TerraCraft {

    private static native String setup();

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
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
