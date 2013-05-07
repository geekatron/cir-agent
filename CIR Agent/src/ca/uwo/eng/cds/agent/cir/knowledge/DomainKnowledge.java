/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.knowledge;

import java.util.Stack;

import ca.uwo.eng.cds.agent.cir.knowledge.domain.goals.GoalKnowledge;
import ca.uwo.eng.cds.agent.cir.knowledge.domain.localhistory.History;
import ca.uwo.eng.cds.agent.cir.knowledge.domain.possiblesolutions.SolutionKnowledge;

/**
 * @author geekatron
 *
 */
public class DomainKnowledge {
	private GoalKnowledge goals;
	private String interests;
	private SolutionKnowledge possible_solutions;
	
	private History local_history;
	
	/**
	 * 
	 */
	public DomainKnowledge() {
		//Initialize the Domain Knowledge
		initDomainKnowledge();
	}
	
	private void initDomainKnowledge() {
		goals = new GoalKnowledge();
		interests = "";
		possible_solutions = new SolutionKnowledge();
		
		local_history = new History();
	}
	
	/* ************************
	 * 	GETTER AND SETTERS
	 * ************************
	 */
	
	/* 		Related to the Local History
	 * ======================================== 
	 * */
	public void updateHistory(String h) {
		this.local_history.updateHistory(h);
	}
	
	public Stack<String> getHistory() {
		return this.local_history.getHistory();
	}
	
	/* 	Related to Goal Knowledge (Goals)
	 * ========================================
	 * */
	public void updateGoals(String g) {
		this.goals.updateGoals(g);
	}
	
	public Stack<String> getGoals() {
		return this.goals.getGoals();
	}
	
	public String peekGoal() {
		return this.goals.peekGoal();
	}
	
	public String getGoal() {
		return this.goals.getGoal();
	}
	
	/* 	Related to Possible Solutions per Goal 
	 * ========================================
	 * */
	public SolutionKnowledge getSolutions() {
		return this.possible_solutions;
	}
	
	public void addSolution(String key, String solution) {
		this.possible_solutions.addSolution(key, solution);
	}
	
	public boolean containsSolution(String key) {
		return this.possible_solutions.containsSolution(key);
	}
	
	public void removeSolutionByKey(String key) {
		this.possible_solutions.removeSolutionByKey(key);
	}
	
	public void removeSolution(Object o) {
		this.possible_solutions.removeSolution(o);
	}
	
	/* ************************
	 * 	END GETTER AND SETTERS
	 * ************************
	 */

}
