package com.out;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class SimpleView
 */
public class SimpleView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SimpleView() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Resource(name = "couchdb/cloudant-demosample")
   	protected CouchDbInstance _db;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String dbname="demodb";
		try{
		CouchDbConnector db=_db.createConnector(dbname,true);
		//Create a connector to the database
		
		//Code obtains values from the predefined view 'generic' created by map-reduce View
				ViewQuery query= new  ViewQuery().designDocId("_design/somedoc").viewName("generic");
				InputStream data=db.queryForStream(query);
		//Copy the values obtained and store it into a StringWriter		
				StringWriter writer=new StringWriter();
				IOUtils.copy(data, writer);			
		
		//Create an object mappers and user JsonNode to obtain the document info 		
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode=mapper.readTree(writer.toString()).get("rows");
				
		
				if(rootNode.isArray()){
					//Traverse the array and display id and key for each document
						for(int i=0;i<rootNode.size();i++)
						{
							JsonNode tmps=rootNode.get(i);
							response.getWriter().println("Docid="+tmps.get("id")+"  Fname="+tmps.get("key"));
							String id=tmps.get("id").toString();
							String key=tmps.get("key").toString();
							//Use the key and id to ping any update and delete etc
							
						}
						
				}		
				
				
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
