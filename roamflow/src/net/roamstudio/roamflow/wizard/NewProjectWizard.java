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
package net.roamstudio.roamflow.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.roamstudio.roamflow.cpcontainer.JbpmClasspathContainer;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 * @author chinakite zhang
 *
 */
public class NewProjectWizard extends Wizard implements INewWizard {

	private WizardNewProjectCreationPage firstPage;

	private IProject newProject;
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			getContainer().run(false, false, new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("New Process Project Monitor", 6);
					createNewProcessProject(monitor);
					if (newProject == null)
						throw new InvocationTargetException(null);
//					selectAndReveal(newProject, workbench.getActiveWorkbenchWindow());
					monitor.done();
				}

			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setNeedsProgressMonitor(true);
		setWindowTitle("New Process Project");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		super.addPages();
		firstPage = new WizardNewProjectCreationPage("vjpbmNewProjectPage"); 
		firstPage.setTitle("Create a New Process Project");
		firstPage.setDescription("Create a New Process Project"); 
		this.addPage(firstPage);
	}

	private void createNewProcessProject(IProgressMonitor monitor) {
		try {
			newProject = createNewProject();
			monitor.worked(1);
			IJavaProject javaProject = JavaCore.create(newProject);
			monitor.worked(1);
			createOutputLocation(javaProject);
			monitor.worked(1);
			addJavaBuilder(javaProject);
			monitor.worked(1);
			setClasspath(javaProject);
			monitor.worked(1);
			newProject.build(IncrementalProjectBuilder.FULL_BUILD, null);
			monitor.worked(1);
		} catch (JavaModelException e) {
			ErrorDialog.openError(getShell(), "Exception happened during project creating", null, e.getStatus());
		} catch (CoreException e) {
			ErrorDialog.openError(getShell(), "Exception happened during project creating", null, e.getStatus()); 
		}
	}
	
	private IProject createNewProject() {
		final IProject newProjectHandle = firstPage.getProjectHandle();
		final IProjectDescription description = createProjectDescription(newProjectHandle);
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException {
				createProject(description, newProjectHandle, monitor);
			}
		};
		runProjectCreationOperation(op, newProjectHandle);
		return newProjectHandle;
	}
	
	void createProject(IProjectDescription description, IProject projectHandle, IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		try {
			monitor.beginTask("", 2000); //$NON-NLS-1$
			projectHandle.create(description, new SubProgressMonitor(monitor, 1000));
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			projectHandle.open(IResource.BACKGROUND_REFRESH, new SubProgressMonitor(monitor, 1000));
		} finally {
			monitor.done();
		}
	}
	
	private IProjectDescription createProjectDescription(IProject newProjectHandle) {
		IPath newPath = null;
		if (!firstPage.useDefaults())
			newPath = firstPage.getLocationPath();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProjectDescription description = workspace.newProjectDescription(newProjectHandle.getName());
		description.setLocation(newPath);
		addJavaNature(description);
		return description;
	}
	
	private void addJavaNature(IProjectDescription description) {
		List<String> natures = new ArrayList<String>();
		natures.addAll(Arrays.asList(description.getNatureIds()));
		natures.add(JavaCore.NATURE_ID);
		description.setNatureIds(natures.toArray(new String[natures.size()]));
	}
	
	private void runProjectCreationOperation(WorkspaceModifyOperation op, IProject newProjectHandle) {
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			// Do nothing
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof CoreException) {
				handleCoreException(newProjectHandle, (CoreException) t);
			} else {
				handleOtherProblem(t);
			}
		}
	}
	
	private void handleOtherProblem(Throwable t) {
		MessageDialog.openError(getShell(), "Exception occured during create project", "error is :" + t.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void handleCoreException(final IProject newProjectHandle, CoreException e) {
		if (e.getStatus().getCode() == IResourceStatus.CASE_VARIANT_EXISTS) {
			MessageDialog.openError(getShell(), "Exception occured during create project", "error is :" + "'" + newProjectHandle.getName() + "'."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		} else {
			ErrorDialog.openError(getShell(), "Exception occured during create project", null, e.getStatus()); //$NON-NLS-1$
		}
	}
	
	private void setClasspath(IJavaProject javaProject) throws JavaModelException, CoreException {
		javaProject.setRawClasspath(new IClasspathEntry[0], null);
		addSourceFolders(javaProject);
		addJRELibraries(javaProject);
		addJbpmLibraries(javaProject);
	}
	
	private void addJRELibraries(IJavaProject javaProject) throws JavaModelException {
		List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		entries.addAll(Arrays.asList(javaProject.getRawClasspath()));
		entries.addAll(Arrays.asList(PreferenceConstants.getDefaultJRELibrary()));
		javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
	}

	private void addSourceFolders(IJavaProject javaProject) throws JavaModelException, CoreException {
		List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		entries.addAll(Arrays.asList(javaProject.getRawClasspath()));
		addSourceFolder(javaProject, entries, "src/process"); //$NON-NLS-1$
		javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
	}

	private void addSourceFolder(IJavaProject javaProject, List<IClasspathEntry> entries, String path) throws CoreException {
		IFolder folder = javaProject.getProject().getFolder(path);
		createFolder(folder);
		IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(folder);
		entries.add(JavaCore.newSourceEntry(root.getPath()));
	}
	
	private void createFolder(IFolder folder) throws CoreException {
		IContainer parent = folder.getParent();
		if (parent != null && !parent.exists() && parent instanceof IFolder) {
			createFolder((IFolder) parent);
		}
		folder.create(true, true, null);
	}
	
	private void addJbpmLibraries(IJavaProject javaProject) throws JavaModelException {
		createJbpmLibraryContainer(javaProject);
		List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		entries.addAll(Arrays.asList(javaProject.getRawClasspath()));
		entries.add(JavaCore.newContainerEntry(getClassPathContainerPath()));
		javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
	}
	
	private void createJbpmLibraryContainer(IJavaProject javaProject) throws JavaModelException {
		JavaCore.setClasspathContainer(getClassPathContainerPath(), new IJavaProject[] { javaProject }, new IClasspathContainer[] { new JbpmClasspathContainer(javaProject) }, null);
	}
	
	private IPath getClassPathContainerPath() {
		return new Path("JBPM/" + getJbpmNamePref()); //$NON-NLS-1$
	}
	
	private String getJbpmNamePref() {
		return "jbpm";
	}
	
	private void createOutputLocation(IJavaProject javaProject) throws JavaModelException, CoreException {
		IFolder binFolder = javaProject.getProject().getFolder("bin"); //$NON-NLS-1$
		createFolder(binFolder);
		IPath outputLocation = binFolder.getFullPath();
		javaProject.setOutputLocation(outputLocation, null);
	}

	private void addJavaBuilder(IJavaProject javaProject) throws CoreException {
		IProjectDescription desc = javaProject.getProject().getDescription();
		ICommand command = desc.newCommand();
		command.setBuilderName(JavaCore.BUILDER_ID);
		desc.setBuildSpec(new ICommand[] { command });
		javaProject.getProject().setDescription(desc, null);
	}
}
