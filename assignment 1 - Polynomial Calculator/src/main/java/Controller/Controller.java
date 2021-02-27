package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.*;
import View.*;

public class Controller {

    View view;
    Polynomial p1;
    Polynomial p2;
    Polynomial rez;

    public Controller (View view) {
        this.view = view;
        rez = new Polynomial();
        view.addListenerAddBtn(new Add());
        view.addListenerSubtractBtn(new Subtract());
        view.addListenerMultiplyBtn(new Multiply());
        view.addListenerDivideBtn(new Divide());
        view.addListenerDerivateBtn(new Derivate());
        view.addListenerIntegrateBtn(new Integrate());
        view.pol1ListenerAddBtn(new Polinom1Listener());
        view.pol2ListenerAddBtn(new Polinom2Listener());
    }

    class Polinom1Listener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            try {
                p1= new Polynomial(view.pol1GetText());
            }catch (NumberFormatException ex) {
                view.showError("Bad input");
            }

        }

    }

    class Polinom2Listener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            try {
                p2= new Polynomial(view.pol2GetText());
            }catch (NumberFormatException ex) {
                view.showError("Bad input");
            }

        }

    }

    class Add implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            try {
                p1 = new Polynomial(view.pol1GetText());
                p2 = new Polynomial(view.pol2GetText());

                System.out.println(p1);
                System.out.println(p2);
                System.out.println();
                rez = p1.addition(p2);
                System.out.println(rez);
                view.rezultatSetText(rez.toString());
                if (rez.getListOfMonomials().isEmpty())
                    throw new NumberFormatException();
            }catch(NumberFormatException ex) {
                view.showError("Bad Input");
            }
            System.out.println();
            System.out.println();
        }

    }

    class Subtract implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            try {
                p1 = new Polynomial(view.pol1GetText());
                p2 = new Polynomial(view.pol2GetText());

                System.out.println(p1);
                System.out.println(p2);
                System.out.println();
                rez = p1.subtraction(p2);
                System.out.println(rez);
                if (rez.getListOfMonomials().isEmpty())
                    throw new NumberFormatException();
                view.rezultatSetText(rez.toString());
            }catch(NumberFormatException ex) {
                view.showError("Bad Input");
            }
            System.out.println();
            System.out.println();
        }

    }

    class Multiply implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            try {
                p1 = new Polynomial(view.pol1GetText());
                p2 = new Polynomial(view.pol2GetText());

                System.out.println(p1);
                System.out.println(p2);
                System.out.println();
                rez = p1.multiplication(p2);
                System.out.println(rez);
                if (rez.getListOfMonomials().isEmpty())
                    throw new NumberFormatException();
                view.rezultatSetText(rez.toString());
            }catch(NumberFormatException ex) {
                view.showError("Bad Input");
            }
            System.out.println();
            System.out.println();
        }

    }

    class Divide implements ActionListener{

        public void actionPerformed(ActionEvent e) {

                p1 = new Polynomial(view.pol1GetText());
                p2 = new Polynomial(view.pol2GetText());

                System.out.println(p1);
                System.out.println(p2);
                System.out.println();
                System.out.println(p1.division(p2));
                view.rezultatSetText(p1.division(p2));

            System.out.println();
            System.out.println();
        }

    }

    class Derivate implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try {
                p1 = new Polynomial(view.pol1GetText());

                System.out.println(p1);
                System.out.println();
                rez= p1.derivative();
                System.out.println(rez);
                if (rez.getListOfMonomials().isEmpty())
                    throw new NumberFormatException();
                view.rezultatSetText(rez.toString());
            }catch(NumberFormatException ex) {
                view.showError("Bad Input");
            }
            System.out.println();
            System.out.println();
        }

    }

    class Integrate implements  ActionListener{
        public void actionPerformed(ActionEvent e) {
            try {
                p1 = new Polynomial(view.pol1GetText());

                System.out.println(p1);
                System.out.println();
                rez = p1.integrate();
                System.out.println(rez);
                if (rez.getListOfMonomials().isEmpty())
                    throw new NumberFormatException();
                view.rezultatSetText(rez.toString());
            }catch(NumberFormatException ex) {
                view.showError("Bad Input");
            }
            System.out.println();
            System.out.println();
        }

    }

}
