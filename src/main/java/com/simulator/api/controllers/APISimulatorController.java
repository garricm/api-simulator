package com.simulator.api.controllers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulator")
@ResponseBody
public class APISimulatorController {

	@Value("${api.simulator.response.fileName}")
	private String responseFileName;

	@RequestMapping(value = "/api", method = RequestMethod.GET)
	public String getResponse() throws IOException {
		try {
			return doProcessing();
		} catch (Exception e) {
			e.printStackTrace();
			return "An Exception occured while processing request. Error Message - " + e.getMessage();
		}
	}

	@RequestMapping(value = "/api", method = RequestMethod.POST)
	public String getResponsePost() {
		try {
			return doProcessing();
		} catch (Exception e) {
			e.printStackTrace();
			return "An Exception occured while processing request. Error Message - " + e.getMessage();
		}
	}

	@RequestMapping(value = "/api/json", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public String getResponseJSON() throws IOException {
		try {
			return doProcessing();
		} catch (Exception e) {
			e.printStackTrace();
			return "An Exception occured while processing request. Error Message - " + e.getMessage();
		}
	}

	@RequestMapping(value = "/api/json", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String getResponsePostJSON() {
		try {
			return doProcessing();
		} catch (Exception e) {
			e.printStackTrace();
			return "An Exception occured while processing request. Error Message - " + e.getMessage();
		}
	}

	@RequestMapping(value = "/api/xml", method = RequestMethod.GET, consumes = "application/xml", produces = "application/xml")
	public String getResponseXML() throws IOException {
		try {
			return doProcessing();
		} catch (Exception e) {
			e.printStackTrace();
			return "An Exception occured while processing request. Error Message - " + e.getMessage();
		}
	}

	@RequestMapping(value = "/api/xml", method = RequestMethod.POST, consumes = "application/xml", produces = "application/xml")
	public String getResponsePostXML() {
		try {
			return doProcessing();
		} catch (Exception e) {
			e.printStackTrace();
			return "An Exception occured while processing request. Error Message - " + e.getMessage();
		}
	}

	private String doProcessing() throws IOException {
		File f = new File(responseFileName);
		FileUtils.forceMkdirParent(f);

		if (!f.exists()) {
			f.createNewFile();
			FileUtils.writeStringToFile(f, "Add your Response contents to File - " + responseFileName, "UTF-8");
		}

		return FileUtils.readFileToString(f, "UTF-8");
	}

}