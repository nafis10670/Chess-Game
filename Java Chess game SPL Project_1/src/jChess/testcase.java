package jChess;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class testcase {
	@Test
	public void test1() 
	{
		Bishop bishop = new Bishop();
		assertEquals(true, bishop.isValid(5,6,4,5));
	}
	@Test
	public void test2() 
	{
		Queen queen = new Queen();
		assertEquals(true, queen.isValid(3,7,5,5));
	}
}
