package edu.wisc.my.personalizedredirection.service;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequestWrapper;

import org.junit.Before;
import org.junit.Test;


import edu.wisc.my.personalizedredirection.dao.AttributeMapWrapper;
import edu.wisc.my.personalizedredirection.dao.UrlDataSource;
import edu.wisc.my.personalizedredirection.dao.UrlDataSourceList;
import edu.wisc.my.personalizedredirection.service.SourceDataLocatorServiceImpl;


public class TestSourceDataLocatorServiceImpl {
	
	SourceDataLocatorServiceImpl serviceUnderTest;
	RedirectionServiceImpl redirectionServiceImpl;
	

	
	@Before
	public void init(){
		serviceUnderTest = new SourceDataLocatorServiceImpl();
		redirectionServiceImpl = new RedirectionServiceImpl();
	}

	@Test
	public void testGetUrlDataSource() {
		//Test whether the data source list is a valid JSON file, and that every entry in that file points to a valid and parseable data source.
		UrlDataSourceList dataSourceList = serviceUnderTest.getUrlDataSourceList();
		for(UrlDataSource ds: dataSourceList){
				AttributeMapWrapper mapList = redirectionServiceImpl.getMapList(ds);
				if(mapList==null){
					fail(ds.getAppName() + " attribute list invalid");
				}
				
			}
      }
    }
