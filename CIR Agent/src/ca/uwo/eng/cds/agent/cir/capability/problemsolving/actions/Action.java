/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.capability.problemsolving.actions;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.DataStore;
import jade.util.Logger;
import ca.uwo.eng.cds.agent.cir.CIRAgent;

/**
 * @author geekatron
 *
 */
public abstract class Action {
	protected Logger myLogger = Logger.getMyLogger(getClass().getName());
	
	protected CIRAgent agent;
	
	protected String name = "";
	protected String ownership = "";
	//Type?
	protected String actionDescription = "";
	protected DataStore preConditions;
	protected DataStore postConditions;
	protected String actionBody = "";
	
	protected CyclicBehaviour behaviour;
	
	public String getName() {
		return name;
	}
	
	//Is this necessary?
	public void setName(String name) {
		this.name = name;
		this.actionDescription = name;
	}
	
	public Behaviour getBehaviour() {
		return behaviour;
	}
	
	public abstract void setupBehaviour();
	
	/**
	 * 
	 */
	public Action(CIRAgent a) {
		this.agent = a;
		
		this.setupBehaviour();
	}

}
