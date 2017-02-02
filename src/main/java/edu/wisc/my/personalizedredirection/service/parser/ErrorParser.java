package edu.wisc.my.personalizedredirection.service.parser;

import edu.wisc.my.personalizedredirection.dao.AttributeMapWrapper;
import org.springframework.core.io.Resource;

/*
 * Returns a dummy AttributeMapWrapper to prevent unsightly null pointers down the line.
 */
public class ErrorParser implements IRedirectURLSourceDataParser {

    @Override
    public AttributeMapWrapper parseResource(Resource resource) throws RuntimeException {
        AttributeMapWrapper error = new AttributeMapWrapper();
        return error;
    }
}
