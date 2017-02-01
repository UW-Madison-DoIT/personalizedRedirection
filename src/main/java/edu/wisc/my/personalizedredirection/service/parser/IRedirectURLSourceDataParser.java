package edu.wisc.my.personalizedredirection.service.parser;
import org.springframework.core.io.Resource;

import edu.wisc.my.personalizedredirection.dao.AttributeMapList;


/**
 * The parser will take a resource containing attribute/url pairs and parse it into an AttributeMapList
 */
public interface IRedirectURLSourceDataParser {
    public AttributeMapList parseResource(Resource resource) throws RuntimeException;
}
