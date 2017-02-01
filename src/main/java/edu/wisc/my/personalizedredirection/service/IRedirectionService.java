package edu.wisc.my.personalizedredirection.service;

import javax.servlet.http.HttpServletRequest;

import edu.wisc.my.personalizedredirection.controller.PersonalizedRedirectionController;
import edu.wisc.my.personalizedredirection.dao.UrlDataSource;


public interface IRedirectionService {

    /*
     * Throwing all exceptions as RuntimeExceptions to centralize handling in the controller.
     * Errors will be caught, logged, and the reponse will return as a 404.
     */
    String getUrl(HttpServletRequest request, UrlDataSource dataSource) throws RuntimeException;
}