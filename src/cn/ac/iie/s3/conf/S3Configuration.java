/**
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ac.iie.s3.conf;

import java.io.File;
import java.io.Serializable;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.ac.iie.s3.log.LogType;
import cn.ac.iie.s3.log.LoggerUtil;
import cn.ac.iie.s3.util.ClassUtil;
import cn.ac.iie.s3.util.PropertyUtil;

/**
 * 配置文件管理的父类
 * 该类提供所有的配置文件的读取(按照路径、File对象等)
 * 该类还提供所有配置项的读取操作，支持文本、数字等各种类型
 * 
 * @author egret
 * @since s3-0.0.1
 */
public class S3Configuration implements Serializable {

	private final static Logger LOG = LoggerUtil.getInstance(LogType.S3
			.toString());
	private static final long serialVersionUID = 1L;
	protected Properties _properties;
	private final String _resourcePath;

	public S3Configuration(final String path) {
		_properties = PropertyUtil.loadProperties(path);
		_resourcePath = PropertyUtil.getPropertiesFilePath(path);
	}

	public S3Configuration(File file) {
		_properties = PropertyUtil.loadProperties(file);
		_resourcePath = file.getAbsolutePath();
	}

	public S3Configuration(Properties properties, String filePath) {
		_properties = properties;
		_resourcePath = filePath;
	}

	public S3Configuration() {
		_properties = new Properties();
		_resourcePath = null;
	}

	public String getResourcePath() {
		return _resourcePath;
	}

	public boolean containsProperty(final String key) {
		return _properties.containsKey(key);
	}

	public String getProperty(final String key) {
		final String value = _properties.getProperty(key);
		return value;
	}

	public String getProperty(final String key, final String defaultValue) {
		String value = _properties.getProperty(key);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	protected void setProperty(String key, String value) {
		_properties.setProperty(key, value);
	}

	protected void setProperty(String key, long value) {
		_properties.setProperty(key, Long.toString(value));
	}

	public int getInt(final String key) {
		return Integer.parseInt(getProperty(key));
	}

	public int getInt(final String key, final int defaultValue) {
		try {
			return Integer.parseInt(getProperty(key));
		} catch (NumberFormatException e) {
			LOG.debug("failed to parse value '" + getProperty(key)
					+ "' for key '" + key + "' - returning default value '"
					+ defaultValue + "'");
			return defaultValue;
		} catch (IllegalStateException e) {
			return defaultValue;
		}
	}

	public long getLong(final String key, final long defaultValue) {
		try {
			return Long.parseLong(getProperty(key));
		} catch (NumberFormatException e) {
			LOG.warn("failed to parse value '" + getProperty(key)
					+ "' for key '" + key + "' - returning default value '"
					+ defaultValue + "'");
			return defaultValue;
		} catch (IllegalStateException e) {
			return defaultValue;
		}
	}

	public int getTime(final String key, final int defaultValue) {
		try {
			return Integer.parseInt(getProperty(key)) * 1000;
		} catch (NumberFormatException e) {
			LOG.debug("failed to parse value '" + getProperty(key)
					+ "' for key '" + key + "' - returning default value '"
					+ defaultValue + "'");
			return defaultValue * 1000;
		} catch (IllegalStateException e) {
			return defaultValue * 1000;
		}
	}

	public float getFloat(final String key, final float defaultValue) {
		try {
			return Float.parseFloat(getProperty(key));
		} catch (NumberFormatException e) {
			LOG.warn("failed to parse value '" + getProperty(key)
					+ "' for key '" + key + "' - returning default value '"
					+ defaultValue + "'");
			return defaultValue;
		} catch (IllegalStateException e) {
			return defaultValue;
		}
	}

	public File getFile(final String key) {
		return new File(getProperty(key));
	}

	public Set<String> getKeys() {
		return _properties.stringPropertyNames();
	}

	public Class<?> getClass(final String key) {
		final String className = getProperty(key);
		return ClassUtil.forName(className, Object.class);
	}

	public Class<?> getClass(final String key, Class<?> defaultValue) {
		final String className = getProperty(key, defaultValue.getName());
		return ClassUtil.forName(className, Object.class);
	}
}
