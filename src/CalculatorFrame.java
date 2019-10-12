import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CalculatorFrame extends JFrame {
    public CalculatorFrame() {
        setTitle("Калькулятор");
        setMinimumSize(new Dimension(180,200));
        setMaximumSize(new Dimension(180, 200));
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        add(new CalculatorPanel());
    }

    class CalculatorPanel extends JPanel {

        private JTextField display;
        private JPanel panel;
        private JPanel panelCommand;
        private double result;
        private String lastCommand;
        private boolean start;

        public CalculatorPanel() {

            setLayout(new BorderLayout());
            newStart();

            display = createDisplay();
            add(display, BorderLayout.NORTH);

            ActionListener insert = new InsertAction();
            ActionListener command = new CommandAction();

            panel = new JPanel();
            panel.setLayout(new GridLayout(4, 4));

            panel.add(createButton("7", insert));
            panel.add(createButton("8", insert));
            panel.add(createButton("9", insert));
            panel.add(createButton("/", command));

            panel.add(createButton("4", insert));
            panel.add(createButton("5", insert));
            panel.add(createButton("6", insert));
            panel.add(createButton("*", command));

            panel.add(createButton("1", insert));
            panel.add(createButton("2", insert));
            panel.add(createButton("3", insert));
            panel.add(createButton("-", command));

            panel.add(createButton("0", insert));
            panel.add(createButton(".", insert));
            panel.add(createButton("^", command));
            panel.add(createButton("+", command));

            add(panel, BorderLayout.CENTER);

            panelCommand = new JPanel();
            panelCommand.setLayout(new GridLayout(1, 3));
            panelCommand.add(createButton("=", command));
            panelCommand.add(createButton("<", command));
            panelCommand.add(createButton("C", command));
            add(panelCommand, BorderLayout.SOUTH);
        }

        private void newStart(){
            result = 0;
            lastCommand = "=";
            start = true;
        }

        private JButton createButton(String label, ActionListener listener){
            JButton button = new JButton(label);
            button.addActionListener(listener);
            return button;
        }

        private JTextField createDisplay(){
            JTextField display = new JTextField("0");
            display.setHorizontalAlignment(JLabel.CENTER);
            display.setPreferredSize(new Dimension(100, 35));
            display.setEnabled(false);
            return display;
        }

        private class InsertAction implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                String input = event.getActionCommand();
                if(start) {
                    display.setText("");
                    start = false;
                }
                display.setText(display.getText() + input);
            }
        }

        private class CommandAction implements ActionListener  {
            public void actionPerformed(ActionEvent event) {

                String command = event.getActionCommand();

                if(command.equals("C")){
                    newStart();
                    display.setText("0");
                    return;
                }
                if(command.equals("<")){
                    String text = display.getText();
                    if (text.length() > 1)
                        display.setText(text.substring(0, text.length() - 1));
                    else
                        display.setText("0");
                    return;
                }

                if(start){
                    if(command.equals("-")){
                        display.setText(command);
                        start = false;
                    }
                    else lastCommand = command;
                }
                else {
                    calculate(Double.parseDouble(display.getText()));
                    lastCommand = command;
                    start = true;
                }

            }
        }

        public void calculate(double x){
            switch (lastCommand){
                case "+": result += x; break;
                case "-": result -= x; break;
                case "*": result *= x; break;
                case "/": result /= x; break;
                case "^": result = Math.pow(result, x); break;
                case "=": result = x;
            }
            display.setText("" + result);

        }
    }
}
