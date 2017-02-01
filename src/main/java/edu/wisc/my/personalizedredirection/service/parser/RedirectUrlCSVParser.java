package edu.wisc.my.personalizedredirection.service.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wisc.my.personalizedredirection.dao.AttributeMap;
import edu.wisc.my.personalizedredirection.dao.AttributeMapList;


/**
 * Implementation of IRedirectURLSourceDataParser which will parse a two-column
 * csv file.
 */
public class RedirectUrlCSVParser implements IRedirectURLSourceDataParser {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public AttributeMapList parseResource(Resource resource) throws RuntimeException {
		AttributeMapList mapList = new AttributeMapList();
		try {
			// The resource should be a two-column csv file.
			// We have already verified its existence.
			BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			String line;
			String csvSplitBy = ",";
			while ((line = br.readLine()) != null) {
				String[] data = line.split(csvSplitBy);
				AttributeMap map = new AttributeMap();
				map.setAttribute(data[0].trim());
				map.setUrl(data[1].trim());
				mapList.addAttributeMap(map);
			}
			br.close();

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return mapList;
	}
}
