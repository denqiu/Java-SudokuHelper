package sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * WE DECLARE THAT THIS CODE IS OUR OWN WRITTEN CODE AND THAT WE,
 * BRITTANY PRICE AND DENNIS QIU, DID NOT VIOLATE THE UNCW ACADEMIC HONOR CODE 
 * WHILE COMPLETING THIS PROJECT.
 * This class models a group of blocks in a SudokuGame board grid.
 * @author BrittanyPrice
 * @author DennisQiu
 */
public class Blocks extends JPanel {
	
	/**
	 * Serial Version Unique ID 1L of public class Blocks extends JPanel.
	 */
	private static final long serialVersionUID = 1L;
	
	private int blockPosition = 0;
	private int x = 0;
	private int y = 0;
	private Cells[] blocks = new Cells[9]; //blocks[0]-blocks[8]
		
	/**
	 * Creates each object of blocks with each own position on the SudokuGame board.
	 * Sets the color format of the game board.
	 * @param blockPosition the position of a block of cells
	 */
	public Blocks(int blockPosition) { //Dennis
		this.blockPosition = blockPosition;
				
		setLayout(new GridLayout(3, 3));
		setPreferredSize(new Dimension(60, 60));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			
		for (int i = 0; i < 9; i++) {
			x = (blockPosition % 3) *3 + i % 3; //column
			y = (blockPosition / 3) *3 + i / 3; //row
			
			blocks[i]= new Cells(x, y);
			for (int j = 0; j < 9; j++)
				if (blockPosition % 2 == 1)
					blocks[i].setBackground(Color.PINK);
			add(blocks[i]);
		}
	}
	
	/**
	 * Gets the position of a block of cells.
	 * @return The position of the block of cells
	 */
	public int getBlockPosition() {
		return blockPosition;
	}
	
	/**
	 * Gets groups of nine cells per block.
	 * @return Blocks on the SudokuGame board, each containing a group of nine cells.
	 */
	public Cells[] getCellsInBlock() {
		return blocks;
	}
	
	/**
	 * Adds the blocks of cells to an array list of cells.
	 * @return The blocks of cells of SudokuGame board.
	 */
	public ArrayList<Cells> getBlocks() {
	    ArrayList<Cells> blocksOfCells = new ArrayList<Cells>(9);
        for (Cells c: blocks) 
        	blocksOfCells.add(c);
        return blocksOfCells; 	
	}
}
