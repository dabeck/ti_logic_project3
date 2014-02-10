import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import de.unikassel.ti.logic.project3.Scanner;
import de.unikassel.ti.logic.project3.herbrand.Gilmore;
import de.unikassel.ti.logic.project3.herbrand.SignatureBuilder;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.Signature;
import de.unikassel.ti.logic.project3.parser;
import org.junit.Ignore;
import org.junit.Test;

import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valentyn on 10.02.2014.
 */
public class GilmoreTest {

    @Ignore @Test public void gilmoreTest() {
        final String string = "FORALL w (P(a, b, w) & Q(f(w), a))";

        parser p = new parser(new Scanner(new StringReader(string)));

        Formula f = null;
        try {
            f = (Formula) p.parse().value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Input: " + f.toString());

        Signature signature = null;
        try {
            signature = SignatureBuilder.build(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Signature: " + signature);
    }

    private BiMap<String, Integer> valueToKey;

    @Test public void gilmoreTest2() {

        final Map<String, Integer> init = new HashMap<>();
        init.put("A", 1);
        init.put("B", -2);
        init.put("C", 3);
        init.put("D", 4);

        valueToKey = ImmutableBiMap.copyOf(Collections.unmodifiableMap(init));

        this.printiKeyOfValue(-2);
        this.printiKeyOfValue(1);
        this.printiKeyOfValue(3);
        this.printiKeyOfValue(5);
        this.printiKeyOfValue(4);
    }

    @Test public void gilmoreTest3() {

    }

    private void printiKeyOfValue(final Integer value) {
        System.out.println("The key of value " + value + " is " +
                (valueToKey.containsValue(value) ? valueToKey.inverse().get(value) : "not present") + ".");
    }
}
