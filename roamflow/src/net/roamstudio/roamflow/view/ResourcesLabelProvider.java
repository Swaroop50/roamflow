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
package net.roamstudio.roamflow.view;

import net.roamstudio.roamflow.util.ImagesUtil;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author chinakite zhang
 *
 */
public class ResourcesLabelProvider extends LabelProvider {
	public String getText(Object element) {
		if (element instanceof IProject) {
			IProject project = (IProject) element;
			return project.getName();
		} else if (element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			return folder.getName();
		} else {
			return super.getText(element);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof IProject) {
			return ImagesUtil.get("project");
		} else if (element instanceof IFolder) {
			return ImagesUtil.get("process");
		} else {
			return null;
		}
	}
}
