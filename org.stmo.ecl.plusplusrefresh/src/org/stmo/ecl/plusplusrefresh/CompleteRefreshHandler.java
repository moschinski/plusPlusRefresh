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
		try {
			new RefreshTargetPlatform().refreshTargetPlatform();
		} catch (CoreException e) {
			IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindow( event );
			MessageDialog.openError(workbenchWindow.getShell(), "Failed to refresh target platform", "Had an exception when refreshing workspace: " + e.getMessage());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
		new RefreshProject().refreshWorkspace();
		return null;
	}

}