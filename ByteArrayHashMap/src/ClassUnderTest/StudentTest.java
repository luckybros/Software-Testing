package ClassUnderTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

@RunWith(Theories.class)
class StudentTest {
	
	ByteArrayHashMap b;

	// Test Costruttore con due parametri con approccio Pair-Wise: vengono valutate
	// tutte le possibili combinazoni di input, i casi di test sono 20 (5*4)
	@ParameterizedTest
	@CsvSource({"-5.7f", "0", "0.7f"})
	void testByteArrayHashMapIntNegativeFloatPairWise(float loadFactor) {
		Exception exception = assertThrows(Exception.class, () -> b = new ByteArrayHashMap(-5, loadFactor));
		assertEquals("Illegal initial capacity: " + -5, exception.getMessage());
	}
	
	@Test
	void testByteArrayHashMapIntNegativeFloatNaNPairWise() {
		Exception exception = assertThrows(Exception.class, () -> b = new ByteArrayHashMap(-5, Float.NaN));
		assertEquals("Illegal initial capacity: " + -5, exception.getMessage());
	}
	
	@DataPoints("exceptionLoadFactor")
	static float[] exceptionLoadFactor() {
		return new float[] {-5.7f, 0, Float.NaN};
	}
	
	@DataPoints("validCapacity")
	static int[] validCapacity() {
		return new int[] {0, 27, 33554432, 40000000};
	}
	
	@Theory
	void testByteArrayHashMapIntFloatErrorPairWise(@FromDataPoints("exceptionLoadFactor") float loadFactor,
			@FromDataPoints("validCapacity") int capacity) {
		Exception exception = assertThrows(Exception.class, () -> b = new ByteArrayHashMap(capacity, loadFactor));
		assertEquals("Illegal load factor: " +
				 loadFactor, exception.getMessage());
	}
	
