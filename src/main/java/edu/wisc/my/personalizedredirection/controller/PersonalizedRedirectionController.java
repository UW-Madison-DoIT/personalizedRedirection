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
import org.springframework.web.servlet.ModelAndView;

import edu.wisc.my.personalizedredirection.dao.UrlDataSource;
import edu.wisc.my.personalizedredirection.exception.PersonalizedRedirectionException;
import edu.wisc.my.personalizedredirection.service.IRedirectionService;
import edu.wisc.my.personalizedredirection.service.ISourceDataLocatorService;
import edu.wisc.my.personalizedredirection.service.RedirectionServiceImpl;


@RestController
@RequestMapping("/personalizedRedirect")
public class PersonalizedRedirectionController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ISourceDataLocatorService sourceDataLocatorService;

	@Autowired
	private IRedirectionService redirectionService;

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

	/**
	 * This method will redirect you to a custom URL based on an attribute in
	 * your shib header.
	 * 
	 * @param request
	 * @param response
	 * @param appName
	 */
	@RequestMapping(value = "/{appName}", method = RequestMethod.GET)
	public @ResponseBody void getUrl(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String appName) {
		logger.trace("Into personalized-redirection controller with " + appName);
		try {
			UrlDataSource dataSource = sourceDataLocatorService.getUrlDataSource(appName);

			if (dataSource == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

			String url = "";

			try {
				url = redirectionService.getUrl(request, dataSource);
			} catch (PersonalizedRedirectionException e) {
				logger.error("Personalized Redirection Error " + e.getMessage());
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

			if(url == null || url.length()!=0) {
				response.sendRedirect(url);
			}else{
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			logger.error("Issues happened while trying to generate custom link", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	public ISourceDataLocatorService getSourceDataLocatorService() {
		return sourceDataLocatorService;
	}

	public void setSourceDataLocatorService(ISourceDataLocatorService sourceDataLocatorService) {
		this.sourceDataLocatorService = sourceDataLocatorService;
	}

	public IRedirectionService getRedirectionService() {
		return redirectionService;
	}

	public void setRedirectionService(IRedirectionService redirectionService) {
		this.redirectionService = redirectionService;
	}

}
