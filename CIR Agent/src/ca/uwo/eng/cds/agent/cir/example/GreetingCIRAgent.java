/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.example;

import ca.uwo.eng.cds.agent.cir.CIRAgent;
import ca.uwo.eng.cds.agent.cir.capability.problemsolving.ProblemSolver;
import ca.uwo.eng.cds.agent.cir.capability.problemsolving.actions.Action;
import ca.uwo.eng.cds.agent.cir.example.actions.GreetingAction;

/**
 * @author geekatron
 *
 */
public class GreetingCIRAgent extends CIRAgent {

	/**
	 * Setup a CIRAgent with the required Actions
	 */
//	public GreetingCIRAgent() {
//		//Setup a problem solver
//		Action[] actions = new Action[1];
//		actions[0] = new GreetingAction(this);
//		
//		this.problem_solver = new ProblemSolver(this, actions);
//	}
	
	protected void cirSetup() {
		Action[] actions = new Action[1];
		actions[0] = new GreetingAction(this);
		
		//this.problem_solver = new ProblemSolver(this, actions);
		this.problem_solver.setupPS(actions);
		
		System.out.println("Added Problem Solver to CIR Agent!");
	}

}
