package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import datatypes.*;
import util.CNUtils;

class CNUtilsTest {

    @Test
    void addUp() {
        //TODO
    }

    @Test
    void areEqual() {
        //TODO
    }

    @Test
    void divide() {
        //FIXME not all cases 64, some are identical.
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("10.5"), new CustomNumber("5.0")), new CustomNumber("2.1")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("10.5"), new CustomNumber("-5.0")), new CustomNumber("-2.1")));
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("10.5"), new CustomNumber()), new CustomNumber());
        });
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("10.5"), new CustomNumber()), new CustomNumber());
        });

        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("2.5"), new CustomNumber("4.0")), new CustomNumber("0.625")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("2.5"), new CustomNumber("-4.0")), new CustomNumber("-0.625")));
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("2.5"), new CustomNumber()), new CustomNumber());
        });
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("2.5"), new CustomNumber()), new CustomNumber());
        });

        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-10.5"), new CustomNumber("5.0")), new CustomNumber("-2.1")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-10.5"), new CustomNumber("-5.0")), new CustomNumber("2.1")));
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("-10.5"), new CustomNumber()), new CustomNumber());
        });
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("-10.5"), new CustomNumber()), new CustomNumber());
        });

        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-2.5"), new CustomNumber("4.0")), new CustomNumber("-0.625")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-2.5"), new CustomNumber("-4.0")), new CustomNumber("0.625")));
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("-2.5"), new CustomNumber()), new CustomNumber());
        });
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("-2.5"), new CustomNumber()), new CustomNumber());
        });

        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("10.0"), new CustomNumber("5.0")), new CustomNumber("2.0")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("10.0"), new CustomNumber("-5.0")), new CustomNumber("-2.0")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("10.0"), new CustomNumber("0.5")), new CustomNumber("20.0")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("10.0"), new CustomNumber("-0.5")), new CustomNumber("-20.0")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("2.0"), new CustomNumber("4.0")), new CustomNumber("0.5")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("2.0"), new CustomNumber("-4.0")), new CustomNumber("-0.5")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("2.0"), new CustomNumber("0.4")), new CustomNumber("5.0")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("2.0"), new CustomNumber("-0.4")), new CustomNumber("-5.0")));



        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-10.0"), new CustomNumber("5.0")), new CustomNumber("-2.0")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-10.0"), new CustomNumber("-5.0")), new CustomNumber("2.0")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-10.0"), new CustomNumber("0.5")), new CustomNumber("-20.0")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-10.0"), new CustomNumber("-0.5")), new CustomNumber("20.0")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-2.0"), new CustomNumber("4.0")), new CustomNumber("-0.5")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-2.0"), new CustomNumber("-4.0")), new CustomNumber("0.5")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-2.0"), new CustomNumber("0.4")), new CustomNumber("-5.0")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-2.0"), new CustomNumber("-0.4")), new CustomNumber("5.0")));



        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.1"), new CustomNumber("5.0")), new CustomNumber("0.02")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.1"), new CustomNumber("-5.0")), new CustomNumber("-0.02")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.1"), new CustomNumber("0.5")), new CustomNumber("0.2")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.1"), new CustomNumber("-0.5")), new CustomNumber("-0.2")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.15"), new CustomNumber("5.0")), new CustomNumber("0.03")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.15"), new CustomNumber("-5.0")), new CustomNumber("-0.03")));
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.15"), new CustomNumber()), new CustomNumber());
        });
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.15"), new CustomNumber()), new CustomNumber());
        });


        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.1"), new CustomNumber("5.0")), new CustomNumber("-0.02")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.1"), new CustomNumber("-5.0")), new CustomNumber("0.02")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.1"), new CustomNumber("0.5")), new CustomNumber("-0.2")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.1"), new CustomNumber("-0.5")), new CustomNumber("0.2")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.15"), new CustomNumber("5.0")), new CustomNumber("-0.03")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.15"), new CustomNumber("-5.0")), new CustomNumber("0.03")));
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.15"), new CustomNumber()), new CustomNumber());
        });
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.15"), new CustomNumber()), new CustomNumber());
        });

        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("4.0")), new CustomNumber()));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("-4.0")), new CustomNumber()));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("0.4")), new CustomNumber()));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("-0.4")), new CustomNumber()));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("4.0")), new CustomNumber()));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("-4.0")), new CustomNumber()));
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber()), new CustomNumber());
        });
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber()), new CustomNumber());
        });
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("4.0")), new CustomNumber()));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("-4.0")), new CustomNumber()));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("0.4")), new CustomNumber()));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("-0.4")), new CustomNumber()));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("4.0")), new CustomNumber()));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber("-4.0")), new CustomNumber()));
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber()), new CustomNumber());
        });
        assertThrows(RuntimeException.class, () -> {
            CNUtils.areEqual(CNUtils.divide(new CustomNumber(), new CustomNumber()), new CustomNumber());
        });

        /*assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("1234."), new CustomNumber("5678."),7), new CustomNumber("0.2173908")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("1234.5678"), new CustomNumber("5678."),8), new CustomNumber("0.2174763")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-1234."), new CustomNumber("5678."),8), new CustomNumber("-0.2173908")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-1234.5678"), new CustomNumber("5678."),8), new CustomNumber("-0.2174763")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.1234"), new CustomNumber("5678."),8), new CustomNumber("0.0000217")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.1234"), new CustomNumber("5678.9012"),8), new CustomNumber("0.0000217")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.1234"), new CustomNumber("5678.")), new CustomNumber("-0.0000217")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.1234"), new CustomNumber("5678.9012")), new CustomNumber("-0.0000217")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("1234."), new CustomNumber("-5678.")), new CustomNumber("-0.2173908")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("1234.5678"), new CustomNumber("-5678.")), new CustomNumber("-0.2174763")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-1234."), new CustomNumber("-5678.")), new CustomNumber("0.2173908")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-1234.5678"), new CustomNumber("-5678.")), new CustomNumber("0.2174763")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.1234"), new CustomNumber("-5678.")), new CustomNumber("-0.0000217")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("0.1234"), new CustomNumber("-5678.9012")), new CustomNumber("-0.0000217")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.1234"), new CustomNumber("-5678.")), new CustomNumber("0.0000217")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-0.1234"), new CustomNumber("-5678.9012")), new CustomNumber("0.0000217")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("1234."), new CustomNumber("0.5678")), new CustomNumber("2174.5284")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("1234.5678"), new CustomNumber("0.5678")), new CustomNumber("2175.1047")));
        assertTrue(CNUtils.areEqual(CNUtils.divide(new CustomNumber("-1234."), new CustomNumber("0.5678")), new CustomNumber("-2174.5284")));
        */

    }

    @Test
    void exp() {
        //TODO
    }

    @Test
    void isGreater() {
        //TODO
    }

    @Test
    void isGreaterOrEqual() {
        //TODO
    }

    @Test
    void isSmaller() {
        //TODO
    }

    @Test
    void isSmallerOrEqual() {
        //TODO
    }

    @Test
    void multiply() {
        //TODO
    }

    @Test
    void squareRoot() {
        //TODO
    }

    @Test
    void subTr() {
        //TODO
    }
}