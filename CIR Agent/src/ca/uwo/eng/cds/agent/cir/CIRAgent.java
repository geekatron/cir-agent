/**
 * 
 */
package ca.uwo.eng.cds.agent.cir;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.DataStore;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

import java.util.Stack;

import javax.sound.midi.MetaEventListener;

import ca.uwo.eng.cds.agent.cir.capability.Reasoning;
import ca.uwo.eng.cds.agent.cir.capability.communication.Communication;
import ca.uwo.eng.cds.agent.cir.capability.problemsolving.ProblemSolver;
import ca.uwo.eng.cds.agent.cir.knowledge.DomainKnowledge;
import ca.uwo.eng.cds.agent.cir.knowledge.domain.localhistory.History;

/**
 * @author geekatron
 *
 */
public class CIRAgent extends Agent {
	//Logger
	private Logger myLogger = Logger.getMyLogger(getClass().getName());
	
	/* ====================
	 * 		KNOWLEDGE  
	 * ====================
	 * 
	 * Broken into: Domain and Model
	 * 	Domain:
	 * 		- local history
	 * 		- goals
	 * 		- possible solution(s) per goal
	 * 		- interests
	 * 	Model:
	 * 		- self model
	 * 		- other agent model
	 * */
	//Domain Knowledge
	public DomainKnowledge domain_knowledge;	
	//Model Knowledge
	protected DataStore model_knowledge;
	
	/* ======================
	 * 		CAPABILITIES 
	 * ======================
	 * 
	 * Break down into: problem-solving, interaction, communication
	 * 	Problem-solving:
	 * 			- Domain Specific Problem
	 * 	Interaction:
	 * 			- Assignment
	 * 			- Resource Scheduling
	 * 			- Conflict Resolution
	 * 			- Redundancy Avoidance
	 * 			- Synchronization
	 * 			- Knowledge Update
	 * 	Communication: ?
	 * 			- Handle communication between agents?
	 * 			-- i.e. Request Communication Handler?
	 * 
	 * 	Pre-Interaction: ?
	 * 			- Identify Interdependencies
	 * 			- Identify potential agents (schedule)
	 * 			- Identify the Interaction characteristics
	 * 			- Invoke the Interaction
	 * */
	//Problem Solving
	protected ProblemSolver problem_solver;
	//Interaction
	protected DataStore interactions;
	//Communication
	protected Communication communication;
	//Pre-Interaction
	protected Reasoning pre_interaction;
	
	/* ======================
	 * 		GOAL STATE 
	 * ======================
	 * 	The Mental State of the Goal as it is going through the different stages.
	 * 		State:
	 * 			- Initial		-> -1
	 * 			- Goal 			->  0
	 * 			- Solutions		->  1
	 * 			- Desire		->  2		
	 * 			- Commitment	->  3
	 * 			- Intention		->  4
	 * */
	private int mental_state = -1; 
	
	public int nextState() {
		if(mental_state == 0) {
			mental_state = 1;
		} else if(mental_state == 1) {
			mental_state = 2;
		} else if(mental_state == 2) {
			mental_state = 3;
		} else if(mental_state == 3) {
			mental_state = 4;
		} else if (mental_state == 4) {
			mental_state = 0;
		}
		return mental_state;
	}
	
	public boolean transitionState(int state) {
		if(mental_state == -1 && state == 0) {
			mental_state = state;
			return true;
		} else if(mental_state == 0 && state == 1) {
			mental_state = state;
			return true;
		} else if(mental_state == 1 && state == 2) {
			mental_state = state;
			return true;
		} else if(mental_state == 2 && state == 3) {
			mental_state = state;
			return true;
		} else if(mental_state == 3 && state == 4) {
			mental_state = state;
			return true;
		} else if(mental_state == 4 && state == 0) {
			mental_state = state;
			return true;
		} 
		//Should it be possible to reset back to the initial state?
		else if(mental_state == 4 && state == -1) {
			mental_state = state;
			return true;
		}
		
		return false;
	}
	
	public int getMentalState() {
		return mental_state;
	}
	
	/* ************************
	 * 	GETTER AND SETTERS
	 * ************************
	 */
	public ProblemSolver getProblemSolvingCapability() {
		return this.problem_solver;
	}
	
	public Reasoning getPreInteractionCapability() {
		return this.pre_interaction;
	}
	
	/* ************************
	 * 	END GETTER AND SETTERS
	 * ************************
	 */
	
	/**
	 * Initialize the CIR Agent 
	 */
	private void initCIRAgent() {
		myLogger.log(Logger.INFO, "Initializing CIR Agent!");
		
		//Initialize the Domain and Model Knowledge
		domain_knowledge = new DomainKnowledge();
		model_knowledge = new DataStore();
		
		//Initialize the Capabilities
		interactions = new DataStore(6);
		problem_solver = null;
		communication = null;
		pre_interaction = new Reasoning(this);
		
	}
		
	protected void setup() {
		//Initialize the CIR Agent
		initCIRAgent();
		
		/* 
		 * 		SETUP THE CIR AGENT'S CAPABILITITES 
		 * */
		//Setup the Problem Solving Capabilities
		//	Call the problem solver in-order to register it's actions/capabilities
		
		//Setup the Interaction Capability
		
		//Setup the Communication Capability
		this.communication = new Communication(this);
		
		
		//Setup all the Problem Solvers and their Actions/Service with the DF
		//Setup the solve for solution Service
		this.problem_solver = new ProblemSolver(this, null);
		
//		DFAgentDescription dfd = new DFAgentDescription();
//		ServiceDescription sd = new ServiceDescription();
//		ServiceDescription sd1 = new ServiceDescription();
//		
//		sd.setType("solution");
//		sd.setName(getName());
//		sd.setOwnership("CDS-Eng");
//		
//		sd1.setType("pintout");
//		sd1.setName(getName());
//		sd1.setOwnership("CDS-Eng");
//		
//		dfd.setName(getAID());
//		dfd.addServices(sd);
//		dfd.addServices(sd1);
//		
		try {
//			myLogger.log(Logger.INFO, "Trying to register the behaviour with DFD!");
//			DFService.register(this, dfd);
//			myLogger.log(Logger.INFO, "Successful registration!");
//			SolveForSolution SolutionBehaviour = new SolveForSolution(this);
//			TestBehaviour TestingBehaviour = new TestBehaviour(this);
//			addBehaviour(SolutionBehaviour);
//			addBehaviour(TestingBehaviour);
//			myLogger.log(Logger.INFO, "Added behaviour!");
		} catch (Exception e) {
			myLogger.log(Logger.SEVERE, "Agent " + getLocalName() + " - Cannot register with DF", e);
			doDelete();
		}
	}
	
}
