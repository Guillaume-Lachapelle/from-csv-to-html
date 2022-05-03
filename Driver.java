package Assignment3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

//-----------------------------------------------------
//Assignment 3
//Written by: Guillaume Lachapelle (40203325) and Oliver Vilney (40214948)
//-----------------------------------------------------

/**
 * @author Guillaume Lachapelle and Oliver Vilney 
 * 
 * COMP249 Assignment 3
 * 
 * 25 March 2022
 *
 * This program asks the user which .csv file they would
 * like to convert into an html file and then creates that file with a
 * table containing the required information. It also makes sure to
 * handle any exception that might get thrown
 **/

public class Driver {

	public static void readAndWrite(BufferedReader sc, PrintWriter pw, PrintWriter pw3, String fileInput)
			throws IOException, CSVAttributeMissing, CSVDataMissing {

		int lineCounter = 1;
		boolean b = false;
		int attribute = 0;
		String[] header = null;

		/**
		 * Based on the line number, this method will create the appropriate html tags
		 * for the table
		 **/
		try {

			pw.println("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<style>\r\n"
					+ "table {font-family: arial, sans-serif;border-collapse: collapse;}\r\n"
					+ "td, th {border: 1px solid #000000;text-align: left;padding: 8px;}\r\n"
					+ "tr:nth-child(even) {background-color: #dddddd;}\r\n" + "span{font-size: small}\r\n"
					+ "</style>\r\n" + "<body>\r\n" + "\r\n" + "<table>");

			String info1 = sc.readLine();

			while (info1 != null) {

				switch (lineCounter) {
				case 1:
					String[] caption1 = info1.split(",");
					pw.println("<caption>" + caption1[0] + "</caption>");
					break;
				case 2:
					header = info1.split(",");
					for (int i = 0; i < header.length; i++) {
						if (header[i] == null || header[i] == "") {
							throw new CSVAttributeMissing();
						} else if (header.length == 3) {
							throw new CSVAttributeMissing();
						}
					}
					pw.println("<tr>");
					for (int i = 0; i < header.length; i++) {
						pw.println("<th>" + header[i] + "</th>");
					}
					pw.println("</tr>");
					break;
				default:
					if (info1.contains("Note:")) {
						b = true;
						pw.println("</table>");
						String[] info2 = info1.split(",");
						pw.println("<span>" + info2[0] + "</span>");
						pw.println("</body>\n</html>");
					} else {
						String[] body = info1.split(",");
						for (int i = 0; i < body.length; i++) {
							if (body[i] == null || body[i] == "") {
								attribute = i;
								throw new CSVDataMissing();
							} else if (body.length == 3) {
								attribute = 3;
								throw new CSVDataMissing();
							}
						}

						pw.println("<tr>");
						for (int i = 0; i < body.length; i++) {
							pw.println("<td>" + body[i] + "</td>");
						}
						pw.println("</tr>");
					}
					break;
				}
				lineCounter++;
				info1 = sc.readLine();
			}
			if (b = false) {
				pw.println("</table>\n</body>\n</html>");
			}

		} /** Any thrown exception will be caught in the catch blocks **/
		catch (CSVAttributeMissing e) {
			pw3.println("Error: Input row cannot be parsed due to missing information.\nERROR: In file " + fileInput
					+ ". Missing attribute. File is not converted to HTML.");
			throw new CSVAttributeMissing(fileInput);
		} catch (CSVDataMissing e) {
			pw3.println("Error: Input row cannot be parsed due to missing information.\nWARNING: In file" + fileInput
					+ " line " + lineCounter + " is not converted to HTML: missing data: " + header[attribute]);
			throw new CSVDataMissing(fileInput, lineCounter, header[attribute]);
		} catch (IOException e) {
			System.out.println("There's an error somewhere! Program will terminate.");
			System.exit(0);
		} finally {
			pw.close();
			pw3.close();
			sc.close();
		}
	}// End of readAndWrite() method

