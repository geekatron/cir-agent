package ca.uwo.eng.cds.agent.cir.knowledge.domain.localhistory;

import com.google.gson.Gson;

public class CommunicationHistory {
	private String type = "communication";
	private String from;
	private String performative;
	private String content;
	
	public CommunicationHistory(String from, String performative, String content) {
		this.from = from;
		this.performative = performative;
		this.content = content;
	}

}
