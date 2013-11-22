import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ServerTest.class,
  ArgumentsTest.class,
  SocketHandlerTest.class,
  RequestTest.class,
  ResponseTest.class,
  FileBodyTest.class,
  TextBodyTest.class,
  NotFoundHandlerTest.class,
  FileHandlerTest.class,
  DirectoryHandlerTest.class,
  OptionsHandlerTest.class,
  ParamHandlerTest.class,
  RedirectHandlerTest.class,
  FormHandlerTest.class,
  InputHandlerTest.class,
  HTMLEncoderTest.class,
  RequestLoggerTest.class
})

public class AllTests {
  // the class remains empty,
  // used only as a holder for the above annotations
}
