package legacyfigher.dietary.newproducts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class OldProductTest {

    // 1. fajnie by bylo miec obsluzone przypadki brzegowe dla wartosci pol
    // - max wartosc
    // - min wartosc, etc.

    // 2. trzeba potestowac wyjatki z tych metod

    private OldProduct oldProduct;

    @AfterEach
    void tearDown() {
        oldProduct = null;
    }

    @Test
    void decrementCounterShouldDecreaseTheProductCount2ToValue1() {
        oldProduct = createDefaultOldProduct();

        assertEquals(2, oldProduct.counter);

        oldProduct.decrementCounter();

        assertEquals(1, oldProduct.counter);
    }

    @Test
    void decrementCounterShouldThrowIllegalStateExceptionWhenCounterIsNullOrIsZeroOrLess() {
        OldProduct oldProduct1 = new OldProduct(new BigDecimal("12.00"), "testDesc", "testLongDesc", 0);

        try {
            oldProduct1.decrementCounter();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Negative counter", e.getMessage());
        }

        OldProduct oldProduct2 = new OldProduct(new BigDecimal("12.00"), "testDesc", "testLongDesc", null);

        try {
            oldProduct2.decrementCounter();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("null counter", e.getMessage());
        }
    }

    @Test
    void decrementCounterShouldThrowIllegalStateExceptionWhenPriceIsNullOrItsSignumIsLessThanZero() {
        OldProduct oldProduct1 = new OldProduct(null, "testDesc", "testLongDesc", 1);

        try {
            oldProduct1.decrementCounter();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Invalid price", e.getMessage());
        }

        OldProduct oldProduct2 = new OldProduct(new BigDecimal(0), "testDesc", "testLongDesc", 1);
        try {
            oldProduct2.decrementCounter();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Invalid price", e.getMessage());
        }

        OldProduct oldProduct3 = new OldProduct(new BigDecimal(-1), "testDesc", "testLongDesc", 1);
        try {
            oldProduct3.decrementCounter();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Invalid price", e.getMessage());
        }
    }

    @Test
    void incrementCounterShouldIncreaseTheProductCount2ToValue3() {
        oldProduct = createDefaultOldProduct();

        assertEquals(2, oldProduct.counter);

        oldProduct.incrementCounter();

        assertEquals(3, oldProduct.counter);
    }

    @Test
    void incrementCounterShouldThrowIllegalStateExceptionWhenPriceIsInvalid() {
        OldProduct oldProduct1 = new OldProduct(null, "testDesc", "testLongDesc", 1);

        try {
            oldProduct1.incrementCounter();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Invalid price", e.getMessage());
        }

        OldProduct oldProduct2 = new OldProduct(new BigDecimal(0), "testDesc", "testLongDesc", 1);

        try {
            oldProduct2.incrementCounter();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Invalid price", e.getMessage());
        }

        OldProduct oldProduct3 = new OldProduct(new BigDecimal(-1), "testDesc", "testLongDesc", 1);
        try {
            oldProduct3.incrementCounter();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Invalid price", e.getMessage());
        }
    }

    @Test
    void incrementCounterShouldThrowIllegalStateExceptionWhenCounterIsNullOrBelowZero() {
        OldProduct oldProduct1 = new OldProduct(new BigDecimal(1), "test", "test", null);

        try {
            oldProduct1.incrementCounter();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("null counter", e.getMessage());
        }

        OldProduct oldProduct2 = new OldProduct(new BigDecimal(1), "test", "test", -2);

        try {
            oldProduct2.incrementCounter();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Negative counter", e.getMessage());
        }
    }

    @Test
    void productPriceShouldBeChangedFromValue12To10() {
        oldProduct = createDefaultOldProduct();

        assertEquals(new BigDecimal("12.00"), oldProduct.price);

        oldProduct.changePriceTo(new BigDecimal("10.00"));

        assertEquals(new BigDecimal("10.00"), oldProduct.price);
    }

    @Test
    void productPriceShouldNotBeChangedIfCounterIsNullAndIllegalStateExceptionShouldBeThrown() {
        oldProduct = new OldProduct(new BigDecimal(1), "test", "test", null);

        try {
            oldProduct.changePriceTo(new BigDecimal(0));
            fail();
        } catch (IllegalStateException e) {
            assertEquals("null counter", e.getMessage());
            assertEquals(new BigDecimal(1), oldProduct.price);
        }
    }

    @Test
    void productPriceShouldNotBeChangedIfCounterIsBelowZero() {
        oldProduct = new OldProduct(new BigDecimal(1), "test", "test", -1);

        assertEquals(new BigDecimal(1), oldProduct.price);

        oldProduct.changePriceTo(new BigDecimal(2));

        assertEquals(new BigDecimal(1), oldProduct.price);
    }

    @Test
    void productPriceShouldNotBeChangedWhenCounterIsAboveZeroButNewPriceIsNull() {
        oldProduct = new OldProduct(new BigDecimal(1), "test", "test", 1);

        assertEquals(new BigDecimal(1), oldProduct.price);

        try {
            oldProduct.changePriceTo(null);
        } catch (IllegalStateException e) {
            assertEquals("new price null", e.getMessage());
            assertEquals(new BigDecimal(1), oldProduct.price);
        }
    }

    @Test
    void productDescriptionShortAndLongShouldBeReplacedWithANewValue() {
        oldProduct = createDefaultOldProduct();

        assertEquals("testDesc *** testLongDesc", oldProduct.formatDesc());

        oldProduct.replaceCharFromDesc("test", "new");

        assertEquals("newDesc *** newLongDesc", oldProduct.formatDesc());
        assertEquals("newLongDesc", oldProduct.longDesc);
    }

    @Test
    void formatDescriptionShouldCombineShortAndLongDescription() {
        oldProduct = createDefaultOldProduct();

        assertEquals("testDesc *** testLongDesc", oldProduct.formatDesc());
    }

    private static OldProduct createDefaultOldProduct() {
        return new OldProduct(new BigDecimal("12.00"), "testDesc", "testLongDesc", 2);
    }
}