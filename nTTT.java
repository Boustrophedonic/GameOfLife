import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class nTTT implements ActionListener {
	char[][] board;
	JLabel infoLabel, errorLabel;
	Font mainFont = new Font("Arial", Font.PLAIN, 18);
	JButton[][] buttonGrid;
	char currentPlayer = 'X';

	public nTTT() {
		initializeBoard();
		initializeGUI();

		/*
		 * Prefilled for testing board[1][0] = 'O'; board[2][0] = 'O'; board[3][0] =
		 * 'O'; board[3][1] = 'O'; board[3][2] = 'O'; board[2][1] = 'O'; board[1][2] =
		 * 'O';
		 */

		infoLabel.setText(currentPlayer + "'s turn!");
	}

	public void actionPerformed(ActionEvent e) {
		int row = 0, col = 0;

		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[0].length; c++)
				if (buttonGrid[r][c] == e.getSource()) {
					row = r;
					col = c;
				}

		if (board[row][col] == '_') {
			board[row][col] = currentPlayer;
			if (currentPlayer == 'X') {
				currentPlayer = 'O';
			} else {
				currentPlayer = 'X';
			}
		} else {
			errorLabel.setText("Invalid move. Try again!");
		}

		if (!checkGameOver(board)) {
			infoLabel.setText(currentPlayer + "'s turn!");
		} else {
			for (int r = 0; r < board.length; r++) {
				for (int c = 0; c < board[0].length; c++) {
					buttonGrid[r][c].setEnabled(false);
				}
			}
		}

		printBoard();
	}

	private void initializeGUI() {
		buttonGrid = new JButton[board.length][board.length];
		JFrame window = new JFrame("Tic Tac Toe");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int dim = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.8);
		window.setSize(dim, dim);

		infoLabel = new JLabel("Info", SwingConstants.CENTER);
		errorLabel = new JLabel("", SwingConstants.CENTER);
		errorLabel.setForeground(Color.RED);
		infoLabel.setFont(mainFont);
		errorLabel.setFont(mainFont);

		JPanel mainPanel = new JPanel(), buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(board.length, board.length));

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				buttonGrid[r][c] = new JButton(board[r][c] + "");
				buttonGrid[r][c].setFont(mainFont);
				buttonPanel.add(buttonGrid[r][c]);
				buttonGrid[r][c].addActionListener(this);
				buttonGrid[r][c].setBackground(Color.WHITE);
			}
		}

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(infoLabel, BorderLayout.NORTH);
		mainPanel.add(errorLabel, BorderLayout.SOUTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);

		window.setBackground(Color.WHITE);

		window.add(mainPanel);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	private void printBoard() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				buttonGrid[r][c].setText(board[r][c] + "");
			}
		}
	}

	private void initializeBoard() {
		JLabel label = new JLabel("What size would you like for the game board?");
		label.setFont(mainFont);
		String s = (String) (JOptionPane.showInputDialog(null, label, "Size Input", JOptionPane.QUESTION_MESSAGE, null,
				null, "3"));
		// String s = (String)JOptionPane.showInputDialog(label,"3");
		int n = Integer.parseInt(s);

		board = new char[n][n];
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				board[r][c] = '_';
			}
		}
	}

	private boolean checkGameOver(char[][] b) {
		int n = b.length;
		char[] masterRowX = new char[n], masterRowO = new char[n];

		for (int r = 0; r < n; r++) {
			masterRowX[r] = 'X';
			masterRowO[r] = 'O';
		}

		// horizontal
		for (int r = 0; r < n; r++) {
			if (Arrays.equals(b[r], masterRowX) || Arrays.equals(b[r], masterRowO)) {
				infoLabel.setText(b[r][0] + " wins! (horizontal)");
				return true;
			}
		}

		// diagonals
		if (b[0][0] != '_') {
			boolean done = true;
			for (int i = 0; i < n - 1; i++) {
				if (b[i][i] != b[i + 1][i + 1]) {
					done = false;
					break;
				}
			}
			if (done) {
				infoLabel.setText(b[0][0] + " wins! (diagonal)");
				return true;
			}
		}

		if (b[n - 1][0] != '_') {
			boolean done = true;
			for (int i = 0; i < n - 1; i++) {
				if (b[n - 1 - i][i] != b[n - 1 - (i + 1)][i + 1]) {
					done = false;
					break;
				}
			}
			if (done) {
				infoLabel.setText(b[n - 1][0] + " wins! (diagonal)");
				return true;
			}
		}

		// verticals
		for (int c = 0; c < n; c++) {
			if (b[0][c] != '_') {
				boolean done = true;
				for (int r = 1; r < n; r++)
					if (b[r][c] != b[0][c])
						done = false;

				if (done) {
					infoLabel.setText(b[0][c] + " wins! (vertical)");
					return true;
				}
			}
		}

		int count = 0;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				if (board[r][c] != '_') {
					count++;
				}
			}
		}

		if (count == n * n) {
			infoLabel.setText("It's a draw!");
			return true;
		}

		return false;
	}
	
	public static void main(String[] args) {
		new nTTT();
	}
}