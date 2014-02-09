package de.unikassel.ti.logic.project3.converters;

import de.unikassel.ti.logic.project3.model.*;

public class SkolemToCNFConverter {

    private static boolean checkForCNF(Formula f) {
        if (f instanceof ExistsQuantification) {
			checkForCNF(((ExistsQuantification) f).getArg());
        } else if (f instanceof ForallQuantification) {
			checkForCNF(((ForallQuantification) f).getArg());
        } else if (f instanceof Disjunction) {
            Formula lf = ((Disjunction) f).getLeftArg();
            Formula rf = ((Disjunction) f).getRightArg();

			applyDistributionIfPossible(f);

			Boolean lfIsCNF = checkForCNF(lf);
			Boolean rfIsCNF = checkForCNF(rf);

			if (lfIsCNF && rfIsCNF) {
				return true;
			}

			if (lfIsCNF && rf instanceof Disjunction ||
				rfIsCNF && lf instanceof Disjunction) {
				return true;
			} //end of disjunction
        } else if (f instanceof Conjunction) {
			Formula lf = ((Conjunction) f).getLeftArg();
			Formula rf = ((Conjunction) f).getRightArg();

			if (lf instanceof RelationFormula && rf instanceof RelationFormula) {
				return true;
			}

			if (lf instanceof Conjunction && rf instanceof Conjunction) {
				return true;
			}

			if (lf instanceof Conjunction && rf instanceof Disjunction ||
				rf instanceof Conjunction && lf instanceof Disjunction) {
				return true;
			}

			if (checkForCNF(lf) && checkForCNF(rf)) {
				return true;
			}
		} else if (f instanceof Negation) {
				Negation nf = (Negation) f;
				checkForCNF(nf.getArg());
		} else if (f instanceof RelationFormula) {
			return true;
		}


        return false;
    }

	private static Formula applyDistributionIfPossible (Formula f) {
		Disjunction df = (Disjunction)f;
		Formula newReplacementJunctor = null;

		//Check if first law is applicable
		Formula lf = df.getLeftArg();
		Formula rf = df.getRightArg();

		if (lf instanceof Conjunction && rf instanceof Conjunction) {
			Formula leftleft = ((Conjunction) lf).getLeftArg();
			Formula rightleft = ((Conjunction) lf).getRightArg();
			Formula leftright = ((Conjunction) rf).getLeftArg();
			Formula rightright = ((Conjunction) rf).getRightArg();

			RelationFormula leftConjOfLeftDisj = null,
							rightConjOfLeftDisj = null,
							leftConjOfRightDisj = null,
							rightConjOfRightDisj = null;

			if (leftleft instanceof RelationFormula) {
				leftConjOfLeftDisj = (RelationFormula)leftleft;
			} else {
				//TODO: ...
			}


			if (rightleft instanceof RelationFormula) {
				rightConjOfLeftDisj = (RelationFormula)rightleft;
			} else {
				//TODO: ...
			}

			if (leftright instanceof RelationFormula) {
				leftConjOfRightDisj = (RelationFormula)leftright;
			} else {
				//TODO: ...
			}

			if (rightright instanceof RelationFormula) {
				rightConjOfRightDisj = (RelationFormula)rightright;
			} else {
				//TODO: ...
			}

			//Check if 'F' exists in both discjunctions
			if (leftConjOfLeftDisj == null ||
				leftConjOfRightDisj == null ||
				rightConjOfLeftDisj == null ||
				rightConjOfRightDisj == null) {
				//TODO: ...
			}

			String leftConjOfLeftDisjName = leftConjOfLeftDisj.getName();
			String leftConjOfRightDisjName = leftConjOfRightDisj.getName();
			String rightConjOfLeftDisjName = rightConjOfLeftDisj.getName();
			String rightConjOfRightDisjName = rightConjOfRightDisj.getName();

			//Check if (F ^ G) v (F ^ H)?
			if (leftConjOfLeftDisjName.equals(leftConjOfRightDisjName))
			{
				newReplacementJunctor = distributionOne(leftConjOfLeftDisj, rightConjOfLeftDisj, rightConjOfRightDisj);
			}
			//Check if (F ^ G) v (H ^ F)?
			else if (leftConjOfLeftDisjName.equals(rightConjOfRightDisj))
			{
				newReplacementJunctor = distributionOne(leftConjOfLeftDisj, rightConjOfLeftDisj, leftConjOfRightDisj);
			}
			//Check if (G ^ F) v (F ^ H)?
			else if (rightConjOfLeftDisjName.equals(leftConjOfRightDisjName))
			{
				newReplacementJunctor = distributionOne(rightConjOfLeftDisj, leftConjOfRightDisj, rightConjOfRightDisj);
			}
			//Check if (G ^ F) v (H ^ F)?
			else if (rightConjOfLeftDisjName.equals(rightConjOfRightDisjName))
			{
				newReplacementJunctor = distributionOne(rightConjOfLeftDisj, leftConjOfRightDisj, leftConjOfRightDisj);
			}

		} else if (lf instanceof RelationFormula && rf instanceof Conjunction) {
			
		} else if (rf instanceof RelationFormula && lf instanceof Conjunction) {

		}

		return null;
	}

	/**
	 * Apply distribution law
	 *
	 * @param f The left argument
	 * @param g The right argument
	 * @param h
	 *
	 * @return converted formula
	 */
	private static Formula distributionOne (Formula f, Formula g, Formula h) {
		Disjunction newDisj = new Disjunction(g, h);
		Conjunction newConj = new Conjunction(f, newDisj);

		return newConj;
	}

	/**
	 *
	 *
	 * @param f
	 * @param g
	 * @param h
	 *
	 * @return
	 */
	private static Formula distributionTwo (Formula f, Formula g, Formula h) {
		Disjunction FoG = new Disjunction(f, g);
		Disjunction FoH = new Disjunction(f, h);

		Conjunction newConj = new Conjunction(FoG, FoH);

		return newConj;
	}

}
