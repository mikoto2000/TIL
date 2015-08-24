package jp.dip.oyasirazu.study.log.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);

        logger.info("info message : {}", "TESTING.");
        logger.debug("debug message : {}", "TESTING.");
        logger.warn("warn message : {}", "TESTING.");
        logger.error("error message : {}", "TESTING.");
    }
}
