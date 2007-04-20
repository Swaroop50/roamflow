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
package net.roamstudio.roamflow.wizard;

import java.io.ByteArrayInputStream;

import net.roamstudio.roamflow.loader.JbpmLibraryConfigurationLoader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * @author chinakite zhang
 *
 */
public class NewProcessWizard extends Wizard implements INewWizard {

	private NewProcessWizardPage firstPage;
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			IFolder folder = firstPage.getProcessSourceFolder();
			folder.create(true, true, null);
			IFile processDefinitionFile = folder.getFile("processdefinition.xml");
			processDefinitionFile.create(createInitialProcessDefinition(), true, null);
			IFile gpdFile = folder.getFile("gpd.xml");
			gpdFile.create(createInitialGpdInfo(), true, null);
//			IDE.openEditor(getActivePage(), gpdFile);
//			BasicNewResourceWizard.selectAndReveal(gpdFile, getActiveWorkbenchWindow());
			return true;
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}
	}

	private IStructuredSelection selection;
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setNeedsProgressMonitor(true);
		setWindowTitle("New Process");
		this.selection = selection;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		firstPage = new NewProcessWizardPage("vjpbmNewProcessPage"); 
		firstPage.setTitle("Create a New Process Project");
		firstPage.setDescription("Create a New Process Project"); 
		firstPage.setSelection(selection);
		this.addPage(firstPage);
	}
	
	private ByteArrayInputStream createInitialProcessDefinition() throws JavaModelException {
		String parName = firstPage.getProcessName();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(
				"<process-definition\n" +
				"  xmlns=\"" + JbpmLibraryConfigurationLoader.getJbpmSchemaNameSpace() + "\"\n" +
				"  name=\"" + parName + "\">\n" +	
				"</process-definition>");	
		byte[] b = null;
		try{
			b = buffer.toString().getBytes("UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ByteArrayInputStream(b);
	}
	
	private ByteArrayInputStream createInitialGpdInfo() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append("<process-diagram></process-diagram>");	
		return new ByteArrayInputStream(buffer.toString().getBytes());
	}
}
