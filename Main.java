// Wayne Pinnock, CS 212, Spring Semester 2023
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.lang.Math;

// Steps Class
class Steps {
  int steps = 0;
}

// Main Class
public class Main {
  public static void main(String[] args) {
    
    // Get number of rows and columns from user
    int rows = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of rows:"));
    int cols = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of columns:"));
    Steps steps = new Steps();

    // Create a grid of buttons with random arrows and one target symbol
    JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
    Random random = new Random();
    int targetRow = (int)(Math.random() * rows);
    int targetCol = (int)(Math.random() * cols);
    JButton[][] grid = new JButton[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid[i][j] = new JButton();
        if (i == targetRow && j == targetCol) {
          grid[i][j].setText("\u25CE"); // Target symbol
          grid[i][j].setForeground(Color.RED);
        } else {
          int arrowCode = 8592 + (int)(Math.random() * 4); // Random arrow symbol
          grid[i][j].setText(Character.toString((char) arrowCode));
          grid[i][j].setForeground(Color.BLACK);
        }
        grid[i][j].setFont(new Font("Arial", Font.PLAIN, 24));
        grid[i][j].addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int row = -1, col = -1;
            // Find the row and column of the button
            for (int i = 0; i < rows; i++) {
              for (int j = 0; j < cols; j++) {
                if (button == grid[i][j]) {
                  row = i;
                  col = j;
                  break;
                }
              }
              if (row != -1)
                break;
            }
            char arrow = button.getText().charAt(0);
            int newRow = row, newCol = col;
            switch (arrow) {
              case '\u2190': // Left arrow
                newCol--;
                steps.steps++;
                break;
              case '\u2191': // Up arrow
                newRow--;
                steps.steps++;
                break;
              case '\u2192': // Right arrow
                newCol++;
                steps.steps++;
                break;
              case '\u2193': // Down arrow
                newRow++;
                steps.steps++;
                break;
            }
            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
              // The next button is out of the grid, therefore turn the button red
              button.setBackground(Color.RED);
              JOptionPane.showMessageDialog(null, "You left the grid!");
            } else if (grid[newRow][newCol].getText().equals("\u25CE")) {
              // The next buttonn is the target symbol, therefore turn the button green
              button.setBackground(Color.YELLOW);
              grid[newRow][newCol].setBackground(Color.GREEN);
              JOptionPane.showMessageDialog(null, "You finished in " + steps.steps + " steps.");
            } else if (grid[newRow][newCol].getBackground().equals(Color.YELLOW)) {
              // The new position is intersecting a previous position, therefore turn the last button to be red
              button.setBackground(Color.RED);
              JOptionPane.showMessageDialog(null, "Your path inersected itself!");
            } else {
              // Move to the next button and turn the previous button to be yellow
              button.setBackground(Color.YELLOW);
              grid[newRow][newCol].doClick();
            }
          }
        });
        gridPanel.add(grid[i][j]);
      }
    }
    // Create and show the window containing the grid of buttons
    JFrame window = new JFrame("Grid Button Program");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.getContentPane().add(gridPanel, BorderLayout.CENTER);
    window.pack();
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }
}