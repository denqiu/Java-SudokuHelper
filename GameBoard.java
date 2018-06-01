package sudoku;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * WE DECLARE THAT THIS CODE IS OUR OWN WRITTEN CODE AND THAT WE,
 * BRITTANY PRICE AND DENNIS QIU, DID NOT VIOLATE THE UNCW ACADEMIC HONOR CODE 
 * WHILE COMPLETING THIS PROJECT.
 * This class models a game board in a SudokuGame.
 * @author BrittanyPrice
 * @author DennisQiu
 */
public class GameBoard extends JFrame { //Dennis and Brittany
	
	/**
	 * Serial Version Unique ID 1L of public class GameBoard extends JFrame.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * An array of nine groups of Blocks.
	 */
	public static Blocks[] blocks = new Blocks[9];
	
	/**
	 * A 2D-Array of nine groups of nine Cells.
	 */
	public static Cells[][] allCells = new Cells[9][9];
	
	/**
	 * The label with the string name "PUZZLE: ".
	 */
	public static JLabel puzzleName; 
	
	/**
	 * The label that shows the number of the puzzle being selected.
	 */
	public static JLabel puzzleNumber;
	
	/**
	 * The label with the string name " HINTS: ".
	 */
	public static JLabel hints;
	
	/**
	 * The label that displays the numbers 1-9 that does not go against the SudokuRules.
	 */
	public static JLabel hintsLabel;
	
	/**
	 * The container that gets the layer to hold objects, sets the layout, and adds the blocks component.
	 */
	public static Container c;

	private JMenuBar menuBar;
	private JMenu fileMenu;	

	/**
	 * Calls the game board constructor to play the SudokuGame.
	 * @param args none
	 */
	public static void main(String [] args) {
		new GameBoard();
	}
	
	/**
	 * Creates a SudokuGame board containing all the blocks and cells in imitation 
	 * of a SudokuBoard. 
	 */
	public GameBoard() {
		setTitle("Sudoku Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		setLocation(500, 30);

	    c = getContentPane();
		c.setLayout(new GridLayout(3, 3));
		
		puzzleName = new JLabel("PUZZLE: ");
		puzzleNumber = new JLabel(" ");
		hintsLabel = new JLabel(" HINTS: ");
		hints = new JLabel(" ");
		
		GamePlay playSudoku = new GamePlay();
		
		int xPosition;
		int yPosition;
		for (int i = 0; i < 9; i++) {
			blocks[i] = new Blocks(i);
			for (Cells cells : blocks[i].getCellsInBlock()) {
				xPosition = (int) cells.getPosition().getX();
				yPosition = (int) cells.getPosition().getY();
				
				cells.addMouseListener(playSudoku);
				cells.addKeyListener(playSudoku);
				cells.addFocusListener(playSudoku);
				
				allCells[xPosition][yPosition] = cells;
			}
			c.add(blocks[i]);
		}
		buildMenu();
		pack();
		setVisible(true);
	}
	
	/**
	 * Builds the menu of the SudokuGameBoard. 
	 * Displays the puzzle options: New, Read, Save, and Exit.
	 * Displays the number of the selected puzzle.
	 * Displays the numbers from 1-9 that are not against the rules of the SudokuGame. 
	 */
	public void buildMenu() {	
        GamePlay gamePlay = new GamePlay();

		menuBar = new JMenuBar();
		fileMenu = new JMenu("Puzzle");
		
		JMenuItem menuItem = new JMenuItem("New");
		fileMenu.add(menuItem);
		menuItem.addActionListener(gamePlay);
		
	    menuItem = new JMenuItem("Read");
		fileMenu.add(menuItem);
		menuItem.addActionListener(gamePlay);

		menuItem = new JMenuItem("Save");
		fileMenu.add(menuItem);
		menuItem.addActionListener(gamePlay);

		menuItem = new JMenuItem("Exit");
		fileMenu.add(menuItem);
		menuItem.addActionListener(gamePlay);

		menuBar.add(fileMenu);
		menuBar.add(puzzleName);
		menuBar.add(puzzleNumber);
		menuBar.add(hintsLabel);
		menuBar.add(hints);

		setJMenuBar(menuBar);
	}
	
	/**
	 * Opens a selected puzzle text file and copy the SudokuPuzzle problem to the game board.
	 * @param file the selected puzzle text file being read or opened
	 */
	public static void readPuzzle(File file) {
		String fileName = file.getName();
		System.out.println("Reading file: " + fileName + "\n");
		GamePlay.clearBoard();
        try {
            Scanner readPuzzle = new Scanner(file);
            ArrayList<Integer> lineNumbers = new ArrayList<Integer>(9);
			int row = 0;
			while (readPuzzle.hasNext()) {
				lineNumbers.clear();
				String line = readPuzzle.nextLine();
				String[] data = line.split(",");
				for (String s: data) {
					if (s.length() == 1) {
						if (s.charAt(0) == '0') {
							lineNumbers.add(0);
						} 
						else if (s.charAt(0) == ',') {
							continue;
						} 
						else {
							lineNumbers.add(Integer.valueOf(s));
						}
					}
				}
				int column = 0;
				System.out.println(lineNumbers.toString());
				
				for (int number: lineNumbers) {
					allCells[column][row].setUniqueCells(number);
					column++;
				}
				row++;
			}
			System.out.println();
			readPuzzle.close();
        }
        catch (IOException e) {
                e.printStackTrace();
        }
	}
    
	/**
	 * Saves the selected puzzle text file and save the SudokuPuzzle problem to a puzzle text file.
	 * @param file the selected puzzle file being saved
	 */
	public static void savePuzzle(File file) { 
		System.out.println("\nSaving file: " + file.getName());
		String fileName = file.getAbsolutePath();
		try {
			String line;
			int cellValue = 0;
			FileWriter writingFile = new FileWriter(fileName);
			for (int i = 0; i < 9; i++) {
				line = "";
				for (int j = 0; j < 9; j++) {
					cellValue = allCells[j][i].getCellValue();
					if (j < 8) {
						line += Integer.toString(cellValue) + ",";
					} 
					else {
						line += Integer.toString(cellValue);
					}
				}
				writingFile.write(line + "\n");
			}
			writingFile.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done saving puzzle: " + file.getName());
	}
}
