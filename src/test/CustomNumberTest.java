package test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import datatypes.*;
import util.CNUtils;

class CustomNumberTest {
    @Test
    void appendDigitLeft() {
        CustomNumber n1 = new CustomNumber("+2.");
        n1.appendDigitLeft((byte) 5);
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+52.")));
        n1.removeNDigitsRight(2);
        n1.appendDigitLeft((byte) 3);
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+3.")));

        CustomNumber n2 = new CustomNumber("+.02");
        n2.appendDigitLeft((byte) 5);
        assertTrue(CNUtils.areEqual(n2, new CustomNumber("+.52")));
        n2.removeNDigitsRight(2);
        n2.appendDigitLeft((byte) 3);
        assertTrue(CNUtils.areEqual(n2, new CustomNumber("+3.")));
    }

    @Test
    void appendDigitRight() {
        CustomNumber n1 = new CustomNumber("+2.");
        n1.appendDigitRight((byte) 2);
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+2.2")));
        n1.removeNDigitsRight(2);
        n1.appendDigitRight((byte) 3);
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+.3")));

        CustomNumber n2 = new CustomNumber("+.02");
        n2.appendDigitRight((byte) 5);
        assertTrue(CNUtils.areEqual(n2, new CustomNumber("+.025")));
        n2.removeNDigitsRight(2);
        n2.appendDigitRight((byte) 3);
        assertTrue(CNUtils.areEqual(n2, new CustomNumber("+.3")));
    }

    @Test
    void clean() {
        CustomNumber n1 = new CustomNumber("+123.");
        n1.appendDigitLeft((byte) 0);
        n1.appendDigitRight((byte) 0);
        n1.clean();
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+123.")));

        CustomNumber n2 = new CustomNumber("+.456");
        n2.appendDigitLeft((byte) 0);
        n2.appendDigitRight((byte) 0);
        n2.clean();
        assertTrue(CNUtils.areEqual(n2, new CustomNumber("+.456")));

        CustomNumber n3 = new CustomNumber();
        n3.appendDigitLeft((byte) 0);
        n3.appendDigitLeft((byte) 0);
        n3.appendDigitRight((byte) 0);
        n3.appendDigitRight((byte) 0);
        n3.clean();
        assertTrue(n3.isZero());
        assertTrue(CNUtils.areEqual(n3, new CustomNumber()));
    }

