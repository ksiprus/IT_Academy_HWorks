package by.ksiprus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MultiConverterTest {

    @Test
    void testToWeekPositiveValues() {

        assertEquals("1 неделя и 3 дня", MultiConverter.toWeek(10));
        assertEquals("5 недель и 2 дня", MultiConverter.toWeek(37));
        assertEquals("44 недели и 1 день", MultiConverter.toWeek(309));
        assertEquals("7 недель", MultiConverter.toWeek(49));
        assertEquals("9 недель и 6 дней", MultiConverter.toWeek(69));
    }

    @Test
    void testToWeekLowerEdgeCases() {
        IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class, () -> MultiConverter.toWeek(-1));
        assertEquals("Количество дней не может быть отрицательным", exception1.getMessage());

        IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class, () -> MultiConverter.toWeek(0));
        assertEquals("Дней не может быть 0", exception2.getMessage());
    }

    @Test
    void testToWeekSingleWeeksAndDays() {
        assertEquals("1 день", MultiConverter.toWeek(1));
        assertEquals("2 дня", MultiConverter.toWeek(2));
        assertEquals("3 дня", MultiConverter.toWeek(3));
        assertEquals("4 дня", MultiConverter.toWeek(4));
        assertEquals("1 неделя", MultiConverter.toWeek(7));
        assertEquals("1 неделя и 1 день", MultiConverter.toWeek(8));
    }

    @Test
    void testNumberToWordsPositiveWholeNumber() {
        assertEquals("сто двадцать три", MultiConverter.numberToWords(123));
        assertEquals("один миллион двести тридцать четыре тысячи пятьсот шестьдесят семь", MultiConverter.numberToWords(1234567));
        assertEquals("девять миллионов девятьсот девяносто девять тысяч девятьсот девяносто девять", MultiConverter.numberToWords(9999999));
    }

    @Test
    void testNumberToWordsNegativeWholeNumber() {
        assertEquals("минус сто двадцать три", MultiConverter.numberToWords(-123));
        assertEquals("минус один миллион двести тридцать четыре тысячи пятьсот шестьдесят семь", MultiConverter.numberToWords(-1234567));
    }

    @Test
    void testNumberToWordsWithFractions() {
        assertEquals("двадцать три целых и сорок пять сотых", MultiConverter.numberToWords(23.45));
        assertEquals("ноль целых и девяносто девять сотых", MultiConverter.numberToWords(0.99));
        assertEquals("минус сто двадцать три целых и сорок пять сотых", MultiConverter.numberToWords(-123.45));
    }

    @Test
    void testNumberToWordsZero() {
        assertEquals("ноль", MultiConverter.numberToWords(0));
        assertEquals("ноль целых и две сотых", MultiConverter.numberToWords(0.02));
    }

    @Test
    void testNumberToWordsEdgeCases() {
        assertEquals("число вне допустимого диапазона", MultiConverter.numberToWords(1000000000));
        assertEquals("число вне допустимого диапазона", MultiConverter.numberToWords(-1000000000));
    }

    @Test
    void testNumberToWordsLargeFractions() {
        assertEquals("один миллион двести тридцать четыре тысячи пятьсот шестьдесят семь целых и восемьдесят девять сотых",
                MultiConverter.numberToWords(1234567.89));
        assertEquals("минус восемь миллионов семьсот шестьдесят пять тысяч четыреста тридцать два целых и десять сотых",
                MultiConverter.numberToWords(-8765432.10));
    }

    @Test
    void testToHoursMinuteSecondMillisecond() {
        assertEquals("1 час",
                MultiConverter.toHoursMinuteSecondMillisecond(3600000));
        assertEquals("35 минут",
                MultiConverter.toHoursMinuteSecondMillisecond(2100000));
        assertEquals("2 секунды",
                MultiConverter.toHoursMinuteSecondMillisecond(2000));
        assertEquals("234 миллисекунды",
                MultiConverter.toHoursMinuteSecondMillisecond(234));
       /* assertEquals("Количество миллисекунд не может быть отрицательным \\n и должно быть больше 0 \"",
                MultiConverter.toHoursMinuteSecondMillisecond(-1));*/


    }
}