/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.knowledge.domain.goals;

import jade.core.behaviours.DataStore;

/**
 * @author geekatron
 *
 */
public class Goal {
	private String name = "";
	private String type = "";
	private String preferredTime = "";
	private String expectedTime = "";
	
	/**
	 * 
	 */
	public Goal() {
		// TODO Auto-generated constructor stub
	}
	
	public Goal(String name, String type, String prefTime, String expTime) {
		this.name = name;
		this.type = type;
		this.preferredTime = prefTime;
		this.expectedTime = expTime;
		
		System.out.println("Created goal!");
	}

}
