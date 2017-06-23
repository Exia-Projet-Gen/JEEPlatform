/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decoding.facade;

import javax.ejb.Remote;
import javax.jws.WebService;

/**
 *
 * @author hyaci
 */

@Remote
public interface DecodingInterfaceRemote {
    Boolean checkMessage(String decodedText, String fileName, String keyVal);
}
