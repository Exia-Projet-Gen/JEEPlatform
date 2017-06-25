/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dictionary.facade;

import com.dictionary.domain.JAXWord;
import com.dictionary.domain.Word;
import com.dictionary.integration.DictionaryDAO;
import com.dictionary.integration.iDictionaryDAO;
import java.util.ArrayList;
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
}
