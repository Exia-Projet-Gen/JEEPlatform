/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decoder.domain;

import java.io.Serializable;

/**
 *
 * @author hyaci
 */
public class Message implements Serializable {
    private String decodedText;
    private String fileName;
    private String keyVal;

    public String getDecodedText() {
        return decodedText;
    }

    public void setDecodedText(String decodedText) {
        this.decodedText = decodedText;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getKeyVal() {
        return keyVal;
    }

    public void setKeyVal(String keyVal) {
        this.keyVal = keyVal;
    }    
}
