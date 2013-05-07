/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.knowledge.domain.localhistory;

import java.util.Stack;

/**
 * @author geekatron
 *
 */
public class History {
	private Stack<String> history;
	/**
	 * 
	 */
	public History() {
		history = new Stack<String>();
	}
	
	public void updateHistory(String h) {
		if(h != null) {
			history.push(h);
		}
	}
	
	public Stack<String> getHistory() {
		return history;
	}

}
