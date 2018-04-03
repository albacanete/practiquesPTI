package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.time.LocalDateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public class CarRentalNew extends HttpServlet {

  int cont = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
   
    String model = req.getParameter("model_vehicle");
    String subModel = req.getParameter("sub_model_vehicle");
    String dies = req.getParameter("dies_lloguer");
    String numero = req.getParameter("num_vehicles");
    String descompte = req.getParameter("descompte");
    
    cont ++;
    out.println("<html><big>Hola</big><br>"+
                cont + " Accesos desde su carga.<br><br></html>");
                        
    out.println("Car Model: " + model + "<br>");
    out.println("Engine: " + subModel + "<br>");
    out.println("Number of days: " + dies + "<br>");
    out.println("Number of units: " + numero + "<br>");
    out.println("Discount: " + descompte + "<br>");
   
    String preu = "Una millonada";
    out.println("Preu: " + preu + "<br>");
   
	LocalDateTime l = LocalDateTime.now();
    int dia = l.getDayOfMonth();
    int mes = l.getMonthValue();
    int any = l.getYear();
    out.println("Data: " + dia + "/" + mes + "/" + any + "<br>");

    JSONParser parser = new JSONParser();
    JSONArray jsonLloguers = new JSONArray();
    
	try {     
		
		File bd = new File("./BD.json");

		if(!bd.exists()) {
			bd.createNewFile();
		}
		else if(bd.exists() && !bd.isDirectory()) {
			Object lloguers = parser.parse(new FileReader(bd));
			jsonLloguers =  (JSONArray) lloguers;
		}
		
		JSONObject nouLloguer = new JSONObject();
		nouLloguer.put("model", model);
		nouLloguer.put("subModel", subModel);
		nouLloguer.put("dies", dies);
		nouLloguer.put("numero", numero);
		nouLloguer.put("descompte", descompte);
		nouLloguer.put("preu", preu);
		
		jsonLloguers.add(nouLloguer); 	
	} 
	
	catch (FileNotFoundException e) {
		out.println("No hi ha cap lloguer fet.");
	} 
	catch (IOException e) {
		out.println("No s'han pogut carregar els lloguers.");
	} 
	catch (ParseException e) {
		out.println("No s'han pogut carregar els lloguers.");
	}
	
	try (FileWriter file = new FileWriter("./BD.json")) {

		file.write(jsonLloguers.toJSONString());
		file.flush();


	} catch (IOException e) {
		e.printStackTrace();
	}
    
    // ESCRIURE A FITXER
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
}
