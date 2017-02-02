package edu.wisc.my.personalizedredirection.service.parser;
import org.springframework.core.io.Resource;

import edu.wisc.my.personalizedredirection.dao.AttributeMapWrapper;


/**
 * The parser will take a resource containing attribute/url pairs and parse it into an AttributeMapWrapper
 */
public interface IRedirectURLSourceDataParser {
    public AttributeMapWrapper parseResource(Resource resource) throws RuntimeException;
}
