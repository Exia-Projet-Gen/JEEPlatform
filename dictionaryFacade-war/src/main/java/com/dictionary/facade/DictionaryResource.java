/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dictionary.facade;

import java.io.StringReader;
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
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String find() {
        String restMsg="{\"message\":\"hello REST\"}";
        return restMsg;
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
            resp = Response.status(400).entity("n° CB invalide").build();
        }
        return resp;
    }   
}
