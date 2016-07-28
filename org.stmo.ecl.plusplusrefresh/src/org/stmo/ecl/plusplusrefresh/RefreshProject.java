package org.stmo.ecl.plusplusrefresh;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

class RefreshProject {
	
	void refreshProjects(Collection<String> projectsToRefresh) throws InterruptedException, CoreException {
		if (projectsToRefresh == null) {
			throw new NullPointerException("projectsToRefresh");
		}
		if (projectsToRefresh.isEmpty()) {
			throw new IllegalArgumentException("at least one project to refresh must be given");
		}
		refreshProjectsByName(projectsToRefresh);
	}

	void refreshWorkspace() {
		try {
			refreshResource(ResourcesPlugin.getWorkspace().getRoot(), "Refreshing workspace");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void refreshProjectsByName(Collection<String> projectsToRefresh)
			throws InterruptedException, CoreException {
		Collection<IProject> projects = getProjectsByNames(projectsToRefresh);
		if (projects.isEmpty()) {
			return;
		}
		refreshProjects(projects);
	}

	private Collection<IProject> getProjectsByNames(Iterable<String> projectsToRefresh) {
		Set<IProject> projects = new HashSet<>();
		for (String projectName : projectsToRefresh) {
			projects.add(ResourcesPlugin.getWorkspace().getRoot().getProject(projectName));
		}
		return projects;
	}

	private void refreshProjects(Iterable<IProject> projects) throws InterruptedException, CoreException {
		for (final IProject project : projects) {
			if (project.isOpen()) {
				String jobName = "Refreshing project " + project.getName();
				refreshResource(project, jobName);
			}
		}
	}

	private void refreshResource(final IResource resource, String jobName) throws InterruptedException {
		Job job = new Job(jobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
						@Override
						public void run(IProgressMonitor monitor) throws CoreException {
							resource.refreshLocal(IResource.DEPTH_INFINITE, monitor);
						}
					}, monitor);
					return Status.OK_STATUS;
				} catch (CoreException e) {
					String errorMsg = String.format("An exception happened while refreshing '%s'", resource.getName());
					return new Status(IStatus.ERROR, "MondShell", errorMsg);
				}
			}
		};
		job.setUser(true);
		job.setPriority(Job.LONG);
		job.schedule();
	}
}
