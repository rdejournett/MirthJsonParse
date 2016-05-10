package com.phdcc.homehealth;

import java.io.IOException;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.llp.MinLowerLayerProtocol;
import ca.uhn.hl7v2.model.GenericMessage;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.parser.GenericModelClassFactory;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.util.Terser;
import ca.uhn.hl7v2.validation.impl.DefaultValidation;
import ca.uhn.hl7v2.model.v25.message.ADT_A01;
import ca.uhn.hl7v2.model.v25.message.ADT_AXX;
import ca.uhn.hl7v2.model.v25.message.ORU_R01;

public class Hapi {
	   /**
	    * The contents of this file are subject to the Mozilla Public License Version 1.1
	    * (the "License"); you may not use this file except in compliance with the License.
	    * You may obtain a copy of the License at http://www.mozilla.org/MPL/
	    * Software distributed under the License is distributed on an "AS IS" basis,
	    * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the
	    * specific language governing rights and limitations under the License.
	    *
	    * The Original Code is "ExampleUseTerser.java".  Description:
	   * "Example Code"
	   *
	   * The Initial Developer of the Original Code is University Health Network. Copyright (C)
	   * 2001.  All Rights Reserved.
	   *
	   * Contributor(s): James Agnew
	   *
	   * Alternatively, the contents of this file may be used under the terms of the
	   * GNU General Public License (the  �GPL�), in which case the provisions of the GPL are
	   * applicable instead of those above.  If you wish to allow use of your version of this
	   * file only under the terms of the GPL and not to allow others to use your version
	   * of this file under the MPL, indicate your decision by deleting  the provisions above
	   * and replace  them with the notice and other provisions required by the GPL License.
	   * If you do not delete the provisions above, a recipient may use your version of
	   * this file under either the MPL or the GPL.
	   *
	   */
	  

	  /**
	   * Example code illustrating how to handle multiple versions of HL7 from one codebase
	   * 
	   * @author <a href="mailto:jamesagnew@sourceforge.net">James Agnew</a>
	   * @version $Revision: 1.1 $ updated on $Date: 2011-05-22 16:52:21 $ by $Author: jamesagnew $
	   */
	  public class HandlingMultipleVersions {
	  
