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
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author hyaci
 */
@Stateless
@LocalBean
public class DecodingServiceBean implements DecodingServiceEndpointInterface {

    @Inject
    private JMSContext context;
    
    @Resource(lookup = "jms/decodingQueue")
    private Queue decodingQueue;
    
    @Override
    public void checkMessage(String decodedText, String fileName, String keyVal) {        
        Message m = new Message();
        m.setDecodedText(decodedText);
        m.setFileName(fileName);
        m.setKeyVal(keyVal);
        
        //Ajout de log (nom des fichiers mis dans la queue par exemple)
        this.sendMessage(m);
    }
    
    private void sendMessage(Message message) {
        JAXBContext jaxbContext;
        
        try {
            //obtention d'une instance JAXBContext associée au type Payment annoté avec JAX-B
            jaxbContext = JAXBContext.newInstance(Message.class);
            //création d'un Marshaller pour transfomer l'objet Java en flux XML
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            StringWriter writer = new StringWriter();
            
            //transformation de l'objet en flux XML stocké dans un writer
            jaxbMarshaller.marshal(message, writer);
            String xmlMessage = writer.toString();
            //affichage du XML dans la console de sortie
            System.out.println(xmlMessage);
            //encapsulation du paiement au format XML dans un objet javax.jms.TextMessage
            TextMessage msg = context.createTextMessage(xmlMessage);
            
            //envoi du message dans la queue paymentQueue
            context.createProducer().send(decodingQueue, msg);
        } catch (JAXBException ex) {
            Logger.getLogger(DecodingServiceBean.class.getName()).log(Level.SEVERE, null, ex); 
        } 
    }
}
