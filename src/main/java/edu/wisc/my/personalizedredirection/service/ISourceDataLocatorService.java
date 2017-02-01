package edu.wisc.my.personalizedredirection.service;

import edu.wisc.my.personalizedredirection.dao.UrlDataSource;


public interface ISourceDataLocatorService {
        public UrlDataSource getUrlDataSource(String appName) throws RuntimeException;
}
