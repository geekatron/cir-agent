/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.knowledge.domain.goals;

import java.util.Stack;

/**
 * @author geekatron
 *
 */
public class GoalKnowledge {
	private Stack<String> goals;
	/**
	 * 
	 */
	public GoalKnowledge() {
		goals = new Stack<String>();
	}

	public void updateGoals(String h) {
		if(h != null) {
			goals.addElement(h);
		}
	}
	
	public Stack<String> getGoals() {
		return goals;
	}
	
	public String peekGoal() {
		return goals.peek();
	}
	
	public String getGoal() {
		return goals.pop();
	}
}
