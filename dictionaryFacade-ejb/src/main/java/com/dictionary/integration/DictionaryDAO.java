/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dictionary.integration;

import com.dictionary.domain.Word;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jdk.nashorn.internal.objects.NativeArray;

/**
 *
 * @author hyaci
 */
@Stateless
public class DictionaryDAO implements iDictionaryDAO {

    @PersistenceContext(unitName = "dictionaryAppPU")
    private EntityManager em;

    @Override
    public List<Word> findByName(String wordName) {
         try {
            if (wordName != null && !wordName.isEmpty()) {
                System.out.println(wordName);
                TypedQuery <Word> query = em.createQuery("SELECT w FROM Word w WHERE w.mot LIKE :pattern", Word.class);
                query.setParameter("pattern", "'%" + wordName + "%'");
                List<Word> words = query.getResultList() ;
                            
                return words ;
            }
        } catch(Exception e) {
            return null;
        }
        System.out.println("failed");
        return null;
    }

    @Override
    public List<Word> getAll() {       
        try {
            TypedQuery <Word> query = em.createQuery("SELECT w FROM Word w", Word.class);
            List<Word> words = query.getResultList() ;
     
            return words ;
        } catch (Exception e) {
            
            System.out.println("tata ");
            return null;
        }
    }

    @Override
    public Boolean update(Word word) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean create(Word word) {
        try {
            if (word != null) {
                em.persist(word);
                return true;
            }
            return false;       
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean delete(Word word) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
}
