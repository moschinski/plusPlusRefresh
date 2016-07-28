package org.stmo.ecl.plusplusrefresh;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;

public class Refresher {

	private final RefreshProject refreshProject;
	private final RefreshTargetPlatform refreshTargetPlatform;

	public Refresher() {
		refreshProject = new RefreshProject();
		refreshTargetPlatform = new RefreshTargetPlatform();
	}
	
	
	public void refreshTargetPlatorm() throws CoreException, InterruptedException {
		refreshTargetPlatform.refreshTargetPlatform();
	}
	
	public void refreshProjects(Collection<String> projectToRefresh) throws CoreException, InterruptedException {
		refreshProject.refreshProjects(projectToRefresh);
	}
	
	public void refreshAllOpenProjects() {
		refreshProject.refreshWorkspace();
	}
	
}
