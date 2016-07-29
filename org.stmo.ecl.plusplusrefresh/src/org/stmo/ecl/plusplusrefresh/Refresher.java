package org.stmo.ecl.plusplusrefresh;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class Refresher {

	private final RefreshProject refreshProject;
	private final RefreshTargetPlatform refreshTargetPlatform;

	public Refresher() {
		refreshProject = new RefreshProject();
		refreshTargetPlatform = new RefreshTargetPlatform();
	}
	
	/**
	 * @param refreshProjects the projects that should be refreshed, {@code null} means that the complete workspace should be refreshed
	 * @param reloadTargetPlatform {@code true} if the target platform should be refreshed, otherwise {@code false}
	 * @throws CoreException
	 */
	public void refresh(boolean refreshProjects, boolean reloadTargetPlatform) throws CoreException {
		final Job refreshProjectsJob = createProjectRefreshJob(refreshProjects);
		
		if (reloadTargetPlatform) {
			refreshTargetPlatform.refreshTargetPlatform(refreshProjectsJob);			
		} else {
			refreshProjectsJob.schedule();
		}		
	}

	private Job createProjectRefreshJob(boolean refreshProjects) {
		if (refreshProjects) {
			return refreshProject.createRefreshJob();			
		} else {
			return new NullJob();
		}
	}
	
	private static class NullJob extends Job {

		public NullJob() {
			super("Non doing job");
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			return Status.OK_STATUS;
		}
		
	}
	
}
