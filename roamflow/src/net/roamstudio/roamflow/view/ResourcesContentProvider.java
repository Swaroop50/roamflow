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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author chinakite zhang
 *
 */
public class ResourcesContentProvider implements ITreeContentProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IProject) {
			IFile[] definitionFiles = getProcessDefinitions((IProject) parentElement);
			IContainer[] elements = new IContainer[definitionFiles.length];
			for (int i = 0; i < definitionFiles.length; i++) {
				elements[i] = definitionFiles[i].getParent();
			}
			return elements;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		if (element instanceof IProject) {
			return getProcessDefinitions((IProject) element).length > 0;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		try {
			return workspace.getRoot().members();
		} catch (CoreException e) {
			return new Object[] {};
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}
	
	private IFile[] getProcessDefinitions(IProject project) {
		try {
			Set processDefinitionFileList = new HashSet();
			findProcessFile(project, processDefinitionFileList, JavaCore.create(project).getOutputLocation());
			IFile[] file = new IFile[processDefinitionFileList.size()];
			toArray(file, processDefinitionFileList);
			return file;
		} catch (JavaModelException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (CoreException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void findProcessFile(IResource resource, Set processFilesList, IPath excludePath) throws CoreException {
		if (resource instanceof IContainer) {
			IContainer folder = (IContainer) resource;
			IResource[] resources = folder.members();
			for (int i = 0; i < resources.length; i++) {
				findProcessFile(resources[i], processFilesList, excludePath);
			}
		} else if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			if ("processdefinition.xml".equalsIgnoreCase(file.getName()) && !file.getFullPath().toString().startsWith(excludePath.toString())) {
				processFilesList.add(file);
			}
		}
	}
	
	private static void toArray(IFile[] file, Set processFilesList){
		if(processFilesList.size() > 0){
			Iterator ite = processFilesList.iterator();
			int i = 0;
			while(ite.hasNext()){
				file[i] = (IFile)(ite.next());
				i++;
			}
		}
	}
}
