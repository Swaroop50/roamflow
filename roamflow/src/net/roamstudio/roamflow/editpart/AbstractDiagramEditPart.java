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
package net.roamstudio.roamflow.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.roamstudio.roamflow.model.AbstractModel;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * @author chinakite zhang
 *
 */
public abstract class AbstractDiagramEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		super.activate();
		((AbstractModel)getModel()).addPropertyChangeListener(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		super.deactivate();
		((AbstractModel)getModel()).removePropertyChangeListener(this);
	}


}
