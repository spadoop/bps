/*** Bugfix, override ***/
package com.eos.system.logging;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Hierarchy;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RootLogger;
import org.apache.log4j.xml.DOMConfigurator;
import org.w3c.dom.Element;

import com.eos.system.utility.FilenameUtil;
import com.eos.system.utility.StringUtil;
import com.primeton.ext.system.logging.LogRepositoryWeakMap;

public class LogRepository {
	private LoggerRepository _log4jRepository = null;

	private Map<String, Logger> createdLoggerCache = new ConcurrentHashMap();

	private String _logConfigFile = null;

	public String getLogConfigFile() {
		return this._logConfigFile;
	}

	public void setLogConfigFile(String configFile) {
		if (StringUtil.isNullOrBlank(configFile)) {
			return;
		}
		this._logConfigFile = FilenameUtil.normalizeInUnixStyle(configFile);
		LogRepositoryWeakMap.put(this._logConfigFile, this);
	}

	public LoggerRepository getLog4jRepository() {
		return this._log4jRepository;
	}

	public static LogRepository create(String configFileLoacation) {
		LogRepository r = new LogRepository();
		r.configure(configFileLoacation);
		return r;
	}

	public static LogRepository create(URL configFileURL) {
		LogRepository r = new LogRepository();
		r.configure(configFileURL);
		return r;
	}

	public static LogRepository create(File configFile) {
		LogRepository r = new LogRepository();
		r.configure(configFile);
		return r;
	}

	public static LogRepository create(Properties log4jConfigProp) {
		LogRepository r = new LogRepository();
		r.configure(log4jConfigProp);
		return r;
	}

	public static LogRepository create(Element log4jConfigElem) {
		LogRepository r = new LogRepository();
		r.configure(log4jConfigElem);
		return r;
	}

	public static LogRepository create(Log4jConfiguration log4jConfig) {
		LogRepository r = new LogRepository();
		r.configure(log4jConfig);
		return r;
	}

	public synchronized void configure(Log4jConfiguration log4jConfig) {
		if (log4jConfig != null) {
			log4jConfig.apply(this);
			LogRepositoryWeakMap.put(this._logConfigFile, this);
		}
	}

	public synchronized void configure(URL resolvedLocationURL) {
		if (resolvedLocationURL == null) {
			return;
		}
		configure(resolvedLocationURL.getFile());
	}

	public synchronized void configure(File log4jConfigFile) {
		if (log4jConfigFile == null) {
			return;
		}

		configure(log4jConfigFile.getAbsolutePath());
	}

	public synchronized void configure(String log4jConfigFileLoacation) {
		this._log4jRepository = reNewLoggerRepository(this._log4jRepository);
		if (log4jConfigFileLoacation == null) {
			return;
		}

		if (log4jConfigFileLoacation.toLowerCase().endsWith(".xml"))
			new DOMConfigurator().doConfigure(log4jConfigFileLoacation,
					this._log4jRepository);
		else if (log4jConfigFileLoacation.toLowerCase().endsWith(".properties"))
			new PropertyConfigurator().doConfigure(log4jConfigFileLoacation,
					this._log4jRepository);
		else {
			return;
		}
		setLogConfigFile(log4jConfigFileLoacation);
		refreshLogger();
	}

	public synchronized void configure(Properties log4jConfigProp) {
		if (log4jConfigProp == null) {
			return;
		}
		this._log4jRepository = reNewLoggerRepository(this._log4jRepository);
		new PropertyConfigurator().doConfigure(log4jConfigProp,
				this._log4jRepository);
		refreshLogger();
		this._logConfigFile = null;
	}

	public synchronized void configure(Element log4jConfigElem) {
		if (log4jConfigElem == null) {
			return;
		}
		this._log4jRepository = reNewLoggerRepository(this._log4jRepository);
		new DOMConfigurator().doConfigure(log4jConfigElem,
				this._log4jRepository);
		refreshLogger();
		this._logConfigFile = null;
	}

	private static LoggerRepository reNewLoggerRepository(
			LoggerRepository repository) {
		if (repository != null) {
			repository.shutdown();
		}
		return new Hierarchy(new RootLogger(org.apache.log4j.Level.DEBUG));
	}

	private void refreshLogger() {
		for (String loggerName : this.createdLoggerCache.keySet()) {
			Category delegator = this._log4jRepository.getLogger(loggerName);
			Logger log = (Logger) this.createdLoggerCache.get(loggerName);
			if (log != null)
				synchronized (log) {
					log.setLoggerDelegate(delegator);
				}
		}
	}

	public Logger getLogger(Class<?> clazz) {
		String loggerName = (clazz == null) ? null : clazz.getName();
		return getLogger(loggerName);
	}

	public Logger getLogger(String loggerName) {
		loggerName = (loggerName == null) ? "null" : loggerName;
		Category delegator = this._log4jRepository.getLogger(loggerName);
		Logger log = (Logger) this.createdLoggerCache.get(loggerName);
		if (log == null)
			log = new Logger(delegator, this);
		else {
			synchronized (log) {
				log.setLoggerRepository(this);
				log.setLoggerDelegate(delegator);
			}
		}
		this.createdLoggerCache.put(loggerName, log);
		return log;
	}

	public void shutdown() {
		System.out.println(StringUtil.concat(new Object[] {
				"LogRepository[file=", this._logConfigFile, "] shutdown!" }));
		if (this._log4jRepository != null) {
			this._log4jRepository.shutdown();
		}
		for (Logger log : this.createdLoggerCache.values()) {
			for (Appender app : log.getAllAppenders())
				try {
					app.close();
				} catch (Exception ignore) {
				}
			log.setLevel(org.apache.log4j.Level.OFF);
		}

		LogRepositoryWeakMap.remove(this._logConfigFile);
	}

	static {
		LogLog.setQuietMode(true);
		LogLog.setInternalDebugging(false);

		java.util.logging.Logger log = java.util.logging.Logger
				.getLogger("org.quartz");
		log.setLevel(java.util.logging.Level.SEVERE);
		log = java.util.logging.Logger
				.getLogger("org.apache.catalina.loader.WebappClassLoader");
		log.setLevel(java.util.logging.Level.SEVERE);
		log = java.util.logging.Logger.getLogger("org.apache.coyote");
		log.setLevel(java.util.logging.Level.SEVERE);
		log = java.util.logging.Logger.getLogger("org.apache.axis2");
		log.setLevel(java.util.logging.Level.SEVERE);
		log = java.util.logging.Logger.getLogger("com.eos");
		log.setLevel(java.util.logging.Level.SEVERE);
		log = java.util.logging.Logger.getLogger("com.primeton");
		log.setLevel(java.util.logging.Level.SEVERE);
		log = java.util.logging.Logger.getLogger("com.mchange");
		log.setLevel(java.util.logging.Level.SEVERE);
		log = java.util.logging.Logger.getLogger("org.springframework");
		log.setLevel(java.util.logging.Level.SEVERE);
		log = java.util.logging.Logger.getLogger("org.apache.commons");
		log.setLevel(java.util.logging.Level.SEVERE);
		log = java.util.logging.Logger.getLogger("net.sf.cglib");
		log.setLevel(java.util.logging.Level.SEVERE);
	}
}