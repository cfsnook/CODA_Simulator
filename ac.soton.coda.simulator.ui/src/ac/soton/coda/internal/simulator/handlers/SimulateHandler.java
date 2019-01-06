/*******************************************************************************
 * (c) Crown owned copyright 2019 (UK Ministry of Defence)
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
 * International License
 *
 * This is to identify the UK Ministry of Defence as owners along with the license rights provided. The
 * URL of the CC BY NC SA 4.0 International License is 
 * http://creativecommons.org/licenses/by-nc-sa/4.0/legalcode (Accessed 02-NOV-15).
 *  
 * Contributors:
 *   University of Southampton - Initial API and implementation
 *******************************************************************************/
package ac.soton.coda.internal.simulator.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eventb.core.IEventBRoot;
import org.eventb.core.IMachineRoot;
import org.eventb.emf.core.EventBElement;
import org.rodinp.core.IRodinProject;
import org.rodinp.core.RodinCore;

import ac.soton.coda.internal.simulator.perspectives.SimPerspective;
import ac.soton.coda.internal.simulator.views.SimulatorView;
import de.prob.core.Animator;
import de.prob.core.command.LoadEventBModelCommand;
import de.prob.exceptions.ProBException;


/**
 * @author cfsnook
 *
 */

public class SimulateHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// Get the current selection
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);

		// Get the selected machine
		IMachineRoot mchRoot = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() == 1) {
				Object obj = ssel.getFirstElement();
				if (obj instanceof IMachineRoot) {
					mchRoot = (IMachineRoot) obj;
				}
			}
		}

		// Return if the current selection is not a machine root.
		if (mchRoot == null)
			return null;

		Animator animator = Animator.getAnimator();
		SimulatorView.getSimulator().initialise(mchRoot);
		try {
			LoadEventBModelCommand.load(animator, mchRoot);
			animator.getLanguageDependendPart().reload(animator);
		} catch (ProBException e1) {
			e1.printStackTrace();
		}

		// Switch to CODA simulation perspective.
		final IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil
				.getActiveWorkbenchWindow(event);
		try {
			workbench.showPerspective(SimPerspective.PERSPECTIVE_ID,
					activeWorkbenchWindow);
		} catch (WorkbenchException e) {
			// ignore exceptions
		}
		return null;
	}


	public static IEventBRoot getEventBRoot(EventBElement element) {
		Resource resource = element.eResource();
		if (resource != null && resource.isLoaded()) {
			IFile file = WorkspaceSynchronizer.getFile(resource);
			IRodinProject rodinProject = RodinCore.getRodinDB()
					.getRodinProject(file.getProject().getName());
			IEventBRoot root = (IEventBRoot) rodinProject.getRodinFile(
					file.getName()).getRoot();
			return root;
		}
		return null;
	}

}
