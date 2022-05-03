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
 *         This program handles the CSVDataMissing exception and outputs to the
 *         console the appropriate message
 **/

public class CSVDataMissing extends Exception {

	public CSVDataMissing() {
		super("Error: Input row cannot be parsed due to missing information.\nWARNING: In file covidStatistics.CSV is not converted to HTML: missing data: ");
	}

	public CSVDataMissing(String s, int l, String data) {
		super("Error: Input row cannot be parsed due to missing information.\nWARNING: In file " + s + " line" + l
				+ " is not converted to HTML: missing data: " + data);
	}

}