	  	//public static void main(String[] args) throws Exception {
		  public void HapiParse(String hl7message) throws Exception {
	  
	  		/*
	  		 * Often, you will need to handle multiple versions of HL7 from a sending system
	  		 * from within the same code base. Because HAPI uses different model classes for
	  		 * each version, this can seem difficult.
	  		 * 
	  		 * Before the first example, a bit of background information that is useful.
	  		 * HL7 v2 is a backwards compatible standard, for the most part. New versions
	  		 * of the standard will deprocate old fields and segments and groups, but they never
	  		 * remove them entirely. They will also rename fields and groups, but this has
	  		 * no effect on encoded messages if they are encoded using ER7 (pipe and hat)
	  		 * encoding, only on the message structure objects themselves.
	  		 * 
	  		 * Unfortunately, because of this renaming, it is not possible for the
	  		 * HAPI library to create a single version of a structure JAR which covers
	  		 * all versions of HL7 v2 (v2.1, v2.2, v2.3, etc). That said, it is always
	  		 * possible to use a HAPI message structure object to parse or encode a
	  		 * message of the same type from an earlier version of the standard. In
	  		 * other words, if you have a v2.2 ADT^A01 message, you can use the v2.3
	  		 * ADT_A01 structure class to parse it, and you can also use the v2.3 ADT_A01
	  		 * structure class to create a new v2.2 message if you are not planning on
	  		 * XML encoding it.  
	  		 * 
	  		 * The following example shows two ways of dealing with this situation. First,
	  		 * for this example, consider the following messages. Each is identical, aside
	  		 * from the version string: "2.5" and "2.3".
	  		 */
	  		
	          String v25message = "MSH|^~\\&|ULTRA|TML|OLIS|OLIS|200905011130||ORU^R01|20169838-v25|T|2.5\r"
	  			+ "PID|||7005728^^^TML^MR||TEST^RACHEL^DIAMOND||19310313|F|||200 ANYWHERE ST^^TORONTO^ON^M6G 2T9||(416)888-8888||||||1014071185^KR\r"
	  			+ "PV1|1||OLIS||||OLIST^BLAKE^DONALD^THOR^^^^^921379^^^^OLIST\r"
	  			+ "ORC|RE||T09-100442-RET-0^^OLIS_Site_ID^ISO|||||||||OLIST^BLAKE^DONALD^THOR^^^^L^921379\r"
	  			+ "OBR|0||T09-100442-RET-0^^OLIS_Site_ID^ISO|RET^RETICULOCYTE COUNT^HL79901 literal|||200905011106|||||||200905011106||OLIST^BLAKE^DONALD^THOR^^^^L^921379||7870279|7870279|T09-100442|MOHLTC|200905011130||B7|F||1^^^200905011106^^R\r"
	  			+ "OBX|1|ST|||Test Value";
	  		
	          String v23message = "MSH|^~\\&|ULTRA|TML|OLIS|OLIS|200905011130||ORU^R01|20169838-v23|T|2.3\r"
	  			+ "PID|||7005728^^^TML^MR||TEST^RACHEL^DIAMOND||19310313|F|||200 ANYWHERE ST^^TORONTO^ON^M6G 2T9||(416)888-8888||||||1014071185^KR\r"
	  			+ "PV1|1||OLIS||||OLIST^BLAKE^DONALD^THOR^^^^^921379^^^^OLIST\r"
	  			+ "ORC|RE||T09-100442-RET-0^^OLIS_Site_ID^ISO|||||||||OLIST^BLAKE^DONALD^THOR^^^^L^921379\r"
	  			+ "OBR|0||T09-100442-RET-0^^OLIS_Site_ID^ISO|RET^RETICULOCYTE COUNT^HL79901 literal|||200905011106|||||||200905011106||OLIST^BLAKE^DONALD^THOR^^^^L^921379||7870279|7870279|T09-100442|MOHLTC|200905011130||B7|F||1^^^200905011106^^R\r"
	  			+ "OBX|1|ST|||Test Value";
	  		
	          /*
	           * The first (and probably better in most ways) technique is as follows. Use a model class
	           * factory called the CanonicalModelClassFactory. This class forces a specific version of
	           * HL7 to be used. Because HL7 v2.x is a backwards compatible standard, you can choose the
	           * highest version you need to support, and the model classes will be compatible with
	           * messages from previous versions.
	           */
	  		
	          HapiContext context = new DefaultHapiContext();
	         
	         // Create the MCF. We want all parsed messages to be for HL7 version 2.5,
	         // despite what MSH-12 says.
	         CanonicalModelClassFactory mcf = new CanonicalModelClassFactory("2.5");
	         context.setModelClassFactory(mcf);
	 
	         // Pass the MCF to the parser in its constructor
	         PipeParser parser = context.getPipeParser();
	         
	         // The parser parses the v2.3 message to a "v25" structure
	         ORU_R01 msg = (ORU_R01) parser.parse(v23message);
	         
	         // 20169838-v23
	         System.out.println(msg.getMSH().getMessageControlID().getValue());
	 
	         // The parser also parses the v2.5 message to a "v25" structure
	         msg = (ORU_R01) parser.parse(v25message);
	         
	         // 20169838-v25
	         System.out.println(msg.getMSH().getMessageControlID().getValue());
	         
	         /*
	          * The second technique is to use the Terser. The Terser allows you
	          * to access field values using a path-like notation. For more information
	          * on the Terser, see the example here:
	          * http://hl7api.sourceforge.net/xref/ca/uhn/hl7v2/examples/ExampleUseTerser.html
	          */
	 
	         // This time we just use a normal ModelClassFactory, which means we will be
	         // using the standard version-specific model classes
	         context.setModelClassFactory(new DefaultModelClassFactory());
	 
	         // 20169838-v23
	         Message v23Message = parser.parse(v23message);
	         Terser t23 = new Terser(v23Message);
	         System.out.println(t23.get("/MSH-10"));
	 
	         // 20169838-v25
	         Message v25Message = parser.parse(v25message);
	         Terser t25 = new Terser(v25Message);
	         System.out.println(t25.get("/MSH-10"));
	         
	         /*
	          * Note that this second technique has one major drawback: Although 
	          * message definitions are backwards compatible, some group names
	          * change between versions. If you are accessing a group within
	          * a complex message structure, this can cause issues.
	          * 
	          * This is less of an issue for some message types where groups are
	          * not used much (e.g. ADT)
	          */
	 
	         // This works and prints "Test Value"
	         System.out.println(t23.get("/RESPONSE/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5"));
	 
	         // This fails...
	         // System.out.println(t25.get("/RESPONSE/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5"));
	         
	         // ...because this would be required to extract the OBX-5 value from a v2.5 message
	         System.out.println(t25.get("/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5"));
	         
	         /*
	          * A third technique which may occasionally be useful is to simply use
	          * a "Generic" message structure. Generic message structures can 
	          * represent anything within an HL7 message, but they don't actually
	          * model all of the intricacies of the structure within the message,
	          * but rather just model all of the data in an unstructured way.
	          */
	         
	         // Create a new context using a Generic Model Class Factory
	         context = new DefaultHapiContext();
	         context.setModelClassFactory(new GenericModelClassFactory());
	 
	         v25message = "MSH|^~\\&|ULTRA|TML|OLIS|OLIS|200905011130||ORU^R01|20169838-v25|T|2.5\r"
	 			+ "PID|||7005728^^^TML^MR||TEST^RACHEL^DIAMOND||19310313|F|||200 ANYWHERE ST^^TORONTO^ON^M6G 2T9||(416)888-8888||||||1014071185^KR\r"
	 			+ "PV1|1||OLIS||||OLIST^BLAKE^DONALD^THOR^^^^^921379^^^^OLIST\r"
	 			+ "ORC|RE||T09-100442-RET-0^^OLIS_Site_ID^ISO|||||||||OLIST^BLAKE^DONALD^THOR^^^^L^921379\r"
	 			+ "OBR|0||T09-100442-RET-0^^OLIS_Site_ID^ISO|RET^RETICULOCYTE COUNT^HL79901 literal|||200905011106|||||||200905011106||OLIST^BLAKE^DONALD^THOR^^^^L^921379||7870279|7870279|T09-100442|MOHLTC|200905011130||B7|F||1^^^200905011106^^R\r"
	 			+ "OBX|1|ST|||Test Value\r"
	 			+ "NTE||Note for OBX(1)\r"
	 			+ "OBX|2|ST|||Value number 2";
	 
	         // The parser will always parse this as a "GenericMessage"
	         GenericMessage message = (GenericMessage) context.getPipeParser().parse(v25message);
	         
	         /* 
	          * A generic message has a flat structure, so you can ask for any
	          * field by only its segment name, not a complex path 
	          */
	         Terser t = new Terser(message);
	         System.out.println(t.get("/OBX-5"));
	         // Prints: Test Value
	         
	         /*
	          * This technique isn't great for messages with complex structures. For
	          * example, the second OBX in the message above is a part of the base structure
	          * because GenericMessage has no groups.
	          * 
	          * It can be accessed using a new segment name (OBX2 instead of OBX)
	          * but this is error prone, so use with caution.
	          */
	         System.out.println(t.get("/OBX2-5"));
	         // Prints: Value number 2
	         
	 	}
	 
	 }
	  
