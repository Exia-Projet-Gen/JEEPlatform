/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decoding.logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author hyaci
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/decodingQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class DecodingProcessor implements MessageListener {
    
    public DecodingProcessor() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            String decodingMessage = message.getBody(String.class);
            
            System.out.println("le message est " + decodingMessage);
        } catch (JMSException ex) {
            Logger.getLogger(DecodingProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
