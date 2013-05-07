/**
 * 
 */
package ca.uwo.eng.cds.agent.cir.example.actions;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;
import ca.uwo.eng.cds.agent.cir.CIRAgent;
import ca.uwo.eng.cds.agent.cir.capability.problemsolving.actions.Action;

/**
 * @author geekatron
 *
 */
public class GreetingAction extends Action {
	
	/**
	 * @param a
	 */
	public GreetingAction(CIRAgent a) {
		super(a);
	}

	/* (non-Javadoc)
	 * @see ca.uwo.agent.cir.Action#setupBehaviour()
	 */
	@Override
	public void setupBehaviour() {
		this.myLogger.log(Logger.INFO, "Setting up Action: " + getClass().getName());
		this.name = "greeting";
		this.actionDescription = "greeting";
		
		this.myLogger.log(Logger.INFO, "Setting up behaviour!");
		this.behaviour = new SolutionBehaviour(this.agent);
		this.myLogger.log(Logger.INFO, "Setting up behaviour: " + this.behaviour.getClass().getName());
	}
	
	private class SolutionBehaviour extends CyclicBehaviour{
		public SolutionBehaviour(Agent a) {
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
					if(content != null) {
						//Check to see if the Agent can provide a solution to the request
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
		
	}//END of inner class SolutionBehaviour

}
