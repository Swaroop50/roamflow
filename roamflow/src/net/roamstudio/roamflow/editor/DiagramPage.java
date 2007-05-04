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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

/**
 * @author chinakite zhang
 *
 */
public class DiagramPage extends EditorPart {

	private ProcessEditor editor;
	private SashForm sashForm;
	private DiagramPagePaletteViewer paletteViewer;
	private DiagramPageModelViewer modelViewer;
//	private DiagramPageOutlineViewer outlineViewer;
	
	public DiagramPage(ProcessEditor editor){
		this.editor = editor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		initSite(site);
		initInput(input);
	}
	
	private void initSite(IEditorSite site) {
		setSite(site);
	}

	private void initInput(IEditorInput input) throws PartInitException {
//		IEditorInput gpdInput = DesignerContentProvider.INSTANCE.getGpdEditorInput(input);
//		setInput(gpdInput);
//		DesignerContentProvider.INSTANCE.addGraphicalInfo(editor.getProcessDefinition(), gpdInput);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		sashForm = new SashForm(parent, SWT.HORIZONTAL);
		addPaletteViewer();
		addDiagramViewer();
		sashForm.setWeights(new int[]{15, 85});
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	private void addDiagramViewer() {
		setModelViewer(new DiagramPageModelViewer(editor));
		getModelViewer().createControl(sashForm);
	}
	
	private void addPaletteViewer() {
		setPaletteViewer(new DiagramPagePaletteViewer(editor));
		getPaletteViewer().createControl(sashForm);
	}

	/**
	 * @return the modelViewer
	 */
	public DiagramPageModelViewer getModelViewer() {
		return modelViewer;
	}

	/**
	 * @param modelViewer the modelViewer to set
	 */
	public void setModelViewer(DiagramPageModelViewer modelViewer) {
		this.modelViewer = modelViewer;
	}

	/**
	 * @return the paletteViewer
	 */
	public DiagramPagePaletteViewer getPaletteViewer() {
		return paletteViewer;
	}

	/**
	 * @param paletteViewer the paletteViewer to set
	 */
	public void setPaletteViewer(DiagramPagePaletteViewer paletteViewer) {
		this.paletteViewer = paletteViewer;
	}
}