    @Test
    void cropRelevant() {
        CustomNumber n1 = new CustomNumber("123456789.123456789");
        n1.cropRelevant(17);
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("123456789.12345678")));

        CustomNumber n2 = new CustomNumber("123456789.123456789");
        n2.cropRelevant(1);
        assertTrue(CNUtils.areEqual(n2, new CustomNumber("100000000.")));

        CustomNumber n3 = new CustomNumber("123456789.123456789");
        n3.cropRelevant(3);
        assertTrue(CNUtils.areEqual(n3, new CustomNumber("123000000.")));

        CustomNumber n4 = new CustomNumber("123456789.123456789");
        n4.cropRelevant(0);
        assertTrue(n4.isZero());

        CustomNumber n5 = new CustomNumber();
        n5.cropRelevant(3);
        assertTrue(n5.isZero());
    }

    @Test
    void evenOut() {
        CustomNumber n1 = new CustomNumber("+123000.");
        CustomNumber n2 = new CustomNumber("+.000123");

        n1.evenOut(n2);
        n2.evenOut(n1);

        CustomNumber n3 = new CustomNumber("+123.");
        for (int i = 0; i < 9; i++) {
            n3.appendDigitRight((byte) 0);
        }

        n3.shiftLeft(3);
        assertTrue(CNUtils.areEqual(n1, n3));

        n3.clean();
        for (int i = 0; i < 9; i++) {
            n3.appendDigitLeft((byte) 0);
        }

        n3.shiftRight(9);
        assertTrue(CNUtils.areEqual(n2, n3));
    }

    @Test
    void getDataLength() {
        CustomNumber n1 = new CustomNumber("+123.");
        assertEquals(3, n1.getDataLength());
        CustomNumber n2 = new CustomNumber("-.1234");
        assertEquals(4, n2.getDataLength());

        CustomNumber n3 = new CustomNumber();
        n3.appendDigitLeft((byte) 6);
        for (int i = 0; i < 10; i++) {
            n3.appendDigitRight((byte) 0);
            n3.shiftLeft();
        }
        n3.appendDigitRight((byte) 1);
        assertEquals(12, n3.getDataLength());
        n3.removeDigitRight();
        assertEquals(11, n3.getDataLength());
        n3.clean();
        assertEquals(1, n3.getDataLength());
    }

    @Test
    void invertSign() {
        CustomNumber n1 = new CustomNumber("+1.");
        assertTrue(n1.isPos());
        n1.invertSign();
        assertTrue(n1.isNeg());

        CustomNumber n2 = new CustomNumber("-1.");
        assertTrue(n2.isNeg());
        n2.invertSign();
        assertTrue(n2.isPos());

        CustomNumber n3 = new CustomNumber();

        RuntimeException thrown = assertThrows(RuntimeException.class, n3::invertSign);
        assertEquals("invertSign but number is Zero!", thrown.getMessage());
    }

    @Test
    void isZero() {
        CustomNumber n1 = new CustomNumber();
        assertTrue(n1.isZero());

        n1.appendDigitLeft((byte) 0);
        assertFalse(n1.isZero());

        n1.clean();
        assertTrue(n1.isZero());
    }

    @Test
    void removeDigitLeft() {
        CustomNumber n1 = new CustomNumber("+12.34");

        n1.removeDigitLeft();
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+2.34")));

        n1.removeDigitLeft();
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+.34")));

        n1.removeDigitLeft();
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+.04")));

        n1.removeDigitLeft();
        assertTrue(n1.isZero());

        RuntimeException thrown = assertThrows(RuntimeException.class, n1::removeDigitLeft);
        assertEquals("removeDigitLeft but number is Zero!", thrown.getMessage());
    }

    @Test
    void removeDigitRight() {
        CustomNumber n1 = new CustomNumber("+12.34");

        n1.removeDigitRight();
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+12.3")));

        n1.removeDigitRight();
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+12.")));

        n1.removeDigitRight();
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+10.")));

        n1.removeDigitRight();
        assertTrue(n1.isZero());

        RuntimeException thrown = assertThrows(RuntimeException.class, n1::removeDigitRight);
        assertEquals("removeDigitRight but number is Zero!", thrown.getMessage());
    }


    @Test
    void removeNDigitsLeft() {
        CustomNumber n1 = new CustomNumber("+123456789.123456789");
        n1.removeNDigitsLeft(3);
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+456789.123456789")));

        n1.removeNDigitsLeft(14);
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+.000000009")));

        n1.removeNDigitsLeft(1);
        assertTrue(n1.isZero());

        n1.removeNDigitsLeft(1);
        assertTrue(n1.isZero());
    }

    @Test
    void removeNDigitsRight() {
        CustomNumber n1 = new CustomNumber("+123456789.123456789");
        n1.removeNDigitsRight(3);
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+123456789.123456")));

        n1.removeNDigitsRight(14);
        assertTrue(CNUtils.areEqual(n1, new CustomNumber("+100000000.")));

        n1.removeNDigitsRight(1);
        assertTrue(n1.isZero());

        n1.removeNDigitsRight(1);
        assertTrue(n1.isZero());
    }

    @Test
    void setPhantomZeros() {
        CustomNumber n1 = new CustomNumber("+1234.");
        CustomNumber n2 = new CustomNumber("+1234.");
        n1.setPhantomZeros();
        assertTrue(CNUtils.areEqual(n1, n2));

        CustomNumber n3 = new CustomNumber("+1200.");
        CustomNumber n4 = new CustomNumber("+1200.");
        n4.appendDigitRight((byte) 0);
        n4.appendDigitRight((byte) 0);
        n3.setPhantomZeros();
        assertTrue(CNUtils.areEqual(n3, n4));

        CustomNumber n5 = new CustomNumber();
        RuntimeException thrown = assertThrows(RuntimeException.class, n5::setPhantomZeros);
        assertEquals("setPhantomZeros but number is Zero!", thrown.getMessage());
    }

    @Test
    void set() {
        CustomNumber n1 = new CustomNumber("+00000123.40000000");
        assertTrue(n1.isPos());
        assertEquals(3, n1.getLBC());
        assertEquals(1, n1.getLAC());
        CustomNumber n2 = new CustomNumber();
        assertTrue(n2.isPos());
        n2.appendDigitLeft((byte) 3);
        n2.appendDigitLeft((byte) 2);
        n2.appendDigitLeft((byte) 1);
        n2.appendDigitRight((byte) 4);
        assertTrue(CNUtils.areEqual(n1, n2));
        CustomNumber n3 = new CustomNumber("-.000001");
        assertEquals(-5, n3.getLBC());
        assertTrue(n3.isNeg());
        assertFalse(CNUtils.areEqual(n1, n3));
        n3.set(n2);
        assertTrue(CNUtils.areEqual(n1, n3));
    }

    @Test
    void setZero() {
        CustomNumber n1 = new CustomNumber("-123.456");
        assertFalse(n1.isZero());
        assertTrue(n1.isNeg());
        assertEquals(3, n1.getLBC());
        assertEquals(3, n1.getLAC());
        assertEquals(6, n1.getDataLength());
        assertEquals(6, n1.getDigitArray().size());

        n1.setZero();
        assertTrue(n1.isZero());
        assertTrue(n1.isPos());
        assertEquals(0, n1.getLBC());
        assertEquals(0, n1.getLAC());
        assertEquals(0, n1.getDataLength());
        assertEquals(0, n1.getDigitArray().size());
    }

    @Test
    void shiftLeft() {
        CustomNumber n1 = new CustomNumber("+100.");
        CustomNumber n2 = new CustomNumber("+1.");
        CustomNumber n3 = new CustomNumber("+1.");

        n2.shiftLeft();
        n2.shiftLeft();

        n3.shiftLeft(2);
        assertTrue(CNUtils.areEqual(n1, n2));
        assertTrue(CNUtils.areEqual(n2, n3));

        CustomNumber n4 = new CustomNumber();

        RuntimeException thrown = assertThrows(RuntimeException.class, n4::shiftLeft);
        assertEquals("shiftLeft but number is Zero!", thrown.getMessage());
    }

    @Test
    void shiftRight() {
        CustomNumber n1 = new CustomNumber("+1.");
        CustomNumber n2 = new CustomNumber("+100.");
        CustomNumber n3 = new CustomNumber("+100.");

        n2.shiftRight();
        n2.shiftRight();

        n3.shiftRight(2);
        assertTrue(CNUtils.areEqual(n1, n2));
        assertTrue(CNUtils.areEqual(n2, n3));

        CustomNumber n4 = new CustomNumber();

        RuntimeException thrown = assertThrows(RuntimeException.class, n4::shiftRight);
        assertEquals("shiftRight but number is Zero!", thrown.getMessage());
    }
}