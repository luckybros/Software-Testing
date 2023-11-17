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
public class StudentTestTheories {
	ByteArrayHashMap b;

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
	
}
