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
package net.roamstudio.roamflow.command;

import net.roamstudio.roamflow.model.AbstractModel;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

/**
 * @author chinakite zhang
 *
 */
public class ChangeConstraintCommand extends Command {
	private AbstractModel startState;
	private Rectangle constraint;
	/**
	 * @param constraint the constraint to set
	 */
	public void setConstraint(Rectangle constraint) {
		this.constraint = constraint;
	}
	/**
	 * @param startState the startState to set
	 */
	public void setStartState(Object startState) {
		this.startState = (AbstractModel)startState;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		startState.setContraint(constraint);
	}
}
