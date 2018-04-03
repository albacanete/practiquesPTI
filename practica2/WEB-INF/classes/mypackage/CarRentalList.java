package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class CarRentalList extends HttpServlet {

  int cont = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    String nombre = req.getParameter("userid");
    String contrasenya = req.getParameter("password");
    
    if (!nombre.equals("admin") && !contrasenya.equals("admin")) {
		out.println("<html><big> Hola! <br> No tienes acceso a este sitio :)</big></html>");
	}
    else {
		cont ++;
		out.println("<html><big>Hola "+ nombre + "!</big><br>"+
					cont + " Acceso(s) desde su carga. <br> <br></html>");
					
		JSONParser parser = new JSONParser();

        try {

            Object bd = parser.parse(new FileReader("./BD.json"));

            JSONArray list = (JSONArray) bd;
            Iterator<JSONObject> iterator = list.iterator();
            int i = 1;
            while (iterator.hasNext()) {
				JSONObject item = (JSONObject) iterator.next();
				
				out.println("Lloguer número " + i + "<br>");

				String model = (String) item.get("model");
				out.println("Car Model: " + model + "<br>");

				String subModel = (String) item.get("subModel");
				out.println("Engine: " + subModel + "<br>");

				String dies = (String) item.get("dies");
				out.println("Number of days: " + dies + "<br>");

				String numero = (String) item.get("numero");
				out.println("Number of units: " + numero + "<br>");

				String descompte = (String) item.get("descompte");
				out.println("Discount: " + descompte + "<br>");

				String preu = (String) item.get("preu");
				out.println("Price: " + preu + "<br> <br>");
				
				++i;
            }
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
	}
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
}
