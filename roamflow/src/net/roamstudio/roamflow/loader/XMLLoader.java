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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import net.roamstudio.roamflow.util.ResourcesUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.IFile;

/**
 * XML文件的读取器.
 * @author chinakite zhang
 *
 */
public abstract class XMLLoader {
	/**
	 * 取得jBPM相关库文件的配置表. Get libraries list of jBPM.
	 * 
	 * @return org.dom4j.Document
	 */
	public static Document getDocument(String path) {
		InputStream is;
		try {
			is = ResourcesUtil.getStream(path);
			Document document = new SAXReader().read(is);
			return document;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 取得jBPM相关库文件的配置表. Get libraries list of jBPM.
	 * 
	 * @return org.dom4j.Document
	 */
	private Document getDocument(IFile file) {
//		InputStream is;
//		try {
//			is = ResourcesUtil.getStreamByExtendResource(file.getFullPath().);
//			Document document = new SAXReader().read(is);
//			return document;
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
		return null;
	}
}
