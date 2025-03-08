package writeon.api.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@UtilityClass
public class LogUtil {

  private static final Logger LOG = LoggerFactory.getLogger(LogUtil.class);

  public void debug(String msg) {
    LOG.info(msg);
  }
  public void debug(Throwable throwable) {
    LOG.info(getStackTrace(throwable));
  }

  public void trace(String msg) {
    LOG.trace(msg);
  }
  public void trace(Throwable throwable) {
    LOG.trace(getStackTrace(throwable));
  }

  public void info(String msg) {
    LOG.info(msg);
  }
  public void info(Throwable throwable) {
    LOG.info(getStackTrace(throwable));
  }

  public void warn(String msg) {
    LOG.warn(msg);
  }
  public void warn(Throwable throwable) {
    LOG.warn(getStackTrace(throwable));
  }

  public void error(String msg) {
    LOG.error(msg);
  }
  public void error(Throwable throwable) {
    LOG.error(getStackTrace(throwable));
  }

  private String getStackTrace(Throwable throwable) {
    if (throwable == null) {
      return "No exception provided.";
    }
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    throwable.printStackTrace(printWriter);
    return stringWriter.toString();
  }

}
