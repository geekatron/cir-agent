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
public class DummyCIRAgent extends CIRAgent {

	/**
	 * 
	 */
//	public DummyCIRAgent() {
//		super();
//		
//		//Setup a problem solver
//		//Action[] actions = new Action[0];
//		//this.problem_solver = new ProblemSolver(this, actions);
//		ProblemSolver ps = new ProblemSolver(this, null);
//		this.problem_solver = ps;
//	}
	
	protected void cirSetup() {
		//ProblemSolver ps = new ProblemSolver(this, null);
		//this.problem_solver = ps;
		this.problem_solver.setupPS(null);
	}

}
