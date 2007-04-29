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
package net.roamstudio.roamflow.cpcontainer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chinakite zhang
 *
 */
public class JbpmLibrary {
	private String name;
	private String namespace;
	private List<ClasspathEntry> classpathEntrys = new ArrayList<ClasspathEntry>();

	/**
	 * @return the classpathEntrys
	 */
	public List<ClasspathEntry> getClasspathEntrys() {
		return classpathEntrys;
	}

	/**
	 * @param classpathEntrys the classpathEntrys to set
	 */
	public void setClasspathEntrys(List<ClasspathEntry> classpathEntrys) {
		this.classpathEntrys = classpathEntrys;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public void addClasspathEntry(ClasspathEntry classpathEntry) {
		classpathEntrys.add(classpathEntry);
	}
}
