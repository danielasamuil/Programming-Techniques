package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private List<Monomial> listOfMonomials;

    public Polynomial ()
    {
        this.listOfMonomials= new ArrayList<Monomial>();
    }

    public Polynomial(String s) {
        this.listOfMonomials = new ArrayList<Monomial>();
        Pattern p = Pattern.compile("(-?\\d\\.\\d+)[xX]\\^(-?\\d+)");
        Matcher m = p.matcher(s);
        while (m.find()) {
            double coefficient = Double.parseDouble(m.group(1));
            int degree = Integer.parseInt(m.group(2));
            Monomial auxiliary = new Monomial(coefficient, degree);
            this.listOfMonomials.add(auxiliary);
        }
    }
    public void addMonomial(Monomial m)
    {
        this.listOfMonomials.add(m);
    }

    public List<Monomial> getListOfMonomials(){
        return this.listOfMonomials;
    }

    public void setListOfMonomials(List<Monomial> newListOfMonomials){this.listOfMonomials=newListOfMonomials;}

    public Polynomial sortPolynomialDegreeWise(Polynomial p){
        p.getListOfMonomials().sort(new Comparator<Monomial>() {
            public int compare(Monomial m1, Monomial m2) {
                return m2.getDegree()-m1.getDegree();
            }
        });
        return p;
    }

    public Polynomial addition(Polynomial polynomial1) {
        Polynomial newPolynomial= new Polynomial();
        for (Monomial monomial1 : polynomial1.getListOfMonomials()) {
            boolean OK=true;
            if(OK)
                for (Monomial monomial2 : this.listOfMonomials) {
                    if (monomial2.getDegree() == monomial1.getDegree())
                    { Monomial newMonomial= new Monomial(monomial1.getCoefficient()+monomial2.getCoefficient(),monomial1.getDegree());
                        newPolynomial.addMonomial(newMonomial);
                        OK=false; } }
            if(OK)
                newPolynomial.addMonomial(monomial1);
        }

        for(Monomial monomial3: this.listOfMonomials){
            boolean OK1=true;
            for(Monomial monomial4: newPolynomial.getListOfMonomials())
                if(monomial4.getDegree()==monomial3.getDegree())
                    OK1=false;
            if(OK1)
                newPolynomial.addMonomial(monomial3);
        }
        return sortPolynomialDegreeWise(newPolynomial);}

    public Polynomial subtraction(Polynomial polynomial1) {
        Polynomial newPolynomial= new Polynomial();
        for (Monomial monomial1 : this.listOfMonomials) {
            boolean OK=true;
            if(OK)
                for (Monomial monomial2 : polynomial1.getListOfMonomials()) {
                    if (monomial2.getDegree() == monomial1.getDegree())
                    { Monomial newMonomial= new Monomial(monomial1.getCoefficient()-monomial2.getCoefficient(),monomial1.getDegree());
                        newPolynomial.addMonomial(newMonomial);
                        OK=false; }
                }
            if(OK)
                newPolynomial.addMonomial(monomial1);
        }

        for(Monomial monomial3: polynomial1.getListOfMonomials()){
            boolean OK1=true;
            for(Monomial monomial4: newPolynomial.getListOfMonomials())
                if(monomial4.getDegree()==monomial3.getDegree())
                    OK1=false;
            if(OK1) {
                Monomial newMonomial = new Monomial(-monomial3.getCoefficient(),monomial3.getDegree());
                newPolynomial.addMonomial(newMonomial);
            }
        }
        return sortPolynomialDegreeWise(newPolynomial);
    }

    public Polynomial multiplication(Polynomial polynomial1){
        Polynomial result = new Polynomial();
        Polynomial polynomialsSumToBeAdded = new Polynomial();
        Polynomial polynomialsToBeDeleted= new Polynomial();
        for(Monomial monomial1: this.listOfMonomials)
            for(Monomial monomial2: polynomial1.getListOfMonomials())
            {
                Monomial m = new Monomial(monomial1.getCoefficient()*monomial2.getCoefficient(),monomial1.getDegree()+monomial2.getDegree());
                result.addMonomial(m);
            }
        for(Monomial monomial1: result.getListOfMonomials())
            for(Monomial monomial2: result.getListOfMonomials())
                if(monomial1!=monomial2)
                    if(monomial1.getDegree()==monomial2.getDegree())
                    {
                        Monomial m = new Monomial(monomial1.getCoefficient()+monomial2.getCoefficient(),monomial1.getDegree());
                        polynomialsSumToBeAdded.addMonomial(m);
                        polynomialsToBeDeleted.addMonomial(monomial1);
                        polynomialsToBeDeleted.addMonomial(monomial2);
                    }
        for(Monomial m: polynomialsToBeDeleted.getListOfMonomials())
            result.getListOfMonomials().remove(m);
        for(int i=0; i<polynomialsSumToBeAdded.getListOfMonomials().size()/2; i++)
            result.addMonomial(polynomialsSumToBeAdded.getListOfMonomials().get(i));
        return sortPolynomialDegreeWise(result);
    }

    public String division(Polynomial polynomial1){
        Polynomial auxiliary= new Polynomial();
        auxiliary.setListOfMonomials(sortPolynomialDegreeWise(this).getListOfMonomials());
        Polynomial rest=new Polynomial();
        Polynomial result=new Polynomial();
        Monomial m1=sortPolynomialDegreeWise(polynomial1).getListOfMonomials().get(0);
        int nr=0;
        while(auxiliary.getListOfMonomials().get(nr).getCoefficient()!=0 && auxiliary.getListOfMonomials().get(nr).getDegree()-m1.getDegree()>=0) {
            Monomial toBeAdded = new Monomial(auxiliary.getListOfMonomials().get(nr).getCoefficient() / m1.getCoefficient(), auxiliary.getListOfMonomials().get(nr).getDegree() - m1.getDegree());
            result.addMonomial(toBeAdded);
            Polynomial polynomial2 =result.getListOfMonomials().get(nr).multiplyByAMonom(polynomial1);
            Polynomial polynomial3 = auxiliary.subtraction(polynomial2);
            auxiliary.setListOfMonomials(polynomial3.getListOfMonomials());
            rest.setListOfMonomials(auxiliary.getListOfMonomials());
            nr++;
        }
        for(Monomial m: rest.getListOfMonomials())
            if(m.getCoefficient()!=0.0)
            return result.toString() + "+("+ rest.toString() + ")/(" + polynomial1.toString() + ")" ;

           return result.toString();
    }

    public Polynomial derivative(){
        Polynomial result = new Polynomial();
        for(Monomial m: this.getListOfMonomials())
            if(m.getDegree()>0) {
                Monomial newMonomial = new Monomial(m.getCoefficient() * m.getDegree(), m.getDegree() - 1);
                result.addMonomial(newMonomial);
            }
        return result;
    }

    public Polynomial integrate(){
        Polynomial result = new Polynomial();
        for(Monomial m: this.getListOfMonomials()){
            Monomial newMonomial = new Monomial (m.getCoefficient()/(m.getDegree()+1),m.getDegree()+1);
            result.addMonomial(newMonomial);
        }
        return result;
    }

    public String toString(){
        String s="";
        for(int i=0;i<this.listOfMonomials.size();i++)
            if(this.listOfMonomials.get(i).getCoefficient()>0.0)
                s=s + "+" +this.listOfMonomials.get(i);
            else if(this.listOfMonomials.get(i).getCoefficient()<0.0)
                s=s + this.listOfMonomials.get(i);

        return s;
    }

}
