package sudokuGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * WE DECLARE THAT THIS CODE IS OUR OWN WRITTEN CODE AND THAT WE,
 * BRITTANY PRICE AND DENNIS QIU, DID NOT VIOLATE THE UNCW ACADEMIC HONOR CODE 
 * WHILE COMPLETING THIS PROJECT.
 * This class models a group of cells in a SudokuGame board grid.
 * @author BrittanyPrice, DennisQiu
 */
public class SudokuCells extends JTextField { 
	
	/**
	 * Serial Version Unique ID 1L of public class Cells extends JTextField.
	 */
	private static final long serialVersionUID = 1L;
	
	private static Font originalFont, newFont;
	private int cellValue;
	private boolean uniqueCells;
	private Point position;
	
	/**
	 * Creates a text field and sets it to empty, or null, within the SudokuGame board.
	 */
	public static JTextField text = null;
	
	/**
	 * Applies the font to all text on the SudokuGame grid board.
	 */
	static {
		text = new JTextField(); 
		Font font = text.getFont();	
		
		originalFont = font.deriveFont(font.getStyle(), (int) (font.getSize2D() * 2.0));
		newFont = originalFont.deriveFont(originalFont.getStyle() ^ Font.BOLD, 
				(int) (font.getSize2D() * 2.0));	
			
	}
	
	/**
	 * Creates each object of cells with each own X and Y position on the SudokuGame board.
	 * @param x the X-Coordinate position of the individual cell
	 * @param y the Y-Coordinate position of the individual cell
	 */
	public SudokuCells(int x, int y) {
		super(" ");
		position = new Point(x, y);
		
		setHorizontalAlignment(SwingConstants.CENTER);
		setOpaque(true);
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFont(newFont);
	}

	/**
	 * Gets the integer value of the cell.
	 * @return The integer value of the cell.
	 */
	public int getCellValue() {
		return cellValue;
	}

	/**
	 * Gets the position of the cell.
	 * @return The position of the cell.
	 */
	public Point getPosition() {
		return position;
	}
	
	/**
	 * Determines whether or not the cell is empty or has a number within.
	 * @return True if contains a number, otherwise false.
	 */
	public boolean isUniqueCells() {
		return uniqueCells;
	}
	
	/**
	 * Resets the value of the cell and determines the position of the numbers and empty spaces.
	 * Sets a particular font depending if the cell contains a number or not.
	 * Numbers from the original puzzle do not change, or cannot be edited. Shown with a pink background.
	 * @param value the integer value of the cell.
	 * @param unique true if contains a number, otherwise false.
	 */
	private void reset(int value, boolean unique) {		
		cellValue = value;
		
		if(cellValue == 0) {
			uniqueCells = false;
			setText(" ");
			setFont(newFont);
			return;
		}
		setForeground(null);

		uniqueCells = unique;
		setText("  " + Integer.toString(value, Character.MAX_RADIX).toUpperCase() + "  ");	
		
		if (uniqueCells) {
			setFont(originalFont);
			setEnabled(false);
			setBackground(Color.MAGENTA);
		}
	}
	
	/**
	 * Sets the value of the cell to false, as an empty cell.
	 * @param value the integer value of the cell
	 */
	public void setCellValue(int value) {
		reset(value, false);
	}
	
	/**
	 * Sets the value of the cell to true, as a cell containing a number.
	 * @param value the integer value of the cell
	 */
	public void setUniqueCells(int value) {
		reset(value, true);
	}
}

