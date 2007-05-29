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
package net.roamstudio.roamflow.policy;

import net.roamstudio.roamflow.command.ChangeConstraintCommand;
import net.roamstudio.roamflow.command.CreateCommand;
import net.roamstudio.roamflow.model.StartState;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

/**
 * @author chinakite zhang
 *
 */
public class DiagramXYLayoutEditPolicy extends XYLayoutEditPolicy {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		ChangeConstraintCommand command = new ChangeConstraintCommand();
		command.setStartState(child.getModel());
		command.setConstraint((Rectangle)constraint);
		return command;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		CreateCommand command = new CreateCommand();
		
		Rectangle constraint = (Rectangle)getConstraintFor(request);
		StartState startState = (StartState)request.getNewObject();
		startState.setContraint(constraint);
		
		command.setProcessDefinitionModel(getHost().getModel());
		command.setStartStateModel(startState);
		
		return command;
	}

}
