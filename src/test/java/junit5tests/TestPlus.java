package junit5tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;

import calculator.*;
import org.junit.jupiter.api.*;
import visitor.OutPutExpressionVisitor;


public class TestPlus {

	private final int value1 = 8;
	private final int value2 = 6;
	private Plus op;
	private List<Expression> params;

	@BeforeEach
	public void setUp() {
		  params = new ArrayList<>(Arrays.asList(new MyNumber(value1),new MyNumber(value2)));
		  try { op = new Plus(params); }
		  catch(IllegalConstruction e) { fail(); }
	}

	@Test
	public void testConstructor1() {
		// It should not be possible to create a Plus expression without null parameter list
		assertThrows(IllegalConstruction.class, () -> op = new Plus(null));
	}

	@SuppressWarnings("AssertBetweenInconvertibleTypes")
	@Test
	public void testConstructor2() {
		// A Times expression should not be the same as a Plus expression
		try {
			assertNotEquals(op, new Times(new ArrayList<>()));
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	public void testEquals() {
		// Two similar expressions, constructed separately (and using different constructors) should be equal
		ArrayList<Expression> p = new ArrayList<>(Arrays.asList(new MyNumber(value1), new MyNumber(value2)));
		try {
			Plus e = new Plus(p, Notation.INFIX);
			assertEquals(op, e);
			assertEquals(e, e);
			assertNotEquals(e, new Plus(new ArrayList<>(Arrays.asList(new MyNumber(5), new MyNumber(4))), Notation.INFIX));
		}
		catch(IllegalConstruction e) { fail(); }
	}

	@SuppressWarnings("ConstantConditions")
	@Test
	public void testEquals2() {
			assertDoesNotThrow(() -> op.equals(null)); // Direct way to to test if the null case is handled.
	}

	@Test
	public void testHashCode() {
		// Two similar expressions, constructed separately (and using different constructors) should have the same hashcode
		ArrayList<Expression> p = new ArrayList<>(Arrays.asList(new MyNumber(value1), new MyNumber(value2)));
		try {
			Plus e = new Plus(p, Notation.INFIX);
			assertEquals(e.hashCode(), op.hashCode());
		}
		catch(IllegalConstruction e) { fail(); }
	}

	@Test
	public void testNullParamList() {
		params = null;
		assertThrows(IllegalConstruction.class, () -> op = new Plus(params));
	}

	@Test
	public void testCountDepth() {
		assertEquals(Integer.valueOf(1), op.countDepth());
	}

	@Test
	public void testCountOps() {
		assertEquals(Integer.valueOf(1), op.countOps());
	}

	@Test
	public void testCountNbs() {
		assertEquals(Integer.valueOf(2), op.countNbs());
	}

	@Test
	public void testPrefix() {
		String prefix = "+ (" + value1 + ", " + value2 + ")";
		OutPutExpressionVisitor out = new OutPutExpressionVisitor(Notation.PREFIX);
		out.visit(op);
		assertEquals(prefix, out.getOutput());
	}

	@Test
	public void testInfix() {
		String infix = "( " + value1 + " + " + value2 + " )";
		OutPutExpressionVisitor out = new OutPutExpressionVisitor(Notation.INFIX);
		out.visit(op);
		assertEquals(infix, out.getOutput());
	}

	@Test
	public void testPostfix() {
		String postfix = "(" + value1 + ", " + value2 + ") +";
		OutPutExpressionVisitor out = new OutPutExpressionVisitor(Notation.POSTFIX);
		out.visit(op);
		assertEquals(postfix, out.getOutput());
	}

}
