package com.phdcc.homehealth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;

import ca.uhn.hl7v2.HL7Exception;

import org.apache.log4j.PropertyConfigurator;
import org.json.XML;


public class JsonParse {

	public static void main (String args[]) {
		try {
			String log4jConfPath = "..\\..\\..\\log4j.properties";
			PropertyConfigurator.configure(log4jConfPath);
			
			// this code for HAPI testing
			String hl7message = "MSH|^~\\&|ULTRA|TML|OLIS|OLIS|200905011130||ORU^R01|20169838-v25|T|2.5\r"
		  			+ "PID|||7005728^^^TML^MR||TEST^RACHEL^DIAMOND||19310313|F|||200 ANYWHERE ST^^TORONTO^ON^M6G 2T9||(416)888-8888||||||1014071185^KR\r"
		  			+ "PV1|1||OLIS||||OLIST^BLAKE^DONALD^THOR^^^^^921379^^^^OLIST\r"
		  			+ "ORC|RE||T09-100442-RET-0^^OLIS_Site_ID^ISO|||||||||OLIST^BLAKE^DONALD^THOR^^^^L^921379\r"
		  			+ "OBR|0||T09-100442-RET-0^^OLIS_Site_ID^ISO|RET^RETICULOCYTE COUNT^HL79901 literal|||200905011106|||||||200905011106||OLIST^BLAKE^DONALD^THOR^^^^L^921379||7870279|7870279|T09-100442|MOHLTC|200905011130||B7|F||1^^^200905011106^^R\r"
		  			+ "OBX|1|ST|||Test Value"; 
			
			Hapi hapi = new Hapi();
			
			// end HAPI testing
			String file = "C:\\mirth\\testadt.xml";
			String hl7XML = "";
			try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    hl7XML = sb.toString();
			}
			file = "C:\\mirth\\testadt.txt";
			String hl7 = "";
			try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    hl7 = sb.toString();
			}

//			System.out.println("Original XML is:");
//			System.out.println(hl7XML);
//			System.out.println("");
			hl7XML = hapi.HapiModifyXML(hl7, hl7XML, "2.5", "ADT^A01");
			System.out.println("Starting XML output");
			System.out.println("");			
			System.out.println(hl7XML);
			System.out.println("Starting JSON output");
			System.out.println("");
			String json = hapi.XMLtoJSON(hl7XML);
			System.out.println(json);
			try(  PrintWriter out = new PrintWriter( "c:\\mirth\\output.txt" )  ){
			    out.println( json );
			}
		} catch (Exception e) {
			System.out.println("ERROR:");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public String XMLtoJSON (String hl7, String xml, String version, String messageType) throws HL7Exception, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException { // this is expected to be called from Mirth
//		Hapi hapi = new Hapi();
//		return hapi.HapiModifyXML(hl7, xml, version, messageType);

		try {
			String log4jConfPath = "..\\..\\..\\log4j.properties";
			PropertyConfigurator.configure(log4jConfPath);
			
			// this code for HAPI testing
			String hl7message = "MSH|^~\\&|ULTRA|TML|OLIS|OLIS|200905011130||ORU^R01|20169838-v25|T|2.5\r"
		  			+ "PID|||7005728^^^TML^MR||TEST^RACHEL^DIAMOND||19310313|F|||200 ANYWHERE ST^^TORONTO^ON^M6G 2T9||(416)888-8888||||||1014071185^KR\r"
		  			+ "PV1|1||OLIS||||OLIST^BLAKE^DONALD^THOR^^^^^921379^^^^OLIST\r"
		  			+ "ORC|RE||T09-100442-RET-0^^OLIS_Site_ID^ISO|||||||||OLIST^BLAKE^DONALD^THOR^^^^L^921379\r"
		  			+ "OBR|0||T09-100442-RET-0^^OLIS_Site_ID^ISO|RET^RETICULOCYTE COUNT^HL79901 literal|||200905011106|||||||200905011106||OLIST^BLAKE^DONALD^THOR^^^^L^921379||7870279|7870279|T09-100442|MOHLTC|200905011130||B7|F||1^^^200905011106^^R\r"
		  			+ "OBX|1|ST|||Test Value"; 
			
			Hapi hapi = new Hapi();
			
			// end HAPI testing
			String file = "C:\\mirth\\testadt.xml";
			String hl7XML = "";
			try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    hl7XML = sb.toString();
			}
			file = "C:\\mirth\\testadt.txt";
			String hl7test = "";
			try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    hl7test = sb.toString();
			}

//			System.out.println("Original XML is:");
//			System.out.println(hl7XML);
//			System.out.println("");
			hl7XML = hapi.HapiModifyXML(hl7test, hl7XML, "2.5", "ADT^A01");
			System.out.println("Starting XML output");
			System.out.println("");			
			System.out.println(hl7XML);
			System.out.println("Starting JSON output");
			System.out.println("");
			String json = hapi.XMLtoJSON(hl7XML);
			System.out.println(json);
			try(  PrintWriter out = new PrintWriter( "c:\\mirth\\output.txt" )  ){
			    out.println( json );
			}
			
			return hl7XML;

		} catch (Exception e) {
			System.out.println("ERROR:");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}		
		return "";
		
		
	}
	
	
}
