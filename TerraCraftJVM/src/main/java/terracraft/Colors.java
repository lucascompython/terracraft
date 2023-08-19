package terracraft;

public class Colors {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";
    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";

    public static final void print(String color, String text) {
        System.out.println(String.format("%s%s%s", color, text, RESET));
    }

    public static final void error(String text) {
        System.err.println(String.format("%sError:%s %s", RED, RESET, text));
    }

    public static final void warning(String text) {
        System.err.println(String.format("%sWarning:%s %s", YELLOW, RESET, text));
    }

    public static final void info(String text) {
        System.out.println(String.format("%sInfo:%s %s", BLUE, RESET, text));
    }

    public static final void success(String text) {
        System.out.println(String.format("%sSuccess:%s %s", GREEN, RESET, text));
    }
}
