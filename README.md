# Inferencer

Inferencer folder:
EnumerationInferencer.java: This is an exact inferencer method. In the enumeration inferencer, there are 2
methods. One is query which returns the exact distribution of query variable given evidence. Another method is Enumerate_All, which is used to calculate specific probability.
Likelihood_Weighting.java: This is the likelihood weighting inference.There are 4 methods. First function is 
		              Random_sample, which generates a random sample for the computation in the 
		              Weight_Sample function. The second is Weight_Sample function, each non evidence 
		              variable is sampled according to the assignemnt given on the values for the variable’s parents, 
		              while a weight is accumulated on the likelihood for each evidence. Then there are 2 query
		              methods, one of which allow user to specify the number of samples. Both of 2 methods
		              return the normalized distribution of query variable given evidence.
Prior_Sampling.java: The prior_sampling class is used to generate a random assignment for variable according to their
		  cpt in the network. The getSample() method is used to generate an assignment to all variables in
		  the network. randomSampleForVariable() method is used to assign a random value according to 
		  the assignment of its parents variable.
RejectionSamplingInfernecer.java: This is the Rejection sampling inferencer. There are 3 methods. The protected isConsistent()
			       method is used to decide whether 2 given assigment are same on their assigned value for same
			       variables. The other 2 methods both return the distribution of a given variable according to given
			       assignment. One of which allow user to specify the number of samples.
