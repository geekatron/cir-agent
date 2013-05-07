/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.knowledge.domain.possiblesolutions;

import jade.core.behaviours.DataStore;

/**
 * @author geekatron
 *
 */
public class SolutionKnowledge {
	private DataStore possible_solutions;
	/**
	 * 
	 */
	public SolutionKnowledge() {
		possible_solutions = new DataStore();
	}
	
	public void addSolution(String key, String solution) {
		possible_solutions.put(key, solution);
	}
	
	public boolean containsSolution(String key) {
		return possible_solutions.containsKey(key);
	}
	
	public void removeSolutionByKey(String key) {
		possible_solutions.remove(possible_solutions.get(key));
	}
	
	public void removeSolution(Object o) {
		possible_solutions.remove(o);
	}

}
