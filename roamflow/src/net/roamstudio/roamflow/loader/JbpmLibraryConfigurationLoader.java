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

import java.util.HashMap;
import java.util.Map;

import net.roamstudio.roamflow.cpcontainer.ClasspathEntry;
import net.roamstudio.roamflow.cpcontainer.JbpmLibrary;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * @author chinakite zhang
 *
 */
public class JbpmLibraryConfigurationLoader extends XMLLoader {
	
	public static String xmlfile = "net/roamstudio/roamflow/resources/jbpm-lib.xml";
	public static String mapfile = "net/roamstudio/roamflow/resources/jbpm-lib-mapping.xml";
	
	private JbpmLibrary jbpmLib;
	
	private static JbpmLibraryConfigurationLoader jbpmLibraryConfigurationLoader;
	
	public static JbpmLibraryConfigurationLoader getInstance(){
		if(jbpmLibraryConfigurationLoader == null){
			jbpmLibraryConfigurationLoader = new JbpmLibraryConfigurationLoader();
		}	
		return jbpmLibraryConfigurationLoader;
	}
	
	public JbpmLibraryConfigurationLoader(){
		init(mapfile, xmlfile);
		jbpmLib = (JbpmLibrary)load(JbpmLibrary.class);
	}
	
	/**
	 * 取得配置文件中的jBPM名称(包括版本号). Get jBPM name(included version) from configuration
	 * file.
	 * 
	 * @return jBPM名称
	 */
	public String getJbpmName() {
		return jbpmLib.getName();
	}
	
	public String getJbpmSchemaNameSpace(){
		return jbpmLib.getName();
	}
	
	public Map getJarNames() {
		HashMap result = new HashMap();
		IPath path = new Path("/");
		
		for(ClasspathEntry classpathEntry : jbpmLib.getClasspathEntrys()){
			IPath srcPath = null;
			if(classpathEntry.getSrc() != null)
				srcPath = path.append(classpathEntry.getSrc());
			result.put(path.append(classpathEntry.getPath()), srcPath);
		}

		return result;
	}
	
}
