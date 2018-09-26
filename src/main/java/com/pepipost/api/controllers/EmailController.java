/*
 * PepipostLib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package com.pepipost.api.controllers;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import com.pepipost.api.*;
import com.pepipost.api.models.*;
import com.pepipost.api.exceptions.*;
import com.pepipost.api.http.client.HttpClient;
import com.pepipost.api.http.client.HttpContext;
import com.pepipost.api.http.request.HttpRequest;
import com.pepipost.api.http.response.HttpResponse;
import com.pepipost.api.http.response.HttpStringResponse;
import com.pepipost.api.http.client.APICallBack;
import com.pepipost.api.controllers.syncwrapper.APICallBackCatcher;

public class EmailController extends BaseController {    
    //private static variables for the singleton pattern
    private static Object syncObject = new Object();
    private static EmailController instance = null;

    /**
     * Singleton pattern implementation 
     * @return The singleton instance of the EmailController class 
     */
    public static EmailController getInstance() {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new EmailController();
            }
        }
        return instance;
    }

    /**
     * This Endpoint sends emails with the credentials passed.
     * @param    apiKey    Optional parameter: Generated header parameter. Example value ='5ce7096ed4bf2b39dfa932ff5fa84ed9ed8'
     * @param    body    Optional parameter: The body passed will be json format.
     * @return    Returns the SendEmailResponse response from the API call 
     */
    public SendEmailResponse createSendEmail(
                final String apiKey,
                final EmailBody body
    ) throws Throwable {
        APICallBackCatcher<SendEmailResponse> callback = new APICallBackCatcher<SendEmailResponse>();
        createSendEmailAsync(apiKey, body, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        return callback.getResult();
    }

    /**
     * This Endpoint sends emails with the credentials passed.
     * @param    apiKey    Optional parameter: Generated header parameter. Example value ='5ce7096ed4bf2b39dfa932ff5fa84ed9ed8'
     * @param    body    Optional parameter: The body passed will be json format.
     * @return    Returns the void response from the API call 
     */
    public void createSendEmailAsync(
                final String apiKey,
                final EmailBody body,
                final APICallBack<SendEmailResponse> callBack
    ) {
        Runnable _responseTask = new Runnable() {
            public void run() {
                //the base uri for api requests
                String _baseUri = Configuration.baseUri;

                //prepare query string for API call
                StringBuilder _queryBuilder = new StringBuilder(_baseUri);
                _queryBuilder.append("/v2/sendEmail");
                //validate and preprocess url
                String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

                //load all headers for the outgoing API request
                Map<String, String> _headers = new HashMap<String, String>() {
                    private static final long serialVersionUID = -4724524578217928859L;
                    {
                        put( "api_key", apiKey );
                        put( "user-agent", "APIMATIC 2.0" );
                        put( "accept", "application/json" );
                        put( "content-type", "application/json" );
                    }
                };

                //prepare and invoke the API call request to fetch the response
                HttpRequest _request;
                try {
                    _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(body));
                } catch (JsonProcessingException jsonProcessingException) {
                    //let the caller know of the error
                    callBack.onFailure(null, jsonProcessingException);
                    return;
                }
                //invoke the callback before request if its not null
                if (getHttpCallBack() != null)
                {
                    getHttpCallBack().OnBeforeRequest(_request);
                }

                //invoke request and get response
                getClientInstance().executeAsStringAsync(_request, new APICallBack<HttpResponse>() {
                    public void onSuccess(HttpContext _context, HttpResponse _response) {
                        try {

                            //invoke the callback after response if its not null
                            if (getHttpCallBack() != null)	
                            {
                                getHttpCallBack().OnAfterResponse(_context);
                            }

                            //Error handling using HTTP status codes
                            int _responseCode = _response.getStatusCode();
                            if (_responseCode == 405)
                                throw new APIException("Method not allowed", _context);

                            //handle errors defined at the API level
                            validateResponse(_response, _context);

                            //extract result from the http response
                            String _responseBody = ((HttpStringResponse)_response).getBody();
                            SendEmailResponse _result = APIHelper.deserialize(_responseBody,
                                                        new TypeReference<SendEmailResponse>(){});

                            //let the caller know of the success
                            callBack.onSuccess(_context, _result);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
                        } catch (IOException ioException) {
                            //let the caller know of the caught IO Exception
                            callBack.onFailure(_context, ioException);
                        } catch (Exception exception) {
                            //let the caller know of the caught Exception
                            callBack.onFailure(_context, exception);
                        }
                    }
                    public void onFailure(HttpContext _context, Throwable _error) {
                        //invoke the callback after response if its not null
                        if (getHttpCallBack() != null)
                        {
                            getHttpCallBack().OnAfterResponse(_context);
                        }

                        //let the caller know of the failure
                        callBack.onFailure(_context, _error);
                    }
                });
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

}