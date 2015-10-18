//****************************************************************************************
//Maze Class (Assignment 1)
//Kelly Guo
//Date: October 15, 2012
//Java, Eclipse 3.4
//****************************************************************************************
//<Problem Definition>
//		This class displays a maze to the user and allows the user to enter a point of which they would like to 
//		start the maze at. The solution is then displayed from the coordinate entered by the user to the exit.
//<Input>
//		- continue or exit the program
//		- x coordinate of the location to begin
//		- y coordinate of the location to begin
//<Output>
//		- original image of the maze
//		- solution from given point to the exit on the original image of the maze
//<List of Identifiers>
//		choice - accepts the input of the user to determine whether the user wishes to continue with the program or not (String)
//		END - constant to terminate the program when it is entered by user (final String)
//		x - the row number of the coordinate to start the maze (byte)
//		y - the column number of the coordinate to start the maze (byte)
//		mazeTest - array that holds the maze with/without solution (char[][])
//		test - determines whether a point on the maze is part of the solution (boolean)
//		valid - determines whether a point chosen by the user is a valid point on the maze (boolean)
//let LIMX = constant used to represent the number of rows of char[][]mazeTest <type byte>
//let LIMY = constant used to represent the number of columns of char[][]mazeTest <type byte>
//****************************************************************************************

import java.io.*;

public class Maze {

	/**Procedural main method
	 * This method calls other non-static methods to assign variables and passes variables as arguments.
	 * <p>
	 * Call choice method to determine to continue or exit the program
	 * Call inputFileArray method to input the image into an array
	 * Call displayMaze method to output the maze to the user
	 * Call inputX method to receive the row value of the array
	 * Call inputY method to receive the column value of the array
	 * call validLocation method to test if the location entered is valid
	 * Call tryMaze method to find the solution to the maze
	 * Call output method to output the maze with the solution
	 * Call playAgain method to play again
	 * 
	 * @param args
	 * @return void
	 * @throws IOException
	 */

