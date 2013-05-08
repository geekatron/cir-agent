/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.example.actions;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;
import ca.uwo.eng.cds.agent.cir.CIRAgent;
import ca.uwo.eng.cds.agent.cir.capability.problemsolving.actions.Action;

/**
 * @author geekatron
 *
 */
public class GreetingAction extends Action {
	
	/**
	 * @param a
	 */
	public GreetingAction(CIRAgent a) {
		super(a);
	}

	/* (non-Javadoc)
	 * @see ca.uwo.agent.cir.Action#setupBehaviour()
	 */
	@Override
	public void setupBehaviour() {
		this.myLogger.log(Logger.INFO, "Setting up Action: " + getClass().getName());
		this.name = "greeting";
		this.actionDescription = "greeting";
		
		this.myLogger.log(Logger.INFO, "Setting up behaviour!");
		this.behaviour = new SolutionBehaviour(this.agent);
		this.myLogger.log(Logger.INFO, "Setting up behaviour: " + this.behaviour.getClass().getName());
	}
	
	private class SolutionBehaviour extends CyclicBehaviour{
		public SolutionBehaviour(Agent a) {
	}

		@Override
		public void action() {
			DataStore ds = this.getDataStore();
			
			String data = (String) ds.get("data");
			
			System.out.println("GREETING: " + data);
		}
		
	}//END of inner class SolutionBehaviour

}
