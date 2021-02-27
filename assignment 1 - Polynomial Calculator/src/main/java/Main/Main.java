package Main;

import Model.Monomial;
import Model.Polynomial;
import View.View;
import Controller.Controller;

public class Main {

    public static void main(String[] args) {
        /*Monomial m1=new Monomial(1,2);
        Monomial m2=new Monomial(-3,1);
        Monomial m3=new Monomial(-10,0);

        Polynomial p1= new Polynomial();
        p1.addMonomial(m1);
        p1.addMonomial(m2);
        p1.addMonomial(m3);

        Monomial m4=new Monomial(1,1);
        Monomial m5=new Monomial(2,0);

        Polynomial p2= new Polynomial();
        p2.addMonomial(m4);
        p2.addMonomial(m5);

        //Polynomial p3 = p1.addition(p2);
        //Polynomial p3= p1.subtraction(p2);
        //Polynomial p3 = p1.multiplication(p2);
        //Polynomial p3=p1.derivative();
        //Polynomial p3=p1.integrate();

        //System.out.println(p1.division(p2));  //res x-5
        //System.out.println(p3);*/

        View view = new View();
        Controller cont= new Controller(view);
    }
}
