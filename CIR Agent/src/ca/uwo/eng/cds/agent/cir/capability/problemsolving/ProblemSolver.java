/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.capability.problemsolving;

import jade.core.ServiceDescriptor;
import jade.core.behaviours.DataStore;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.util.Logger;

import java.util.Iterator;

import ca.uwo.eng.cds.agent.cir.CIRAgent;
import ca.uwo.eng.cds.agent.cir.capability.problemsolving.actions.Action;
import ca.uwo.eng.cds.agent.cir.knowledge.DomainKnowledge;

/**
 * @author geekatron
 * 
 * Currently Simulating for the project
 *
 *	Goal-driven approach
 * 	PS: Gi X ACi X Wti -> Sig
 * 		Gi = Set of Agi's goals
 * 		ACi = Set of Agi's domain actions
 * 		Wi = Set of Agi's world history
 * 		Sig = Set of Agi's solutions for g E Gi
 * 
 *  Ps: local-control over achieving a domain goal
 *  	- irresponsible for domain-actions associated with interdependency problem
 *
 */
public class ProblemSolver {
	private Logger myLogger = Logger.getMyLogger(getClass().getName());
	
	private CIRAgent agent;
	private DataStore actions;
	/**
	 * 
	 */
	public ProblemSolver(CIRAgent a, Action[] actions) {
		myLogger.log(Logger.INFO, "Problem solver created! " + getClass().getName());
		
		//Initialize the Problem Solver
		initProblemSolver(a, actions);
		
		if(actions != null && actions.length > 0) {
			//Setup the actions
			setupActions(actions);
			//Register all the actions with the Directory Facilitator
			registerActions();
		} else {
			myLogger.log(Logger.INFO, "No actions to initiate with Problem Solver!");
		}
	}

	private void initProblemSolver(CIRAgent a, Action[] actions) {
		myLogger.log(Logger.INFO, "Initiailizing Problem Solver: " + getClass().getName());
		
		this.actions = new DataStore();
		this.agent = a;
	}
	
	private void setupActions(Action[] actions) {
		myLogger.log(Logger.INFO, "Initiailizing Problem Solver Actions[" + actions.length + "]");
		for (Action action : actions) {
			this.actions.put(action.getName(), action);
		}
	}
	
	/**
	 * Register all the available actions with the Directory Facilitator.
	 */
	void registerActions() {
		//Setup the components necessary to register the Problem Solver's Actions with the Directory Facilitator
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = null;
		//Set the agent name for the DFD
		dfd.setName(this.agent.getAID());
		
		//Iterate over the actions and register each one
		Iterator it = this.actions.keySet().iterator();
	    while (it.hasNext()) {	    	
	    	//Retrieve the Key (String) and Value (Action)
	    	String key = (String) it.next();
	        Action value = (Action) this.actions.get(key);
	        
	        //Create the Service Description for the Action
	        sd = new ServiceDescription();
	        sd.setType(value.getName());
	        sd.setName(this.agent.getName());
	        sd.setOwnership(value.getOwnership());
	        //Add the Service Description to the DFD
	        dfd.addServices(sd);
	        
	        //Output to console - debugging
	        System.out.println(key + " = " + this.actions.get(key));
	        //Remove from the iterator
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    //Try to register the Service Descriptions with the DF
	    try {
	    	DFService.register(this.agent, dfd);
	    } catch (FIPAException e) {
	    	myLogger.log(Logger.SEVERE, "Agent "+this.agent.getLocalName()+" - Cannot register with DF", e);
	    	//Destroy the agent since it couldn't register it's capabilities
			this.agent.doDelete();
	    }
	}
	
	
	/**
	 * Behaviour to see if there is a possible solution for the specified goal.
	 * 
	 * This should be a behaviour of the Problem Solver vs an action that
	 * the Problem Solver can access.
	 * @return
	 */
	public void solution() {
		DomainKnowledge dk = this.agent.domain_knowledge;
		
		//Look to see if there is an Available Solution
		//	Check to see the domain knowledge for goals
		String goal = dk.peekGoal();
		//	 Check to see if any Action can satisfy the Goal
		if(actions.containsKey(goal)) {
			//If there is an action matching the goal add the potential solution to the Potential Solution Knowledge
			dk.addSolution(goal, ((Action)actions.get(goal)).getName());
		} 
		
		//Go to pre-interaction (If there is no available solution pre-interaction will identify interdependency)
		this.agent.getPreInteractionCapability().reason();
		
		//Transition the mental state (1 -> 2 || Solutions -> Desire)
		this.agent.transitionState(2);
	}
}
