/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decoding.facade;

import com.decoding.domain.Message;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;


/**
 *
 * @author hyaci
 */
public class DecodingServiceBean implements iDecoding{ 
    
    private final String EMAIL_PATTEN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    @EJB(lookup = "java:global/dictionaryFacade-ejb/DictionaryServiceBean")
    private DictionaryServiceRemote dictionaryService;
    
    private final HashMap dictionary = getWordsFromDictionary();

    
    public Message checkMessage(Message message) {
        
        //TODO 
        // get words length of the decoded text
        // find the number of french words in the decoded text
        // get the percentage of french words in the text
        // if % > 80% search mail
        
        
        
        
        return null;
    }
    
    private String findMailAddress(String decodedText) {
        
        
        // Regex to find an email address
        Pattern p = Pattern.compile(EMAIL_PATTEN);
        // Search if there is a match with the regex
        Matcher m = p.matcher(decodedText);

        if (m.find())
        {
          String email = m.group();

          return email;
        } else {
            return null;
        }
    }
    
    private Number countFrenchWords(String decodedText) {
        
        
        return 0;
    }
    
    private HashMap getWordsFromDictionary() {
        HashMap words = new HashMap();
        
        
        
        return words;
    }
}
