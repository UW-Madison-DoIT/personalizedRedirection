package edu.wisc.my.personalizedredirection.service;

import javax.servlet.http.HttpServletRequest;

import edu.wisc.my.personalizedredirection.controller.PersonalizedRedirectionController;
import edu.wisc.my.personalizedredirection.dao.UrlDataSource;


public interface IRedirectionService {

    String getUrl(HttpServletRequest request, UrlDataSource dataSource) throws RuntimeException;
}