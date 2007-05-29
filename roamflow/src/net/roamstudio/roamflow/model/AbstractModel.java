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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @author chinakite zhang
 *
 */
public abstract class AbstractModel {
	protected String name;
	protected Rectangle contraint;

	public static final String P_CONSTRAINT = "_constraint";
	
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
	 * @return the contraint
	 */
	public Rectangle getContraint() {
		return contraint;
	}

	/**
	 * @param contraint the contraint to set
	 */
	public void setContraint(Rectangle contraint) {
		this.contraint = contraint;
		firePropertyChangeListener(P_CONSTRAINT, null, contraint);
	}
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void firePropertyChangeListener(String propertyName, Object oldValue, Object newValue){
		listeners.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
}
