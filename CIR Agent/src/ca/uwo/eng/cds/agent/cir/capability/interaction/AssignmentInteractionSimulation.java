/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.capability.interaction;

import java.util.ArrayList;
import java.util.Random;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;
import ca.uwo.eng.cds.agent.cir.CIRAgent;

/**
 * @author geekatron
 *
 */
public class AssignmentInteractionSimulation {
	private Logger myLogger = Logger.getMyLogger(getClass().getName());
	
	CIRAgent agent;
	//AID[] candidates;
	ArrayList<AID> candidates;
	AID candidate;
	String goal;
	/**
	 * 
	 */
	public AssignmentInteractionSimulation(CIRAgent a) {
		this.agent = a;
		this.candidate = null;
	}
	
	//public void setupCandidates(AID[] candidates) {
	public void setupCandidates(ArrayList<AID> candidates) {
		this.candidates = candidates;
	}
	
	public void setupGoal(String goal) {
		this.goal = goal;
	}
	
	public void setupAssignment(String[] candidates, String goal) {
		myLogger.log(Logger.INFO, "Agent "+agent.getLocalName() + " EXECUTING SIMULATION ASSIGNMENT INTERACTION DEVICE!");
	}
	
	public void interact() {
		myLogger.log(Logger.INFO, "Agent "+agent.getLocalName() + " SIMULATION INTERACTING!");
		//Randomly pick one of the candidates
		if(candidates.size() > 1) {
			int index = new Random().nextInt(candidates.size());
			candidate = candidates.get(index);
		} else {
			candidates.trimToSize();
			candidate = candidates.get(0);
		}
		//Msg the candidate for execution
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setContent("g=" + goal);
		msg.addReceiver(candidate);
		
		this.agent.send(msg);
	}

}
