package org.stmo.ecl.plusplusrefresh.console;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.stmo.ecl.plusplusrefresh.AutomaticBuildConfigurator;
import org.stmo.ecl.plusplusrefresh.Refresher;

public class PlusPlusCommandHandler implements CommandProvider  {
	
	private final Refresher refresher = new Refresher();
	private final AutomaticBuildConfigurator automaticBuildConfigurator = new AutomaticBuildConfigurator();
	
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
	
	/**
	 * @param ci {@link CommandInterpreter}
	 * @throws Exception 
	 */
	public void _automaticBuild(CommandInterpreter ci) throws Exception {
		String enableStr = ci.nextArgument();
		if (enableStr == null) {
			throw new IllegalArgumentException("A boolean argument for the 'automaticBuild' command must be given");
		}
		boolean enable = Boolean.valueOf(enableStr).booleanValue();
		if (enable) {
			automaticBuildConfigurator.enableAutomaticBuild();			
		} else {
			automaticBuildConfigurator.disableAutomaticBuild();						
		}
		ci.println((enable ? "Enabled" : "Disabled") + " automatic builds");
	}
	
}
