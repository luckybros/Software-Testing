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

	// Test Costruttore con due parametri con approccio BCC: i casi di test sono
	// n di classi di equivalenza - n input + 1, cioè 9-2+1=8. Le varie classi di
	// equivalenza sono specificate nel documento
	@Test
	void testByteArrayHashMapIntNegativeFloatBCC() {
		Exception exception = assertThrows(Exception.class, () -> b = new ByteArrayHashMap(-5, 0.4f));
		assertEquals("Illegal initial capacity: " + -5, exception.getMessage());
	}
	
	@ParameterizedTest
	@CsvSource({"-5.7f", "0"})
	void testByteArrayHashMapIntFloatErrorBCC(float loadFactor) {
		Exception exception = assertThrows(Exception.class, () -> b = new ByteArrayHashMap(27, loadFactor));
		assertEquals("Illegal load factor: " +
				 loadFactor, exception.getMessage());
	}
	
	@Test
	void testByteArrayHashMapIntFloatNaNBCC() {
		Exception exception = assertThrows(Exception.class, () -> b = new ByteArrayHashMap(27, Float.NaN));
		assertEquals("Illegal load factor: " +
				 Float.NaN, exception.getMessage());
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
	void testSizeEmpty() {
		b = new ByteArrayHashMap<String>();
		assertEquals(b.size(), 0);
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

	// Test Get con un solo input: in questo caso, poiché non abbiamo vincoli sui 
	// byte[], possiamo semplicemente verificare il caso in cui sia presente un oggetto
	// nella mappa con quella chiave e il caso in cui non lo sia. Un altra verifica
	// potrebbe essere nel caso siano presenti più oggetti relativi a quella chiave
	// Non c'è bisogno in questo caso di fare ragionamenti sulla classe di equivalenza
	// sull'input poiché non ci sono vincoli sui byte[]
	@Test
	void testGetByteArray() {
		ByteArrayHashMap<String> b = new ByteArrayHashMap<String>();
		byte[] key = {1, 2, 3, 4, 5};
		String value = "test";
		b.put(key, value);
		assertEquals(value, b.get(key), "La Get non ha funzionato: ci si aspettava "
				+ value + " e si è ottenuto "+ b.get(key));
	}

	@Test
	void testGetByteArrayNotPresent() {
		ByteArrayHashMap<String> b = new ByteArrayHashMap<String>();
		byte[] key = {1, 2, 3, 4, 5};
		assertNull(b.get(key), "La Get non ha funzionato: si è ottenuto il valore " +
				b.get(key) + " al posto di null");
	}
	
	@Test
	void testGetByteArrayMultiple() {
		ByteArrayHashMap<String> b = new ByteArrayHashMap<String>();
		byte[] key1 = {1};
		String value1 = "test";
		byte[] key2 = {1, 2, 3, 4, 5};
		String value2 = "test2";
		byte[] key3 = {1, 2, 3, 4, 5};
		String value3 = "test3";
		b.put(key1, value1);
		b.put(key2, value2);
		b.put(key3, value3);
		assertEquals(value3, b.get(key3), "La Get non ha funzionato: ci si aspettava "
				+ value3 + " e si è ottenuto "+ b.get(key3));
	}
	
	// Test di containsKey: poiché non abbiamo input e dobbiamo verificare l'appartenenza
	// di una key o meno nella mappa
	@Test
	void testContainsKey() {
		ByteArrayHashMap<String> b = new ByteArrayHashMap<String>();
		byte[] key1 = {1};
		String value1 = "test";
		b.put(key1, value1);
		assertTrue(b.containsKey(key1), "Errore containsKey: la chiave non è presente");
	}
	
	@Test
	void testContainsKeyNotPresent() {
		ByteArrayHashMap<String> b = new ByteArrayHashMap<String>();
		byte[] key1 = {1};
		assertFalse(b.containsKey(key1), "Errore containsKey: la chiave è presente");
	}

	@Test
	void testPut() {
		fail("Not yet implemented");
	}

	// Test remove: anche in questo caso, verifichiamo l'appartenenza di una key o meno
	// alla map
	@Test
	void testRemove() {
		ByteArrayHashMap<String> b = new ByteArrayHashMap<String>();
		byte[] key1 = {1};
		String value1 = "test";
		b.put(key1, value1);
		assertEquals(value1, b.remove(key1), "Errore: l'elemento non è stato rimosso");
	}
	
	@Test
	void testRemoveNotPresent() {
		ByteArrayHashMap<String> b = new ByteArrayHashMap<String>();
		byte[] key1 = {1};
		assertNull(b.remove(key1), "Errore: è presente un elemento");
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
