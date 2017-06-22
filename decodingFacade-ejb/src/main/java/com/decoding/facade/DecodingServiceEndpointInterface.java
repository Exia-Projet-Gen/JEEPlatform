package com.decoding.facade;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author hyaci
 */
@WebService(name="DecodingEndpoint")
public interface DecodingServiceEndpointInterface { 
    @WebMethod(operationName="decodingOperation")
    @WebResult(name = "successedDecoding")     
    Boolean checkMessage(@WebParam(name="decodedText") String decodedText, @WebParam(name="fileName") String fileName, @WebParam(name="keyVal") String keyVal) ;
}

