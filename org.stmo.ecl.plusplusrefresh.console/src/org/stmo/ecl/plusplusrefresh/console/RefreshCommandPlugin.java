package org.stmo.ecl.plusplusrefresh.console;

import java.util.Hashtable;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.BundleContext;

public class RefreshCommandPlugin extends Plugin {

	@Override
	public void start(BundleContext context) throws Exception {
		PlusPlusCommandHandler refreshCmdHandler = new PlusPlusCommandHandler();
		Hashtable<String, ?> properties = new Hashtable<String, String>();
		context.registerService(CommandProvider.class, refreshCmdHandler, properties);
	}
	
}
