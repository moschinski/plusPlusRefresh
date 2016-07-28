package org.stmo.ecl.plusplusrefresh;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class CompleteRefreshHandler extends AbstractHandler {

	public static final String COMMAND_ID = "org.stmo.ecl.plusplusrefresh.CompleteRefreshCommand";

	@Override
	public Object execute(ExecutionEvent event) {
		Refresher refresher = new Refresher();
		try {
			refresher.refreshTargetPlatorm();
			refresher.refreshAllOpenProjects();
		} catch (CoreException e) {
			IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
			MessageDialog.openError(workbenchWindow.getShell(), "Failed to refresh workspace",
					"Had an exception when refreshing workspace: " + e.getMessage());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return null;
	}

}