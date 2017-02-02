package edu.wisc.my.personalizedredirection.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.wisc.my.personalizedredirection.dao.UrlDataSource;
import edu.wisc.my.personalizedredirection.service.IRedirectionService;
import edu.wisc.my.personalizedredirection.service.ISourceDataLocatorService;

@RestController
public class PersonalizedRedirectionController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ISourceDataLocatorService sourceDataLocatorService;
    private IRedirectionService redirectionService;

    @Autowired
    public void setSourceDataLocatorService(
            ISourceDataLocatorService sourceDataLocatorService) {
        this.sourceDataLocatorService = sourceDataLocatorService;
    }
    
    @Autowired
    public void setRedirectionService(IRedirectionService redirectionService) {
        this.redirectionService = redirectionService;
    }

    /**
     * This method will redirect you to a custom URL based on an attribute in
     * your shib header. The expected result is a redirect. 
     * 
     * @param request
     * @param response
     * @param appName
     */
    @RequestMapping(value = "/{appName}", method = RequestMethod.GET)
    public @ResponseBody void getUrl(HttpServletRequest request,
            HttpServletResponse response, @PathVariable String appName) throws IOException {

        String errorURL = "/404.html";

        logger.trace(
                "Calling PersonalizedRedirectionController " + appName);
        try {
            UrlDataSource dataSource = sourceDataLocatorService
                    .getUrlDataSource(appName);

            if (dataSource == null) {
                logger.error("No data source retrieved for " + appName);
                response.sendRedirect(errorURL);
            }

            String url = "";

            url = redirectionService.getUrl(request, dataSource);

            if (url == null || url.length() == 0) {
            	logger.error("No url found for app " + appName);
                response.sendRedirect(errorURL);
            } else {
            	response.sendRedirect(url);
            }

        }catch(IOException ioe){
            logger.error("IO issues happened while trying to generate custom link",
                    ioe);
            response.sendRedirect(errorURL);
        }
        catch (Exception e) {
            logger.error("Issues happened while trying to generate custom link",
                    e);
            response.sendRedirect(errorURL);
        }
    }
    
    /**
     * Status page
     * 
     * @param response
     */
    @RequestMapping("/")
    public @ResponseBody void index(HttpServletResponse response) {
        try {
            JSONObject responseObj = new JSONObject();
            responseObj.put("status", "up");
            response.getWriter().write(responseObj.toString());
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            logger.error("Issues happened while trying to write Status", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
