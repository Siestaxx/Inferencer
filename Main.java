import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bn.core.Assignment;
// import bn.base.BayesianNetwork;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.RandomVariable;
import bn.inference.EnumerationInferencer;
import bn.inference.Likelihood_Weighting;
import bn.inference.RejectionSamplingInferencer;
import bn.parser.BIFParser;
import bn.parser.XMLBIFParser;

public class Main {
	private String[] args;
	private String inferencer;

	//constructor of Main class
	public Main(String[] args, String inferencer) {
		this.args = args;
		this.inferencer = inferencer;

	}

	//a method which read in user input and according to their input to select different inferencer and print the distribution.
	public void perform(){
		String parameter="";
		String filename = "";
		String[] evidence = new String[100000];
		int samples = 0;
		if(inferencer.equals("ExactInferencer")){
			filename = args[1];     
			parameter = args[2];
			evidence = Arrays.copyOfRange(args, 3, args.length);
		}else if(inferencer.equals("LikelihoodInferencer")){
			samples = Integer.parseInt(args[1]);
			filename = args[2];     
			parameter = args[3];    
			evidence = Arrays.copyOfRange(args, 4, args.length);		
		}else if(inferencer.equals("RejectionInferencer")){
			samples = Integer.parseInt(args[1]);
			filename = args[2];
            parameter = args[3];
			evidence = Arrays.copyOfRange(args, 4, args.length);
		}


		String type = "XML";
		if (filename.toLowerCase().endsWith(".bif")) { 
			type = "BIF";
		}else if (filename.toLowerCase().endsWith(".xml")) { 
			type = "XML";
		}
	

		try {
			String newpath = filename;
			BayesianNetwork BN = new bn.base.BayesianNetwork();
			if (type.equals("BIF")) {
				BIFParser parser = new BIFParser(new FileInputStream(newpath));
				BN = parser.parseNetwork();
			}else {
				XMLBIFParser parser = new XMLBIFParser();
				BN = parser.readNetworkFromFile(newpath);
			}

			RandomVariable query = BN.getVariableByName(parameter);
			Assignment A = new bn.base.Assignment();
			RandomVariable[] key= new RandomVariable[evidence.length];
			for (int i = 0; i < evidence.length ; i = i+2 ) {    
				key[i] = BN.getVariableByName(evidence[i]);
				A.set(key[i], evidence[i+1]);      
			}


			if (inferencer.equals("ExactInferencer")) {
				EnumerationInferencer inf = new EnumerationInferencer();  
				Distribution dist = inf.query(query, A, BN);
				System.out.println("\nCompleted by:" + inferencer);
				System.out.println("\nThe distribution is:" + dist.toString() + "\n");
			
			}else if (inferencer.equals("LikelihoodInferencer")) {
				Likelihood_Weighting inf = new Likelihood_Weighting();
				Distribution dist = inf.query( query, A, BN,samples);
				System.out.println("\nCompleted by:" + inferencer);
				System.out.println("\nThe distribution is :" + dist.toString() + "\n");

			}else if (inferencer.equals("RejectionInferencer")){
				RejectionSamplingInferencer inf = new RejectionSamplingInferencer();
				Distribution dist = inf.query(query, A, (bn.base.BayesianNetwork) BN, samples);
                System.out.println("\nCompleted by:" + inferencer);
				System.out.println("\nThe distribution is:" + dist.toString() + "\n");}

		}
			catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}		
	}

	//main method to run the program
	public static void main(String[] args) {
        String inferencer = args[0];
        Main AI = new Main(args,inferencer);
        AI.perform();        
    }
}