package jChess;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class testcase {
	@Test
	public void testKing1() 
	{
		Rook rook = new Rook();
		assertEquals(false, rook.isValid(0,0, 0, 8));
	}
}
