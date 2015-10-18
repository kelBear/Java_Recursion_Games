//****************************************************************************************
//Blob Class
//Kelly Guo
//Date: October 15, 2012
//Java, Eclipse 3.4
//****************************************************************************************
//<Problem Definition>
//		This class displays an image of 3 blobs to the user. The user is then shown a list of 500 numbers
//		and is asked to play the game by memorizing a number on the location of the asterisk to eat the
//		blob. If the user wishes, the game continues until all blobs are eaten. Then, a new game begins.
//<Input>
//		- continue or exit the program
//		- the number that represents the location the user has chosen
//<Output>
//		- original image of blobs
//		- list of numbers that represent locations of the blobs
//		- image of blobs after blob(s) have been eaten
//<List of Identifiers>
//		choice - accepts the input of the user to determine whether the user wishes to continue with the program or not (String)
//		x - the row number of the coordinate to start the maze (byte)
//		y - the column number of the coordinate to start the maze (byte)
//		count - counter used for eating all 3 blobs before a new game begins (byte)
//		pattern - array that holds the blobs (char [][])
//		test - determines whether the location chosen by the user contains a * (boolean)
//		loc - input of number chosen by user to represent their choice of location (int)
//		result - determines whether the user would like to continue or exit after viewing numbers (int)
//		strRandom - array that holds 500 numbers in random order (String[][])
//let LIMX = constant used to represent the number of rows of char[][]pattern and String[][] strRandom <type byte>
//let LIMY = constant used to represent the number of columns of char[][]pattern and String[][] strRandom <type byte>
//let END = constant to terminate the program when it is entered by user <type String>
//****************************************************************************************

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class Blob {

	/**Procedural main method
	 * This method calls other non-static methods to assign variables and passes variables as arguments.
	 * <p>
	 * Call beginGame method to determine to continue or exit the program
	 * Call fileInputArray method to input the image of blobs into an array
	 * Call displayBlob method to output the image of blobs
	 * Call randomNumbers method to assign a list of randomized order numbers into an array
	 * Call displayNumbers method to output the list of numbers
	 * Call locationInput method to receive user input of the number they chose
	 * Call testLocation method to see if location has a *
	 * Call findX method to assign the row number
	 * Call findY method to assign the column number
	 * Call eraseBlob method to erase the blob of *s
	 * Call finalBlob method to output the image of blob(s) have been eaten
	 * Call playAgain method to determine whether to play again or not
	 * 
	 * @param args
	 * @return void
	 * @throws IOException
	 */
	
	final byte LIMX = 20;
	final byte LIMY = 25;
	final String END = "end";
	
	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
		Blob b1 = new Blob ();
		byte x, y, count = 0;
		int loc, result;
		String choice;
		char pattern [][] = new char [b1.LIMX][b1.LIMY];
		String strRandom [][] = new String [b1.LIMX][b1.LIMY];
		boolean test;
		choice = b1.beginGame();
		pattern = b1.fileInputArray();
		while (!choice.equals (b1.END)){
			count ++;
			choice = b1.displayBlob(pattern);
			if (choice.equals(b1.END))
				break;
			strRandom = b1.randomNumbers();
			result = b1.displayNumbers(strRandom);
			if (result == JOptionPane.CANCEL_OPTION){
				System.out.println("ByeBye!");
				choice = b1.END;
				break;
			}
			else {
				loc = b1.locationInput();
				test = b1.testLocation(loc, strRandom, pattern);
				while (test == false){
					System.out.println ("Sorry, you did not choose an asterisk. Try again!");
					loc = b1.locationInput();
					test = b1.testLocation(loc, strRandom, pattern);
				}
				x = b1.findX(loc, strRandom);
				y = b1.findY(loc, strRandom);
				pattern = b1.eraseBlob(x, y, pattern);
				b1.finalBlob(pattern);
			}
			if (count > 2)
				pattern = b1.fileInputArray();
			choice = b1.playAgain();
		}
		System.out.println ("Byebye!");
	}	//end main method

	/** Functional beginGame method
	 * Returns a string object that is received from the user input to determine whether the user would like to
	 * continue on with the program or not.
	 * <p>
	 * This method provides the user with a brief explanation of the rules of the game and asks for an option to continue or
	 * to exit.
	 * 
	 * Let choice represent the input of the user to continue or exit the program (String)
	 * 
	 * @param none
	 * @return choice - the choice the user inputs
	 * @throws IOException
	 */
	public String beginGame () throws IOException{
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		String choice;
		System.out.println("Welcome to the Blob Eater game! This game will test your memory skills to the max!");
		System.out.println();
		System.out.println("Let's first explain the rules. An image of asterisks (*) in random blobs will be displayed.");
		System.out.println("Should you choose to accept the challenge, a list of 500 numbers will pop up and your job is to");
		System.out.println("memorize those numbers to the best of your abilities. Each number corresponds to the same");
		System.out.println("position as the image with blobs. For example, if you chose the 3rd number on the 2nd row,");
		System.out.println("you would have chosen the 3rd character on the 2nd row of the image. It will either be a space");
		System.out.println("or an asterisk. Your goal is to memorize a number that is on the location of an asterisk");
		System.out.println("and not a space so that your blob will be eaten. Good luck to the brave souls who accept this challenge!");
		System.out.println();
		System.out.println("It is understandable if you would like to chicken out, simply enter 'end' if you wish to exit.");
		System.out.println("Enter anything else to enter the ultimate challenge and view the image.");
		choice = br.readLine();
		return choice;
	}	//end beginGame method

	/** Functional fileInputArray method
	 * Returns a 2D character array of the original blobs read from a file.
	 * <p>
	 * This method creates and instantiates a character array that is then filled by file input with object input.
	 * The file is read by creating and instantiating a BufferedReader and uses it to read the file.
	 * The entire file is then returned in the type of a character array.
	 * 
	 * Let pattern represent the image of the blobs (char[][])
	 * let line represent a line inputed from the file (String)
	 * Let c represent the counter used when filling the array from file (byte)
	 * 
	 * @param none
	 * @return the character array of the blobs of asterisks
	 * @throws IOException
	 */
	public char[][] fileInputArray () throws IOException{
		BufferedReader input = new BufferedReader (new FileReader ("E:/Computer 12/KellyGuoAssign1/Blob.txt"));
		char [][] pattern = new char [20][25];
		String line;
		byte c = 0;
		line = input.readLine();
		while (c < LIMX ) { //File is terminated at the 20th line
			for (int a = 0; a < LIMY; a++){
				pattern[c][a]=line.charAt(a);
			}
			line = input.readLine ();  //Read next line.
			c++;
		}
		return pattern;
	}	//end fileInputArray method

	/** Functional displayBlob method
	 *This method outputs the 2D array that holds the image of the blobs of asterisks and returns the input of the
	 *user of whether they would like to continue with the game or exit.
	 * </p>
	 * This method uses a loop to display the 2D array of the image of blobs. It then prompts the user for an input
	 * to determine whether the user would like to continue with the games or exit the game. By using a nested method 
	 * in this method, it helps reduce the use of a while loop in the main method.
	 * 
	 * Let choice represent the user input to continue or exit (String)
	 * 
	 * @param pattern - 2D character array containing the blobs of asterisks (char[][])
	 * @return choice - input by user to determine if the game continues or exits (String)
	 */
	public String displayBlob (char[][]pattern) throws IOException {
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		String choice;
		for (byte a = 0; a < LIMX; a ++){
			for(byte b = 0; b < LIMY; b ++){
				System.out.print(pattern[a][b]);
			}
			System.out.println();
		}
		System.out.println("Enter anything to view the numbers. (HINT: ROW 11 HAS THE MOST ASTERISKS!). Enter 'end' to exit.");
		choice = br.readLine();
		if (!choice.equals("end"))
			randomNumbers();	//reduces one loop in the main method (nested method)
		else {
			System.out.println("ByeBye!");
			choice.equals (END);
		}
		return choice;
	}	//end displayBlob method
	
	/** Functional randomNumbers method
	 * Returns a 2D string array containing 500 numbers in randomized order.
	 * <p>
	 * This method first fills an integer array with numbers from 0 to 499. The numbers are then
	 * added to an array list so that the numbers can be randomly shuffled into a randomized order.
	 * The numbers are then assigned to fill a 2D string array with the use of formatting.
	 * 
	 * Let c represent the counter used for filling in the array
	 * 
	 * @param none
	 * @return strRandom - the 2D string array of randomized numbers (String[][])
	 */
	public String[][] randomNumbers () {
		int c = 0;
		NumberFormat df = new DecimalFormat ("000");
		List<Integer> randomNum = new ArrayList<Integer>();		//creates and instantiates an array list of integers
		int [][]random = new int [LIMX][LIMY];
		String [][] strRandom = new String [LIMX][LIMY];
		while (c < 500 ){
			for (byte a = 0; a <LIMX; a ++){
				for (byte b = 0; b <LIMY; b ++){
					random[a][b] = c;
					randomNum.add(new Integer(c));
					c++;
				}
			}
		}
		Collections.shuffle(randomNum);	//puts all numbers in the list into random order
		c = 0;
		while (c < 500){
			for (byte a = 0; a <LIMX; a ++){
				for (byte b = 0; b <LIMY; b ++){
					random [a][b] = randomNum.get(c);
					strRandom[a][b] = df.format(random[a][b]);
					c++;
				}
			}
		}
		return strRandom;
	}	//end randomNumbers method

	/** Functional displayNumbers method
	 * Returns the option chosen by the user in integer type.
	 * <p>
	 * This method outputs the array containing 500 numbers to the user using a pop-up confirmation dialog box using swing.
	 * The numbers are displayed in a text area and the user must choose OK or CANCEL.
	 * 
	 * Let result represent the user choice to continue or exit the program (int)
	 * 
	 * @param strRandom - string array containing the list of 500 numbers (String [][])
	 * @return result - the result the user decides in integer type for selection in the main method
	 */
	public int displayNumbers (String [][] strRandom){
		JTextArea textArea = new JTextArea(LIMX, LIMY);	//creates and instantiates a text area using swing
		textArea.setEditable(false);	//disables ability to edit text area
		textArea.setBackground (new Color (135, 206, 250));	//sets background colour of the text area
		textArea.setFont (new Font ("Berlin Sans", Font.BOLD, 15));	//sets font type, style, and size of the text area
		for (byte a = 0; a <LIMX; a ++){
			for (byte b = 0; b <LIMY; b ++){
				textArea.append(strRandom[a][b]+"   ");		//writes to the text area
			}
			textArea.append("\n");
		}
		int result = JOptionPane.showConfirmDialog(null, textArea, "Click 'OK' when ready. Choose 'CANCEL' to exit.", JOptionPane.OK_CANCEL_OPTION);	//pop-up window with options OK and CANCEL
		return result;
	}	//end displayNumbers method

	/** Functional locationInput method
	 * Returns the number input that the user memorized from the list of numbers.
	 * <p>
	 * This method prompts the user to enter a number of which they remembered when displayed the list of numbers.
	 * The input is then assigned to an integer type variable using BufferedReader
	 * 
	 * Let loc represent the number inputed by the user to represent the location they have chosen (int)
	 * 
	 * @param none
	 * @return loc - the number that the user inputs (int)
	 * @throws IOException
	 */
	public int locationInput () throws IOException {
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		int loc;
		System.out.println ("I hope you remembered the right location!");
		System.out.println ("Please enter the number that represents the location you have chosen.");
		while (true) {
			try {
				loc = Integer.parseInt(br.readLine());
				break;
			}
			catch (NumberFormatException e){
				System.out.println("Invalid Input!");
				System.out.println ("Please enter the number that represents the location you have chosen.");
			}
		}
		return loc;
	}	//end locationInput method

	/** Functional testLocation method
	 * Returns a boolean type variable of whether the location the user chose contains an asterisk or not.
	 * <p>
	 * This method searches through the array of random numbers until a match is found with the numbers entered
	 * by the user. The location is then tested to see if it contains an asterisk. If all conditions are met,
	 * the boolean returns true.
	 * 
	 * Let test represent if the location entered by the user is valid and contains a *
	 * 
	 * @param loc - number inputed by the user after memorization (int)
	 * @param strRandom - string array containing the list of 500 numbers (String[][])
	 * @param pattern - 2D character array containing the blobs of asterisks (char[][])
	 * @return test - the result of whether the location is valid or not (boolean)
	 */
	public boolean testLocation (int loc, String [][] strRandom, char[][]pattern){
		boolean test = false;
		for (byte a = 0; a < LIMX; a ++){
			for (byte b = 0; b < LIMY; b ++){
				if (loc == Integer.parseInt(strRandom[a][b]) && pattern[a][b]==42){	// 42 = '*'
					test = true;
					break;
				}
				else
					test = false;
			}
			if (test == true)
				break;
		}
		return test;
	}	//end testLocation method

	/** Functional findX method
	 * Returns a byte type variable of the row value of the blobs.
	 * <p>
	 * This method searches through the array of randomized numbers to find a match to the
	 * number inputed by the user. The row value of the location of that numbered is then assigned
	 * to the variable representing the row value.
	 * 
	 * Let x represent the row number of the array (byte)
	 * 
	 * @param loc - number inputed by the user after memorization (int)
	 * @param strRandom - string array containing the list of 500 numbers (String[][])
	 * @return x - the row value of the blobs of asterisks (byte)
	 */
	public byte findX (int loc, String [][] strRandom){
		byte x = 0;
		for (byte a = 0; a < LIMX; a ++){
			for (byte b = 0; b < LIMY; b ++){
				if (loc == Integer.parseInt(strRandom[a][b]))
					x = a;
			}
		}
		return x;
	}	//end findX method

	/** Functional findY method
	 * Returns a byte type variable of the column value of the blobs.
	 * <p>
	 * This method searches through the array of randomized numbers to find a match to the
	 * number inputed by the user. The column value of the location of that numbered is then assigned
	 * to the variable representing the column value.
	 * 
	 * Let y represent the column number of the array (byte)
	 * 
	 * @param loc - number inputed by the user after memorization (int)
	 * @param strRandom - string array containing the list of 500 numbers (String[][])
	 * @return y - the column value of the blobs of asterisks (byte)
	 */
	public byte findY (int loc, String [][] strRandom){
		byte y = 0;
		for (byte a = 0; a < LIMX; a ++){
			for (byte b = 0; b < LIMY; b ++){
				if (loc == Integer.parseInt(strRandom[a][b]))
					y = b;
			}
		}
		return y;
	}	//end findY method
	
	/** Functional eraseBlob method
	 * Returns a the 2D character array containing the new image of the blobs of asterisks.
	 * <p>
	 * This method uses recursion to erase a blob of asterisks. If the location it is currently 
	 * testing contains an asterisk, the method searches for coordinates around its current
	 * location recursively until an entire blob is erased.
	 * 
	 * @param x - the row number of the coordinate (int)
	 * @param y - the column number of the coordinate (int)
	 * @param pattern - the 2D character array containing the blobs of asterisks (char[][])
	 * @return pattern - new array of blobs of asterisks after a blob is erased (char[][])
	 */
	public char [][] eraseBlob (int x, int y, char[][]pattern){
		if (pattern[x][y] == 42){	// 42 = '*'
			pattern[x][y] = 32;		// 32 = ' '
			eraseBlob(x,y+1,pattern);
			eraseBlob(x,y-1,pattern);
			eraseBlob(x+1,y,pattern);
			eraseBlob(x-1,y,pattern);
		}
		return pattern;
	}	//end eraseBlob method

	/** Functional finalBlob method
	 * Returns the array of blobs of asterisks.
	 * <p>
	 * This method outputs the new blobs of asterisks after one blob has been erased.
	 * 
	 * @param pattern - 2D character array containing the blobs of asterisks (char[][])
	 * @return pattern - the new image of blobs of asterisks in an array (char[][])
	 */
	public char[][] finalBlob (char [][] pattern){
		for (int a = 0; a < LIMX; a ++){
			for(int b = 0; b < LIMY; b ++){
				System.out.print(pattern[a][b]);
			}
			System.out.println();
		}
		return pattern;
	}	//end finalBlob method
	
	/** Functional playAgain method
	 * Returns a string object that is received from the user input to determine whether the user would like to
	 * play again or exit.
	 * <p>
	 * This method prompts the user to enter their choice to play the game again or exit the game.
	 * 
	 * Let choice represent the user input to continue or exit (String)
	 * 
	 * @param none
	 * @return choice - the choice the user inputs (String)
	 * @throws IOException
	 */
	public String playAgain () throws IOException {
		String choice;
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		System.out.println ("Amazing Job! If you would like to play again, press any key to begin.");
		System.out.println ("You may also enter 'end' to exit.");
		choice = br.readLine();
		return choice;
	}	//end playAgain method
	
}	//end Blob class
