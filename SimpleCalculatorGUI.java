import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculatorGUI extends JFrame {
    private JTextField display;
    private double currentInput;
    private String lastOperation;

    public SimpleCalculatorGUI() {
        // Set up the JFrame
        super("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());

        // Create the display
        display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Create the number buttons and operations buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Display the calculator
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            // Handle digit buttons
            if (Character.isDigit(buttonText.charAt(0)) || buttonText.equals(".")) {
                display.setText(display.getText() + buttonText);
            }
            // Handle operation buttons
            else if (isOperation(buttonText)) {
                performOperation();
                lastOperation = buttonText;
                currentInput = Double.parseDouble(display.getText());
                display.setText("");
            }
            // Handle equals button
            else if (buttonText.equals("=")) {
                performOperation();
                lastOperation = null;
            }
        }

        private void performOperation() {
            if (lastOperation != null) {
                double operand = Double.parseDouble(display.getText());

                switch (lastOperation) {
                    case "+":
                        currentInput += operand;
                        break;
                    case "-":
                        currentInput -= operand;
                        break;
                    case "*":
                        currentInput *= operand;
                        break;
                    case "/":
                        if (operand != 0) {
                            currentInput /= operand;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                }

                display.setText(String.valueOf(currentInput));
            }
        }

        private boolean isOperation(String s) {
            return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleCalculatorGUI());
    }
}
