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

import net.roamstudio.roamflow.util.ImagesUtil;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author chinakite zhang
 *
 */
public class NewProcessWizardPage extends WizardPage {

	private Text processNameText;
	private Text sourceFolderText;
	IStructuredSelection selection;
	private IWorkspaceRoot workspaceRoot;
	
	protected NewProcessWizardPage(String pageName) {
		super(pageName);
		setTitle("Create a new process definition");
		setImageDescriptor(ImagesUtil.getDescriptor("newprocess"));
		setDescription("Create a new process definition");
		workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite topComp = new Composite(parent, SWT.NONE);
		GridLayout gl_topComp = new GridLayout();
		gl_topComp.numColumns = 2;
		topComp.setLayout(gl_topComp);
		
		createInfoLabel(topComp);
		
		createSourceFolderLine(topComp);

		createProcessNameLine(topComp);
		
		setControl(topComp);
	}

	/**
	 * @param topComp
	 */
	private void createProcessNameLine(Composite topComp) {
		final Label processNameLabel = new Label(topComp, SWT.NONE);
		processNameLabel.setText("Process name : ");

		processNameText = new Text(topComp, SWT.BORDER);
		final GridData gd_processNameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		processNameText.setLayoutData(gd_processNameText);
		processNameText.setFocus();
	}

	/**
	 * @param topComp
	 */
	private void createSourceFolderLine(Composite topComp) {
		final Label sourceFolderLabel = new Label(topComp, SWT.NONE);
		sourceFolderLabel.setText("Source folder : ");

		sourceFolderText = new Text(topComp, SWT.BORDER);
		sourceFolderText.setEditable(false);
		final GridData gd_sourceFolderText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		sourceFolderText.setLayoutData(gd_sourceFolderText);
		
		sourceFolderText.setText(initSourceFolder(selection));
	}

	/**
	 * @param topComp
	 */
	private void createInfoLabel(Composite topComp) {
		final Label infoLabel = new Label(topComp, SWT.NONE);
		infoLabel.setText("Choose a source folder and a process name.");
		final GridData gd_infoLabel = new GridData(GridData.FILL_HORIZONTAL);
		gd_infoLabel.horizontalSpan = 2;
		infoLabel.setLayoutData(gd_infoLabel);
	}

	public void setSelection(IStructuredSelection selection){
		this.selection = selection;
	}
	
	public String initSourceFolder(IStructuredSelection selection){
		IContainer container = null;
		if (selection != null && !selection.isEmpty()) {
			Object object = selection.getFirstElement();
			if (IFile.class.isInstance(object) && !IContainer.class.isInstance(object)) {
				container = ((IFile)object).getParent();
			} else if (IContainer.class.isInstance(object)) {
				container = (IContainer)object;
			}
		}
		return container.getName() + "/src/process";
	}
	
	public IFolder getProcessSourceFolder(){
		IPath path = new Path(sourceFolderText.getText()).append(getProcessName());
		return workspaceRoot.getFolder(path);
	}
	
	public String getProcessName(){
		return processNameText.getText();
	}
}
