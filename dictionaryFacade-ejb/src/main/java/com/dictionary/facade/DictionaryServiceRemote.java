/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dictionary.facade;

import javax.ejb.Remote;

/**
 *
 * @author hyaci
 */

@Remote
public interface DictionaryServiceRemote {
    Boolean addWord(String word);
}
