package org.stmo.ecl.plusplusrefresh;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class AutomaticBuildConfigurator {

	public void enableAutomaticBuild() throws CoreException {
		setAutoBuild(true);
	}

	public void disableAutomaticBuild() throws CoreException {
		setAutoBuild(false);
	}

	private static void setAutoBuild(boolean enable) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription desc = workspace.getDescription();
		boolean isAutoBuilding = desc.isAutoBuilding();
		if (isAutoBuilding != enable) {
			desc.setAutoBuilding(enable);
			workspace.setDescription(desc);
		}
	}
}
