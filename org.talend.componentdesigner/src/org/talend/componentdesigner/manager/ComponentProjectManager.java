// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.componentdesigner.manager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.talend.componentdesigner.ComponentDesigenerPlugin;
import org.talend.componentdesigner.PluginConstant;
import org.talend.componentdesigner.ui.progress.ProgressUI;

/**
 * @author rli
 *
 */
public class ComponentProjectManager {

	// cache of newly-created project
	private IProject newProject;
	private final Shell shell;
	
	public ComponentProjectManager(Shell shell) {
		this.shell = shell;

	}

	
	/**
	 * Creates a new project resource with the selected name.
	 * <p>
	 * In normal usage, this method is invoked after the user has pressed Finish on the wizard; the enablement of the
	 * Finish button implies that all controls on the pages currently contain valid values.
	 * </p>
	 * <p>
	 * Note that this wizard caches the new project once it has been successfully created; subsequent invocations of
	 * this method will answer the same project resource without attempting to create it again.
	 * </p>
	 * 
	 * @return the created project resource, or <code>null</code> if the project was not created
	 */
	public IProject createNewProject(String directroy, String projectName) {
		if (newProject != null) {
			return newProject;
		}

		// get a project handle
		final IProject newProjectHandle = ResourcesPlugin.getWorkspace()
				.getRoot().getProject(projectName);
		
//		final IJavaProject javaProjHandle  = JavaCore.create(newProjectHandle);
		// get a project descriptor
		URI location = null;
		if (directroy == null || directroy.equals(PluginConstant.EMPTY_STRING)) {
			return null;
		} else {
			location = new File(directroy).toURI();
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProjectDescription description = workspace
				.newProjectDescription(newProjectHandle.getName());
		description.setLocationURI(location);

		// create the new project operation
		IRunnableWithProgress op = new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				CreateProjectOperation op = new CreateProjectOperation(
						description, "New Component Project");
				try {
					PlatformUI.getWorkbench().getOperationSupport()
							.getOperationHistory().execute(
									op,
									monitor,
									WorkspaceUndoUtil
											.getUIInfoAdapter(shell));
				} catch (ExecutionException e) {
					throw new InvocationTargetException(e);
				}
			}
		};

		// run the new project creation o`peration
		try {
			ProgressUI.popProgressDialog(op, shell);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof ExecutionException
					&& t.getCause() instanceof CoreException) {
				CoreException cause = (CoreException) t.getCause();
				StatusAdapter status;
				if (cause.getStatus().getCode() == IResourceStatus.CASE_VARIANT_EXISTS) {
					status = new StatusAdapter(

							new Status(
									IStatus.WARNING,
									ComponentDesigenerPlugin.PLUGIN_ID,
									IStatus.WARNING,
									"The underlying file system is case insensitive. There is an existing project which conflicts with"
											+ newProjectHandle.getName(), cause));
				} else {
					status = new StatusAdapter(new Status(cause.getStatus()
							.getSeverity(), ComponentDesigenerPlugin.PLUGIN_ID,
							cause.getStatus().getSeverity(),
							"Creation Problems", cause));
				}
				status.setProperty(StatusAdapter.TITLE_PROPERTY,
						"Creation Problems");
				StatusManager.getManager().handle(status, StatusManager.BLOCK);
			} else {
				StatusAdapter status = new StatusAdapter(new Status(
						IStatus.WARNING, ComponentDesigenerPlugin.PLUGIN_ID, 0,
						"Internal error:" + t.getMessage(), t));
				status.setProperty(StatusAdapter.TITLE_PROPERTY,
						"Creation Problems");
				StatusManager.getManager().handle(status,
						StatusManager.LOG | StatusManager.BLOCK);
			}
			return null;
		}

		newProject = newProjectHandle;

		return newProject;
	}

	/**
	 * Returns the newly created project.
	 * 
	 * @return the created project, or <code>null</code> if project not created
	 */
	public IProject getNewProject() {
		return newProject;
	}
}
