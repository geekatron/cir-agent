/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.capability.communication;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;
import ca.uwo.eng.cds.agent.cir.CIRAgent;

/**
 * This Class is meant to handle the communication between agents before interaction.
 * 	i.e. An agent would like to make a request to solve a goal. This class should handle the communication request.
 * @author geekatron
 *
 */
public class Communication {
	//Setup the logger for the Communication Class
	protected Logger myLogger = Logger.getMyLogger(getClass().getName());
	
	//Reference to the CIR Agent the Communication class is associated with
	private CIRAgent myAgent;
	//Behaviour to handle the communication
	protected Behaviour myBehaviour;

	/**
	 * 
	 */
	public Communication(CIRAgent a) {
		this.myLogger.log(Logger.INFO, "Setting up Communications: " + getClass().getName());
		
		if( a != null) {
			this.myAgent = a;
			initCommunication();
		} else {
			this.myLogger.log(Logger.WARNING, "No CIR Agent provided! (" + getClass().getName() + ")");
		}
	}
	
	private void initCommunication() {
		//Create the Communication Behaviour
		this.myBehaviour = new CommunicationBehaviour(this.myAgent);
		
		try {
			myLogger.log(Logger.INFO, "Adding Communication behaviour!");
			//Add the Communication Behaviour to the CIR Agent
			this.myAgent.addBehaviour(this.myBehaviour);
		} catch (Exception e) {
			//Log the Exception
			myLogger.log(Logger.SEVERE, "Agent " + this.myAgent.getLocalName() + " - Cannot register with DF", e);
			//Couldn't setup the communication; Destroy the agent
			this.myAgent.doDelete();
		}
	}
	
	private class CommunicationBehaviour extends CyclicBehaviour {
		
		public CommunicationBehaviour(CIRAgent a) {
			super(a);
		}

		@Override
		public void action() {
			ACLMessage msg = myAgent.receive();
			if(msg != null) {
				ACLMessage reply = msg.createReply();
				//We received a request
				if(msg.getPerformative() == ACLMessage.REQUEST) {
					//Check for the content
					String content = msg.getContent();

					//Check to see if the content has specified one or more goals: G={}
					if(content != null && ((content.indexOf("G={") != -1) || (content.indexOf("g={") != -1)) ) {
						//Check to see if the Agent can provide a solution to the request - Call the Problem Solver
						//Update Local History
						
						//Update Domain Knowledge about request (FIFO)
						//Update the Goal
						//Inform the Requester that the CIR Agent is trying to fulfill the request
						reply.setPerformative(ACLMessage.INFORM);
						reply.setContent("Working on fulfilling your request!");
					} else {
						//No content - respond that we cannot deal with blank request (REFUSE)
						myLogger.log(Logger.INFO, "Agent "+ myAgent.getLocalName()+" - Unexpected request ["+content+"] received from "+msg.getSender().getLocalName());
						reply.setPerformative(ACLMessage.REFUSE);
						reply.setContent("( UnexpectedContent ("+content+"))");
					}
				} else {
					//Cannot understand the Performative
					myLogger.log(Logger.INFO, "Agent "+ myAgent.getLocalName() +" - Unexpected message ["+ACLMessage.getPerformative(msg.getPerformative())+"] received from "+msg.getSender().getLocalName());
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
					reply.setContent("( (Unexpected-act "+ACLMessage.getPerformative(msg.getPerformative())+") )");
				}
				//Send the reply
				myAgent.send(reply);
			} else {
				//Null message - block until message received
				block();
			}
			
		}
		
	}

}
