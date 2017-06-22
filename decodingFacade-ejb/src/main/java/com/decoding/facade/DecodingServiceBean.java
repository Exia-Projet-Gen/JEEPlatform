/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decoding.facade;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author hyaci
 */
@Stateless
@LocalBean
public class DecodingServiceBean implements DecodingServiceEndpointInterface {

    @Override
    public Boolean checkMessage(String decodedText, String fileName, String keyVal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
