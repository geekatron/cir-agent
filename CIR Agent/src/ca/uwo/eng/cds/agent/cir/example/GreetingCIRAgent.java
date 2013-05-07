/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.example;

import ca.uwo.eng.cds.agent.cir.CIRAgent;
import ca.uwo.eng.cds.agent.cir.capability.problemsolving.ProblemSolver;
import ca.uwo.eng.cds.agent.cir.capability.problemsolving.actions.Action;
import ca.uwo.eng.cds.agent.cir.example.actions.SolutionAction;

/**
 * @author geekatron
 *
 */
public class GreetingCIRAgent extends CIRAgent {

	/**
	 * Setup a CIRAgent with the required Actions
	 */
	public GreetingCIRAgent() {
		//Setup a problem solver
		Action[] actions = new Action[1];
		actions[0] = new SolutionAction(this);
		
		this.problem_solver = new ProblemSolver(this, actions);
	}

}
