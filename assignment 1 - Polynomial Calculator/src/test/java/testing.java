import org.junit.Assert;
import org.junit.Test;

import Model.Polynomial;

public class testing {

        @Test
        public void testAddition() {
            Polynomial p1= new Polynomial("2.0x^2");
            Polynomial p2 = new Polynomial("4.0x^1+5.0x^0");
            Assert.assertTrue(p1.addition(p2).toString().equals("+2x^2+4x^1+5x^0"));
        }

        @Test
        public void testSubtraction() {
            Polynomial p1= new Polynomial("3.5x^2");
            Polynomial p2 = new Polynomial("3.0x^2+1.5x^0");
            Assert.assertTrue(p1.subtraction(p2).toString().equals("+0.50x^2-1.50x^0"));

        }

        @Test
        public void testMultiplication() {
            Polynomial p1= new Polynomial("4.0x^1+2.0x^0");
            Polynomial p2 = new Polynomial("3.0x^2");
            Assert.assertTrue(p1.multiplication(p2).toString().equals("+12x^3+6x^2"));

        }

    //@Test
   // public void testDivision() {
    //    Polynomial p1= new Polynomial("1.0x^2-3.0x^1-10.0x^0");
    //    Polynomial p2 = new Polynomial("1.0x^1+2.0x^0");
    //    Assert.assertTrue(p1.division(p2).equals("+1x^1+5x^0"));
//}

        @Test
        public void testDerivate() {
            Polynomial p1= new Polynomial("6.0x^3+2.5x^2");
            Assert.assertTrue(p1.derivative().toString().equals("+18x^2+5x^1"));
        }

        @Test
        public void testIntegrate() {
            Polynomial p1= new Polynomial("5.0x^1");
            Assert.assertTrue(p1.integrate().toString().equals("+2.50x^2"));
        }
    }
