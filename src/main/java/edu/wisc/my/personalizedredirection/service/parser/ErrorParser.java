package edu.wisc.my.personalizedredirection.service.parser;

import edu.wisc.my.personalizedredirection.dao.AttributeMapList;
import org.springframework.core.io.Resource;


public class ErrorParser implements IRedirectURLSourceDataParser {

    @Override
    public AttributeMapList parseResource(Resource resource) throws RuntimeException {
        AttributeMapList error = new AttributeMapList();
        return error;
    }
}
