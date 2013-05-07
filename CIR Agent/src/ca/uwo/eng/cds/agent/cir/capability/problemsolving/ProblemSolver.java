/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.capability.problemsolving;

import jade.core.behaviours.DataStore;
import jade.util.Logger;

import java.util.Iterator;

import ca.uwo.eng.cds.agent.cir.CIRAgent;
import ca.uwo.eng.cds.agent.cir.capability.problemsolving.actions.Action;

/**
 * @author geekatron
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
		//Register all the actions with the Directory Faciitator
		registerActions();
	}

	private void initProblemSolver(CIRAgent a, Action[] actions) {
		myLogger.log(Logger.INFO, "Initiailizing Problem Solver: " + getClass().getName());
		
		this.actions = new DataStore();
		this.agent = a;
		
		myLogger.log(Logger.INFO, "Initiailizing Problem Solver Actions[" + actions.length + "]");
		for (Action action : actions) {
			this.actions.put(action.getName(), action);
		}
	}
	
	/**
	 * Register all the available actions with the Directory Facilitator.
	 */
	void registerActions() {
		//Iterate over the actions and register each one
		Iterator it = this.actions.keySet().iterator();
	    while (it.hasNext()) {
	    	String key = (String) it.next();
	        
	        System.out.println(key + " = " + this.actions.get(key));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	
	/**
	 * Behaviour to see if there is a possible solution for the specified goal.
	 * @return
	 */
	public String solution() {
		//Look to see if there is an Available Solution
		return null;
	}
}
