package de.unikassel.ti.logic.project3.tests;

import de.unikassel.ti.logic.project3.Scanner;
import de.unikassel.ti.logic.project3.parser;
import de.unikassel.ti.logic.project3.model.Formula;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.StringReader;

/**
 * Created by Valentyn on 06.02.2014.
 */
public class PositiveTest {

    @Test public void parseTest() {

        final String input = "- FORALL x R(f(z))";
        final String output = "-FORALL x R(f(z))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(output, f.toString());
        System.out.println("Parse passed.");
    }

    // '- FORALL x R(f(z))' to 'EXISTS x -R(f(z))'
    @Test public void notForAllToExistsNot() {

        final String input = "- FORALL x R(f(z))";
        final String output = "EXISTS x -R(f(z))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Formula fo = f.transformToPositiveNF();

        Assert.assertEquals(output, fo.toString());
        System.out.println("FORALL passed.");
    }

    // '- EXISTS x R(f(z))' to 'FORALL x -R(f(z))'
    @Test public void notExistsToForAllNot() {

        final String input = "- EXISTS x R(f(z))";
        final String output = "FORALL x -R(f(z))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Formula fo = f.transformToPositiveNF();

        Assert.assertEquals(output, fo.toString());
        System.out.println("EXISTS passed.");
    }

    // '- (A | B)' to '-A & -B'
    @Test public void deMorgan3() {

        final String input = "- (R(z) | P(x)) ";
        final String output = "-R(z) & -P(x)";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Formula fo = f.transformToPositiveNF();

        Assert.assertEquals(output, fo.toString());
        System.out.println("DE MORGAN OR passed.");
    }

    // '- (A & B)' to '-A | -B'
    @Test public void deMorgan4() {

        final String input = "- (R(z) & P(x)) ";
        final String output = "-R(z) | -P(x)";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Formula fo = f.transformToPositiveNF();

        Assert.assertEquals(output, fo.toString());
        System.out.println("DE MORGAN AND passed.");
    }

    // Implication
    @Test public void implication() {

        final String input = "FORALL x R(z) -> F(x)";
        final String output = "EXISTS x (-R(z) | F(x))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Formula fo = f.transformToPositiveNF();
        Assert.assertEquals(output, fo.toString());
        System.out.println("IMPLICATION passed.");
    }

    // biimplication: 'A <-> B' to '(A & B) | (-A & -B)'
    @Test public void biimplication() {

        final String input = "FORALL x R(z) <-> F(x)";
        final String output = "EXISTS x (R(z) & F(x) | -R(z) & -F(x))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Formula fo = f.transformToPositiveNF();

        Assert.assertEquals(output, fo.toString());
        System.out.println("BI-IMPLICATION passed.");
    }
}
