package org.stmo.ecl.plusplusrefresh;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.pde.core.target.ITargetDefinition;
import org.eclipse.pde.core.target.ITargetPlatformService;
import org.eclipse.pde.core.target.LoadTargetDefinitionJob;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.target.P2TargetUtils;
import org.eclipse.pde.internal.ui.PDEUIMessages;

@SuppressWarnings("restriction")
class RefreshTargetPlatform {
	
	/**
	 * @param callback the job that should be executed when refreshing the target platform is done or not necessary
	 * @throws CoreException 
	 */
	void refreshTargetPlatform(Job callback) throws CoreException {
		ITargetPlatformService platfomService = (ITargetPlatformService) PDECore.getDefault()
				.acquireService(ITargetPlatformService.class.getName());
		ITargetDefinition activeTarget = platfomService.getWorkspaceTargetDefinition();
		activeTarget.resolve(new NullProgressMonitor());
		if (!activeTarget.isResolved() || activeTarget.getStatus().getSeverity() == IStatus.ERROR || platfomService.compareWithTargetPlatform(activeTarget).isOK()) {
			callback.schedule();
			return;
		}
		triggerReload(activeTarget, callback);
	}

	private void triggerReload(ITargetDefinition activeTarget, final Job callback) {
		LoadTargetDefinitionJob.load(activeTarget, new JobChangeAdapter() {
			
			@Override
			public void done(IJobChangeEvent event) {
				callback.schedule();
			}
			
		});
		runGC();
	}
	
	/**
	 * @see {@link org.eclipse.pde.internal.ui.preferences.TargetPlatformPreferencePage#performOk()} 
	 */
	private void runGC() {
		Job job = new Job(PDEUIMessages.TargetPlatformPreferencePage2_26) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask(PDEUIMessages.TargetPlatformPreferencePage2_27, IProgressMonitor.UNKNOWN);
				P2TargetUtils.garbageCollect();
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}
}
