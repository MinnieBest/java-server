import java.util.Arrays;

public class OptionParser {

    private String[] args;

    public OptionParser(String[] args) {
        this.args = args;
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
