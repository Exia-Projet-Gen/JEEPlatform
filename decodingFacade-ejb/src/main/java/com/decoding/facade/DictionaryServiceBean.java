/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decoding.facade;

import com.decoding.domain.Message;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author hyaci
 */
public class DictionaryServiceBean implements DecodingInterfaceRemote {

    @Inject
    private JMSContext context;
    
    @Resource(lookup = "jms/decodingQueue")
    private Queue decodingQueue;
    
    @Override
    public Boolean checkMessage(String decodedText, String fileName, String keyVal) {        
        Message m = new Message();
        m.setDecodedText(decodedText);
        m.setFileName(fileName);
        m.setKeyVal(keyVal);
        
        //TODO Ajout de log (nom des fichiers mis dans la queue par exemple)
        
        //TODO check if the message has the good format
        
        this.sendMessage(m);
        
        return true;
    }
    
    private void sendMessage(Message message) {
        JAXBContext jaxbContext;
        
        try {
            jaxbContext = JAXBContext.newInstance(Message.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            StringWriter writer = new StringWriter();
            
            jaxbMarshaller.marshal(message, writer);
            String xmlMessage = writer.toString();
            System.out.println(xmlMessage);
            TextMessage msg = context.createTextMessage(xmlMessage);
            
            context.createProducer().send(decodingQueue, msg);
        } catch (JAXBException ex) {
            Logger.getLogger(DictionaryServiceBean.class.getName()).log(Level.SEVERE, null, ex); 
        } 
    }
    
    private Boolean checkMessage(Message message) {
        
        //TODO implement a method that check the message using the dictionary
        
        return true;
    }
}
