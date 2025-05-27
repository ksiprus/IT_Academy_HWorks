package by.ksiprus;

public class MultiConverter {

    private static final String[] units = {"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
    private static final String[] unitsFemale = {"", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
    private static final String[] teens = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
    private static final String[] tens = {"", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
    private static final String[] hundreds = {"", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"};

    private static String DigitToWords(int num, boolean female) {
        if (num < 0 || num > 999) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 0 до 999");
        }
        if (num == 0) return "";
        StringBuilder words = new StringBuilder();
        words.append(hundreds[num / 100]).append(" ");
        int rem = num % 100;
        if (rem >= 10 && rem <= 19) {
            words.append(teens[rem - 10]).append(" ");
        } else {
            words.append(tens[rem / 10]).append(" ");
            if (female) {
                words.append(unitsFemale[rem % 10]).append(" ");
            } else {
                words.append(units[rem % 10]).append(" ");
            }
        }
        return words.toString().trim();
    }

    public static String numberToWords(double number) {
        if (number < -999999999.99 || number > 999999999.99) {
            return "число вне допустимого диапазона";
        }

        if (number == 0) {
            return "ноль";
        }

        StringBuilder result = new StringBuilder();

        if (number < 0) {
            result.append("минус ");
            number = Math.abs(number);
        }

        double fractionalTest = number - Math.floor(number);
        boolean hasDecimal = fractionalTest > 0;

        number = Math.round(number * 100.0) / 100.0;

        long wholePart = (long) number;
        int fractionalPart = (int) Math.round((number - wholePart) * 100);

        int millions = (int) (wholePart / 1_000_000);
        int thousands = (int) ((wholePart % 1_000_000) / 1_000);
        int remainder = (int) (wholePart % 1_000);

        if (millions > 0) {
            result.append(DigitToWords(millions, false)).append(" ");
            result.append(getForm(millions, "миллион", "миллиона", "миллионов")).append(" ");
        }

        if (thousands > 0) {
            result.append(DigitToWords(thousands, true)).append(" ");
            result.append(getForm(thousands, "тысяча", "тысячи", "тысяч")).append(" ");
        }

        if (remainder > 0) {
            result.append(DigitToWords(remainder, false)).append(" ");
        }

        if (fractionalPart > 0 && hasDecimal) {
            if (wholePart > 0) {
                result.append(getForm((int) wholePart, "целая", "целых", "целых")).append(" и ");
            } else {
                result.append("ноль целых и ");
            }
            result.append(DigitToWords(fractionalPart, true)).append(" ");
            result.append(getForm(fractionalPart, "сотая", "сотых", "сотых"));
        }

        return result.toString().replaceAll("\\s+", " ").trim();
    }

    private static String getForm(int number, String form1, String form2, String form5) {
        number = number % 100;
        if (number >= 11 && number <= 14) {
            return form5;
        }
        switch (number % 10) {
            case 1:
                return form1;
            case 2:
            case 3:
            case 4:
                return form2;
            default:
                return form5;
        }
    }


    public static String toWeek(int day) {
        if (day < 0) throw new IllegalArgumentException("Количество дней не может быть отрицательным");
        if (day == 0) throw new IllegalArgumentException("Дней не может быть 0");

        int weeks = day / 7;
        int remainingDays = day % 7;
        StringBuilder result = new StringBuilder();

        if (weeks > 0) {
            result.append(weeks).append(" ");
            int lastDigit = weeks % 10;
            int lastTwoDigits = weeks % 100;
            if (weeks == 1) result.append("неделя");
            else if (lastTwoDigits >= 11 && lastTwoDigits <= 14) result.append("недель");
            else if (lastDigit == 1) result.append("неделя");
            else if (lastDigit >= 2 && lastDigit <= 4) result.append("недели");
            else result.append("недель");
        }

        if (remainingDays > 0) {
            if (weeks > 0) result.append(" и ");
            result.append(remainingDays).append(" ");
            int lastDigit = remainingDays % 10;
            if (remainingDays == 1) result.append("день");
            else if (lastDigit == 1) result.append("день");
            else if (lastDigit >= 2 && lastDigit <= 4) result.append("дня");
            else result.append("дней");
        }

        if (weeks == 0) {
            int lastDigit = day % 10;
            if (day == 1) return "1 день";
            else if (lastDigit == 1) return day + " день";
            else if (lastDigit >= 2 && lastDigit <= 4) return day + " дня";
            else return day + " дней";
        }

        return result.toString();
    }

    public static String toHoursMinuteSecondMillisecond(long millisecond) {
        if (millisecond <= 0) {
            throw new IllegalArgumentException("Количество миллисекунд не может быть отрицательным \n и должно быть больше 0 ");
        }

        long hours = millisecond / (60 * 60 * 1000);
        long minutes = (millisecond / (60 * 1000)) % 60;
        long seconds = (millisecond / 1000) % 60;
        long ms = millisecond % 1000;

        StringBuilder result = new StringBuilder();

        if (hours > 0) {
            result.append(hours).append(" ")
                    .append(getForm((int) hours, "час", "часа", "часов")).append(" ");
        }

        if (minutes > 0) {
            result.append(minutes).append(" ")
                    .append(getForm((int) minutes, "минута", "минуты", "минут")).append(" ");
        }

        if (seconds > 0) {
            result.append(seconds).append(" ")
                    .append(getForm((int) seconds, "секунда", "секунды", "секунд")).append(" ");
        }

        if (ms > 0) {
            result.append(ms).append(" ")
                    .append(getForm((int) ms, "миллисекунда", "миллисекунды", "миллисекунд"));
        }

        return result.toString().trim();
    }
        }