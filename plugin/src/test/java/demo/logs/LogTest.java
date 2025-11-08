package demo.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {
    // Create a logger for this class
    private static final Logger logger = LogManager.getLogger(LogTest.class);

    // Get logger instance
    public static Logger getLogger() {
        return logger;
    }
}
