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
		boolean reloadTargetPlatform = shouldReloadTargetPlatform(ci);
		refresher.refresh(/* refresh all projects */ true, reloadTargetPlatform);
		ci.println("Triggered refresh of workspace" + (reloadTargetPlatform ? " and reload of target platform" : ""));
	}

	private boolean shouldReloadTargetPlatform(CommandInterpreter ci) {
		String arg;
		while ((arg = ci.nextArgument()) != null) {
			if (arg.equalsIgnoreCase("-platform")) {
				return true;
			} else {
				throw new IllegalArgumentException(String.format("The reload argument '%s' is unknown", arg));
			}
		}
		return false;
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
