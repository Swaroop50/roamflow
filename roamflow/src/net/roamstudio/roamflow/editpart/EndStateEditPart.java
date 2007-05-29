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

import net.roamstudio.roamflow.model.EndState;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;

/**
 * @author chinakite zhang
 *
 */
public class EndStateEditPart extends AbstractDiagramEditPart {
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		EndState model = (EndState)getModel();
		
		Label label = new Label();
		label.setText(model.getName());
		
		label.setBorder(new CompoundBorder(new LineBorder(), new MarginBorder(3)));
		label.setBackgroundColor(ColorConstants.red);
		label.setOpaque(true);
		
		return label;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		Rectangle constraint = ((EndState)getModel()).getContraint();
		if(constraint == null)
			constraint = new Rectangle(0, 80, -1, -1);
		((GraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), constraint);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(EndState.P_CONSTRAINT)){
			refreshVisuals();
		}
	}
}
