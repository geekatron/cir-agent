/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.capability;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.DataStore;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.util.Logger;
import ca.uwo.eng.cds.agent.cir.CIRAgent;
import ca.uwo.eng.cds.agent.cir.capability.interaction.AssignmentInteractionSimulation;
import ca.uwo.eng.cds.agent.cir.knowledge.DomainKnowledge;

/**
 * @author geekatron
 *
 */
public class Reasoning {
	//Logger
	private Logger myLogger = Logger.getMyLogger(getClass().getName());
	
	boolean capabilityInterdependency = false;
	boolean decompositionInterdependency = false;
	boolean interestInterdependency = false;
	boolean resourceInterdependency = false;
	boolean knowledgeInterdependency = false;
	
	String goal;
	String[] goalPerformative;
	String[] goalDescription;
	String g;
	
	ArrayList<AID> candidates;
	
	//AID[] candidates;
	
	CIRAgent agent;
	
	/**
	 * 
	 */
	public Reasoning(CIRAgent a) {
		this.agent = a;
	}
	
	public void reason() {
		myLogger.log(Logger.INFO, "Agent (" + agent.getLocalName() + ") executing reasoning!");
		
		capabilityInterdependency = false;
		decompositionInterdependency = false;
		interestInterdependency = false;
		resourceInterdependency = false;
		knowledgeInterdependency = false;
		
		boolean hasSolution = false;
		
		//candidates = new AID[0];
		candidates = new ArrayList<AID>();
		
		//Clear previous interactions
		this.agent.getInteractions().clear();
		
		//Check to see if a potential solution was generated
		DomainKnowledge dk = this.agent.domain_knowledge;
		
		goal = dk.peekGoal();
		//Find the Goal description
		goalPerformative = goal.split("g=");
		goalDescription = goalPerformative[1].split("\\(");
		g = goalDescription[0];
		
		//Check for the solution
		hasSolution = dk.containsSolution(goal);
		
		//If not, run through pre-interaction
		if(!hasSolution) {
			reasonWhich();
			reasonHow();
		} else {
			//No pre-interaction necessary
			//Transition the mental state (1 -> 2 || Solutions -> Desire)
			this.agent.nextState();
			//Transition the mental state (2 -> 3 || Desire -> Commitment)
			this.agent.nextState();
		}

	}

	private void reasonWhich() {
		myLogger.log(Logger.INFO, "Agent (" + agent.getLocalName() + ") identifying interdependencies!");
		//Identify the types of interdependency (Which interdependency)
		//	There was no Possible Solution which means there is capability interdependence
		capabilityInterdependency = true;
		
		myLogger.log(Logger.INFO, "Agent (" + agent.getLocalName() + ") CAPABILITY interdependencies == " + capabilityInterdependency);
		myLogger.log(Logger.INFO, "Agent (" + agent.getLocalName() + ") DECOMPOSITION interdependencies == " + decompositionInterdependency);
		myLogger.log(Logger.INFO, "Agent (" + agent.getLocalName() + ") INTEREST interdependencies == " + interestInterdependency);
		myLogger.log(Logger.INFO, "Agent (" + agent.getLocalName() + ") RESOURCE interdependencies == " + resourceInterdependency);
		myLogger.log(Logger.INFO, "Agent (" + agent.getLocalName() + ") KNOWLEDGE interdependencies == " + knowledgeInterdependency);
		
		
		//Determine the agent's beliefs about their existence
	}
	
	private void reasonHow() {
		//To rationally anticipate the characteristics of the interaction devices
		//The number of potential participants
		//	Query the DF for other agents that can fulfill the goal
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(g);
		dfd.addServices(sd);
		
		DFAgentDescription[] result = null;
		try {
			result = DFService.search(this.agent, dfd);
			System.out.println(result.length + " results" );
	        if (result.length>0) {
	            System.out.println(" " + result[0].getName() );
	            
	            for (int i = 0; i < result.length; i++) {
					DFAgentDescription dfAgentDescription = result[i];
					
					candidates.add(dfAgentDescription.getName());
				}
	            
	        }
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//If there is more than one result then setup the Capability Interdependency
		
		//Add the interaction to the possible solution knowledge
		if(capabilityInterdependency) {
			//Load the mock interdependency
			if(candidates != null && !candidates.isEmpty()) {
				DataStore interactions = this.agent.getInteractions();
				AssignmentInteractionSimulation ais = new AssignmentInteractionSimulation(this.agent);
				ais.setupCandidates(candidates);
				ais.setupGoal(goalPerformative[1]);
				interactions.put("ASSIGNMENT", ais);
			}

			//Load the interdependency manager for integration
		}
		if(decompositionInterdependency) {
			
		}
		if(interestInterdependency) {
			
		}
		if(resourceInterdependency) {
			
		}
		if(knowledgeInterdependency) {
			
		}
		//Pre-interaction is complete so transition the mental state (1 -> 2 || Solutions -> Desire)
		this.agent.nextState();
	}
}
