package com.writely.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@UtilityClass
public class LogUtil {

  private static final Logger LOG = LoggerFactory.getLogger(LogUtil.class);

  public void debug(String msg) {
    LOG.info(msg);
  }

  public void trace(String msg) {
    LOG.trace(msg);
  }

  public void info(String msg) {
    LOG.info(msg);
  }

  public void warn(String msg) {
    LOG.warn(msg);
  }

  public void error(String msg) {
    LOG.error(msg);
  }
}
