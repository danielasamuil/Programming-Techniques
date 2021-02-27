package View;
import Model.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import static java.awt.Font.BOLD;

public class View extends JFrame{

    private static Polynomial p1,p2;
    private JLabel p1Label;
    private JLabel p2Label;
    private JLabel resultLabel;
    private JTextField polinomial1;
    private JTextField polinomial2;
    private JTextField result;
    private JButton addition;
    private JButton subtraction;
    private JButton multiplication;
    private JButton division;
    private JButton derivate;
    private JButton integrate;

    public View() {
        p1 = new Polynomial();
        p2 = new Polynomial();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        this.setTitle("Polynomial operations calculator");
        this.setBounds(100, 135, 500, 250);

        p1Label = new JLabel("POLYNOM 1:");
        p1Label.setBounds(10, 9, 75, 20);
        getContentPane().add(p1Label);

        p2Label = new JLabel("POLYNOM 2:");
        p2Label.setBounds(10, 35, 75, 20);
        getContentPane().add(p2Label);

        resultLabel = new JLabel("RESULT:");
        resultLabel.setBounds(10, 63, 75, 20);
        getContentPane().add(resultLabel);

        polinomial1 = new JTextField();
        polinomial1.setBounds(88, 8, 86, 20);
        getContentPane().add(polinomial1);

        polinomial2 = new JTextField();
        polinomial2.setBounds(88, 33, 86, 20);
        getContentPane().add(polinomial2);

        result = new JTextField();
        result.setBounds(88, 60, 86, 20);
        getContentPane().add(result);

        addition = new JButton("Add");
        addition.setBounds(224, 7, 86, 20);
        getContentPane().add(addition);
        addition.setFont(addition.getFont().deriveFont(BOLD));

        subtraction = new JButton("Subtract");
        subtraction.setBounds(224, 32, 86, 20);
        getContentPane().add(subtraction);
        subtraction.setFont(subtraction.getFont().deriveFont(BOLD));

        multiplication = new JButton("Multiply");
        multiplication.setBounds(224, 57, 86, 20);
        getContentPane().add(multiplication);
        multiplication.setFont(multiplication.getFont().deriveFont(BOLD));

        division = new JButton("Divide");
        division.setBounds(224, 82, 86, 20);
        getContentPane().add(division);
        division.setFont(division.getFont().deriveFont(BOLD));

        derivate = new JButton("Derivate");
        derivate.setBounds(224, 107, 86, 20);
        getContentPane().add(derivate);
        derivate.setFont(derivate.getFont().deriveFont(BOLD));

        integrate = new JButton("Integrate");
        integrate.setBounds(224, 132, 86, 20);
        getContentPane().add(integrate);
        integrate.setFont(integrate.getFont().deriveFont(BOLD));

        this.setVisible(true);
    }

    public String pol1GetText() {
        return polinomial1.getText();
    }

    public String pol2GetText() {
        return polinomial2.getText();
    }

    public void rezultatSetText (String x) {
        result.setText(x);
    }

    public void addListenerAddBtn(ActionListener e) {
        addition.addActionListener(e);
    }

    public void addListenerSubtractBtn(ActionListener e) { subtraction.addActionListener(e); }

    public void addListenerMultiplyBtn(ActionListener e) { multiplication.addActionListener(e); }

    public void addListenerDivideBtn(ActionListener e) {
        division.addActionListener(e);
    }

    public void addListenerDerivateBtn(ActionListener e) {
        derivate.addActionListener(e);
    }

    public void addListenerIntegrateBtn(ActionListener e) {
        integrate.addActionListener(e);
    }

    public void pol1ListenerAddBtn(ActionListener e) {
        polinomial1.addActionListener(e);
    }

    public void pol2ListenerAddBtn(ActionListener e) { polinomial2.addActionListener(e); }

    public void showError (String s) { JOptionPane.showMessageDialog(this, s); }
}
