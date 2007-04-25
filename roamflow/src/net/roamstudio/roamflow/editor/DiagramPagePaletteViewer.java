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

import org.eclipse.gef.EditDomain;
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
		editDomain.setPaletteRoot(new DiagramPagePaletteRoot(editor));
	}
}
