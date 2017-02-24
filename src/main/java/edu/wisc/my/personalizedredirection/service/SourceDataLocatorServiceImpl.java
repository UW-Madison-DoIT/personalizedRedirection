package edu.wisc.my.personalizedredirection.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wisc.my.personalizedredirection.dao.UrlDataSource;
import edu.wisc.my.personalizedredirection.dao.UrlDataSourceList;


@Service
public class SourceDataLocatorServiceImpl implements ISourceDataLocatorService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	// hardcoded path to map containing the map of application name to the
	// location of that application's redirect data
	public final String DATA_LOCATION = "dataSources.json";
	
	
	protected UrlDataSourceList getUrlDataSourceList(){
		UrlDataSourceList sourceList = null;
		try {
			Resource resource = new ClassPathResource(DATA_LOCATION);
			if (resource.exists()) {
				
				// resource should be a valid json file containing an array of data source objects.
				BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
				StringBuilder stringBuilder = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					stringBuilder.append(line).append(' ');
				}
				br.close();
				String json = stringBuilder.toString();

				if (isValidJSON(json)) {
					ObjectMapper objectMapper = new ObjectMapper();
					sourceList = objectMapper.readValue(json, UrlDataSourceList.class);
				}
			}

		} catch (Exception e) {
		    logger.error("Exception retrieving UrlDataSource:: ", e);
		}
	
		return sourceList;
	}

	@Override
	public UrlDataSource getUrlDataSource(String appName){
		UrlDataSource retVal = new UrlDataSource();
		Logger logger = LoggerFactory.getLogger(getClass());
		UrlDataSourceList dataSourceList = getUrlDataSourceList();
		
		if(dataSourceList != null){
		 retVal = dataSourceList.findByAppName(appName);
		}

		if(retVal==null){
		    logger.error("No metadata found for " + appName);
		    return new UrlDataSource();
        }else{
            logger.trace(retVal.getAppName() + " searching for " + retVal.getAttributeName() + " at " + retVal.getDataSourceLocation() );
        }

		return retVal;
	}
    
	/*
	 * Insurance that the file we've read is actually json.
	 */
	private boolean isValidJSON(final String json) {
		boolean valid = false;
		try {
			final JsonParser parser = new ObjectMapper().getFactory().createParser(json);
			while (parser.nextToken() != null) {
			}
			valid = true;
		} catch (Exception jpe) {
			// eat error
			valid = false;
		}

		return valid;
	}

}
