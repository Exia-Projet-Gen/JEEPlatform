/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dictionary.facade;

import com.dictionary.domain.Word;
import com.dictionary.integration.DictionaryDAO;
import com.dictionary.integration.iDictionaryDAO;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;

/**
 *
 * @author hyaci
 */
@Stateless
public class DictionaryServiceBean implements DictionaryServiceRemote {

    @Inject
    private iDictionaryDAO dictionaryDAO;
          
    @Override
    public Boolean addWord(String wordName) {
        Word word = new Word();
        word.setName(wordName);
        
        return dictionaryDAO.create(word);
    }

    @Override
    public Boolean updateWord(Long id, String wordValue) {
        final Word word = new Word();
        word.setId(id);
        word.setName(wordValue);
        return dictionaryDAO.update(word);
    }
    
    @Override
    public Boolean deleteWord(Long id) {   
        return dictionaryDAO.delete(id);
    }
    
    @Override
    public List<Word> searchWord(String wordName) {
        return dictionaryDAO.findByName(wordName);
    }
    
    
    @Override
    public List<Word> getWords() {
        List<Word> words = dictionaryDAO.getAll();
        return words;
    }
}
