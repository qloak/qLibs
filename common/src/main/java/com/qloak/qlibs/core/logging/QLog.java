package com.qloak.qlibs.core.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// thin wrapper so i don't have to type LoggerFactory everywhere
public final class QLog {
    private final Logger log;

    private QLog(String name) {
        this.log = LoggerFactory.getLogger(name);
    }

    public static QLog create(String modId) {
        return new QLog(modId);
    }

    public static QLog create(Class<?> clazz) {
        return new QLog(clazz.getSimpleName());
    }

    public void info(String msg, Object... args) { log.info(msg, args); }
    public void warn(String msg, Object... args) { log.warn(msg, args); }
    public void error(String msg, Object... args) { log.error(msg, args); }
    public void error(String msg, Throwable t) { log.error(msg, t); }
    public void debug(String msg, Object... args) { log.debug(msg, args); }
    public void trace(String msg, Object... args) { log.trace(msg, args); }

    public Logger unwrap() { return log; }
}
