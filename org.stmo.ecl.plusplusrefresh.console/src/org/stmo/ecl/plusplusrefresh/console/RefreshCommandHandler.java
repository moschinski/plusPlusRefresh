package org.stmo.ecl.plusplusrefresh.console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.stmo.ecl.plusplusrefresh.Refresher;

public class RefreshCommandHandler implements CommandProvider  {

	private static final String REFRESH_TARGET_PLATFORM = "-platform";
	
	private final Refresher refresher = new Refresher();
	
	@Override
	public String getHelp() {
		return null;
	}
	
	public void _refresh(CommandInterpreter ci) throws Exception {
		RefreshCmd cmd = parseRefreshCommand(ci);
		if (cmd.refreshPlatform) {
			refresher.refreshTargetPlatorm();
		} 
		if (cmd.projectsToRefresh.isEmpty()) {
			refresher.refreshAllOpenProjects();
		} else {
			refresher.refreshProjects(cmd.projectsToRefresh);
		}
	}

	private RefreshCmd parseRefreshCommand(CommandInterpreter ci) {
		boolean refreshPlatform = false;
		List<String> projectsToRefresh = new ArrayList<>();
		
		String argument;
		while ((argument = ci.nextArgument()) != null) {
			if (argument.equals(REFRESH_TARGET_PLATFORM)) {
				refreshPlatform = true;
			} else {
				projectsToRefresh.add(argument);
			}
		}
		return new RefreshCmd(refreshPlatform, projectsToRefresh);
	}
	
	private static class RefreshCmd {

		private final boolean refreshPlatform;
		private final List<String> projectsToRefresh;

		public RefreshCmd(boolean refreshPlatform, List<String> projectsToRefresh) {
			this.refreshPlatform = refreshPlatform;
			this.projectsToRefresh = Collections.unmodifiableList(projectsToRefresh);
		}
		
	}

}
