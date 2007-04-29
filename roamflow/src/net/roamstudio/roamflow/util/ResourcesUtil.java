/*
 * Copyright (C) 2006-2010, Roamstudio Members
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, email to roamstudio@163.com
 */
package net.roamstudio.roamflow.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import net.roamstudio.roamflow.log.RoamflowLog;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;

/**
 * @author chinakite zhang
 *
 */
public class ResourcesUtil {

	/**
	 * 加载Java类。 使用全限定类名
	 * 
	 * @paramclassName
	 * @return
	 */
	public static Class loadClass(String className) {
		try {
			return getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("class not found '" + className + "'", e);
		}
	}

	/**
	 * 得到类加载器
	 * 
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		return ResourcesUtil.class.getClassLoader();
	}

	/**
	 * 提供相对于classpath的资源路径，返回文件的输入流
	 * 
	 * @paramrelativePath必须传递资源的相对路径。是相对于classpath的路径。如果需要查找classpath外部的资源，需要使用../来查找
	 * @return 文件输入流
	 * @throwsIOException
	 * @throwsMalformedURLException
	 */
	public static InputStream getStream(String relativePath)
			throws MalformedURLException, IOException {
		if (!relativePath.contains("../")) {
			return getClassLoader().getResourceAsStream(relativePath);
		} else {
			return ResourcesUtil.getStreamByExtendResource(relativePath);
		}
	}
	
	/**
	 * 提供相对于classpath的资源路径，返回文件的输入流
	 * 
	 * @paramrelativePath必须传递资源的相对路径。是相对于classpath的路径。如果需要查找classpath外部的资源，需要使用../来查找
	 * @return 文件输入流
	 * @throwsIOException
	 * @throwsMalformedURLException
	 */
	public static InputStream getStream(IFile file)
			throws MalformedURLException, IOException {
		if (file != null && file.exists()) {
			return ResourcesUtil.getStreamByExtendResource(file.getFullPath().toOSString());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @paramurl
	 * @return
	 * @throwsIOException
	 */
	public static InputStream getStream(URL url) throws IOException {
		if (url != null) {
			return url.openStream();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @paramrelativePath必须传递资源的相对路径。是相对于classpath的路径。如果需要查找classpath外部的资源，需要使用../来查找
	 * @return
	 * @throwsMalformedURLException
	 * @throwsIOException
	 */
	public static InputStream getStreamByExtendResource(String relativePath)
			throws MalformedURLException, IOException {
		return ResourcesUtil.getStream(ResourcesUtil
				.getExtendResource(relativePath));
	}

	/**
	 * 提供相对于classpath的资源路径，返回属性对象，它是一个散列表
	 * 
	 * @paramresource
	 * @return
	 */
	public static Properties getProperties(String resource) {
		Properties properties = new Properties();
		try {
			properties.load(getStream(resource));
		} catch (IOException e) {
			throw new RuntimeException("couldn't load properties file '"
					+ resource + "'", e);
		}
		return properties;
	}

	/**
	 * 得到本Class所在的ClassLoader的Classpat的绝对路径。 URL形式的
	 * 
	 * @return
	 */
	public static String getAbsolutePathOfClassLoaderClassPath() {
		return ResourcesUtil.getClassLoader().getResource("").toString();
	}

	/**
	 * 
	 * @paramrelativePath 必须传递资源的相对路径。是相对于classpath的路径。如果需要查找classpath外部的资源，需要使用../来查找
	 * @return资源的绝对URL
	 * @throwsMalformedURLException
	 */
	public static URL getExtendResource(String relativePath)
			throws MalformedURLException {

		if (!relativePath.contains("../")) {
			return ResourcesUtil.getResource(relativePath);
		}
		String classPathAbsolutePath = ResourcesUtil
				.getAbsolutePathOfClassLoaderClassPath();
		if (relativePath.substring(0, 1).equals("/")) {
			relativePath = relativePath.substring(1);
		}

		String wildcardString = relativePath.substring(0, relativePath
				.lastIndexOf("../") + 3);
		relativePath = relativePath
				.substring(relativePath.lastIndexOf("../") + 3);
		int containSum = ResourcesUtil.containSum(wildcardString, "../");
		classPathAbsolutePath = ResourcesUtil.cutLastString(
				classPathAbsolutePath, "/", containSum);
		String resourceAbsolutePath = classPathAbsolutePath + relativePath;
		URL resourceAbsoluteURL = new URL(resourceAbsolutePath);
		return resourceAbsoluteURL;
	}

	/**
	 * 
	 * @paramsource
	 * @paramdest
	 * @return
	 */
	private static int containSum(String source, String dest) {
		int containSum = 0;
		int destLength = dest.length();
		while (source.contains(dest)) {
			containSum = containSum + 1;
			source = source.substring(destLength);
		}
		return containSum;
	}

	/**
	 * 
	 * @paramsource
	 * @paramdest
	 * @paramnum
	 * @return
	 */
	private static String cutLastString(String source, String dest, int num) {
		// String cutSource=null;
		for (int i = 0; i < num; i++) {
			source = source.substring(0, source.lastIndexOf(dest, source
					.length() - 2) + 1);
		}
		return source;
	}

	/**
	 * 
	 * @paramresource
	 * @return
	 */
	public static URL getResource(String resource) {
		return ResourcesUtil.getClassLoader().getResource(resource);
	}
	
	public static URL toNativeURL(URL url){
		try {
			return FileLocator.resolve(url);
		} catch (IOException e) {
			RoamflowLog.logError(e);
			e.printStackTrace();
		}
		return null;
	}
}
