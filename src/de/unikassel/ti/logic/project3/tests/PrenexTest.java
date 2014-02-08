package de.unikassel.ti.logic.project3.tests;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import de.unikassel.ti.logic.project3.Scanner;
import de.unikassel.ti.logic.project3.parser;
import de.unikassel.ti.logic.project3.model.Formula;

public class PrenexTest {

    // AND

    // A(y) & FORALL x (B(x))
    @Test public void prenex01() {

        final String input = "R(y) & FORALL x (Q(x))";
        final String output = "FORALL x (R(y) & Q(x))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.println("Input: " + f.toString());
        System.out.println("Output: " + fo.toString());
        Assert.assertEquals(output, fo.toString());
    }

    // FORALL x B(x) & A
    @Test public void prenex01reverse() {

        final String input = "FORALL x (Q(x)) & R(y)";
        final String output = "FORALL x (Q(x) & R(y))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.println("Input: " + f.toString());
        System.out.println("Output: " + fo.toString());
        Assert.assertEquals(output, fo.toString());
    }

    // A(y) & EXISTS x (B(x))
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

        System.out.println("Input: " + f.toString());
        System.out.println("Output: " + fo.toString());
        Assert.assertEquals(output, fo.toString());
    }

    // EXISTS x (B(x)) & A(y)
    @Test public void prenex02reverse() {

        final String input = "EXISTS x (Q(x)) & R(y)";
        final String output = "EXISTS x (Q(x) & R(y))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.println("Input: " + f.toString());
        System.out.println("Output: " + fo.toString());
        Assert.assertEquals(output, fo.toString());
    }


    // OR

    // A(y) | EXISTS x (B(x))
    @Test public void prenex03() {

        final String input = "A(y) | EXISTS x (B(x))";
        final String output = "EXISTS x (A(y) | B(x))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.println("Input: " + f.toString());
        System.out.println("Output: " + fo.toString());
        Assert.assertEquals(output, fo.toString());
    }

    // EXISTS x (B(x)) | A(y)
    @Test public void prenex03reverse() {

        final String input = "EXISTS x (B(x)) | A(y)";
        final String output = "EXISTS x (B(x) | A(y))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.println("Input: " + f.toString());
        System.out.println("Output: " + fo.toString());
        Assert.assertEquals(output, fo.toString());
    }

    // A(y) | FORALL x (B(x))
    @Test public void prenex04() {

        final String input = "A(y) | FORALL x (B(x))";
        final String output = "FORALL x (A(y) | B(x))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.println("Input: " + f.toString());
        System.out.println("Output: " + fo.toString());
        Assert.assertEquals(output, fo.toString());
    }

    // FORALL x (B(x)) | A(y)
    @Test public void prenex04reverse() {

        final String input = "FORALL x (B(x)) | A(y)";
        final String output = "FORALL x (B(x) | A(y))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Formula fo = f.transformToPrenexNF();

        System.out.println("Input: " + f.toString());
        System.out.println("Output: " + fo.toString());
        Assert.assertEquals(output, fo.toString());
    }
}
