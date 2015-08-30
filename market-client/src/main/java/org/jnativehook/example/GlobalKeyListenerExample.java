package org.jnativehook.example;

import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListenerExample implements NativeKeyListener {
	
	private String rex = "11";
	private static long TIME_MILLS = 999l;
	private Date lastUpdate = new Date();
	private String sBuffer = "";
	
    public void nativeKeyPressed(NativeKeyEvent e) {
//        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
//        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
//            GlobalScreen.unregisterNativeHook();
//        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    	boolean diff = diffSecond(lastUpdate,new Date());
    	if(!diff){
    		sBuffer="";
    	}
		if(StringUtils.isNumeric(NativeKeyEvent.getKeyText(e.getKeyCode()))){
			sBuffer+=NativeKeyEvent.getKeyText(e.getKeyCode());
		}else if(e.getKeyCode() == NativeKeyEvent.VC_ENTER){
			if(sBuffer.length() >= rex.length() && rex.equals(sBuffer.substring(0, rex.length()))){
		        Thread th1 = new Thread() {
		            public synchronized void run() {
		            	Snippet173.getInstance().open("http://localhost:8080/e/Usuario/show/1?code=" + sBuffer);
		            }
		        };
		        th1.start();
			}
		}
    	lastUpdate = new Date();
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
//        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }
    
    public void start(){
    	try {
    		GlobalScreen.registerNativeHook();
    		// Get the logger for "org.jnativehook" and set the level to off.
    		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
    		logger.setLevel(Level.OFF);
    		
    		// Change the level for all handlers attached to the default logger.
    		Handler[] handlers = Logger.getLogger("").getHandlers();
    		for (int i = 0; i < handlers.length; i++) {
    			handlers[i].setLevel(Level.OFF);
    		}
    	}
    	catch (NativeHookException ex) {
    		System.err.println("There was a problem registering the native hook.");
    		System.err.println(ex.getMessage());
    		
    		System.exit(1);
    	}
    	
    	GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());    	
    }

    public boolean diffSecond(Date from, Date to){
        // conseguir la representacion de la fecha en milisegundos
        long milis1 = from.getTime();
        long milis2 = to.getTime();
        // calcular la diferencia en milisengundos
        long diff = milis2 - milis1;
        // calcular la diferencia en dias
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if(diffDays != 0) return false;
        // calcular la diferencia en horas
        long diffHours = diff / (60 * 60 * 1000);
        if(diffHours != 0) return false;
        // calcular la diferencia en minutos
        long diffMinutes = diff / (60 * 1000);
        if(diffMinutes != 0) return false;
        // calcular la diferencia en segundos
        long diffSeconds = diff / 1000;
        if(diffSeconds != 0) return false;
        return diff <= TIME_MILLS;
    }
    
    public static void main(String[] args) {
    	GlobalKeyListenerExample keyListener = new GlobalKeyListenerExample();
    	keyListener.start();
//    	Date from = new Date();
//    	try {
//			Thread.sleep(400);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//    	System.out.println(keyListener.diffSecond(from,new Date()));
    	
    }
}