package com.jsbv99.gymmate

import com.jsbv99.gymmate.data.auth.filterName
import com.jsbv99.gymmate.data.auth.isValidName
import org.junit.Assert.*
import org.junit.Test

class NameValidationTest {
    @Test fun acceptsAccentsAndSpacesButFiltersDigitsAndSymbols() {
        assertEquals("José Muñoz", filterName("José 1Muñoz!"))
        assertTrue(isValidName("José Muñoz"))
        assertFalse(isValidName("Ab 1"))
        assertFalse(isValidName("A  B"))
    }
}
