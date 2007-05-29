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

import java.util.Iterator;

import net.roamstudio.roamflow.editpart.GraphicEditPartFactory;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * @author chinakite zhang
 *
 */
public class DiagramPageModelViewer extends ScrollingGraphicalViewer {
	private ProcessEditor editor;
	private ISelectionListener selectionListener;
	
	public DiagramPageModelViewer(ProcessEditor editor) {
		this.editor = editor;
		setKeyHandler(new GraphicalViewerKeyHandler(this));
		setRootEditPart(new ScalableRootEditPart());
		setEditPartFactory(new GraphicEditPartFactory());
//		prepareGrid();
	}
	
	public void createControl(SashForm parent) {
		initControl(parent);
		initEditDomain(parent);
		initSite(parent);
//		initEditPartFactory(parent);
		initContents(parent);
//		initPropertySheetPage(parent);
	}
	
	private void initEditDomain(SashForm parent) {
		EditDomain editDomain = editor.getEditDomain();
		editDomain.addViewer(this);
	}

	private void initContents(SashForm parent) {
		setContents(editor.getProcessDefinition());
	}

	private void initControl(SashForm parent) {
		super.createControl(parent);
		getControl().setBackground(ColorConstants.white);
//		ContextMenuProvider provider = new DesignerContextMenuProvider(this, editor.getActionRegistry());
//		setContextMenu(provider);
//		editor.getSite().registerContextMenu("org.jbpm.ui.editor.modelviewer.context", provider, this);
	}

	private void initSite(SashForm parent) {
		IWorkbenchPartSite site = editor.getDiagramPage().getSite();
		site.setSelectionProvider(this);
		selectionListener = new ISelectionListener() {
			public void selectionChanged(IWorkbenchPart part, ISelection sel) {
				changeSelection(part, sel);
			}
		};
		site.getPage().addPostSelectionListener(selectionListener);
	}
	
	private void changeSelection(IWorkbenchPart part, ISelection sel) {
		if (!(sel instanceof IStructuredSelection)) return;
		IStructuredSelection structuredSelection = (IStructuredSelection)sel;
		Iterator iterator = structuredSelection.iterator();
		while (iterator.hasNext()) {
			Object selectedObject = iterator.next();
			if (selectedObject == null || !(selectedObject instanceof EditPart)) continue;
			EditPart editPart = (EditPart)selectedObject;
			EditPart objectToSelect = (EditPart)getEditPartRegistry().get(editPart.getModel());
			if (objectToSelect != null) select(objectToSelect);
		}
	}
}
