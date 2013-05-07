/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.example.actions;

import ca.uwo.eng.cds.agent.cir.CIRAgent;
import ca.uwo.eng.cds.agent.cir.capability.problemsolving.actions.Action;

/**
 * @author geekatron
 *
 */
public class RequestAction extends Action {

	/**
	 * @param a
	 */
	public RequestAction(CIRAgent a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ca.uwo.agent.cir.Action#setupBehaviour()
	 */
	@Override
	public void setupBehaviour() {
		this.name = "request";
		
		

	}

}
