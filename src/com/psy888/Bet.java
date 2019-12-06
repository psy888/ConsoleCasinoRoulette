package com.psy888;

import java.util.Arrays;
import java.util.Scanner;

public class Bet {

    public static final BetType straightUp = new BetType("на 1 число", 35);
    public static final BetType splitUp = new BetType("на два числа", 17);
    public static final BetType streetBet = new BetType("на ряд", 11);
    public static final BetType splitBet = new BetType("на три цифры (0 и лбые 2 из первого ряда)", 11);
    public static final BetType cornerBet = new BetType("на четыре числа или каре", 8);
    public static final BetType sixLineBet = new BetType("на два ряда", 5);
    public static final BetType columnBet = new BetType("на колонку", 2);
    //    public static final BetType dozenBet = new BetType("на дюжины", 2);
//    public static final BetType halfBet = new BetType("на большее-меньшее", 1);
    public static final BetType evenOddBet = new BetType("чёт-нечёт", 1);


    //тип ставки - константы
    //сумма ставки
    private int betSum;
    private BetType betType;
    private int[] betNums;
    private UI ui = new UI();

    //конструктор
    public Bet() {
        this.betType = ui.multiplyChoice("Выберете тип ставки:",
                straightUp, splitUp, streetBet, splitBet, cornerBet,
                sixLineBet, columnBet, evenOddBet);

        int sum = ui.getInputInt("Введте сумму ставки : ");
        this.betSum = sum;
        do {
            try {
                this.betNums = inputBetNums();
            } catch (Exception e) {
                ui.printMsg(e.getMessage());
            }
            System.out.println("Arrays.toString(betNums) = " + Arrays.toString(betNums));
        } while (betNums == null);
    }

    public int getBetSum() {
        return betSum;
    }

    public void setBetSum(int betSum) {
        this.betSum = betSum;
    }

    public String getBetType() {
        return betType.toString();
    }

    private int[] inputBetNums() throws Exception {
        Scanner sc = new Scanner(System.in);
        if (betType == straightUp) { // на одно число
            betNums = new int[]{ui.getInputInt("Введите число от 1 до 36")};
        }

        if (betType == splitUp) { //на 2 числа
            int[] result = ui.getMultipleArgs("Введите два смежных числа от 1 до 36 ", 2);
//            System.out.println("Введите два смежных числа от 1 до 36 ");
//            int[] result = new int[]{sc.nextInt(), sc.nextInt()};
            int a = result[0];
            int b = result[1];
            if (b == a + 1 || b == a - 1 || b == a + 3 || b == a - 3) {
                betNums = result;
            } else {
                throw new Exception("Два числа должны быть соседними");
            }
        }

        if (betType == streetBet) { //ставка на ряд
            int row = ui.getInputInt("Введите номер ряда (1-12): ") - 1;
            if (row < 0 || row > 11) throw new Exception("Введен неверный номер ряда");
            betNums = Game.gf[row];
        }
        if (betType == splitBet) { //ставка на три числа
            int second = ui.getInputInt("Введите второе число: ");
            int third = ui.getInputInt("Введите третье число: ");
            if (third < 1 || third > 3 && second < 1 || second > 3)
                throw new Exception("Ставка на три числа подразумевает ставку на 0 и любые два числа из первого ряда");
            betNums = new int[]{0, second, third};
        }
        if (betType == cornerBet) { //на четыре числа или каре
            int[] result = ui.getMultipleArgs("Введите 4 смежных числа 2 из одного ряда, 2 из соседнего ряда по порядку слева на право разделитель \",\" или \" \" - пробел", 4);
            if (result == null || (
                    result[0] + 3 != result[2] &&
                            result[0] - 1 != result[1]) ||
                    (result[2] - 1 != result[3] &&
                            result[1] + 3 != result[3]))
                throw new Exception("Неверный ввод " + Arrays.toString(result));
            betNums = result;
        }
        if (betType == sixLineBet) { //на два ряда
            int[] rows = ui.getMultipleArgs("Введите два номера смежных рядов. Разделитель \",\" или \" \" - пробел", 2);

            if (rows == null && ((rows[0] != rows[1] + 1) || (rows[0] != rows[1] - 1)))
                throw new Exception("Введен неверный номер ряда " + rows[0] + "," + rows[1]);

            int[] result = Arrays.copyOf(Game.gf[rows[0] - 1], Game.gf[rows[0] - 1].length * 2);
            System.arraycopy(Game.gf[rows[1] - 1], 0, result, Game.gf[rows[1] - 1].length, Game.gf[rows[1] - 1].length);
            betNums = result;
        }
        if (betType == columnBet) { //на столбец
            int column = ui.getInputInt("Введите номер столбца 1 - 3 :");

            if (column < 1 || column > 3) throw new Exception("Введен неверный номер столбца " + column);

            betNums = new int[Game.gf.length];
            for (int i = 0; i < betNums.length; i++) {
                betNums[i] = Game.gf[i][column - 1];
            }
        }

        if (betType == evenOddBet) { // чёт-нечёт
            int evenOdd = ui.getInputInt("Введите число от 1 - не четные, 2 - четные");
            betNums = new int[Game.gf.length * Game.gf[0].length / 2];
            int cnt = 0;
            for (int i = 0; i < Game.gf.length; i++) {
                for (int j = 0; j < Game.gf[i].length; j++) {
                    if (Game.gf[i][j] % 2 != 0 && evenOdd == 1) {
                        betNums[cnt++] = Game.gf[i][j];
                    } else if (Game.gf[i][j] % 2 == 0 && evenOdd == 2) {
                        betNums[cnt++] = Game.gf[i][j];
                    }
                }
            }
        }
        return betNums;
    }


    public int getRatio() {
        return betType.getRatio();
    }

    //вернуть массив чисел входящих в ставку
    public int[] getBetNums() {
        return this.betNums;
    }
}
