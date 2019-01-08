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

package ac.soton.coda.internal.simulator.perspectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * <p>
 * The perspective for CODA Simulation perspective. This is the default
 * implementation. All views and actions are added via Eclipse extension
 * mechanism.
 * </p>
 * 
 * @author htson
 * @version 0.1
 * @see IPerspectiveFactory
 * @since 3.0
 */
public class SimPerspective implements IPerspectiveFactory {

	// The Perspective ID.
	public static final String PERSPECTIVE_ID = "ac.soton.coda.simulator.simPerspective";

	@Override
	public void createInitialLayout(IPageLayout layout) {
		// Everything is done via extension mechanism.
	}

}
