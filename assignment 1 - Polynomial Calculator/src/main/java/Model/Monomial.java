package Model;
import java.text.DecimalFormat;

public class Monomial{
    private static DecimalFormat df = new DecimalFormat("0.00");

    private double coefficient;
    private int degree;

    public Monomial(double c,int d)
    {
        this.coefficient=c;
        this.degree=d;
    }

    public int getDegree(){
        return this.degree;
    }

    public double getCoefficient(){
        return this.coefficient;
    }

    public String toString(){
        if(this.coefficient==(int) this.coefficient)
            return (int) this.coefficient + "x^" + this.degree;

        return  df.format(this.coefficient) + "x^" + this.degree;
    }

    public Polynomial multiplyByAMonom(Polynomial polynomial1){
        Polynomial result = new Polynomial();
        for(Monomial m: polynomial1.getListOfMonomials())
        {
            if(m.getDegree()!=0){
                Monomial newMonomial=new Monomial(this.getCoefficient()*m.getCoefficient(),this.getDegree()+m.getDegree());
                result.addMonomial(newMonomial);
            }
            else
            if(m.getDegree()==0 && this.degree!=0)
            {
                Monomial newM = new Monomial(this.getCoefficient()*m.getCoefficient(),m.getDegree()+1);
                result.addMonomial(newM);
            }
            else if(this.degree==0 && m.getDegree()==0)
            {
                Monomial newM = new Monomial(this.getCoefficient()*m.getCoefficient(),m.getDegree());
                result.addMonomial(newM);
            }
        }
        return result;
    }
}
