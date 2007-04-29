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
import java.util.Iterator;
import java.util.Map;

import net.roamstudio.roamflow.loader.JbpmLibraryConfigurationLoader;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class JbpmClasspathContainer implements IClasspathContainer {

	IClasspathEntry[] jbpmLibraryEntries;

	IJavaProject javaProject = null;

	/**
	 * ¹¹Ôìº¯Êý
	 * @param javaProject
	 */
	public JbpmClasspathContainer(IJavaProject javaProject) {
		this.javaProject = javaProject;
	}
	
	public IClasspathEntry[] getClasspathEntries() {
		if (jbpmLibraryEntries == null) {
			jbpmLibraryEntries = createJbpmLibraryEntries(javaProject);
		}
		return jbpmLibraryEntries;
	}

	public String getDescription() {
		String jbpmName = JbpmLibraryConfigurationLoader.getInstance().getJbpmName();
		return "jBPM Library [" + jbpmName + "]";
	}

	public int getKind() {
		return IClasspathContainer.K_APPLICATION;
	}

	public IPath getPath() {
		return new Path("JBPM/jbpm");
	}

	private IClasspathEntry[] createJbpmLibraryEntries(IJavaProject project) {
		Map jarNames = JbpmLibraryConfigurationLoader.getInstance().getJarNames();
		ArrayList entries = new ArrayList();
		Iterator iterator = jarNames.keySet().iterator();
		while (iterator.hasNext()) {
			IPath jarPath = (IPath) iterator.next();
			IPath srcPath = (IPath) jarNames.get(jarPath);
			IPath srcRoot = null;
			entries.add(JavaCore.newLibraryEntry(jarPath, srcPath, srcRoot));
		}
		return (IClasspathEntry[]) entries.toArray(new IClasspathEntry[entries
				.size()]);
	}
}
