package de.unikassel.ti.logic.project3;

import java.io.StringReader;
import java.util.ArrayList;

import de.unikassel.ti.logic.project3.converters.PrenexConverter;
import de.unikassel.ti.logic.project3.converters.SkolemConverter;
import de.unikassel.ti.logic.project3.converters.SkolemToCNFConverter;
import de.unikassel.ti.logic.project3.herbrand.Gilmore;
import de.unikassel.ti.logic.project3.herbrand.HerbrandExpander;
import de.unikassel.ti.logic.project3.model.Formula;

/**
 * 
 * @author Felix
 * 
 *         	Schritte, die durchgeführt werden müssen um eine prädikatenlogischen
 *         	Formel auf Unefüllbarkeit zu prüfen:
 * 
 *         	1. Parsen der Formel um einen Syntaxbaum zu bekommen 
 *         	
 *         	2. Umwandeln der Formel in Postive Normalform 
 *         	In diesem Schritt werden die Negationen nach nach "Innen gedrückt"
 *         	damit sie nur noch von den Relationen stehen. Dazu werden die 
 *         	DeMorgan Regeln um Äquivalenzbeziehungen zwischen den Quantoren 
 *         	angewandt. Des Weiteren werden Implikationen und Biimplikationen 
 *          entfernt.
 * 
 *         	3. Umwandeln der Formel in Pränex Normalform. 
 *         	Variablen, die von  Quantoren gebunden werden, werden umbenannt. 
 *         	Der neue Name darf in  der Formel noch nicht verwendet worden sein.
 *         	Anschließend wird der Existenzabschluss gebildet, damit keine freien
 *         	Variablen mehr vorkommen. Existenz- und Allquantoren werden mittels
 *         	Äquivalenzbeziehungen nach "oben", also zur Wurzel des Syntaxbaumes
 *         	gezogen.
 * 
 *         	4. Umwandeln der Formel in eine erfüllbarkeitsäquivalente in 
 *         	Skolemnormalform 
 *         	Bei der Skolemisierung werden alle Existenzquantoren von außen 
 *         	nach innen ersetzt durch neue Funktionssymbole deren Stelligkeit
 *         	der Anzahl der Allquantoren vor dem jeweils betrachteten Existenzquantor
 *          entspricht und als Argumente die Variblen erhält, die durch die gleichen 
 *          zuvor betrachteten (vor dem Existenzquantor stehenden) Allquantoren gebunden
 *          werden.
 *          
 *         	5. Umwandeln der Matrix der skolemisierten Formel in KNF
 *         	Durch Anwenden von Distributivgesetzen wird die Formel in eine konjunktive
 *		 	Normalform gebracht.
 *
 *			6. Bilden des Herbrand Universums um die Herbrand Expansion durchführen zu
 *			können. Dabei wird angenommen, dass die Signatur der Formel mindestens ein
 *			Konstantensymbol enthält.
 *
 * 			7. Der Satz von Herbrand besagt u.a., dass eine Aussage in Skolemform genau dann
 * 			unerfüllbar ist, wenn es eine endliche Teilmenge der Herbrand Expansion gibt,
 * 			die im aussagenlogischen Sinn unerfüllbar ist.
 * 
 * 			8. Nach der H.-Expansion können die Prädikate wie aussagenlogische Formeln
 * 			behandelt werden. 
 * 
 * 			9. Mittels des Gilmore Algorithmus prüft man dann also sukzessive Teilmengen 
 * 			auf Unerfüllbarkeit und nimmt Elemente der Expansion hinzu.
 * 			Falls keine weiteren Elemente mehr hinzugefügt werden können und die Formel 
 * 			nicht unerfüllbar ist, ist sie erfüllbar. Sollten aber immer weitere Elemente
 * 			hinzugefügt werden können (sobald die Signatur mindestens ein Funktionssymbol mit
 * 			einer Stelligkeit > 0 enthält), entscheidet der Algorithmus von Gilmore ob die 
 * 			Formel unerfüllbar ist oder er hält nicht an (in der Praxis schon wenn z.B. der
 * 			Speicher voll ist oder eine zeitliche Obergrenze gesetzt wird. Es kann dann aber
 * 			keine Aussage über die Erfüllbarkeit gemacht werden).
 */

public class Tool {

	private static final String FORMULA = "- EXISTS x ( R(f(x), g(g(c,x))) & -P(f(f(d))))";

	public static void main(String[] args) {

		System.out.println("Starting tool.");
		String cmdLineFormula = null;

		// check if there are command line args and interpret them as one input
		// string
		if (0 != args.length) {
			StringBuilder sb = new StringBuilder();
			for (String string : args) {
				sb.append(string);
				sb.append(" ");
			}

			cmdLineFormula = sb.toString();
		}
		
		String stringToParse = FORMULA;

		if (null != cmdLineFormula) {
			stringToParse = cmdLineFormula;
		}
		
		// parser p = new parser(new Scanner(new InputStreamReader(System.in)));
		parser p = new parser(new Scanner(new StringReader(
				stringToParse)));
		Formula f = null;

		// 1. parse formula
		try {
			f = (Formula) p.parse().value;
			System.out.println("Input:\n" + f.toString());

			// 2. convert formula to PNF. Has to return a formula
			Formula transformedToPositiveNF = f.transformToPositiveNF();
			System.out.println("\nFormula in postive normal form:\n"
					+ f.toString());

			// 3. convert formula to prenex normal form. Has to return a formula
			Formula transformedToPrenexNF = PrenexConverter
					.convert(transformedToPositiveNF);
			System.out.println("\nFormula in prenex normal form:\n"
					+ f.toString());

			// 4. convert formula to Skolem normal form. Has to return a formula
			SkolemConverter.setVariables(PrenexConverter.getVariableList());
			Formula transformedToSkolemNF = SkolemConverter
					.convert(transformedToPrenexNF);

			System.out.println("\nFormula in Skolem normal form:\n"
					+ f.toString());

			// 5. Transform to KNF
			// Formula transformedToCnf =
			// SkolemToCNFConverter.convert(transformedToSkolemNF);
			System.out.println("\nFormula in KNF:\n" + f.toString());

			// 6. Herbrand Expansion and Gilmore
			ArrayList<Formula> formulasExpanded = new ArrayList<Formula>();

			HerbrandExpander he = new HerbrandExpander(transformedToSkolemNF);
			// HerbrandExpander he = new HerbrandExpander(transformedToCnf);
			Gilmore gilmore = new Gilmore();

			while (he.hasNext()) {
				Formula next = he.getNext();
				formulasExpanded.add(next);
			}

		} catch (Exception e) {
			System.out.println("Something went seriously wrong: "
					+ e.getMessage());
			e.printStackTrace();
			// if the formula cannot be parsed, no further steps are neccessary
		}

	}

}
