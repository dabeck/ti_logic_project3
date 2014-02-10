import de.unikassel.ti.logic.project3.Scanner;
import de.unikassel.ti.logic.project3.herbrand.SignatureBuilder;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.FunctionSymbol;
import de.unikassel.ti.logic.project3.model.Signature;
import de.unikassel.ti.logic.project3.parser;
import org.junit.Test;

import java.io.StringReader;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Valentyn on 09.02.2014.
 */
public class HerbrandSignatureTest {

    // This test takes a FO formula and builds a structure from it.
    @Test public void formulaTest() throws Exception {

        final String input = "FORALL x FORALL y FORALL z (R(f(x)) & S( g( h(y), y(u), r ), z))";

        parser p = new parser(new Scanner(new StringReader(input)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Signature signature = SignatureBuilder.build(f);

        // should consist of 9 elements
        assertTrue(9 == signature.size());

        //
        System.out.println("Input : " + input);
        System.out.print("Output: ");
        for (FunctionSymbol s : signature) {
            System.out.print(s.getName() + "^(" + s.getArity() + "), ");
        }
        System.out.println();
    }
}
