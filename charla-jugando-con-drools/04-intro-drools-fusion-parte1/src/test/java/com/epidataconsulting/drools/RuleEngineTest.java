package com.epidataconsulting.drools;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.drools.time.SessionPseudoClock;
import org.junit.Test;

import com.epidataconsulting.drools.model.EventA;
import com.epidataconsulting.drools.model.EventB;

public class RuleEngineTest extends RuleEngine {

	@Test
	public void ruleAfter(){
		SessionPseudoClock clock = getClock();
		// advance seconds
		clock.advanceTime(10, TimeUnit.SECONDS);
		// Only set one event A created
		insert(createEventA(clock));
		// advance seconds
		clock.advanceTime(10, TimeUnit.SECONDS);
		//event b
		insert(createEventB(clock));
		//verificamos que la regla que se ejecute sea la esperada
		addExpectedRules("Rule After");
	}
	
	@Test
	public void ruleBefore(){
		SessionPseudoClock clock = getClock();
		// advance seconds
		clock.advanceTime(10, TimeUnit.SECONDS);
		// Only set one event A created
		insert(createEventA(clock));
		// advance seconds
		clock.advanceTime(10, TimeUnit.SECONDS);
		//event b
		insert(createEventB(clock));
		//verificamos que la regla que se ejecute sea la esperada
		addExpectedRules("Rule Before");
	}

	@Test
	public void ruleCoincides(){
		SessionPseudoClock clock = getClock();
		// advance seconds
		clock.advanceTime(10, TimeUnit.SECONDS);
		// Only set one event A created
		insert(createEventA(clock));
		// advance seconds
		clock.advanceTime(0, TimeUnit.SECONDS);
		//event b
		insert(createEventB(clock));
		//verificamos que la regla que se ejecute sea la esperada
		addExpectedRules("Rule Coincides");
	}
	
	//-----------------------------------------------------
	
	private EventA createEventA(SessionPseudoClock clock) {
		EventA event = new EventA(); 
		try {
			event.setId("123");
			event.setTimestamp(new Date(clock.getCurrentTime()));
			event.setDurationTime(10L);
		} catch (Exception e) {
			System.out.println("Error in fire rules: " + e);
			e.printStackTrace();
		}
		return event;
	}
	
	private EventB createEventB(SessionPseudoClock clock) {
		EventB event = new EventB(); 
		try {
			event.setId("123");
			event.setTimestamp(new Date(clock.getCurrentTime()));
			event.setDurationTime(10L);
		} catch (Exception e) {
			System.out.println("Error in fire rules: " + e);
			e.printStackTrace();
		}
		return event;
	}
}