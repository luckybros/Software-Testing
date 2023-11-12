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
	void testByteArrayHashMapIntNegativeFloatBCC() {
		Exception exception = assertThrows(Exception.class, () -> b = new ByteArrayHashMap(-5, 0.4f));
		assertEquals("Illegal initial capacity: " + -5, exception.getMessage());
	}
	
	@ParameterizedTest
	@CsvSource({"-5.7f, 0, Float.NaN"})
	void testByteArrayHashMapIntFloatErrorBCC(float loadFactor) {
		Exception exception = assertThrows(Exception.class, () -> b = new ByteArrayHashMap(27, -5.7f));
		assertEquals("Illegal load factor: " +
				 loadFactor, exception.getMessage());
	}
	
	
	@ParameterizedTest
	@CsvSource({"27, 0.4f, 32, 0.4f", "0, 0.4f, 1, 0.4f", "33554432, 0.4f, 33554432, 0.4f"
		, "40000000, 0.4f, 33554432, 0.4f"})
	void testByteArrayHashMapIntFloatBCC(int capacity, float loadFactor, int expectedCapacity,
			float expectedLoadFactor) {
		b = new ByteArrayHashMap(capacity, loadFactor);
		assertEquals(b.table.length, expectedCapacity, "ByteArrayHashMap non creata con la capacity specificata: " 
				+ b.table.length + " al posto di " + expectedCapacity);
		assertEquals(b.loadFactor, expectedLoadFactor, "ByteArrayHashMap non creata con il load factor specificato: " 
				+ b.loadFactor + " al posto di " + expectedLoadFactor);
	}

	// Test Costruttore con solo int in ingresso: scelte le classi di equivalenza,
	// che sono {capacity < 0}, {0 <= capacity <= MAX_CAPACITY}, {capacity > 0}
	// dalle quali ci aspettiamo un comportamento diverso (Funcionality-based)
	// essendo un solo input basta valutare un caso di test per ogni classe di appartenenza.
	// In questo caso potremmo valutare anche i valori limite, ma non sono necessari
	// in quanto non ci aspettiamo comportamenti speciali per i valori limite
	// degli intervalli a livello funzionale, ma possiamo comunque aggiungerli
	// in questo caso essendo utile testare i limiti del sistema e garantire 
	// che il software gestisca correttamente situazioni particolari
	@Test
	void testByteArrayHashMapIntNegative() {
		Exception exception = assertThrows(Exception.class, () -> b = new ByteArrayHashMap(-5));
		assertEquals("Illegal initial capacity: " + -5, exception.getMessage());
	}
	
	@ParameterizedTest
	@CsvSource({"0, 1","27, 32","33554432, 33554432", "40000000, 33554432"})
	void testByteArrayHashMapInt(int capacity, int expectedCapacity) {
		b = new ByteArrayHashMap(capacity);
		assertEquals(expectedCapacity, b.table.length, "ByteArrayHashMap non creata con la capacity specificata");
	}

	// Test Costruttore di default: in questo caso basta accertarsi che sia inizializzato
	// correttamente ai valori di default non avendo l'input
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
		b = new ByteArrayHashMap<String>();
		byte[] key1 = {1, 2, 3, 4, 5};
		String value1 = "test";
		b.put(key1, value1);
		byte[] key2 = {1, 2, 3, 4, 6};
		String value2 = "test2";
		b.put(key2, value2);
		assertEquals(b.size(), 2);
	}
	
	@Test
	void testIsEmpty() {
		b = new ByteArrayHashMap();
		//System.out.println("Verifichiamo che ByteArrayHashMap sia vuoto");
		assertTrue(b.isEmpty(), "Il ByteArrayHashMap non è vuoto");
	}
	
	@Test
	void testIsNotEmpty() {
		ByteArrayHashMap<String> b = new ByteArrayHashMap<String>();
		byte[] key = {1, 2, 3, 4, 5};
		String value = "test";
		b.put(key, value);
		//System.out.println("Verifichiamo che ByteArrayHashMap non sia vuoto");
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
