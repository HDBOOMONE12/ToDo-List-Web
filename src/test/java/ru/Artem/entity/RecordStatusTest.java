package ru.Artem.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecordStatusTest {

    @Test
    void enumValues_ShouldContainActiveAndDone() {
        RecordStatus[] values = RecordStatus.values();

        assertEquals(2, values.length);
        assertTrue(contains(values, RecordStatus.ACTIVE));
        assertTrue(contains(values, RecordStatus.DONE));
    }

    @Test
    void valueOf_WithActive_ShouldReturnActive() {
        RecordStatus result = RecordStatus.valueOf("ACTIVE");

        assertEquals(RecordStatus.ACTIVE, result);
    }

    @Test
    void valueOf_WithDone_ShouldReturnDone() {
        RecordStatus result = RecordStatus.valueOf("DONE");

        assertEquals(RecordStatus.DONE, result);
    }

    @Test
    void valueOf_WithInvalidValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            RecordStatus.valueOf("INVALID");
        });
    }

    @Test
    void enumConstants_ShouldBeAccessible() {
        assertNotNull(RecordStatus.ACTIVE);
        assertNotNull(RecordStatus.DONE);
    }

    @Test
    void enumConstants_ShouldBeDifferent() {
        assertNotEquals(RecordStatus.ACTIVE, RecordStatus.DONE);
    }

    @Test
    void enumConstants_ShouldBeComparable() {
        assertTrue(RecordStatus.ACTIVE.compareTo(RecordStatus.DONE) < 0);
        assertTrue(RecordStatus.DONE.compareTo(RecordStatus.ACTIVE) > 0);
        assertEquals(0, RecordStatus.ACTIVE.compareTo(RecordStatus.ACTIVE));
        assertEquals(0, RecordStatus.DONE.compareTo(RecordStatus.DONE));
    }

    @Test
    void enumConstants_ShouldHaveCorrectOrdinal() {
        assertEquals(0, RecordStatus.ACTIVE.ordinal());
        assertEquals(1, RecordStatus.DONE.ordinal());
    }

    @Test
    void enumConstants_ShouldHaveCorrectName() {
        assertEquals("ACTIVE", RecordStatus.ACTIVE.name());
        assertEquals("DONE", RecordStatus.DONE.name());
    }

    @Test
    void enumConstants_ShouldBeSerializable() {
        assertTrue(RecordStatus.ACTIVE instanceof java.io.Serializable);
        assertTrue(RecordStatus.DONE instanceof java.io.Serializable);
    }

    @Test
    void enumConstants_ShouldBeComparableWithEquals() {
        assertEquals(RecordStatus.ACTIVE, RecordStatus.ACTIVE);
        assertEquals(RecordStatus.DONE, RecordStatus.DONE);
        assertNotEquals(RecordStatus.ACTIVE, RecordStatus.DONE);
    }

    @Test
    void enumConstants_ShouldHaveConsistentHashCode() {
        assertEquals(RecordStatus.ACTIVE.hashCode(), RecordStatus.ACTIVE.hashCode());
        assertEquals(RecordStatus.DONE.hashCode(), RecordStatus.DONE.hashCode());
        assertNotEquals(RecordStatus.ACTIVE.hashCode(), RecordStatus.DONE.hashCode());
    }

    private boolean contains(RecordStatus[] values, RecordStatus status) {
        for (RecordStatus value : values) {
            if (value == status) {
                return true;
            }
        }
        return false;
    }
}
