package com.phdcc.homehealth;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;

public class JsonParse {

	public static void main (String args[]) {
		try {
			String file = "C:\\mirth\\adttest.xml";
			JsonParse parse = new JsonParse();
			String json = parse.XMLtoJSON(file);
			try(  PrintWriter out = new PrintWriter( "C:\\mirth\\adttest.json" )  ){
			    out.println( json );
			}
		} catch (Exception e) {
			
		}
	}
	public String XMLtoJSON (String xml) throws JAXBException {
		
		
		//Statement statement = new Statement();
		JAXBContext jaxbContext = JAXBContext.newInstance(HL7Message.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(xml);
		HL7Message hl7 = (HL7Message) jaxbUnmarshaller.unmarshal(reader);
		
		Gson gson = new Gson();
		String json = gson.toJson(hl7);
		
		// generation json from the object
			

		return json;
		
	}
	public String XMLtoJSONTest (String xmlFile) throws JAXBException {
		
		
		//Statement statement = new Statement();
		JAXBContext jaxbContext = JAXBContext.newInstance(HL7Message.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		HL7Message hl7 = (HL7Message) jaxbUnmarshaller.unmarshal(new File(xmlFile));
		
		Gson gson = new Gson();
		String json = gson.toJson(hl7);
		
		// generation json from the object
			

		return json;
		
	}
}
