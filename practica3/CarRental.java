import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Namespace;
import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import org.jdom2.input.sax.XMLReaders;

public class CarRental {

    /**
     * Read and parse an xml document from the file at example.xml.
     * @return the JDOM document parsed from the file.
     */
    public static Document readDocument() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new File("carrental.xml"));
            return anotherDocument;
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method creates a JDOM document with elements that represent the
     * properties of a car.
     * @return a JDOM Document that represents the properties of a car.
     */
    public static Document createDocument() {
		
		final Namespace namespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		final Element carrental = new Element ("carrental");
		carrental.setAttribute("noNamespaceSchemaLocation", "carrental.xsd", namespace);
	    
       
		Document myDocument = new Document(carrental);
        
        return myDocument;
    }

    /**
     * This method accesses a child element of the root element 
     * @param myDocument a JDOM document 
     */
    public static void accessChildElement(Document myDocument) {
        //some setup
        Element carElement = myDocument.getRootElement();

        //Access a child element
        Element yearElement = carElement.getChild("year");

        //show success or failure
        if(yearElement != null) {
            System.out.println("Here is the element we found: " +
                yearElement.getName() + ".  Its content: " +
                yearElement.getText() + "\n");
        } else {
            System.out.println("Something is wrong.  We did not find a year Element");
        }
    }

    /**
     * This method removes a child element from a document.
     * @param myDocument a JDOM document.
     */
    public static void removeChildElement(Document myDocument) {
        //some setup
        System.out.println("About to remove the year element.\nThe current document:");
        resetDocument(myDocument);
        Element carElement = myDocument.getRootElement();

        //remove a child Element
        boolean removed = carElement.removeChild("year");

        //show success or failure
        if(removed) {
            System.out.println("Here is the modified document without year:");
            resetDocument(myDocument);
        } else {
            System.out.println("Something happened.  We were unable to remove the year element.");
        }
    }

    public static void resetDocument(Document myDocument) {
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(myDocument, System.out);
            outputDocumentToFile(myDocument);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shows how to use XMLOutputter to output a JDOM document to
     * a file located at myFile.xml.
     * @param myDocument a JDOM document.
     */
    public static void outputDocumentToFile(Document myDocument) {
        //setup this like outputDocument
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter();

            //output to a file
            FileWriter writer = new FileWriter("carrental.xml");
            outputter.output(myDocument, writer);
            writer.close();

        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes a JDOM document in memory, an XSLT file at example.xslt,
     * and outputs the results to stdout.
     * @param myDocument a JDOM document .
     */
    public static void executeXSLT(Document myDocument) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
            // Make the input sources for the XML and XSLT documents
            org.jdom2.output.DOMOutputter outputter = new org.jdom2.output.DOMOutputter();
            org.w3c.dom.Document domDocument = outputter.output(myDocument);
            javax.xml.transform.Source xmlSource = new javax.xml.transform.dom.DOMSource(domDocument);
            StreamSource xsltSource = new StreamSource(new FileInputStream("caRRental.xslt"));
			//Make the output result for the finished document
            StreamResult xmlResult = new StreamResult(System.out);
			//Get a XSLT transformer
			Transformer transformer = tFactory.newTransformer(xsltSource);
			//do the transform
			transformer.transform(xmlSource, xmlResult);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(TransformerConfigurationException e) {
            e.printStackTrace();
		} catch(TransformerException e) {
            e.printStackTrace();
        } catch(org.jdom2.JDOMException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Reads data from std input.
	 */
	public static Element askData() {
		Element carElement = new Element("rental");
		// Make
		System.out.print("Make:");
		String makeInput = System.console().readLine();
		Element make = new Element("make");
		make.addContent(makeInput);
		carElement.addContent(make);
		// Model
		System.out.print("Model:");
		String modelInput = System.console().readLine();
		Element model = new Element("model");
		model.addContent(modelInput);
		carElement.addContent(model);
		// Number of days
		System.out.print("Number of days:");
		String nofdaysInput = System.console().readLine();
		Element nofdays = new Element("nofdays");
		nofdays.addContent(nofdaysInput);
		carElement.addContent(nofdays);
		// Number of units
		System.out.print("Number of units:");
		String nofunitsInput = System.console().readLine();
		Element nofunits = new Element("nofunits");
		nofunits.addContent(nofunitsInput);
		carElement.addContent(nofunits);
		// Discount
		System.out.print("Discount:");
		String discountInput = System.console().readLine();
		Element discount = new Element("discount");
		discount.addContent(discountInput);
		carElement.addContent(discount);
		
		carElement.setAttribute(new Attribute("id", "anyID"));
		return carElement;
	}

	
	/**
	 * Reads a new rental from std input and saves adds it to carrental.xml .
	 */
	public static void newRental() {
		Document doc = readDocument();
		Element root = doc.getRootElement();
		
		Element rental = askData();
		root.addContent(rental);
		doc.setContent(root);
		outputDocumentToFile(doc);
	}
	
	
	 /**
     * This method shows how to use XMLOutputter to output a JDOM document to
     * the stdout.
     * @param myDocument a JDOM document.
     */
    public static void outputDocument(Document myDocument) {
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(myDocument, System.out);
            outputDocumentToFile(myDocument);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Reads the document carrental.xml and shows it to the stdout.
	 */
	public static void listRentals() {
		Document doc = readDocument();
		outputDocument(doc);
	}


	public static void validateDocument(Document Doc) {	
		SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
		
		try {
		
		Document anotherDocument = builder.build(new File("carrental.xml"));
		System.out.println("Root: " + anotherDocument.getRootElement().getName());
		} catch(java.io.IOException e) {
            e.printStackTrace();
        } catch(org.jdom2.JDOMException e) {
           System.out.print("Ups! El document de les reserves no esta be!\n");
        }

	}

    /**
     * Main method that allows the various methods to be used.
     * It takes a single command line parameter.  If none are
     * specified, or the parameter is not understood, it prints
     * its usage.
     */
    public static void main(String argv[]) {
        if(argv.length == 1) {
            String command = argv[0];
            if(command.equals("reset")) resetDocument(createDocument());
            else if(command.equals("new")) newRental();
            else if(command.equals("list")) listRentals();
            else if(command.equals("xslt")) executeXSLT(readDocument());
            else if(command.equals("validate")) validateDocument(readDocument());
            else {
                System.out.println(command + " is not a valid option.");
                printUsage();
            }
        } else {
            printUsage();
        }
    }

    /**
     * Convience method to print the usage options for the class.
     */
    public static void printUsage() {
        System.out.println("Usage: Example [option] \n where option is one of the following:");
        System.out.println("  reset - create a new document in memory, if the document exists ereases its content");
        System.out.println("  new	 -ask the user for a new rental and safe the data in memory");
        System.out.println("  xslt    - create a new document and transform it to HTML with the XSLT stylesheet in caRRental.xslt");
        System.out.println("  list   - Reads the document carrental.xml and shows it to the stdout");
        System.out.println("  validate   - The application will read the carrental.xml XML document into memory and validate it againt carrental.xsd.");
    }
      
       
}
