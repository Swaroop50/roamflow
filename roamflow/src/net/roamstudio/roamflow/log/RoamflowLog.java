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
package net.roamstudio.roamflow.log;

import net.roamstudio.roamflow.RoamflowActivator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

/**
 * @author chinakite zhang
 *
 */
public class RoamflowLog {
	
	/**
	 * 
	 * @param message
	 */
	public static void logInfo(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}
	
	/**
	 * 
	 * @param exception
	 */
	public static void logError(Throwable exception) {
		log(IStatus.ERROR, IStatus.OK, "Unexpected Exception", exception);
	}
	
	/**
	 * 
	 * @param message
	 * @param exception
	 */
	public static void logError(String message, Throwable exception) {
		log(IStatus.ERROR, IStatus.OK, message, exception);
	}
	
	/**
	 * 
	 * @param serverity
	 * @param code
	 * @param message
	 * @param exception
	 */
	public static void log(int serverity,
			               int code,
			               String message,
			               Throwable exception) {
		log(createStatus(serverity, code, message, exception));
	}
	
	/**
	 * 
	 * @param serverity
	 * @param code
	 * @param message
	 * @param exception
	 * @return
	 */
	public static IStatus createStatus(int serverity,
            						   int code,
            						   String message,
            						   Throwable exception) {
		return new Status(serverity,
						  Platform.getBundle(RoamflowActivator.PLUGIN_ID).getSymbolicName(),
				          code,
				          message,
				          exception);
	}
	
	/**
	 * 
	 * @param status
	 */
	public static void log(IStatus status) {
		RoamflowActivator.getDefault().getLog().log(status);
	}
}
