package calculator;

import java.util.List;

final public class Plus extends Operation
{

  public /*constructor*/ Plus(List<Expression> elist) throws IllegalConstruction {
	super(elist);
	symbol = "+";
	neutral = 0;
  }
  
  public Plus(List<Expression> elist, Notation n) throws IllegalConstruction {
		super(elist,n);
		symbol = "+";
		neutral = 0;
  }

  public int op(int l, int r) {
  	return (l+r);
  }
}
