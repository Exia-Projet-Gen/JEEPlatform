/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decoder.facade;

import com.decoder.domain.Message;
import com.decoder.domain.Word;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author hyaci
 */
@Stateless
public class treatmentServiceBean {
private final String EMAIL_PATTEN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    private final ArrayList<String> dictionary = getWordsFromDictionary();
    private final double MATCH_PERCENT_NEEDED = 0.7;
    private String mail = null;

    
    public Message checkMessage(Message message) {
        
        // Find the number of words in the decoded text
        String decodedText = message.getDecodedText();
        ArrayList<String> decodedWordList = new ArrayList<String>(Arrays.asList(decodedText.split(" ")));
        Collections.sort(decodedWordList);
        int decodedWordsLength = decodedWordList.size();

        // Find the number of french words in the decoded text
        int frenchWordsLength = countFrenchWords(decodedWordList);
        
        // get the percentage of french words in the text
        double matchPercent = frenchWordsLength / decodedWordsLength;

        
        // if matching percentage is enough high, search the mail address
        if ( matchPercent > MATCH_PERCENT_NEEDED) {
            mail = findMailAddress(decodedText);
        }
            
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
    
    private int countFrenchWords(List<String> decodedWordsList) {
        
        int wordsCount = 0;
        
        for (String word : decodedWordsList) {
            if (dictionary.contains(word)) {
                wordsCount++;
            }
        }
              
        return wordsCount;
    }
    
    private ArrayList<String> getWordsFromDictionary() {
        ArrayList<String> words = new ArrayList<>();
     
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://192.168.1.10:11080/dictionaryFacade-war/gen/dictionary/");
        Response resp = target.request().accept(MediaType.APPLICATION_JSON_TYPE).get();
        String jsonContent = resp.readEntity(String.class);
        
        JsonReader jsonReader = Json.createReader(new StringReader(jsonContent));
        JsonArray jsonArray = jsonReader.readArray();
       
        for(int i = 0; i < jsonArray.size(); i++) {
            
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            String value = jsonObject.getString("name").toLowerCase();
            words.add(value);
        }
                            
        Collections.sort(words);
        
        return words;
    }
}
