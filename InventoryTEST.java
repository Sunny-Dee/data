package myhw3.data;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.Test;

// DONE: complete the tests
public class InventoryTEST {
	InventorySet s = new InventorySet();
	final VideoObj v1 = new VideoObj( "A", 2000, "B" );
	final VideoObj v2 = new VideoObj( "B", 2000, "B" );
	final VideoObj v3 = new VideoObj( "D", 1999, "E" );
	final VideoObj v1copy = new VideoObj( "A", 2000, "B" );

	@Test
	public void testSize() {
		assertEquals( 0, s.size() );
		s.addNumOwned(v1,  1); assertEquals( 1, s.size() );
		s.addNumOwned(v1,  2); assertEquals( 1, s.size() );
		s.addNumOwned(v2,  1); assertEquals( 2, s.size() );
		s.addNumOwned(v2, -1); assertEquals( 1, s.size() );
		s.addNumOwned(v1, -3); assertEquals( 0, s.size() );
		try { s.addNumOwned(v1, -3); fail(); } catch ( IllegalArgumentException e ) {}
		assertEquals( 0, s.size() );
	}

	@Test
	public void testAddNumOwned() {
		// DONE
		assertEquals( null, s.get(v1) );
		s.addNumOwned(v1, 1);     assertEquals( v1, s.get(v1).video() );
		assertEquals( 1, s.get(v1).numOwned());
		s.addNumOwned(v1, 2);     assertEquals( 3, s.get(v1).numOwned());
		s.addNumOwned(v1, -1);    assertEquals( 2, s.get(v1).numOwned());
		s.addNumOwned(v2, 1);     assertEquals( 2, s.get(v1).numOwned());
		s.addNumOwned(v1copy, 1); assertEquals( 3, s.get(v1).numOwned());
		s.addNumOwned(v1, -3);    assertEquals( null, s.get(v1) );
		
		assertNull(s.addNumOwned(v3, 4));
		assertFalse(s.addNumOwned(v3, -2).equals(v3));
		assertTrue(s.addNumOwned(v3, -2).numOwned() == 2);
		
		try { s.addNumOwned(null, 1);   fail(); } catch ( IllegalArgumentException e ) {}
	}

	@Test
	public void testCheckOutCheckIn() {
		// DONE
		try { s.checkOut(null);     fail(); } catch ( IllegalArgumentException e ) {}
		try { s.checkIn(null);      fail(); } catch ( IllegalArgumentException e ) {}
		s.addNumOwned(v1, 2); assertTrue( s.get(v1).numOut() == 0 && s.get(v1).numRentals() == 0 );
		s.checkOut(v1);       assertTrue( s.get(v1).numOut() == 1 && s.get(v1).numRentals() == 1 );
		try { s.addNumOwned(v1,-3); fail(); } catch ( IllegalArgumentException e ) {}
		try { s.addNumOwned(v1,-2); fail(); } catch ( IllegalArgumentException e ) {}
		s.addNumOwned(v1,-1); assertTrue( s.get(v1).numOut() == 1 && s.get(v1).numRentals() == 1 );
		s.addNumOwned(v1, 1); assertTrue( s.get(v1).numOut() == 1 && s.get(v1).numRentals() == 1 );
		s.checkOut(v1);       assertTrue( s.get(v1).numOut() == 2 && s.get(v1).numRentals() == 2 );
		try { s.checkOut(v1);       fail(); } catch ( IllegalArgumentException e ) {}
		s.checkIn(v1);        assertTrue( s.get(v1).numOut() == 1 && s.get(v1).numRentals() == 2 );
		s.checkIn(v1copy);    assertTrue( s.get(v1).numOut() == 0 && s.get(v1).numRentals() == 2 );
		try { s.checkIn(v1);        fail(); } catch ( IllegalArgumentException e ) {}
		try { s.checkOut(v2);       fail(); } catch ( IllegalArgumentException e ) {}
		s.checkOut(v1);       assertTrue( s.get(v1).numOut() == 1 && s.get(v1).numRentals() == 3 );
		
	}

	@Test
	public void testClear() {
		// DONE
		s.addNumOwned(v1, 2); assertEquals( 1, s.size() );
		s.addNumOwned(v2, 2); assertEquals( 2, s.size() );
		s.clear();            assertEquals( 0, s.size() );
		try { s.checkOut(v2);       fail(); } catch ( IllegalArgumentException e ) {}
		assertTrue(s.size() == 0);
		
		s.addNumOwned(v1, 2); assertEquals( 1, s.size() );
		s.addNumOwned(v2, 2); assertEquals( 2, s.size() );
		assertEquals(s.clear().size(), 2);
	}

	@Test
	public void testGet() {
		// DONE
		s.addNumOwned(v1, 1);
		Record r1 = s.get(v1);
		Record r2 = s.get(v1);
		assertTrue( r1.equals(r2) );
		assertTrue( r1 == r2 );
	}

	@Test
	public void testIterator1() {
		// DONE
		Set<Video> expected = new HashSet<Video>();
		InventorySet inv = new InventorySet();
		Video v1 = new VideoObj("XX", 2004, "XX");
		Video v2 = new VideoObj("XY", 2000, "XY");
		inv.addNumOwned(v1,10);
		inv.addNumOwned(v2,20);
		expected.add(v1);
		expected.add(v2);

		Iterator<Record> i = inv.iterator();
		try { i.remove(); fail(); } catch (UnsupportedOperationException e) { }
		while(i.hasNext()) {
			Record r = i.next();
			assertTrue(expected.contains(r.video()));
			expected.remove(r.video());
		}
		assertTrue(expected.isEmpty());
	}
	
	@Test
	public void testIterator2() {
		// DONE
		InventorySet inv = new InventorySet();
		Video v1 = new VideoObj("XX", 2004, "XX");
		Video v2 = new VideoObj("XY", 2000, "XY");
		inv.addNumOwned(v1,10);
		inv.addNumOwned(v2,20);

		{
			Comparator<Record> c = (r1, r2) -> r1.video().year() - r2.video().year();
			Iterator<Record> i = inv.iterator(c);
			try { i.remove(); fail(); } catch (UnsupportedOperationException e) { }
			assertSame(v2, i.next().video());
			assertSame(v1, i.next().video());
			assertFalse(i.hasNext());
		}
		{
			Comparator<Record> c = (r1, r2) -> r2.video().year() - r1.video().year();
			Iterator<Record> i = inv.iterator(c);
			assertSame(v1, i.next().video());
			assertSame(v2, i.next().video());
			assertFalse(i.hasNext());
		}
	}
}
