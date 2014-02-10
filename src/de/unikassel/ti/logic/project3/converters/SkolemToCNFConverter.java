package de.unikassel.ti.logic.project3.converters;

import de.unikassel.ti.logic.project3.model.*;

public class SkolemToCNFConverter {

 	private static Formula convertToCNF(Formula f) {

		// A
		if (f instanceof RelationFormula)
		{
			return f;
		}

		if (f instanceof Negation)
		{
			Formula nfArg = ((Negation) f).getArg();
			// -A
			if (nfArg instanceof RelationFormula)
			{
				return f;
			}
			// -(-A)
			else if (nfArg instanceof Negation)
			{
				return convertToCNF(((Negation) nfArg).getArg());
			}
			// E(A)
			else if (nfArg instanceof ExistsQuantification)
			{
				return convertToCNF(((Negation) nfArg).getArg());
			}
			// FA(A)
			else if (nfArg instanceof ForallQuantification)
			{
				return convertToCNF(((Negation) nfArg).getArg());
			}
			// -( & )
			else if (nfArg instanceof Conjunction)
			{
				return new Disjunction(new Negation(((Conjunction) nfArg).getLeftArg()), new Negation(((Conjunction) nfArg).getRightArg()));
			}
			// -( | )
			else if (nfArg instanceof Disjunction)
			{
				return new Conjunction(new Negation(((Disjunction) nfArg).getLeftArg()), new Negation(((Disjunction) nfArg).getRightArg()));
			}
			// -(...)
			else
			{
				return convertToCNF(nfArg);
			}
		}


		if (f instanceof ExistsQuantification)
		{
			Formula nfArg = ((ExistsQuantification) f).getArg();
			// -A
			if (nfArg instanceof RelationFormula)
			{
				return f;
			}
			// -(-A)
			else if (nfArg instanceof Negation)
			{
				return convertToCNF(((Negation) nfArg).getArg());
			}
			// E(A)
			else if (nfArg instanceof ExistsQuantification)
			{
				return convertToCNF(((Negation) nfArg).getArg());
			}
			// FA(A)
			else if (nfArg instanceof ForallQuantification)
			{
				return convertToCNF(((Negation) nfArg).getArg());
			}
			// -( & )
			else if (nfArg instanceof Conjunction)
			{
				return new Disjunction(new Negation(((Conjunction) nfArg).getLeftArg()), new Negation(((Conjunction) nfArg).getRightArg()));
			}
			// -( | )
			else if (nfArg instanceof Disjunction)
			{
				return new Conjunction(new Negation(((Disjunction) nfArg).getLeftArg()), new Negation(((Disjunction) nfArg).getRightArg()));
			}
			// -(...)
			else
			{
				return convertToCNF(nfArg);
			}
		}

		if (f instanceof ForallQuantification)
		{
			Formula nfArg = ((ForallQuantification) f).getArg();
			// -A
			if (nfArg instanceof RelationFormula)
			{
				return f;
			}
			// -(-A)
			else if (nfArg instanceof Negation)
			{
				return convertToCNF(((Negation) nfArg).getArg());
			}
			// E(A)
			else if (nfArg instanceof ExistsQuantification)
			{
				return convertToCNF(((Negation) nfArg).getArg());
			}
			// FA(A)
			else if (nfArg instanceof ForallQuantification)
			{
				return convertToCNF(((Negation) nfArg).getArg());
			}
			// -( & )
			else if (nfArg instanceof Conjunction)
			{
				return new Disjunction(new Negation(((Conjunction) nfArg).getLeftArg()), new Negation(((Conjunction) nfArg).getRightArg()));
			}
			// -( | )
			else if (nfArg instanceof Disjunction)
			{
				return new Conjunction(new Negation(((Disjunction) nfArg).getLeftArg()), new Negation(((Disjunction) nfArg).getRightArg()));
			}
			// -(...)
			else
			{
				return convertToCNF(nfArg);
			}
		}

		Formula cnfLeft = convertToCNF(f.getLeftArg()), cnfRight = convertToCNF(f.getRightArg());

		if (f instanceof Conjunction)
		{
			return new Conjunction(cnfLeft, cnfRight);
		}

		if (f instanceof Disjunction)
		{

			//   |
			// |   |
			if (
					(cnfLeft instanceof Negation || cnfLeft instanceof RelationFormula || cnfLeft instanceof Disjunction) &&
							(cnfRight instanceof Negation || cnfRight instanceof RelationFormula || cnfRight instanceof Disjunction)
					)
			{
				return new Disjunction(cnfLeft, cnfRight);
			}


			//   |
			// &   |
			else if (
					(cnfLeft instanceof Conjunction) &&
							(cnfRight instanceof Negation || cnfRight instanceof RelationFormula || cnfRight instanceof Disjunction)
					)
			{
				Formula newL = new Disjunction(cnfLeft.getLeftArg(), cnfRight);
				Formula newR = new Disjunction(cnfLeft.getRightArg(), cnfRight);

				return new Conjunction(convertToCNF(newL), convert(newR));
			}

			//   |
			// |   &
			else if (
					(cnfLeft instanceof Negation || cnfLeft instanceof RelationFormula || cnfLeft instanceof Disjunction) &&
							(cnfRight instanceof Conjunction)
					)
			{
				Formula newL = new Disjunction(cnfLeft, cnfRight.getRightArg());
				Formula newR = new Disjunction(cnfLeft, cnfRight.getLeftArg());

				return new Conjunction(convertToCNF(newL), convertToCNF(newR));
			}

			//   |
			// &   &
			else if (
					(cnfLeft instanceof Conjunction) &&
							(cnfRight instanceof Conjunction)
					)
			{
				Formula newL = new Conjunction (
						new Disjunction (cnfLeft.getLeftArg(), cnfRight.getLeftArg()),
						new Disjunction (cnfLeft.getRightArg(), cnfRight.getRightArg())
				);
				Formula newR = new Conjunction (
						new Disjunction (cnfLeft.getLeftArg(), cnfRight.getLeftArg()),
						new Disjunction (cnfLeft.getRightArg(), cnfRight.getLeftArg())
				);

				return new Conjunction(convertToCNF(newL), convertToCNF(newR));
			}
		}


		if (f instanceof Biimplication)
		{
			Formula newL = new Disjunction (f.getLeftArg(), new Negation(f.getRightArg()));
			Formula newR = new Disjunction (new Negation(f.getLeftArg()), f.getRightArg());

			return convertToCNF(new Conjunction(
					new Disjunction(f.getLeftArg(), new Negation(f.getRightArg())),
					new Disjunction(new Negation(f.getLeftArg()), f.getRightArg())
			));
		}



		if (f instanceof Implication)
		{
			return convertToCNF(
					new Disjunction(
							new Negation(f.getLeftArg()),
							f.getRightArg()
					)
			);
		}

		//ERROR
		return null;
	}


	public static Formula convert(Formula transformedToSkolemNF) {
		return convertToCNF(transformedToSkolemNF);
	}

}
