import java.util.Arrays;

public class Arguments {
    public static final int DEFAULT_PORT = 5000;
    public static final String DEFAULT_DIR = System.getProperty("user.dir");

    private String[] args;

    public Arguments(String[] args) {
        this.args = args;
    }

    public int port() {
        int port = DEFAULT_PORT;
        if (hasFlag("-p")) {
            port = Integer.parseInt(getFlagValue("-p"));
        }
        return port;
    }

    public String directory() {
        String directory = DEFAULT_DIR;
        if (hasFlag("-d")) {
            directory = getFlagValue("-d");
        }
        return directory;
    }

    public Boolean hasFlag(String flag) {
        int index = Arrays.asList(args).indexOf(flag);
        return (index != -1);
    }

    public String getFlagValue(String flag) {
        int flagIndex = getFlagIndex(flag);
        return args[flagIndex];
    }

    public int getFlagIndex(String flag) {
        int index = Arrays.asList(args).indexOf(flag);
        return index + 1;
    }
}
