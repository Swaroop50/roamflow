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
package net.roamstudio.roamflow.editor;

import net.roamstudio.roamflow.loader.ProcessDefinitionLoader;
import net.roamstudio.roamflow.model.ProcessDefinition;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditDomain;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorPart;

/**
 * @author chinakite zhang
 * 
 */
public class ProcessEditor extends XMLMultiPageEditorPart {
	private DiagramPage diagramPage;
	private ProcessDefinition processDefinition;

	private StructuredTextEditor sourcePage;

	private EditDomain editDomain;

	private void initSourcePage() {
		int pageCount = getPageCount();
		for (int i = 0; i < pageCount; i++) {
			if (getEditor(i) instanceof StructuredTextEditor) {
				sourcePage = (StructuredTextEditor) getEditor(i);
				
				ProcessDefinitionLoader pdl = new ProcessDefinitionLoader(
						((FileEditorInput)getEditorInput()).getPath());
				processDefinition = pdl.getProcessDefinition();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorPart#createPages()
	 */
	@Override
	protected void createPages() {
		super.createPages();
		initSourcePage();
		initGraphPage();
	}

	public EditDomain getEditDomain() {
		return editDomain;
	}

	private void initGraphPage() {
		diagramPage = new DiagramPage(this);
		try {
			addPage(0, diagramPage, getEditorInput());
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		setPageText(0, "Diagram");
	}

	private void initEditDomain() {
		editDomain = new DefaultEditDomain(this);
	}

	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		FileEditorInput fileInput = (FileEditorInput) input;
		super.init(site, fileInput);
		initEditDomain();
	}

	/**
	 * @return the diagramPage
	 */
	public DiagramPage getDiagramPage() {
		return diagramPage;
	}

	/**
	 * @return the processDefinition
	 */
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	/**
	 * @param processDefinition the processDefinition to set
	 */
	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
}
