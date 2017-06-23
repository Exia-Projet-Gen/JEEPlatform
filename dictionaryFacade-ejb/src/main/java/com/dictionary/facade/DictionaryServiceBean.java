/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dictionary.facade;

import com.dictionary.domain.Word;
import com.dictionary.integration.DictionaryDAO;
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
    private DictionaryDAO dictionaryDAO;
    
    @Override
    public void addWord(String wordName) {
        Word word = new Word();
        word.setName(wordName);
        dictionaryDAO.insert(word);
    }
}
