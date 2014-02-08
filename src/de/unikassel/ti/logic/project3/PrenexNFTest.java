package de.unikassel.ti.logic.project3;

import de.unikassel.ti.logic.project3.model.Formula;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

/**
 * Created by Valentyn on 06.02.2014.
 */
public class PrenexNFTest {

    // A & FORALL x B(x)
    @Test public void prenex01() {

        final String input = "R(y) & FORALL x (Q(z))";
        final String output = "FORALL x (R(y) & Q(z))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.printf("Input: %s\n", f.toString());
        System.out.printf("Output: %s\n", fo.toString());
        Assert.assertEquals(output, fo.toString());
    }

    // A & FORALL x B(x) ...(reversed)
    @Test public void prenex01reverse() {

        final String input = "FORALL x (Q(z)) & R(y)";
        final String output = "FORALL x (Q(z) & R(y))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.printf("Input: %s\n", f.toString());
        System.out.printf("Output: %s\n", fo.toString());
        Assert.assertEquals(output, fo.toString());
    }

    // A & EXISTS x B(x)
    @Test public void prenex02() {

        final String input = "A(y) & EXISTS x (B(x))";
        final String output = "EXISTS x (A(y) & B(x))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.printf("Input: %s\n", f.toString());
        System.out.printf("Output: %s\n", fo.toString());
        Assert.assertEquals(output, fo.toString());
    }

    // EXISTS x B(x) & A ...(reversed)
    @Test public void prenex02reverse() {

        final String input = "EXISTS x (Q(z)) & R(y)";
        final String output = "EXISTS x (Q(z) & R(y))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.printf("Input: %s\n", f.toString());
        System.out.printf("Output: %s\n", fo.toString());
        Assert.assertEquals(output, fo.toString());
    }
}
