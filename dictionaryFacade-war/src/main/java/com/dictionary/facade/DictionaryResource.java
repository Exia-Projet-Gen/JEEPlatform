/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dictionary.facade;

import com.dictionary.domain.Word;
import com.google.gson.Gson;
import java.io.StringReader;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hyaci
 */
@Path("dictionary")
@RequestScoped
public class DictionaryResource {
    
    @EJB(lookup = "java:global/dictionaryFacade-ejb/DictionaryServiceBean")
    private DictionaryServiceRemote dictionaryService;
    
    @Path("{word}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("word") String wordName) {
        List<Word> matchingWords = dictionaryService.searchWord(wordName);
        
        System.out.println(matchingWords);
        
        if (matchingWords != null) {
            GenericEntity<List<Word>> genericList = new GenericEntity<List<Word>>(matchingWords){};
            Response resp = Response.ok(genericList).build();
            return resp;
        }
        Response failedResp = Response.serverError().build();
        return failedResp;
    }  
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Word> words = dictionaryService.getWords();
        GenericEntity<List<Word>> genericList = new GenericEntity<List<Word>>(words){};
  
        System.out.println("empty " + words.isEmpty());
        for(int i = 0; i < words.size(); i++) {
            System.out.println(words.get(i).getName());
        }
        
        Response resp = Response.ok(genericList).build();
        return resp;
    } 
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(String content) {
        System.out.println(content);
        
        StringReader reader = new StringReader(content);
        String wordName;
        try (JsonReader jreader = Json.createReader(reader)) {
            JsonObject wordInfo = jreader.readObject();
            wordName = wordInfo.getString("name");
        }
        
        Boolean isValid = dictionaryService.addWord(wordName);
        
        Response resp = null;
        if (isValid) {
            resp = Response.accepted().build();
        } else {
            resp = Response.status(400).entity("A problem occured while adding the word in database.").build();
        }
        return resp;
    }   
}
