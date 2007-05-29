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

import net.roamstudio.roamflow.model.gpd.ProcessDiagram;

import org.eclipse.core.runtime.IPath;

/**
 * @author chinakite zhang
 *
 */
public class ProcessDiagramLoader extends XMLLoader {
	public static String mapfile = "net/roamstudio/roamflow/resources/gpd-mapping.xml";
	
	public ProcessDiagramLoader(IPath path){
		init(mapfile, path.toString());
	}
	
	public ProcessDiagram getProcessDiagram(){
		return (ProcessDiagram)load(ProcessDiagram.class);
	}
}