	@ParameterizedTest
	@CsvSource({"0, 1","27, 32","33554432, 33554432", "40000000, 33554432"})
	void testByteArrayHashMapIntFloatPairWise(int capacity, int expectedCapacity) {
		b = new ByteArrayHashMap(capacity, 0.4f);
		assertEquals(b.table.length, expectedCapacity, "ByteArrayHashMap non creata con la capacity specificata: " 
				+ b.table.length + " al posto di " + expectedCapacity);
		assertEquals(b.loadFactor, 0.4f, "ByteArrayHashMap non creata con il load factor specificato: " 
				+ b.loadFactor + " al posto di " + 0.4f);
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
	void testSizeZero() {
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
		b = new ByteArrayHashMap<String>();
		byte[] key = {1, 2, 3, 4, 5};
		String value = "test";
		b.put(key, value);
		//System.out.println("Verifichiamo che ByteArrayHashMap non sia vuoto");
		assertFalse(b.isEmpty(), "Il ByteArrayHashMap è vuoto");
	}
	
	// Test Get con tre input: utilizzo un approccio BCC considerando l'array di byte
	// sempre valido, poiché non abbiamo vincoli su di esso (uno potrebbe essere quello
	// di andare a considerare un byte[] vuoto ma può essere verificato a prescindere dagli
	// altri), i cui input su cui andare ad effettuare il BCC sono l'offset e la len
	// Avendo quattro classi di appartenenza ognuno otteniamo 7 casi di test, di cui in
	// in realtà l'ultimo è dipendente da entrambi
	@ParameterizedTest
	@CsvSource({"-2, 2", "8, 2", "3, -2", "3, 5"})
	void testGetByteArrayIntIntError(int offset, int len) {
		b = new ByteArrayHashMap<String>();
		byte[] key = {1, 2, 3, 4, 5, 6, 7};
		Exception exception = assertThrows(Exception.class, () -> b.get(key, offset, len));
		assertNotNull(exception);
	}
	
	@ParameterizedTest
	@CsvSource({"3, 2", "0, 2", "3, 0"})
	void testGetByteArrayIntInt(int offset, int len) {
		b = new ByteArrayHashMap<String>();
		byte[] key = {1, 2, 3, 4, 5, 6, 7};
		byte[] k = new byte[len];
		String value = "test";
		System.arraycopy(key, offset, k, 0, len);
		b.put(k, value);
		assertEquals(value, b.get(key, offset, len), "La Get non ha funzionato: ci si aspettava "
				+ value + " e si è ottenuto "+ b.get(key, 3, 2));
	}

	// Test Get con un solo input: in questo caso, poiché non abbiamo vincoli sui 
	// byte[], possiamo semplicemente verificare il caso in cui sia presente un oggetto
	// nella mappa con quella chiave e il caso in cui non lo sia. Un altra verifica
	// potrebbe essere nel caso siano presenti più oggetti relativi a quella chiave
	// Non c'è bisogno in questo caso di fare ragionamenti sulla classe di equivalenza
	// sull'input poiché non ci sono vincoli sui byte[]
	@Test
	void testGetByteArray() {
		b = new ByteArrayHashMap<String>();
		byte[] key = {1, 2, 3, 4, 5};
		String value = "test";
		b.put(key, value);
		assertEquals(value, b.get(key), "La Get non ha funzionato: ci si aspettava "
				+ value + " e si è ottenuto "+ b.get(key));
	}

	@Test
	void testGetByteArrayNotPresent() {
		b = new ByteArrayHashMap<String>();
		byte[] key = {1, 2, 3, 4, 5};
		assertNull(b.get(key), "La Get non ha funzionato: si è ottenuto il valore " +
				b.get(key) + " al posto di null");
	}
	
	@Test
	void testGetByteArrayMultiple() {
		b = new ByteArrayHashMap<String>();
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
		b = new ByteArrayHashMap<String>();
		byte[] key1 = {1};
		String value1 = "test";
		b.put(key1, value1);
		assertTrue(b.containsKey(key1), "Errore containsKey: la chiave non è presente");
	}
	
	@Test
	void testContainsKeyNotPresent() {
		b = new ByteArrayHashMap<String>();
		byte[] key1 = {1};
		assertFalse(b.containsKey(key1), "Errore containsKey: la chiave è presente");
	}

	// Non avendo anche qui input, verifichiamo se il valore è stato inserito correttamente
	@Test
	void testPut() {
		b = new ByteArrayHashMap<String>(0,0.7f);
		byte[] key1 = {1};
		String value1 = "test";
		b.put(key1, value1);
		assertEquals(value1, b.get(key1), "Errore: la put non ha inserito l'elemento");
	}
	
	// Test remove: anche in questo caso, verifichiamo l'appartenenza di una key o meno
	// alla map
	@Test
	void testRemove() {
		b = new ByteArrayHashMap<String>();
		byte[] key1 = {1};
		String value1 = "test";
		b.put(key1, value1);
		assertEquals(value1, b.remove(key1), "Errore: l'elemento non è stato rimosso");
	}
	
	@Test
	void testRemoveNotPresent() {
		b = new ByteArrayHashMap<String>();
		byte[] key1 = {1};
		assertNull(b.remove(key1), "Errore: è presente un elemento");
	}

	
	@Test
	void testClear() {
		b = new ByteArrayHashMap<String>();
		byte[] key1 = {1};
		String value1 = "test";
		byte[] key2 = {1, 2, 3, 4, 5};
		String value2 = "test2";
		byte[] key3 = {1, 2, 3, 4, 5};
		String value3 = "test3";
		b.put(key1, value1);
		b.put(key2, value2);
		b.put(key3, value3);
		b.clear();
		assertEquals(b.size, 0, "Errore: clear non andata a buon fine");
	}

	// Non avendo input verifichiamo che la retrieve delle chiavi
	// sia fatta correttamente
	@Test
	void testKeys() {
		b = new ByteArrayHashMap<>();
        byte[] key1 = {1, 2, 3};
        byte[] key2 = {4, 5, 6};
        byte[] key3 = {7, 8, 9};
        b.put(key1, "test1");
        b.put(key2, "test2");
        b.put(key3, "test3");
        List<byte[]> keys = b.keys();
        assertTrue(keys.contains(key1));
        assertTrue(keys.contains(key2));
        assertTrue(keys.contains(key3));
        assertEquals(3, keys.size());
    }

	@Test
	void testValues() {
		b = new ByteArrayHashMap<>();
        byte[] key1 = {1, 2, 3};
        byte[] key2 = {4, 5, 6};
        byte[] key3 = {7, 8, 9};
        b.put(key1, "test1");
        b.put(key2, "test2");
        b.put(key3, "test3");
        List<String> values = b.values();
        assertTrue(values.contains("test1"));
        assertTrue(values.contains("test2"));
        assertTrue(values.contains("test3"));
        assertEquals(3, values.size());
	}

	@Test
	void testDuplicate() {
		b = new ByteArrayHashMap<String>(0, 0.7f);
		byte[] key1 = {1, 2, 3};
        b.put(key1, "test1");
        ByteArrayHashMap<String> copy = b.duplicate();
        assertTrue(b.loadFactor == copy.loadFactor && b.size == copy.size &&
        		b.get(key1) == copy.get(key1), "Errore: non ottenuta una copia dell'hashmap");
	}

	// Test della resize: in questo caso possiamo identificare 5 classi di equivalenza:
	// {newCapacity < 0}, {newCapacity = 0}, {newCapacity < oldCapacity}, {newCapacity = oldCapacity}, 
	// {newCapacity > oldCapacity}. La prima classe e la seconda classe sono ovviamente sempre problematiche,
	// mentre le altre non dovrebbero dare grossi problemi.
	// Si può estendere l'ultima classe di equivalenza anche al caso di newCapacity >> oldCapacity
	
	@Test 
	void testResizeNegative() {
		b = new ByteArrayHashMap<String>(27, 0.7f);
		Exception exception = assertThrows(Exception.class, ()->b.resize(-5));
		assertNotNull(exception, "Errore: eccezione resize negativa non catturata");
	}
	
	@Test
	void testResizeZero() {
		b = new ByteArrayHashMap<String>(27, 0.7f);
		int newCapacity = 0;
		b.put("key1".getBytes(), "value1");
        Exception exception = assertThrows(Exception.class, ()->b.resize(newCapacity));
		assertNotNull(exception, "Errore: eccezione resize con newCapacity minore della old non catturata");
	}
	
	@Test
	void testResizeOldGreaterThanNew() {
		b = new ByteArrayHashMap<String>(4, 0.7f);
		int newCapacity = 1;
		b.put("key1".getBytes(), "value1");
		b.put("key2".getBytes(), "value2");
		b.put("key3".getBytes(), "value3");
		b.put("key4".getBytes(), "value4");
		b.resize(newCapacity);
		assertEquals(b.table.length, newCapacity, "Errore: nuova size diversa da quella"
				+ "aspettata. Ottenuta " + b.table.length + " al posto di "+ newCapacity);
	}
	
	@ParameterizedTest
	@CsvSource({"32, 32", "32, 67", "32, 300080"})
	void testResize(int oldCapacity, int newCapacity) {
		b = new ByteArrayHashMap<String>(oldCapacity, 0.7f);
		b.put("key1".getBytes(), "value1");
		b.put("key2".getBytes(), "value2");
		b.resize(newCapacity);
		assertEquals(b.table.length, newCapacity, "Errore: nuova size diversa da quella"
				+ "aspettata. Ottenuta " + b.table.length + " al posto di "+ newCapacity);
	}
	
	@Test
	void testResizeMax() {
		b = new ByteArrayHashMap<String>(ByteArrayHashMap.MAXIMUM_CAPACITY, 0.7f);
		int newCapacity = 8;
		b.resize(newCapacity);
		assertEquals(b.table.length, ByteArrayHashMap.MAXIMUM_CAPACITY, "Errore: nuova size diversa da quella"
				+ "aspettata. Ottenuta " + b.table.length + " al posto di "+ newCapacity);
	}
	
	
	@Test
	void testTransfer() {
		b = new ByteArrayHashMap<String>(27, 0.7f);
		b.put("key1".getBytes(), "value1");
		b.put("key2".getBytes(), "value2");
		b.put("key3".getBytes(), "value3");
		b.put("key4".getBytes(), "value4");
		ByteArrayHashMap.Entry<String>[] newTable = new ByteArrayHashMap.Entry[2*27];
		b.transfer(newTable);
		b.table = newTable;
		assertTrue(b.get("key1".getBytes()) == "value1" && b.get("key2".getBytes()) == "value2"
				&& b.get("key3".getBytes()) == "value3" && b.get("key4".getBytes()) == "value4");
	}

	@Test
	void testTransferNull() {
		b = new ByteArrayHashMap<String>(27, 0.7f);
		ByteArrayHashMap.Entry<String>[] newTable = null;
		Exception exception = assertThrows(Exception.class, ()->b.transfer(newTable));
		assertNotNull(exception);
	}
	
	@Test
	void testRemoveEntryForKey() {
		b = new ByteArrayHashMap<String>(27, 0.7f);
		b.put("key1".getBytes(), "value1");
		b.put("key2".getBytes(), "value2");
		b.put("key3".getBytes(), "value3");
		assertEquals("value1", b.removeEntryForKey("key1".getBytes()).value);
	}
	
	@Test 
	void testRemoveEntryForKeyNotPresent() {
		b = new ByteArrayHashMap<String>(27, 0.7f);
		assertNull(b.removeEntryForKey("key1".getBytes()));
	}

	// I test per AddEntry e CreateEntry sono molto simili, poiché prendono gli stessi
	// input. hash, key e value possono assumere qualsiasi valore, mentre per 
	// il bucketIndex questo deve essere maggiore o uguale di 0. Bastano quindi
	// 2 casi di test
	@Test
	void testAddEntry1() {
		b = new ByteArrayHashMap<String>(27, 0.7f);
		int hash = 123;
		byte[] key = {1,2,3,4,5};
		String value = "test";
		int bucketIndex = 0;
		b.addEntry(hash, key, value, bucketIndex);
		assertEquals(value, b.table[bucketIndex].value);
	}
	
	@Test
	void testAddEntryError() {
		b = new ByteArrayHashMap<String>(27, 0.7f);
		int hash = 123;
		byte[] key = {1,2,3,4,5};
		String value = "test";
		int bucketIndex = 0;
		Exception exception = assertThrows(Exception.class, ()->b.addEntry(hash, key, value, -5));
		assertNotNull(exception);
	}

	@Test
	void testCreateEntry() {
		b = new ByteArrayHashMap<String>(27, 0.7f);
		int hash = 123;
		byte[] key = {1,2,3,4,5};
		String value = "test";
		int bucketIndex = 0;
		b.createEntry(hash, key, value, bucketIndex);
		assertEquals(value, b.table[bucketIndex].value);
	}
	
	@Test
	void testCreateEntryErrors() {
		b = new ByteArrayHashMap<String>(27, 0.7f);
		int hash = 123;
		byte[] key = {1,2,3,4,5};
		String value = "test";
		int bucketIndex = 0;
		Exception exception = assertThrows(Exception.class, ()->b.createEntry(hash, key, value, -5));
		assertNotNull(exception);
	}
	
	@Test
	void TestGetKey() {
		byte[] key = {1,2,3,4,5};
		String value = "test";
		int hash = 123;
		ByteArrayHashMap.Entry<String> entry = new ByteArrayHashMap.Entry<String>(hash, key, value, null);
		assertEquals(key, entry.getKey());
	}
	
	@Test
	void TestGetValue() {
		byte[] key = {1,2,3,4,5};
		String value = "test";
		int hash = 123;
		ByteArrayHashMap.Entry<String> entry = new ByteArrayHashMap.Entry<String>(hash, key, value, null);
		assertEquals(value, entry.getValue());
	}

}
