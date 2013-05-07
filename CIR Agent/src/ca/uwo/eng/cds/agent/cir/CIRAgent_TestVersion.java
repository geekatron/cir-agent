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

import ca.uwo.eng.cds.agent.cir.capability.communication.Communication;

/**
 * @author geekatron
 *
 */
public class CIRAgent_TestVersion extends Agent {
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
	protected DataStore domain_knowledge;
	protected Stack<String> local_history;
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
	 * */
	//Problem Solving
	protected Object problem_solver;
	//Interaction
	protected DataStore interactions;
	//Communication
	protected Object communication;
	
	/**
	 * Initialize the CIR Agent 
	 */
	private void initCIRAgent() {
		myLogger.log(Logger.INFO, "Initializing CIR Agent!");
		
		//Initialize the Domain and Model Knowledge
		domain_knowledge = new DataStore();
		local_history = new Stack<String>();
		model_knowledge = new DataStore();
		
		//Initialize the Capabilities
		problem_solver = new Object();
		interactions = new DataStore(6);
		communication = new Object();
		
	}
	
	private class TestBehaviour extends CyclicBehaviour {

		public TestBehaviour(Agent a) {
			super(a);
		}
		
		@Override
		public void action() {
			ACLMessage msg = myAgent.receive();
			
			if(msg != null) {
			
			myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Received SOLUTION/PS Request from "+msg.getSender().getLocalName());
			myLogger.log(Logger.INFO, "Message performative "+ ACLMessage.getPerformative(msg.getPerformative()));
			myLogger.log(Logger.INFO, "Message content "+ msg.getContent());
			
			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.INFORM);
			reply.setContent("Received!");
			
			send(reply); 
			} else {
				block();
			}
		}
		
	}
	
	private class SolveForSolution extends CyclicBehaviour {
			
			public SolveForSolution(Agent a) {
				super(a);
			}
	
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage msg = myAgent.receive();
				
				if(msg != null) {
					ACLMessage reply = msg.createReply();
					
					//Check to see if the message performative is a Request type
					if(msg.getPerformative() == ACLMessage.REQUEST) {
						String content = msg.getContent();
						if((content != null) && (content.indexOf("PS:") != -1)) {
							myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Received SOLUTION/PS Request from "+msg.getSender().getLocalName());
							myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Received the following Solution/PS Request "+ msg.getContent());
							
							//Check to see if the Problem Can be Solved - Solution?
							
							reply.setPerformative(ACLMessage.INFORM);
							reply.setContent("pong");
						} else {
							myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Unexpected request ["+content+"] received from "+msg.getSender().getLocalName());
							reply.setPerformative(ACLMessage.REFUSE);
							reply.setContent("( UnexpectedContent ("+content+"))");
						} //END IF-ELSE content evaluation
					} else {
						//Indicate that the Behaviour does not understand the specified message
						myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Unexpected message ["+ACLMessage.getPerformative(msg.getPerformative())+"] received from "+msg.getSender().getLocalName());
						reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
						reply.setContent("( (Unexpected-act "+ACLMessage.getPerformative(msg.getPerformative())+") )");  
					}//END IF-ELSE Performative
					//Send a reply
					send(reply);
				} else {
					block();
				}//END IF-ELSE msg != null
			}
			
		}//END SolveForSolution
		
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
		
		
		//Setup all the Problem Solvers and their Actions/Service with the DF
		//Setup the solve for solution Service
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		ServiceDescription sd1 = new ServiceDescription();
		
		sd.setType("solution");
		sd.setName(getName());
		sd.setOwnership("CDS-Eng");
		
		sd1.setType("pintout");
		sd1.setName(getName());
		sd1.setOwnership("CDS-Eng");
		
		dfd.setName(getAID());
		dfd.addServices(sd);
		dfd.addServices(sd1);
		
		try {
			myLogger.log(Logger.INFO, "Trying to register the behaviour with DFD!");
			DFService.register(this, dfd);
			myLogger.log(Logger.INFO, "Successful registration!");
			SolveForSolution SolutionBehaviour = new SolveForSolution(this);
			TestBehaviour TestingBehaviour = new TestBehaviour(this);
			addBehaviour(SolutionBehaviour);
			addBehaviour(TestingBehaviour);
			myLogger.log(Logger.INFO, "Added behaviour!");
		} catch (FIPAException e) {
			myLogger.log(Logger.SEVERE, "Agent " + getLocalName() + " - Cannot register with DF", e);
			doDelete();
		}
	}
	
}
