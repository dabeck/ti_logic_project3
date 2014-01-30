package de.unikassel.ti.logic.project3;


//----------------------------------------------------
//The following code was generated by CUP v0.11a beta 20060608
//Wed Dec 25 21:15:10 CET 2013
//----------------------------------------------------

import java_cup.runtime.*;

import java.util.Vector;

import de.unikassel.ti.logic.project3.model.Biimplication;
import de.unikassel.ti.logic.project3.model.Conjunction;
import de.unikassel.ti.logic.project3.model.Disjunction;
import de.unikassel.ti.logic.project3.model.ExistsQuantification;
import de.unikassel.ti.logic.project3.model.ForallQuantification;
import de.unikassel.ti.logic.project3.model.Formula;
import de.unikassel.ti.logic.project3.model.FunctionSymbol;
import de.unikassel.ti.logic.project3.model.Implication;
import de.unikassel.ti.logic.project3.model.Negation;
import de.unikassel.ti.logic.project3.model.RelationFormula;
import de.unikassel.ti.logic.project3.model.Term;

/** CUP v0.11a beta 20060608 generated parser.
* @version Wed Dec 25 21:15:10 CET 2013
*/
public class parser extends java_cup.runtime.lr_parser {

/** Default constructor. */
public parser() {super();}

/** Constructor which sets the default scanner. */
public parser(java_cup.runtime.Scanner s) {super(s);}

/** Constructor which sets the default scanner. */
public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

/** Production table. */
protected static final short _production_table[][] = 
  unpackFromStrings(new String[] {
  "\000\016\000\002\002\006\000\002\002\004\000\002\002" +
  "\005\000\002\002\005\000\002\002\005\000\002\002\004" +
  "\000\002\002\005\000\002\002\005\000\002\002\005\000" +
  "\002\002\005\000\002\004\003\000\002\004\005\000\002" +
  "\003\003\000\002\003\006" });

/** Access to production table. */
public short[][] production_table() {return _production_table;}

/** Parse-action table. */
protected static final short[][] _action_table = 
  unpackFromStrings(new String[] {
  "\000\041\000\014\006\007\011\004\012\006\013\010\016" +
  "\005\001\002\000\004\017\042\001\002\000\004\013\030" +
  "\001\002\000\004\017\026\001\002\000\014\006\007\011" +
  "\004\012\006\013\010\016\005\001\002\000\014\006\007" +
  "\011\004\012\006\013\010\016\005\001\002\000\014\002" +
  "\015\004\014\005\016\007\012\010\013\001\002\000\014" +
  "\006\007\011\004\012\006\013\010\016\005\001\002\000" +
  "\014\006\007\011\004\012\006\013\010\016\005\001\002" +
  "\000\014\006\007\011\004\012\006\013\010\016\005\001" +
  "\002\000\004\002\000\001\002\000\014\006\007\011\004" +
  "\012\006\013\010\016\005\001\002\000\016\002\ufffd\004" +
  "\014\005\ufffd\007\ufffd\010\ufffd\014\ufffd\001\002\000\016" +
  "\002\ufffe\004\ufffe\005\ufffe\007\ufffe\010\ufffe\014\ufffe\001" +
  "\002\000\016\002\ufffa\004\014\005\016\007\012\010\ufffa" +
  "\014\ufffa\001\002\000\016\002\ufffb\004\014\005\016\007" +
  "\012\010\ufffb\014\ufffb\001\002\000\014\004\014\005\016" +
  "\007\012\010\013\014\024\001\002\000\016\002\uffff\004" +
  "\uffff\005\uffff\007\uffff\010\uffff\014\uffff\001\002\000\016" +
  "\002\ufffc\004\ufffc\005\ufffc\007\ufffc\010\ufffc\014\ufffc\001" +
  "\002\000\014\006\007\011\004\012\006\013\010\016\005" +
  "\001\002\000\016\002\ufff8\004\014\005\016\007\012\010" +
  "\013\014\ufff8\001\002\000\004\017\031\001\002\000\010" +
  "\013\037\014\ufff5\015\ufff5\001\002\000\006\014\ufff7\015" +
  "\035\001\002\000\004\014\034\001\002\000\016\002\001" +
  "\004\001\005\001\007\001\010\001\014\001\001\002\000" +
  "\004\017\031\001\002\000\004\014\ufff6\001\002\000\004" +
  "\017\031\001\002\000\004\014\041\001\002\000\006\014" +
  "\ufff4\015\ufff4\001\002\000\014\006\007\011\004\012\006" +
  "\013\010\016\005\001\002\000\016\002\ufff9\004\014\005" +
  "\016\007\012\010\013\014\ufff9\001\002" });

/** Access to parse-action table. */
public short[][] action_table() {return _action_table;}

/** <code>reduce_goto</code> table. */
protected static final short[][] _reduce_table = 
  unpackFromStrings(new String[] {
  "\000\041\000\004\002\010\001\001\000\002\001\001\000" +
  "\002\001\001\000\002\001\001\000\004\002\024\001\001" +
  "\000\004\002\022\001\001\000\002\001\001\000\004\002" +
  "\021\001\001\000\004\002\020\001\001\000\004\002\017" +
  "\001\001\000\002\001\001\000\004\002\016\001\001\000" +
  "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
  "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
  "\001\000\004\002\026\001\001\000\002\001\001\000\006" +
  "\003\031\004\032\001\001\000\002\001\001\000\002\001" +
  "\001\000\002\001\001\000\002\001\001\000\006\003\031" +
  "\004\035\001\001\000\002\001\001\000\006\003\031\004" +
  "\037\001\001\000\002\001\001\000\002\001\001\000\004" +
  "\002\042\001\001\000\002\001\001" });

/** Access to <code>reduce_goto</code> table. */
public short[][] reduce_table() {return _reduce_table;}

/** Instance of action encapsulation class. */
protected CUP$parser$actions action_obj;

/** Action encapsulation object initializer. */
protected void init_actions()
  {
    action_obj = new CUP$parser$actions(this);
  }

/** Invoke a user supplied parse action. */
public java_cup.runtime.Symbol do_action(
  int                        act_num,
  java_cup.runtime.lr_parser parser,
  java.util.Stack            stack,
  int                        top)
  throws java.lang.Exception
{
  /* call code in generated class */
  return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
}

/** Indicates start state. */
public int start_state() {return 0;}
/** Indicates start production. */
public int start_production() {return 1;}

/** <code>EOF</code> Symbol index. */
public int EOF_sym() {return 0;}

/** <code>error</code> Symbol index. */
public int error_sym() {return 1;}

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$parser$actions {
private final parser parser;

/** Constructor */
CUP$parser$actions(parser parser) {
  this.parser = parser;
}

/** Method with the actual generated action code. */
public final java_cup.runtime.Symbol CUP$parser$do_action(
  int                        CUP$parser$act_num,
  java_cup.runtime.lr_parser CUP$parser$parser,
  java.util.Stack            CUP$parser$stack,
  int                        CUP$parser$top)
  throws java.lang.Exception
  {
    /* Symbol object for return from actions */
    java_cup.runtime.Symbol CUP$parser$result;

    /* select the action based on the action number */
    switch (CUP$parser$act_num)
      {
        /*. . . . . . . . . . . . . . . . . . . .*/
        case 13: // term ::= FUNSYMBOL LPAREN termlist RPAREN 
          {
            Term RESULT =null;
		int hleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).left;
		int hright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).right;
		String h = (String)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-3)).value;
		int tlleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int tlright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Vector<Term> tl = (Vector<Term>)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new Term(new FunctionSymbol(h, tl.size()), tl); 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("term",1, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 12: // term ::= FUNSYMBOL 
          {
            Term RESULT =null;
		int hleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int hright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		String h = (String)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Term(new FunctionSymbol(h,0), new java.util.Vector<Term>()); 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("term",1, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 11: // termlist ::= term COMMA termlist 
          {
            Vector<Term> RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int tright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Term t = (Term)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int tlleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int tlright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Vector<Term> tl = (Vector<Term>)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 tl.add(0,t); RESULT = tl; 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("termlist",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 10: // termlist ::= term 
          {
            Vector<Term> RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int tright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Term t = (Term)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 Vector<Term> ts = new Vector<Term>(); ts.add(t); RESULT = ts; 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("termlist",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 9: // formula ::= FORALL FUNSYMBOL formula 
          {
            Formula RESULT =null;
		int xleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int xright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		String x = (String)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Formula f = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new ForallQuantification(x,f); 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 8: // formula ::= EXISTS FUNSYMBOL formula 
          {
            Formula RESULT =null;
		int xleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int xright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		String x = (String)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Formula f = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new ExistsQuantification(x,f); 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 7: // formula ::= formula BIIMP formula 
          {
            Formula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Formula f = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int gleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int gright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Formula g = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Biimplication(f,g); 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 6: // formula ::= formula IMP formula 
          {
            Formula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Formula f = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int gleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int gright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Formula g = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Implication(f,g); 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 5: // formula ::= NEG formula 
          {
            Formula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Formula f = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Negation(f); 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 4: // formula ::= formula OR formula 
          {
            Formula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Formula f = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int gleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int gright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Formula g = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Disjunction(f,g); 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 3: // formula ::= formula AND formula 
          {
            Formula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Formula f = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int gleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int gright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Formula g = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new Conjunction(f,g); 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 2: // formula ::= LPAREN formula RPAREN 
          {
            Formula RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Formula f = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = f; 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 1: // $START ::= formula EOF 
          {
            Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Formula start_val = (Formula)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
            CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        /* ACCEPT */
        CUP$parser$parser.done_parsing();
        return CUP$parser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 0: // formula ::= RELSYMBOL LPAREN termlist RPAREN 
          {
            Formula RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).right;
		String s = (String)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-3)).value;
		int tleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int tright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Vector<Term> t = (Vector<Term>)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = new RelationFormula(s,t); 
            CUP$parser$result = parser.getSymbolFactory().newSymbol("formula",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
          }
        return CUP$parser$result;

        /* . . . . . .*/
        default:
          throw new Exception(
             "Invalid action number found in internal parse table");

      }
  }
}


