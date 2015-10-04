package myhw3.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class VideoTEST {
	
	@Test
	public void testHashCode() {
		assertEquals
		(-875826552,
				new VideoObj("None", 2009, "Zebra").hashCode());
		assertEquals
		(-1391078111,
				new VideoObj("Blah", 1954, "Cante").hashCode());
	}

	// DONE: complete the tests
	@Test
	public void testEquals(){
		String title = "The Big Lebowski";
		int year = 1998;
		String director = "Coen Brothers";
		VideoObj v1 = new VideoObj(title, year, director);
		assertTrue(v1.equals(v1));
		assertTrue(v1.equals(new VideoObj(title, year, director)));
		assertTrue(v1.equals(new VideoObj(new String(title), year, director)));
		assertTrue(v1.equals(new VideoObj(title, year, new String(director))));
		assertFalse(v1.equals(new VideoObj(title + "Reloaded", year, director)));
		assertFalse(v1.equals(new VideoObj(title, year+2, director)));
		assertFalse(v1.equals(new VideoObj(title, year, director + "Spielberg")));
		assertFalse(v1.equals(new Object()));
		assertFalse(v1.equals(null));
	}
	
	@Test
	public void testCompareTo(){
		String title = "A", title2 = "B";
		int year = 1997, year2 = 1999;
		String director = "M", director2 = "N";
		VideoObj v1 = new VideoObj(title, year, director), 
				 v2 = new VideoObj(title2, year, director);
		//compare by title
		assertTrue(v1.compareTo(v2) < 0);
		assertTrue(v1.compareTo(v2) == -v2.compareTo(v1));
		assertTrue(v1.compareTo(v1)== 0);

		//compare by year
		v2 = new VideoObj(title, year2, director);
		assertTrue( v1.compareTo(v2) < 0 );
		assertTrue( v1.compareTo(v2) == -v2.compareTo(v1) );
	
		//compare by director 
		v2 = new VideoObj(title, year, director2);
		assertTrue( v1.compareTo(v2) < 0 );
		assertTrue( v1.compareTo(v2) == -v2.compareTo(v1) );
		
		//compare if everything is different
		v2 = new VideoObj(title2, year2, director2);
		assertTrue(v1.compareTo(v2) < 0);
		assertTrue(v1.compareTo(v2) == -v2.compareTo(v1));
		
		try{
			v1.compareTo(null);
			fail();
		} catch (NullPointerException e) {}
		
	}
	
	@Test
	public void testToString(){
		String expected = "El Mariachi (1996) : Rodriguez";
		String title = "El Mariachi";
		int year = 1996;
		String director = "Rodriguez";
		VideoObj v1 = new VideoObj(title, year, director);
		assertEquals(v1.toString(), expected);
		
	}

}
