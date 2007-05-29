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

import net.roamstudio.roamflow.model.StartState;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.swt.custom.SashForm;

/**
 * @author chinakite zhang
 *
 */
public class DiagramPagePaletteViewer extends PaletteViewer {
	private ProcessEditor editor;
	
	public DiagramPagePaletteViewer(ProcessEditor editor) {
		this.editor = editor;
	}
	
	public void createControl(SashForm parent) {
		initForm(parent);
		initEditDomain(parent);
	}
	
	private void initForm(SashForm parent) {
		super.createControl(parent);
	}

	private void initEditDomain(SashForm parent) {
		EditDomain editDomain = editor.getEditDomain();
		editDomain.setPaletteViewer(this);
		editDomain.setPaletteRoot(initPaletteRoot());
	}
	
	private PaletteRoot initPaletteRoot(){
		PaletteRoot root = new PaletteRoot();
		PaletteGroup toolGroup = new PaletteGroup("Tools");
		
		ToolEntry selection = new SelectionToolEntry();
		toolGroup.add(selection);
		root.setDefaultEntry(selection);
		
		MarqueeToolEntry marquee = new MarqueeToolEntry();
		toolGroup.add(marquee);
		
		PaletteDrawer drawer = new PaletteDrawer("Workflow");
		
		CreationToolEntry startStateNode = new CreationToolEntry(
				"Start State",
				"Create a Start State Node",
				new SimpleFactory(StartState.class),
				null,
				null
				);
		drawer.add(startStateNode);
		
		root.add(toolGroup);
		root.add(drawer);
		
		return root;
	}
}
