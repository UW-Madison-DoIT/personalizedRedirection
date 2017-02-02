package edu.wisc.my.personalizedredirection.service;

import javax.servlet.http.HttpServletRequest;

import edu.wisc.my.personalizedredirection.service.parser.ErrorParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wisc.my.personalizedredirection.dao.AttributeMapList;
import edu.wisc.my.personalizedredirection.dao.UrlDataSource;
import edu.wisc.my.personalizedredirection.service.parser.IRedirectURLSourceDataParser;
import edu.wisc.my.personalizedredirection.service.parser.RedirectUrlCSVParser;


@Service
public class RedirectionServiceImpl implements IRedirectionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public String getUrl(HttpServletRequest request, UrlDataSource dataSource) throws RuntimeException {

		// Find search key in the header
		String attributeToSearchFor = dataSource.getAttributeName();
		String toFind = request.getHeader(attributeToSearchFor);
		String retVal = null;

		if (toFind == null || toFind.length() == 0) {
			logger.error("Attribute " + attributeToSearchFor + " not found in header.");
			return null;
		}else{
			logger.trace("Header attribute = " + toFind);
		}

		// Grab the resource which contains your key/url pairs.
		String dataSourceLocation = dataSource.getDataSourceLocation();
		Resource resource = new ClassPathResource(dataSourceLocation);
		AttributeMapList mapList;

		if (resource.exists()) {
			IRedirectURLSourceDataParser parser = getParser(dataSource);
			mapList = parser.parseResource(resource);
			retVal = mapList.find(toFind);
		} else {
		    logger.error("Invalid data location - " + dataSourceLocation + " not found.");
		}


		return retVal;
	}

	
	
	/*
	 * This service is currently set up to handle a two-column CSV file. 
	 * To implement other file types, write new implementations of edu.wisc.my.personalizedredirection.service.parser.IRedirectURLSourceDataParser
	 */
	private IRedirectURLSourceDataParser getParser(UrlDataSource dataSource) throws RuntimeException {
		IRedirectURLSourceDataParser parser = null;

		// Add new implementations of IRedirectUrlSourceDataParser as demanded.
		
		// Currently, there is one implementation which handles a two-column
		// CSV.
		if (dataSource.getDataSourceType().equalsIgnoreCase("CSV")) {
			parser = new RedirectUrlCSVParser();
		}

		if (parser == null) {
			parser = new ErrorParser();
		    logger.error("Unsuccessfully attempted to instantiate a DataSource with type " + dataSource.getDataSourceType());
		}
		
		return parser;
	}
}
