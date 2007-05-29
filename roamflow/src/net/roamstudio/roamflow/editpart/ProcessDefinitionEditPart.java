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

import java.util.ArrayList;
import java.util.List;

import net.roamstudio.roamflow.model.ProcessDefinition;
import net.roamstudio.roamflow.policy.DiagramXYLayoutEditPolicy;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * @author chinakite zhang
 *
 */
public class ProcessDefinitionEditPart extends AbstractGraphicalEditPart {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		Layer layer =  new Layer();
		layer.setLayoutManager(new XYLayout());
		return layer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramXYLayoutEditPolicy());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List getModelChildren() {
		List list = new ArrayList();
		ProcessDefinition model = (ProcessDefinition)getModel();
		if(model.getStartState() != null){
			list.add(model.getStartState());
		}
		if(model.getEndState() != null){
			list.add(model.getEndState());
		}
		return list;
	}

}