	final byte LIMX = 7;
	final byte LIMY = 11;

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		Maze m1 = new Maze ();
		String choice;
		final String END = "end";
		byte x, y;
		char mazeTest [][] = new char [m1.LIMX][m1.LIMY];
		boolean test = false, valid = false;
		choice = m1.choice();
		while (!choice.equals(END)){
			mazeTest = m1.inputFileArray();
			m1.displayMaze(mazeTest);
			x = m1.inputX();
			y = m1.inputY();
			valid = m1.validLocation(x, y, mazeTest);
			while (valid == false){
				System.out.println("The location you entered is invalid. Please enter a new one.");
				x = m1.inputX();
				y = m1.inputY();
				valid = m1.validLocation(x, y, mazeTest);
			}
			test = m1.tryMaze(x, y, mazeTest, test);
			m1.output(mazeTest);
			choice = m1.playAgain();
		}
		System.out.println("ByeBye!");
	} //ends main method

	/** Functional choice method
	 * Returns a string object that is received from the user input to determine whether the user would like to
	 * continue on with the program or not.
	 * <p>
	 * This method provides the user with a brief explanation of the program and asks for an option to continue or
	 * to exit.
	 * 
	 * Let choice represent the user input to continue or exit (String)
	 * 
	 * @param none
	 * @return choice - the choice the user inputs (String)
	 * @throws IOException
	 */
	public String choice () throws IOException {
		String choice;
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		System.out.println("Welcome to the Amazing Maze Solver! Here, you will be shown a path from any point on the maze to");
		System.out.println("the exit of the maze. If you would like to continue, enter anything. You may also enter 'end' to exit.");
		choice = br.readLine();
		return choice;
	} 	//end choice method

	/** Functional inputFileArray method
	 * Returns a 2D character array of the original maze read from a file.
	 * <p>
	 * This method creates and instantiates a character array that is then filled by file input with object input.
	 * The file is read by creating and instantiating a BufferedReader and uses it to read the file.
	 * The entire file is then returned in the type of a character array.
	 * 
	 * Let line represent the line of input from the file (String)
	 * Let c represent the counter used when filling the array with file input (byte)
	 * let mazeTest represent the image of the maze (char[][])
	 * 
	 * @param none
	 * @return mazeTest - the character array of the maze (char [][])
	 * @throws IOException
	 */
	public char[][] inputFileArray () throws IOException{
		BufferedReader input = new BufferedReader (new FileReader ("E:/Computer 12/KellyGuoAssign1/Maze.txt"));
		String line;
		byte c = 1;
		char mazeTest [][] = new char [LIMX][LIMY];
		line = input.readLine ();  
		for (byte a = 0; a < LIMY; a ++){
			mazeTest[0][a]= line.charAt(a);
		}
		while (c < LIMX ) { 	   
			line = input.readLine (); 
			for (byte a = 0; a < LIMY; a++){
				mazeTest[c][a]=line.charAt(a);
			}
			c++;
		}
		return mazeTest;
	}	//end inputFileArray method

	/** Procedural displayMaze method
	 *This method outputs the 2D array that holds the maze along with the row and column numbers.
	 * 
	 * @param mazeTest - 2D character array of the maze (char[][])
	 * @return void
	 */
	public void displayMaze (char[][]mazeTest){
		System.out.println("  0 2 4 6 8 10");
		for (byte a = 0; a < LIMX; a ++){
			System.out.print(a+" ");
			for (byte b = 0; b < LIMY; b ++){
				System.out.print (mazeTest[a][b]);
			}
			System.out.println();
		}
	}	//end displayMaze method

	/** Functional inputX method
	 * Returns a byte type variable from receiving user input.
	 * <p>
	 * This method prompts the user to enter the row number of which they would like to start the maze at.
	 * It creates and instantiates BufferedReader to receive user input.
	 * The method uses try/catch to prevent the program from crashing from invalid input.
	 * 
	 * Let x represent the row number entered by the user (byte)
	 * 
	 * @param none
	 * @return x - the row value the user inputs (byte)
	 * @throws IOException
	 */
	public byte inputX () throws IOException {
		byte x;
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		System.out.println("Please enter the row number of the coordinate you would like to begin the maze at:"); 
		while (true){
			try{
				x = Byte.parseByte(br.readLine());
				break;
			}
			catch (NumberFormatException e){
				System.out.println("Invalid Input!");
				System.out.println("Please enter the row number of the coordinate you would like to begin the maze at:"); 
			}
		}
		return x;
	}	//ends inputX method

	/** Functional inputY method
	 * Returns a byte type variable from receiving user input.
	 * <p>
	 * This method prompts the user to enter the column number of which they would like to start the maze at.
	 * It creates and instantiates BufferedReader to receive user input.
	 * The method uses try/catch to prevent the program from crashing from invalid input.
	 * 
	 * Let y represent the column number entered by the user (byte)
	 * 
	 * @param none
	 * @return y - the column value the user inputs (byte)
	 * @throws IOException
	 */
	public byte inputY () throws IOException {
		byte y;
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		System.out.println("Please enter the column number of the coordinate you would like to begin the maze at:"); 
		while (true){
			try{
				y = Byte.parseByte(br.readLine());
				break;
			}
			catch (NumberFormatException e){
				System.out.println("Invalid Input!");
				System.out.println("Please enter the column number of the coordinate you would like to begin the maze at:"); 
			}
		}
		return y;
	}	//ends inputY method

	/** Functional validLocation method
	 * Returns a boolean type variable to determine whether the input is a valid location on the maze.
	 * <p>
	 * This method tests to see if the coordinate entered by the user is outside of the maze boundaries
	 * or if it is on the walls of the maze.
	 * 
	 * Let valid represent if the location is valid or not (boolean)
	 * 
	 * @param x - the row number of the coordinate (byte)
	 * @param y - the column number of the coordinate (byte)
	 * @param mazeTest - the maze in a 2D array (char[][])
	 * @return valid - the result of if the location is valid or not (boolean)
	 */
	public boolean validLocation (byte x, byte y, char[][]mazeTest){
		boolean valid = false;
		if (x < LIMX && x >= 0 && y >= 0 && y < LIMY && mazeTest[x][y] !=66)	//66 = 'B'
			valid = true;
		else
			valid = false;
		return valid;
	}	//ends validLocation method

	/** Functional tryMaze method
	 * Returns a boolean type variable to determine the path to the exit of the maze.
	 * <p>
	 * This method uses recursion to determine whether each coordinate on the maze is part of the 
	 * solution of the maze to the exit or not. The method contains base case of if the point being
	 * tested is not a space. If a space is found, recursion is used to look at points around.
	 * 
	 * @param x - the row number of the coordinate (int)
	 * @param y - the column number of the coordinate (int)
	 * @param mazeTest - the maze in a 2D array (char[][])
	 * @param test - to determine whether a location is part of the solution of not (boolean)
	 * @return test - result of true or false to determine if the point is part of the solution (boolean)
	 */
	public boolean tryMaze (int x, int y, char[][]mazeTest, boolean test){
		if (mazeTest[x][y]==32){		//32 = ' ' 
			mazeTest[x][y]=46;			//46 = '.'
			test = tryMaze(x, y-1, mazeTest, test);
			if (test == false)
				test = tryMaze(x+1, y, mazeTest, test);
			if (test == false)
				test = tryMaze(x, y+1, mazeTest, test);
			if (test == false)
				test = tryMaze(x-1, y, mazeTest, test);
		}
		else if (mazeTest[x][y] == 88)		//88 = 'X'
			test = true;
		else if (mazeTest[x][y] == 66 )		//66 = 'B',	46 = '.'
			test = false;
		if (test == true && mazeTest[x][y] != 88)		//88 = 'X'    
			mazeTest[x][y] = 46;		//46 = '.'
		else if (test == false && mazeTest[x][y] != 66)	//66 = 'B'
			mazeTest[x][y] = 42;	//42 = '*'
		return test;
	}	//ends tryMaze method

	/** Functional output method
	 * Returns the array of maze with solution.
	 * <p>
	 * This method outputs the path of the maze with the original maze.
	 * 
	 * @param mazeTest - the 2D character array of the maze and solution (char[][])
	 * @param test - determines which points are part of the solution to the maze (boolean)
	 * @return mazeTest - the maze and solution in an array (char[][])
	 */
	public char[][] output (char[][]mazeTest){
		for (byte a = 0; a < 7; a ++){
			for(byte b = 0; b < 11; b ++){
				if (mazeTest[a][b] == 42)	// 42 = '*'
					mazeTest[a][b] = 32;	// 32 = ' '
				System.out.print(mazeTest[a][b]);
			}
			System.out.println();

		}
		return mazeTest;
	}	//ends output method

	/** Functional playAgain method
	 * Returns a string object that is received from the user input to determine whether the user would like to
	 * play again or exit.
	 * <p>
	 * This method prompts the user to enter their choice to watch the solution again or to exit the program.
	 * 
	 * Let choice represent the user input of whether to exit or not (String)
	 * 
	 * @param none
	 * @return choice - the choice the user inputs to continue or exit (String)
	 * @throws IOException
	 */
	public String playAgain ()throws IOException{
		String choice;
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		System.out.println ("I hope you enjoyed watching the solution to the maze. Press any key to watch it again.");
		System.out.println ("You may also enter 'end' to exit.");
		choice = br.readLine();
		return choice;
	}	//ends playAgain method

}	//ends Maze Class