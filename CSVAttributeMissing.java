package Assignment3;

//-----------------------------------------------------
//Assignment 3
//Written by: Guillaume Lachapelle (40203325) and Oliver Vilney (40214948)
//-----------------------------------------------------

/**
 * @author Guillaume Lachapelle and Oliver Vilney
 * 
 *         COMP249 Assignment 3
 * 
 *         25 March 2022
 *
 *         This program handles the CSVAttributeMissing exception and outputs to
 *         the console the appropriate message
 **/

public class CSVAttributeMissing extends Exception {

	public CSVAttributeMissing() {
		super("Error: Input row cannot be parsed due to missing information.\nERROR: In file covidStatistics.CSV. Missing attribute. File is not converted to HTML.");
	}

	public CSVAttributeMissing(String s) {
		super("Error: Input row cannot be parsed due to missing information.\nERROR: In file " + s
				+ ". Missing attribute. File is not converted to HTML.");
	}

}
