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
import javax.persistence.TypedQuery;

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
                List<Word> words = em.createQuery("SELECT w FROM Word w WHERE w.name LIKE :pattern")
                    .setParameter("pattern", "%" + wordName + "%")
                    .getResultList();
  
                return words ;
            }
        } catch(Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public List<Word> getAll() {       
        try {
            TypedQuery <Word> query = em.createQuery("SELECT w FROM Word w", Word.class);
            List<Word> words = query.getResultList() ;
     
            return words ;
        } catch (Exception e) {
            
            return null;
        }
    }

    @Override
    public Boolean update(Word word) {
        try {
            if (word != null) {
                em.merge(word);
                return true;
            }
        } catch (Exception e) {
            return false;
        }    
        return false;
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
    public Boolean delete(Long id) {
        try {
            if (id != null) {
                Word word = em.find(Word.class, id);
                if (word != null) {
                    em.remove(word);
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }   
}