	  public String HapiModifyXML(String hl7message, String hl7XML, String version, String messageType ) throws HL7Exception, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		 
		  
		  MinLowerLayerProtocol llp = new MinLowerLayerProtocol();
		  ca.uhn.hl7v2.validation.impl.DefaultValidation dv = new ca.uhn.hl7v2.validation.impl.DefaultValidation( );
		  
		  ca.uhn.hl7v2.HapiContext context = new ca.uhn.hl7v2.DefaultHapiContext( dv );
         
         if (version.isEmpty()) version = "2.5";
         if (messageType.isEmpty()) version = "ADT^A01";
	         
         // Create the MCF. We want all parsed messages to be for HL7 version 2.5,
         // despite what MSH-12 says.
         ca.uhn.hl7v2.parser.CanonicalModelClassFactory mcf = new ca.uhn.hl7v2.parser.CanonicalModelClassFactory(version); //TODO pass in the exact version here
         context.setModelClassFactory(mcf);
 
         
         ca.uhn.hl7v2.parser.PipeParser parser = new ca.uhn.hl7v2.parser.PipeParser( mcf );
         
         // Pass the MCF to the parser in its constructor
         
         
         if (messageType.contains("ADT^A01"))
        	 hl7XML = ReplaceADT_A01(mcf, parser, hl7message, hl7XML );
         System.out.println(hl7XML);
         //context.close();
//        		 
//        		 ADT_A01
//        		 ADT_A02
//        		 ADT_A03
//        		 ADT_A05
//        		 ADT_A06
//        		 ADT_A09
//        		 ADT_A12
//        		 ADT_A15
//        		 ADT_A16
//        		 ADT_A17
//        		 ADT_A18
//        		 ADT_A20
//        		 ADT_A21
//        		 ADT_A24
//        		 ADT_A30
//        		 ADT_A37
//        		 ADT_A38
//        		 ADT_A39
//        		 ADT_A43
//        		 ADT_A45
//        		 ADT_A50
//        		 ADT_A52
//        		 ADT_A54
//        		 ADT_A60
//        		 ADT_A61
//        		 ADT_AXX        		 
//        		 
         // The parser parses the v2.3 message to a "v25" structure


         
         // testing to get the stuff to work without manually invoking the segment
//         for (int i = 0; i < msgNames.length; i++ ) {
//        	 System.out.println(msgNames[i]);
//        	 Class<? extends Structure> c = msg.getClass(msgNames[i]);
//        	 Structure myInstance = (Structure) msg.get(c.getName()); 
//        			 //Class.forName(c.getName()).newInstance();
//        	 Field[] fields = c.getFields();
//        	 for (int j = fields.length-1; j > 0; j-- ) {
//        		 System.out.println(fields[j].getName());
//        	 }
//        	 
//         }
            
