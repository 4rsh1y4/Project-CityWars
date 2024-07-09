package phase1;

import java.util.Random;

public class Captcha {
    private static final String[][] DIGITS = {
            {
                    " *01* ",
                    "$6  2&",
                    "$2  3&",
                    "$3  4&",
                    " 0G67 "
            },

            {
                    "d888  ",
                    "  88  ",
                    "  77  ",
                    "  56  ",
                    "d87878"
            },

            {
                    " d432 ",
                    "22  *2",
                    "   %22",
                    "  2&2 ",
                    "22jh32"
            },
            {
                    " %44% ",
                    "2%  %5",
                    "  3$5 ",
                    "34  41",
                    " dggd3"
            },
            {
                    "gf  \\n",
                    "fg  %t",
                    "fh443g",
                    "    35",
                    "    28"
            },
            {
                    "5dd45 ",
                    "r5    ",
                    "rt565 ",
                    "    78",
                    "25675 "
            },
            {
                    " 4566 ",
                    "*4    ",
                    "76563 ",
                    "33  34",
                    " 6236 "
            },
            {
                    "7dddd7",
                    "   dh ",
                    "  hd  ",
                    " dd   ",
                    "df    "
            },

            {
                    " 8888 ",
                    "45  54",
                    " 8er2 ",
                    "*7  Gt",
                    " *775 "
            },
            {
                    " 2345 ",
                    "92  39",
                    " 49594",
                    "    36",
                    " 4562 "
            }
    };


    public static String generateCaptchaNumber(int length) {
        Random random = new Random();
        StringBuilder captchaNumber = new StringBuilder();
        for (int i = 0; i < length; i++) {
            captchaNumber.append(random.nextInt(10));
        }
        return captchaNumber.toString();
    }
    public static void displayCaptcha(String captchaNumber) {
        for (int row = 0; row < 5; row++) {
            for (int i = 0; i < captchaNumber.length(); i++) {
                int digit = Character.getNumericValue(captchaNumber.charAt(i));
                System.out.print(DIGITS[digit][row] + "  ");
            }
            System.out.println();
        }
    }

    public static String getCaptchaDisplay(String captchaNumber) {
        StringBuilder captchaDisplay = new StringBuilder();
        for (int row = 0; row < 5; row++) {
            for (int i = 0; i < captchaNumber.length(); i++) {
                int digit = Character.getNumericValue(captchaNumber.charAt(i));
                captchaDisplay.append(DIGITS[digit][row]).append("  ");
            }
            captchaDisplay.append("\n");
        }
        return captchaDisplay.toString();
    }


}