	public static void displayToConsole(BufferedReader sc) {
		/** Display each line of the html file to the console for the user **/
		try {
			String line = sc.readLine();
			while (line != null) {
				System.out.println(line);
				line = sc.readLine();
			}
			sc.close();
		} /** Any thrown exception will be caught in the catch block **/
		catch (IOException e) {
			System.out.println("Couldn't display the requested file. Program will terminate.");
			System.exit(0);
		}

	}// End of displayToConsole() method

	public static void main(String[] args)
			throws FileNotFoundException, CSVAttributeMissing, IOException, CSVDataMissing {

		System.out.println("Welcome to Guillaume Lachapelle's and Oliver Vilney's CSV to HTML converter program!");

		/**
		 * Ask user for .csv file they want to convert and make sure it is a .csv file
		 **/
		Scanner keyIn = new Scanner(System.in);
		System.out.println("Please enter the name of the .csv file you would like to convert to .html:");
		String fileName = keyIn.next();
		if (!fileName.contains(".csv")) {
			keyIn.close();
			throw new FileNotFoundException();
		}

		String htmlFile = fileName.substring(0, fileName.length() - 3);

		BufferedReader sc = null;
		PrintWriter pw1 = null;
		PrintWriter pw3 = null;

		try {/**
				 * Read from the .csv file and write to the html file, then display the
				 * requested html file to the console
				 **/

			sc = new BufferedReader(new FileReader(fileName));
			pw1 = new PrintWriter(new FileOutputStream(htmlFile + "html"));
			pw3 = new PrintWriter(new FileOutputStream("Exceptions.log", true));
			readAndWrite(sc, pw1, pw3, fileName);
			System.out.println("Which .html file would you like to display?");
			String userString = keyIn.next();
			sc = new BufferedReader(new FileReader(userString));
			displayToConsole(sc);

		} /** Any thrown exception will be caught in the catch block **/
		catch (FileNotFoundException e) {

			try {/**
					 * Second and final chance for the user to enter a valid file name if the first
					 * one was not valid
					 **/

				Scanner keyIn2 = new Scanner(System.in);
				System.out.println(
						"Please enter the name of the .csv file you would like to convert to .html. This is your last try:");
				String fileName2 = keyIn2.next();
				if (!fileName2.contains(".csv")) {
					keyIn2.close();
					throw new IOException();
				}

				String htmlFile2 = fileName2.substring(0, fileName2.length() - 3);

				try {

					sc = new BufferedReader(new FileReader(fileName2));
					pw1 = new PrintWriter(new FileOutputStream(htmlFile2 + "html"));
					pw3 = new PrintWriter(new FileOutputStream("Exceptions.log", true));
					readAndWrite(sc, pw1, pw3, fileName);
					System.out.println("Which .html file would you like to display?");
					String userString = keyIn.next();
					sc = new BufferedReader(new FileReader(userString));
					displayToConsole(sc);

				} /** Any thrown exception will be caught in the catch block **/
				catch (IOException e1) {

					System.out.println("Couldn't display the requested file. Program will terminate.");
					System.exit(0);

				} finally {
					keyIn2.close();
				}

			} /** Any thrown exception will be caught in the catch blocks **/
			catch (IOException e2) {

				System.out.println("Couldn't display the requested file. Program will terminate.");
				System.exit(0);

			}

			PrintStream out = new PrintStream(new FileOutputStream("Exceptions.log", true));
			System.setOut(out);
			System.out.println("Could not open input file" + e + " for reading.\r\n"
					+ "Please check that the file exists and is readable. This program will terminate after closing any opened files.");

			out.close();
			System.out.println("Could not open input file" + e + " for reading.\r\n"
					+ "Please check that the file exists and is readable. This program will terminate after closing any opened files.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("There's an error. Program will terminate");
			System.exit(0);
		} finally {
			keyIn.close();
			System.out.println("Thank you for using this program!");
		}
	}// End of main() method

}// End of class Driver