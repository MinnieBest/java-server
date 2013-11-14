import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ServerTest.class,
  ArgumentsTest.class,
  SocketHandlerTest.class,
  RequestTest.class,
  RequestHandlerTest.class,
  ResponseTest.class,
  FileResponseTest.class,
  TextResponseTest.class
})

public class AllTests {
  // the class remains empty,
  // used only as a holder for the above annotations
}
