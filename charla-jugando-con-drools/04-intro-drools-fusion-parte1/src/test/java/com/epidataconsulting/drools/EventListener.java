package com.epidataconsulting.drools;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.drools.definition.rule.Rule;
import org.drools.event.rule.BeforeActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;
 
public class EventListener extends DefaultAgendaEventListener {
 
	private static Logger logger = Logger.getLogger(EventListener.class);
	
	private List<String> executedRulesNames;
	
	public EventListener(){
		executedRulesNames = new ArrayList<String>();
	}
	
    @Override
    public void beforeActivationFired(final BeforeActivationFiredEvent event) {
        Rule rule = event.getActivation().getRule();
        executedRulesNames.add(rule.getName());
        logger.info("###### REGLA CORRECTAMENTE EJECUTADA: " + rule.getName() + " ["+ rule.getPackageName() +"]");
    }
    
	public boolean containsAll(List<String> expectedRulesNames) {
		return executedRulesNames.containsAll(expectedRulesNames);
	}
	
	public void clear(){
		executedRulesNames.clear();
	}
	
	public boolean isEmpty(){
		return executedRulesNames.isEmpty();
	}
}