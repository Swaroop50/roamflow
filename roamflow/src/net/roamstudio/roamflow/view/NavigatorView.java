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
import net.roamstudio.roamflow.wizard.NewProcessWizard;
import net.roamstudio.roamflow.wizard.NewProjectWizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

/**
 * @author chinakite zhang
 * 
 */
public class NavigatorView extends ViewPart {

	private TreeViewer viewer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.NONE);
		viewer.setContentProvider(new ResourcesContentProvider());
		viewer.setLabelProvider(new ResourcesLabelProvider());

		viewer.addFilter(new ResourcesViewerFilter());
		viewer.setInput(new Object());

		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				new IResourceChangeListener() {
					public void resourceChanged(IResourceChangeEvent event) {
						PlatformUI.getWorkbench().getDisplay().asyncExec(
								new Runnable() {
									public void run() {
										if (!viewer.getControl().isDisposed()) {
											viewer.refresh();
										}
									}
								});
					}
				});

		viewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				handleDoubleClick((IStructuredSelection) event.getSelection());
			}
		});

		getSite().setSelectionProvider(viewer);

		MenuManager menuMgr = new MenuManager();
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				NavigatorView.this.fillContextMenu(manager);
			}
		});

		viewer.getControl().setMenu(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {

	}

	protected void fillContextMenu(IMenuManager manager) {

		manager.add(new Action("&New Project", ImagesUtil
				.getDescriptor("project")) { //$NON-NLS-1$	
					public void run() {
						NewProjectWizard wizard = new NewProjectWizard();
						wizard.init(PlatformUI.getWorkbench(), null);
						WizardDialog dialog = new WizardDialog(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getShell(), wizard);
						dialog.open();
					}
				});

		final IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();

		manager.add(new Action("&New Process", ImagesUtil
				.getDescriptor("process")) { //$NON-NLS-1$	
					public void run() {
						NewProcessWizard wizard = new NewProcessWizard();
						wizard.init(PlatformUI.getWorkbench(), selection);
						WizardDialog dialog = new WizardDialog(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getShell(), wizard);
						dialog.open();
					}
				});
	}

	protected void handleDoubleClick(IStructuredSelection selection) {
		if (selection.getFirstElement() != null) {
			Object element = selection.getFirstElement();
			if (element instanceof IFolder) {
				IFolder folder = (IFolder) element;
				IFile processDefinitionFile = folder
						.getFile("processdefinition.xml");
				if (processDefinitionFile.exists()) {
					try {
						IDE.openEditor(PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage(),
								processDefinitionFile,
								"net.roamstudio.roamflow.editor.ProcessEditor");
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			} else if (element instanceof IFile) {
				try {
					IDE.openEditor(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage(),
							(IFile) element,
							"net.roamstudio.roamflow.editor.ProcessEditor");
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