         //throwing NPE context.close();
         
         return hl7XML;

         
	  }
	  
	  private String ReplaceADT_A01 (CanonicalModelClassFactory mcf, PipeParser parser, String hl7message, String hl7XML ) throws HL7Exception {
		  
	         ADT_A01 msg = new ca.uhn.hl7v2.model.v25.message.ADT_A01( mcf ); //(ca.uhn.hl7v2.model.v25.message.ORU_R01) parser.parse(v23message);
			  
	         msg = (ca.uhn.hl7v2.model.v25.message.ADT_A01) parser.parse(hl7message);
	         
	         hl7XML = HL7XMLReplace(msg.getMSH().getNames(), hl7XML, "MSH");
	         System.out.println("past MSH");
	         hl7XML = HL7XMLReplace(msg.getSFT().getNames(), hl7XML, "SFT");
	         hl7XML = HL7XMLReplace(msg.getEVN().getNames(), hl7XML, "EVN");
	         hl7XML = HL7XMLReplace(msg.getPID().getNames(), hl7XML, "PID");
	         hl7XML = HL7XMLReplace(msg.getPD1().getNames(), hl7XML, "PD1");
	         hl7XML = HL7XMLReplace(msg.getROL().getNames(), hl7XML, "ROL");
	         hl7XML = HL7XMLReplace(msg.getNK1().getNames(), hl7XML, "NK1");
	         hl7XML = HL7XMLReplace(msg.getPV1().getNames(), hl7XML, "PV1");
	         hl7XML = HL7XMLReplace(msg.getPV2().getNames(), hl7XML, "PV2");
	         hl7XML = HL7XMLReplace(msg.getAL1().getNames(), hl7XML, "AL1");
	         hl7XML = HL7XMLReplace(msg.getDB1().getNames(), hl7XML, "DB1");
	         hl7XML = HL7XMLReplace(msg.getDRG().getNames(), hl7XML, "DRG");
	         hl7XML = HL7XMLReplace(msg.getACC().getNames(), hl7XML, "ACC");
	         hl7XML = HL7XMLReplace(msg.getPDA().getNames(), hl7XML, "PDA");
	         hl7XML = HL7XMLReplace(msg.getDG1().getNames(), hl7XML, "DG1");
	         //hl7XML = HL7XMLReplace(msg.getPR1().getNames(), hl7XML, "PR1");
	         
	         hl7XML = HL7XMLReplace(msg.getGT1().getNames(), hl7XML, "GT1");

	         // only need to do this once for N insurances
	         hl7XML = HL7XMLReplace(msg.getINSURANCE(0).getIN1().getNames(), hl7XML, "IN1");
	         hl7XML = HL7XMLReplace(msg.getINSURANCE(0).getIN2().getNames(), hl7XML, "IN2");
	         hl7XML = HL7XMLReplace(msg.getINSURANCE(0).getIN3().getNames(), hl7XML, "IN3");
	         
	         return hl7XML;
	  }
	  
	  public String ReplaceADT_A01Msg (ca.uhn.hl7v2.model.v25.message.ADT_A01 msg, String hl7XML ) throws HL7Exception {
		  
	         
	         hl7XML = HL7XMLReplace(msg.getMSH().getNames(), hl7XML, "MSH");
	         hl7XML = HL7XMLReplace(msg.getSFT().getNames(), hl7XML, "SFT");
	         hl7XML = HL7XMLReplace(msg.getEVN().getNames(), hl7XML, "EVN");
	         hl7XML = HL7XMLReplace(msg.getPID().getNames(), hl7XML, "PID");
	         hl7XML = HL7XMLReplace(msg.getPD1().getNames(), hl7XML, "PD1");
	         hl7XML = HL7XMLReplace(msg.getROL().getNames(), hl7XML, "ROL");
	         hl7XML = HL7XMLReplace(msg.getNK1().getNames(), hl7XML, "NK1");
	         hl7XML = HL7XMLReplace(msg.getPV1().getNames(), hl7XML, "PV1");
	         hl7XML = HL7XMLReplace(msg.getPV2().getNames(), hl7XML, "PV2");
	         hl7XML = HL7XMLReplace(msg.getAL1().getNames(), hl7XML, "AL1");
	         hl7XML = HL7XMLReplace(msg.getDB1().getNames(), hl7XML, "DB1");
	         hl7XML = HL7XMLReplace(msg.getDRG().getNames(), hl7XML, "DRG");
	         hl7XML = HL7XMLReplace(msg.getACC().getNames(), hl7XML, "ACC");
	         hl7XML = HL7XMLReplace(msg.getPDA().getNames(), hl7XML, "PDA");
	         hl7XML = HL7XMLReplace(msg.getDG1().getNames(), hl7XML, "DG1");
	         //hl7XML = HL7XMLReplace(msg.getPR1().getNames(), hl7XML, "PR1");
	         
	         hl7XML = HL7XMLReplace(msg.getGT1().getNames(), hl7XML, "GT1");

	         // only need to do this once for N insurances
	         hl7XML = HL7XMLReplace(msg.getINSURANCE(0).getIN1().getNames(), hl7XML, "IN1");
	         hl7XML = HL7XMLReplace(msg.getINSURANCE(0).getIN2().getNames(), hl7XML, "IN2");
	         hl7XML = HL7XMLReplace(msg.getINSURANCE(0).getIN3().getNames(), hl7XML, "IN3");
	         
	         return hl7XML;
	  }
	  
	  private String ReplaceADT_AXX (CanonicalModelClassFactory mcf, PipeParser parser, String hl7message, String hl7XML ) throws HL7Exception {
		  
	         ADT_AXX msg = new ca.uhn.hl7v2.model.v25.message.ADT_AXX( mcf ); //(ca.uhn.hl7v2.model.v25.message.ORU_R01) parser.parse(v23message);
			  
	         msg = (ca.uhn.hl7v2.model.v25.message.ADT_AXX) parser.parse(hl7message);
	         
	         hl7XML = HL7XMLReplace(msg.getMSH().getNames(), hl7XML, "MSH");
	         hl7XML = HL7XMLReplace(msg.getSFT().getNames(), hl7XML, "SFT");
	         hl7XML = HL7XMLReplace(msg.getEVN().getNames(), hl7XML, "EVN");
	         hl7XML = HL7XMLReplace(msg.getPID().getNames(), hl7XML, "PID");
	         hl7XML = HL7XMLReplace(msg.getPD1().getNames(), hl7XML, "PD1");
	         hl7XML = HL7XMLReplace(msg.getROL().getNames(), hl7XML, "ROL");
	         hl7XML = HL7XMLReplace(msg.getNK1().getNames(), hl7XML, "NK1");
	         hl7XML = HL7XMLReplace(msg.getPV1().getNames(), hl7XML, "PV1");
	         hl7XML = HL7XMLReplace(msg.getPV2().getNames(), hl7XML, "PV2");
	         hl7XML = HL7XMLReplace(msg.getAL1().getNames(), hl7XML, "AL1");
	         hl7XML = HL7XMLReplace(msg.getDB1().getNames(), hl7XML, "DB1");
	         hl7XML = HL7XMLReplace(msg.getDRG().getNames(), hl7XML, "DRG");
	         hl7XML = HL7XMLReplace(msg.getACC().getNames(), hl7XML, "ACC");
	         hl7XML = HL7XMLReplace(msg.getPDA().getNames(), hl7XML, "PDA");
	         hl7XML = HL7XMLReplace(msg.getDG1().getNames(), hl7XML, "DG1");
	         hl7XML = HL7XMLReplace(msg.getOBX().getNames(), hl7XML, "OBX");
	         hl7XML = HL7XMLReplace(msg.getMRG().getNames(), hl7XML, "MRG");
	         //hl7XML = HL7XMLReplace(msg.getPR1().getNames(), hl7XML, "PR1");
	         
	         hl7XML = HL7XMLReplace(msg.getGT1().getNames(), hl7XML, "GT1");

	         // only need to do this once for N insurances
	         hl7XML = HL7XMLReplace(msg.getINSURANCE(0).getIN1().getNames(), hl7XML, "IN1");
	         hl7XML = HL7XMLReplace(msg.getINSURANCE(0).getIN2().getNames(), hl7XML, "IN2");
	         hl7XML = HL7XMLReplace(msg.getINSURANCE(0).getIN3().getNames(), hl7XML, "IN3");
	         
	         return hl7XML;
	  }
	  
	  public String HL7XMLReplace(String[] names, String hl7XML, String segment) {
	      
	      for (int i = names.length-1; i > 0; i-- ) {
	     	 //System.out.println("MSH" + i + "Field name:" + mshNames[i]);	
	     	 hl7XML = hl7XML.replaceAll(segment + "." + i, names[i].replaceAll(" ", "").replaceAll("'", "").replaceAll(",", "").replaceAll("/", "").replaceAll(":", ""));
	      }
	      return hl7XML;
		  
	  }
	  
	  public String XMLtoJSON(String hl7XML) {
		  
		  
          JSONObject xmlJSONObj = XML.toJSONObject(hl7XML);
          int PRETTY_PRINT_INDENT_FACTOR = 4;
          String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
          //System.out.println(jsonPrettyPrintString);
          
		  return jsonPrettyPrintString;
		  
	  }

}

class MyTypeAdapter<T> extends TypeAdapter<T> {

	@Override
	public void write(JsonWriter writer, T obj) throws IOException {
        if (obj == null) {
            writer.nullValue();
            return;
        }
        writer.value(obj.toString());
		
	}

	@Override
	public T read(JsonReader in) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}

