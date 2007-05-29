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

import net.roamstudio.roamflow.model.ProcessDefinition;
import net.roamstudio.roamflow.model.StartState;

import org.eclipse.gef.commands.Command;

/**
 * @author chinakite zhang
 *
 */
public class CreateCommand extends Command {
	private ProcessDefinition processDefinitionModel;
	private StartState startStateModel;
	
	public void execute(){
		processDefinitionModel.addStartState(startStateModel);
	}

	/**
	 * @return the processDefinitionModel
	 */
	public ProcessDefinition getProcessDefinitionModel() {
		return processDefinitionModel;
	}

	/**
	 * @param processDefinitionModel the processDefinitionModel to set
	 */
	public void setProcessDefinitionModel(Object processDefinitionModel) {
		this.processDefinitionModel = (ProcessDefinition)processDefinitionModel;
	}

	/**
	 * @return the startStateModel
	 */
	public StartState getStartStateModel() {
		return startStateModel;
	}

	/**
	 * @param startStateModel the startStateModel to set
	 */
	public void setStartStateModel(Object startStateModel) {
		this.startStateModel = (StartState)startStateModel;
	}
	
}
