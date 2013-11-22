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
  FileBodyTest.class,
  TextBodyTest.class,
  ControllerTest.class,
  FileControllerTest.class,
  DirectoryControllerTest.class,
  OptionsControllerTest.class,
  ParamControllerTest.class,
  RedirectControllerTest.class,
  FormControllerTest.class,
  LogsControllerTest.class,
  InputControllerTest.class,
  HTMLEncoderTest.class,
  RequestLoggerTest.class
})

public class AllTests {
  // the class remains empty,
  // used only as a holder for the above annotations
}
