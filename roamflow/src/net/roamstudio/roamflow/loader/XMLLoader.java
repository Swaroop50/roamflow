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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.roamstudio.roamflow.log.RoamflowLog;
import net.roamstudio.roamflow.util.ResourcesUtil;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

/**
 * XML文件的读取器.
 * 
 * @author chinakite zhang
 * 
 */
public abstract class XMLLoader {

	private Mapping mapping;

	private String xmlfile;

	/**
	 * 初始化Castor操作XML文件时使用的mapping.
	 * 
	 * @param mapfile
	 * @return
	 */
	private void initMapping(String mapfile) {
		try {
			mapping = new Mapping();
			mapping.loadMapping(mapfile);
		} catch (IOException e) {
			RoamflowLog.logError("Unable to init XML file mapping.", e);
			e.printStackTrace();
		} catch (MappingException e) {
			RoamflowLog.logError("Unable to init XML file mapping.", e);
			e.printStackTrace();
		}
	}

	private void initXmlfile(String xmlfile) {
		this.xmlfile = xmlfile;
	}

	public void init(String mapfile, String xmlfile) {
		if (mapfile != null) {
			mapfile = mapfile.trim();
			if (mapfile.trim().indexOf(":") == -1) {
				if (mapfile.startsWith("/")) {
					mapfile = mapfile.substring(1);
				}
				mapfile = ResourcesUtil.toNativeURL(ResourcesUtil.getResource(mapfile)).getPath();
			}
		}
		if (xmlfile != null) {
			xmlfile = xmlfile.trim();
			if (xmlfile.indexOf(":") == -1) {
				if (xmlfile.startsWith("/")) {
					xmlfile = xmlfile.substring(1);
				}
				xmlfile = ResourcesUtil.toNativeURL(ResourcesUtil.getResource(xmlfile)).getPath();
			}
		}
		initXmlfile(xmlfile);
		initMapping(mapfile);
	}

	public Object load(Class clazz) {
		Object obj;
		Unmarshaller un = new Unmarshaller(clazz);
		try {
			un.setMapping(mapping);
			FileReader in = new FileReader(xmlfile);
			obj = un.unmarshal(in);
			in.close();
			return obj;
		} catch (MappingException e) {
			RoamflowLog.logError("Unable to load object from XML file.", e);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			RoamflowLog.logError("Unable to load object from XML file.", e);
			e.printStackTrace();
		} catch (IOException e) {
			RoamflowLog.logError("Unable to load object from XML file.", e);
			e.printStackTrace();
		} catch (MarshalException e) {
			RoamflowLog.logError("Unable to load object from XML file.", e);
			e.printStackTrace();
		} catch (ValidationException e) {
			RoamflowLog.logError("Unable to load object from XML file.", e);
			e.printStackTrace();
		}

		return null;
	}
}
