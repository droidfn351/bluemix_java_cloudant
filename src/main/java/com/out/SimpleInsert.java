package com.out;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
 

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * Servlet implementation class SimpleInsert
 */
public class SimpleInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SimpleInsert() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    @Resource(name = "couchdb/cloudant-demosample")
    protected CouchDbInstance _db;
    
	/**
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		try{
			
			CouchDbConnector db=_db.createConnector("demodb",true);
			//Connects to the database specified
			 Map<String, Object> doc = new  HashMap<String, Object>();
		        doc.put("_id",  UUID.randomUUID().toString());
		        doc.put("fname",request.getParameter("fn")); //fn is obtained from the user
		        doc.put("lname",request.getParameter("ln")); //ln is obtained from the user 
		     //Inserts the data onto cloudant, each entry is stored as a document
		        db.create(doc);
		     //Creates a document
		        response.sendRedirect("landing.html");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
		
		
		
		
	}


