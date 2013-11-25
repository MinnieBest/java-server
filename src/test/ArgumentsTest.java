import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import kevin.directory_app.*;

@RunWith(JUnit4.class)
public class ArgumentsTest {

    private Arguments arguments = new Arguments(new String[] {"-myFlag", "myValue"});

    @Test
    public void returnsCorrectIndex() {
        assertEquals(1, arguments.getFlagIndex("-myFlag"));
    }

    @Test
    public void returnsCorrecValue() {
        assertEquals("myValue", arguments.getFlagValue("-myFlag"));
    }

    @Test
    public void returnsFlaggedPort() {
        Arguments arguments = new Arguments(new String[] {"-p", "9393"});
        assertEquals(9393, arguments.port());
    }

    @Test
    public void returnsDefaultPort() {
       Arguments arguments = new Arguments(new String[] {"-d", "testing"});
        assertEquals(5000, arguments.port());
    }

    @Test
    public void returnsFlaggedDirectory() {
        Arguments arguments = new Arguments(new String[] {"-d", "/test/here"});
        assertEquals("/test/here", arguments.directory());
    }

    @Test
    public void returnsDefaultDirectory() {
        Arguments arguments = new Arguments(new String[] {"-p", "6789"});
        assertEquals("/Users/kevinbuchanan/programs/java/server", arguments.directory());
    }
}

