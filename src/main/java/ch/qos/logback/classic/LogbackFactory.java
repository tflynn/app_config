package ch.qos.logback.classic;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.Logger;

public class LogbackFactory {

	
	/**
	 * Get a Logback logger
	 * 
	 * @param klass Class for logger
	 * 
	 * @return Logback logger instance
	 */
	public static Logger getLogger(Class klass) {
		return LogbackFactory.getLogger(klass.getCanonicalName());
	}
	
	/**
	 * Get a Logback logger
	 * 
	 * @param className ClassName for logger
	 * 
	 * @return Logback logger instance
	 */
	public static Logger getLogger(String className) {
		LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
		Logger logger = loggerContext.getLogger(className);
		return logger;
	}
}
