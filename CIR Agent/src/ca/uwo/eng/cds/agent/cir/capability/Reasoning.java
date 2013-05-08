/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.capability;

import jade.util.Logger;
import ca.uwo.eng.cds.agent.cir.CIRAgent;
import ca.uwo.eng.cds.agent.cir.knowledge.DomainKnowledge;

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
		
		boolean hasSolution = false;
		
		//Check to see if a potential solution was generated
		DomainKnowledge dk = this.agent.domain_knowledge;
		
		String goal = dk.peekGoal();
		//Find the Goal description
		String[] goalPerformative = goal.split("g=");
		String[] goalDescription = goalPerformative[1].split("\\(");
		String g = goalDescription[0];
		
		//Check for the solution
		hasSolution = dk.containsSolution(g);
		
		//If not, run through pre-interaction
		if(hasSolution) {
			reasonWhich();
			reasonHow();
		} else {
			//No pre-interaction necessary
			//Transition the mental state (1 -> 2 || Solutions -> Desire)
			this.agent.nextState();
			//Transition the mental state (2 -> 3 || Desire -> Commitment)
			this.agent.nextState();
		}

	}

	private void reasonWhich() {
		//Identify the types of interdependency (Which interdependency)
		//Determine the agent's beliefs about their existence
	}
	
	private void reasonHow() {
		//To rationally anticipate the characteristics of the interaction devices
		//The number of potential participants
		
		
		//Add the interaction to the possible solution knowledge
		
		//Pre-interaction is complete so transition the mental state (1 -> 2 || Solutions -> Desire)
		this.agent.nextState();
	}
}
