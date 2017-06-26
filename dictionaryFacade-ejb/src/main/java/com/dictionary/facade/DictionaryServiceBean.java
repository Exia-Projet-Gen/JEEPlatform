/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dictionary.facade;

import com.dictionary.domain.JAXDecodedText;
import com.dictionary.domain.JAXWord;
import com.dictionary.domain.Word;
import com.dictionary.integration.iDictionaryDAO;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
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
public class DictionaryServiceBean implements DictionaryServiceRemote {

    @Inject
    private iDictionaryDAO dictionaryDAO;
    
    @Inject
    private JMSContext context;
    
    @Resource(lookup = "jms/decodingQueue")
    private Queue decodingQueue;
          
    @Override
    public JAXWord addWord(String wordName) {
        Word word = new Word();
        word.setName(wordName);
        
        Word addedWord = dictionaryDAO.create(word);
        JAXWord jaxAddedWord = transformWordToJsonObject(addedWord);
        
        return jaxAddedWord;
    }

    @Override
    public Boolean updateWord(Long id, String wordValue) {
        Word word = new Word();
        word.setId(id);
        word.setName(wordValue);
        return dictionaryDAO.update(word);
    }
    
    @Override
    public Boolean deleteWord(Long id) {   
        return dictionaryDAO.delete(id);
    }
    
    @Override
    public List<JAXWord> searchWord(String wordName) {
        
        List<Word> words = dictionaryDAO.findByName(wordName);
        List<JAXWord> transformedWords = new ArrayList<>();
        words.forEach((word) -> {
            transformedWords.add(transformWordToJsonObject(word));
        });
        
        return transformedWords;
    }
    
    
    @Override
    public List<JAXWord> getWords() {
        List<Word> words = dictionaryDAO.getAll();
        List<JAXWord> transformedWords = new ArrayList<>();
        words.forEach((word) -> {
            transformedWords.add(transformWordToJsonObject(word));
        });
        
        return transformedWords;
    }
    
    public JAXWord transformWordToJsonObject(Word word) {       
        try {
            JAXWord transformedWord = new JAXWord();
            transformedWord.setId(word.getId());
            transformedWord.setName(word.getName());
            return transformedWord;
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Boolean sendDecodedText(String decodedText) {
        try {            
            JAXDecodedText decodedObject = new JAXDecodedText();
            
            decodedObject.setKey("keyvalue");
            decodedObject.setFileName("keyvalue");
            decodedObject.setDecodedText(decodedText);
                    
            sendMessage(decodedObject);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void sendMessage(JAXDecodedText decodedText) {
        JAXBContext jaxbContext;
        
        try {
            //obtention d'une instance JAXBContext associée au type Payment annoté avec JAX-B
            jaxbContext = JAXBContext.newInstance(JAXDecodedText.class);
            //création d'un Marshaller pour transfomer l'objet Java en flux XML
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            StringWriter writer = new StringWriter();
            
            //transformation de l'objet en flux XML stocké dans un writer
            jaxbMarshaller.marshal(decodedText, writer);
            String xmlMessage = writer.toString();
            //affichage du XML dans la console de sortie
            System.out.println(xmlMessage);
            //encapsulation du paiement au format XML dans un objet javax.jms.TextMessage
            TextMessage msg = context.createTextMessage(xmlMessage);
            
            //envoi du message dans la queue paymentQueue
            context.createProducer().send(decodingQueue, msg);
        } catch (JAXBException ex) {
            Logger.getLogger(DictionaryServiceBean.class.getName()).log(Level.SEVERE, null, ex); 
        }                                      
    }
}
