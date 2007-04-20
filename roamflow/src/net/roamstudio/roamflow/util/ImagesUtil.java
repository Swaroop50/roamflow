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

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import net.roamstudio.roamflow.RoamflowActivator;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * Cache project's image by a java.util.HashSet.
 * 
 * @author chinakite zhang
 *
 */
public class ImagesUtil {
	
	private ImagesUtil() {
	}

	private static ImageRegistry register = new ImageRegistry();

	private static Set<String> keys = new HashSet<String>();

	static {
		initial();
	}

	/**
	 * Get a ImageDescriptor.
	 * @param key
	 * @return
	 */
	public static ImageDescriptor getDescriptor(String key) {
		ImageDescriptor image = register.getDescriptor(key);
		if (image == null) {
			image = ImageDescriptor.getMissingImageDescriptor();
		}
		return image;
	}

	/**
	 * Get a Image.
	 * @param key
	 * @return
	 */
	public static Image get(String key) {
		Image image = register.get(key);
		if (image == null) {
			image = ImageDescriptor.getMissingImageDescriptor().createImage();
		}
		return image;
	}

	public static String[] getImageKey() {
		return (String[]) keys.toArray(new String[keys.size()]);
	}

	private static void initial() {
		Bundle bundle = Platform.getBundle(RoamflowActivator.PLUGIN_ID);
		URL url = bundle.getEntry("icons");
		try {
			url = FileLocator.toFileURL(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file = new File(url.getPath());
		File[] images = file.listFiles();
		for (int i = 0; i < images.length; i++) {
			File f = images[i];
			if (!f.isFile()) {
				continue;
			}
			String name = f.getName();
			if (!name.endsWith(".gif") && !name.endsWith(".ico")) {
				continue;
			}
			String key = name.substring(0, name.indexOf('.'));
			URL fullPathString = bundle.getEntry("icons/" + name);
			ImageDescriptor des = ImageDescriptor.createFromURL(fullPathString);
			register.put(key, des);
			keys.add(key);
		}
	}
}
