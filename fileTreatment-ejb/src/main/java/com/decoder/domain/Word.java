/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decoder.domain;
/**
 *
 * @author hyaci
 */
public class Word {
    private Long id;
    
    private String name;
    
    public Word(long id, String nameWord) {
        this.id = id;
        this.name = nameWord;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
