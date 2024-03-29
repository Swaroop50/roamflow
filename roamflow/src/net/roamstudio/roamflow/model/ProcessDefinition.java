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
package net.roamstudio.roamflow.model;

/**
 * @author chinakite zhang
 *
 */
public class ProcessDefinition {
	private String name;

	private StartState startState;
	
	private EndState endState;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the startState
	 */
	public StartState getStartState() {
		return startState;
	}

	/**
	 * @param startState the startState to set
	 */
	public void setStartState(StartState startState) {
		this.startState = startState;
	}	
	
	public void addStartState(StartState startState){
		this.startState = startState;
	}

	/**
	 * @return the endState
	 */
	public EndState getEndState() {
		return endState;
	}

	/**
	 * @param endState the endState to set
	 */
	public void setEndState(EndState endState) {
		this.endState = endState;
	}
}
