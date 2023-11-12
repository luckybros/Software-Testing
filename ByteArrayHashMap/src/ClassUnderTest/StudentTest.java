package ClassUnderTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

class StudentTest {
	
	ByteArrayHashMap b;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testByteArrayHashMapIntFloat() {
		fail("Not yet implemented");
	}

	@Test
	void testByteArrayHashMapIntNegative() {
		Exception exception = assertThrows(Exception.class, () -> b = new ByteArrayHashMap(-5));
		assertEquals("Illegal initial capacity: " + -5, exception.getMessage());
	}
	
	@ParameterizedTest
	@CsvSource({"0, 1","27, 32","33554434, 33554432"})
	void testByteArrayHashMapInt(int capacity, int expectedCapacity) {
		b = new ByteArrayHashMap(capacity);
		assertEquals(expectedCapacity, b.table.length, "ByteArrayHashMap non creata con la capacity specificata");
	}

	@Test
	void testByteArrayHashMap() {
		b = new ByteArrayHashMap();
		assertEquals(16, 
				b.table.length, "ByteArrayHashMap non creato con la capacity di default");
		assertEquals(0.75f, 
				b.loadFactor, "ByteArrayHashMap non creato con il loadFactor di default");
		
	}

	@Test
	void testSize() {
		fail("Not yet implemented");
	}

	@Test
	void testIsEmpty() {
		b = new ByteArrayHashMap();
		System.out.println("Verifichiamo che ByteArrayHashMap sia vuoto");
		assertTrue(b.isEmpty(), "Il ByteArrayHashMap non è vuoto");
	}
	
	@Test
	void testIsNotEmpty() {
		ByteArrayHashMap<String> b = new ByteArrayHashMap<String>();
		byte[] key = {1, 2, 3, 4, 5};
		String value = "test";
		b.put(key, value);
		System.out.println("Verifichiamo che ByteArrayHashMap non sia vuoto");
		assertFalse(b.isEmpty(), "Il ByteArrayHashMap è vuoto");
	}
	

	@Test
	void testGetByteArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	void testGetByteArray() {
		fail("Not yet implemented");
	}

	@Test
	void testContainsKey() {
		fail("Not yet implemented");
	}

	@Test
	void testPut() {
		fail("Not yet implemented");
	}

	@Test
	void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	void testClear() {
		fail("Not yet implemented");
	}

	@Test
	void testKeys() {
		fail("Not yet implemented");
	}

	@Test
	void testValues() {
		fail("Not yet implemented");
	}

	@Test
	void testDuplicate() {
		fail("Not yet implemented");
	}

	@Test
	void testResize() {
		fail("Not yet implemented");
	}

	@Test
	void testTransfer() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveEntryForKey() {
		fail("Not yet implemented");
	}

	@Test
	void testAddEntry() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateEntry() {
		fail("Not yet implemented");
	}

}
