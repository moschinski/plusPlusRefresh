package org.stmo.ecl.plusplusrefresh;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.pde.core.target.ITargetDefinition;
import org.eclipse.pde.core.target.ITargetPlatformService;
import org.eclipse.pde.core.target.LoadTargetDefinitionJob;
import org.eclipse.pde.internal.core.PDECore;

@SuppressWarnings("restriction")
class RefreshTargetPlatform {
	
	void refreshTargetPlatform() throws CoreException, InterruptedException {
		ITargetPlatformService platfomService = (ITargetPlatformService) PDECore.getDefault()
				.acquireService(ITargetPlatformService.class.getName());
		ITargetDefinition activeTarget = null;
		activeTarget = platfomService.getWorkspaceTargetDefinition();
		
		// copy target before compare (?)
		activeTarget.resolve(new NullProgressMonitor());
		if (!activeTarget.isResolved() || activeTarget.getStatus().getSeverity() == IStatus.ERROR) {
			return;
		}
		if (platfomService.compareWithTargetPlatform(activeTarget).isOK()) {
			return;
		}
		triggerAndWaitReload(activeTarget);
	}

	private void triggerAndWaitReload(ITargetDefinition activeTarget) throws InterruptedException {
		final CountDownLatch targetLoaded = new CountDownLatch(1);
		LoadTargetDefinitionJob.load(activeTarget, new JobChangeAdapter() {
			
			@Override
			public void done(IJobChangeEvent event) {
				targetLoaded.countDown();
			}
			
		});
		targetLoaded.await(1, TimeUnit.MINUTES);
	}
}
