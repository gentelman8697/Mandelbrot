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
        assertTrue(CNUtils.areEqual(n1,new CustomNumber("+52.")));
        n1.removeNDigitsRight(2);
        n1.appendDigitLeft((byte) 3);
        assertTrue(CNUtils.areEqual(n1,new CustomNumber("+3.")));

        CustomNumber n2 = new CustomNumber("+.02");
        n2.appendDigitLeft((byte) 5);
        assertTrue(CNUtils.areEqual(n2,new CustomNumber("+.52")));
        n2.removeNDigitsRight(2);
        n2.appendDigitLeft((byte) 3);
        assertTrue(CNUtils.areEqual(n2,new CustomNumber("+3.")));
    }

    @Test
    void appendDigitRight() {
        CustomNumber n1 = new CustomNumber("+2.");
        n1.appendDigitRight((byte) 2);
        assertTrue(CNUtils.areEqual(n1,new CustomNumber("+2.2")));
        n1.removeNDigitsRight(2);
        n1.appendDigitRight((byte) 3);
        assertTrue(CNUtils.areEqual(n1,new CustomNumber("+.3")));

        CustomNumber n2 = new CustomNumber("+.02");
        n2.appendDigitRight((byte) 5);
        assertTrue(CNUtils.areEqual(n2,new CustomNumber("+.025")));
        n2.removeNDigitsRight(2);
        n2.appendDigitRight((byte) 3);
        assertTrue(CNUtils.areEqual(n2,new CustomNumber("+.3")));
    }

    @Test
    void clean() {
        CustomNumber n1 = new CustomNumber("+123.");
        n1.appendDigitLeft((byte) 0);
        n1.appendDigitRight((byte) 0);
        n1.clean();
        assertTrue(CNUtils.areEqual(n1,new CustomNumber("+123.")));

        CustomNumber n2 = new CustomNumber("+.456");
        n2.appendDigitLeft((byte) 0);
        n2.appendDigitRight((byte) 0);
        n2.clean();
        assertTrue(CNUtils.areEqual(n2,new CustomNumber("+.456")));

        CustomNumber n3 = new CustomNumber();
        n3.appendDigitLeft((byte) 0);
        n3.appendDigitLeft((byte) 0);
        n3.appendDigitRight((byte) 0);
        n3.appendDigitRight((byte) 0);
        n3.clean();
        assertTrue(n3.isZero());
        assertTrue(CNUtils.areEqual(n3,new CustomNumber()));
    }

    @Test
    void evenOut() {
        CustomNumber n1 = new CustomNumber("+123000.");
        CustomNumber n2 = new CustomNumber("+.000123");

        n1.evenOut(n2);
        n2.evenOut(n1);

        CustomNumber n3 = new CustomNumber("+123.");
        for(int i = 0; i < 9 ; i++)
        {
            n3.appendDigitRight((byte) 0);
        }

        n3.shiftLeft(3);
        assertTrue(CNUtils.areEqual(n1,n3));

        n3.clean();
        for(int i = 0; i < 9; i++)
        {
            n3.appendDigitLeft((byte) 0);
        }

        n3.shiftRight(9);
        assertTrue(CNUtils.areEqual(n2,n3));
    }

    @Test
    void getDataLength() {
        CustomNumber n1 = new CustomNumber("+123.");
        assertEquals(3,n1.getDataLength());
        CustomNumber n2 = new CustomNumber("-.1234");
        assertEquals(4,n2.getDataLength());

        CustomNumber n3 = new CustomNumber();
        n3.appendDigitLeft((byte) 6);
        for(int i = 0; i < 10; i++)
        {
            n3.appendDigitRight((byte)0);
            n3.shiftLeft();
        }
        n3.appendDigitRight((byte)1);
        assertEquals(12,n3.getDataLength());
        n3.removeDigitRight();
        assertEquals(11,n3.getDataLength());
        n3.clean();
        assertEquals(1,n3.getDataLength());
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

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            n3.invertSign();
        });
        assertEquals("invertSign but number is Zero!",thrown.getMessage());
    }

    @Test
    void set() {
    }

    @Test
    void isNeg() {
    }

    @Test
    void isPos() {
    }

    @Test
    void isZero() {
    }

    @Test
    void printDigitArray() {
    }

    @Test
    void removeDataDigit() {
    }

    @Test
    void removeNDataDigitsLeft() {
    }

    @Test
    void cropRelevant() {
    }

    @Test
    void removeNDataDigitsRight() {
    }

    @Test
    void setValue() {
    }

    @Test
    void setZero() {
    }

    @Test
    void shiftLeft() {
    }

    @Test
    void testShiftLeft() {
    }

    @Test
    void shiftRight() {
    }

    @Test
    void decLBC() {
    }

    @Test
    void setPhantomZeros() {
    }

    @Test
    void areDigitArraysEqual() {
    }

    @Test
    void print() {
    }

    @Test
    void testToString() {
    }
}