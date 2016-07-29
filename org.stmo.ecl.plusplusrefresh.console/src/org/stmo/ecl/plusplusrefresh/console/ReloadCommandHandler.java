package org.stmo.ecl.plusplusrefresh.console;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.stmo.ecl.plusplusrefresh.Refresher;

public class ReloadCommandHandler implements CommandProvider  {
	
	private final Refresher refresher = new Refresher();
	
	@Override
	public String getHelp() {
		return null;
	}
	
	/**
	 * the 'refresh' command keyword is already assigned...
	 * 
	 * @param ci {@link CommandInterpreter}
	 * @throws Exception 
	 */
	public void _reload(CommandInterpreter ci) throws Exception {
		refresher.refresh(/* refresh all projects */ true, /*refresh target platform */ true);
		ci.println("Reload of target platform and refresh of workspace triggered");
	}
	
}
