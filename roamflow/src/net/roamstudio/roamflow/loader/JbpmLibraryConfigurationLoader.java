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
package net.roamstudio.roamflow.loader;

import org.dom4j.Document;

/**
 * @author chinakite zhang
 *
 */
public class JbpmLibraryConfigurationLoader extends XMLLoader {
	
	public static final String CONFIG_PATH = "net/roamstudio/roamflow/resources/jbpm-lib.xml";
	
	/**
	 * 取得配置文件中的jBPM名称(包括版本号). Get jBPM name(included version) from configuration
	 * file.
	 * 
	 * @return jBPM名称
	 */
	public static String getJbpmName() {
		Document document = getDocument(CONFIG_PATH);
		return document.getRootElement().attributeValue("name");
	}
	
	public static String getJbpmSchemaNameSpace(){
		Document document = getDocument(CONFIG_PATH);
		return document.getRootElement().attributeValue("namespace");
	}
}
