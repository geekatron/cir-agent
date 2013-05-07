/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.capability;

import jade.util.Logger;
import ca.uwo.eng.cds.agent.cir.CIRAgent;

/**
 * @author geekatron
 *
 */
public class Reasoning {
	//Logger
	private Logger myLogger = Logger.getMyLogger(getClass().getName());
	
	CIRAgent agent;
	
	/**
	 * 
	 */
	public Reasoning(CIRAgent a) {
		this.agent = a;
	}
	
	public void reason() {
		myLogger.log(Logger.INFO, "Agent (" + agent.getLocalName() + ") executing reasoning!");
		//Check to see if a potential solution was generated
		//If not, run through pre-interaction
				
		//After pre-interaction is complete update the Mental State ( 1 -> 2 || Solution -> Desire)
	}

	private void reasonWhich() {
		
	}
	
	private void reasonHow() {
		
	}
}
