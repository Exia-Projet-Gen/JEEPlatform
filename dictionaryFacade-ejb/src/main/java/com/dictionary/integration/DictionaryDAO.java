/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dictionary.integration;

import com.dictionary.domain.Word;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author hyaci
 */
@Stateless
public class DictionaryDAO {

    @PersistenceContext(unitName = "dictionaryAppPU")
    private EntityManager em;
    
    public void insert(Word word){
        em.persist(word);
    }
}
