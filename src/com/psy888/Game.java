package com.psy888;

import java.util.ArrayList;

public class Game {
    private User curUser;
    private int winNum = -1;
    public UI ui = new UI();

    public static int[][] gf = new int[12][3];

    {
        //заполнить игровое поле
        int num = 1;
        for (int i = 0; i < gf.length; i++) {
            for (int j = 0; j < gf[i].length; j++) {
                gf[i][j] = num++;
            }
        }

        //Создание пользователя
        curUser = new User(
                ui.getInputStr("Введите ваше имя:"),
                ui.getInputInt("Сколько у вас денег?")
        );
    }

    public void startGame() {
        //todo start
        String input = "";
        do {
            showGameField();

            do {
                curUser.makeBet();
                input = ui.getInputStr("Хотите сделать еще ставку? (y/n) или exit для выхода");
            }while (input.equalsIgnoreCase("y"));
            if(input.equalsIgnoreCase("exit"))break;
            winNum = spinOut();
            checkBets();

        } while (curUser.getMoney() > 0);
    }

    private void checkBets() {
        ArrayList<Bet> bets = curUser.getBets();
        boolean isWin = false;
        for (Bet bet : bets) {
            int[] nums = bet.getBetNums();
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == winNum) {
                    int winSum = bet.getBetSum() * bet.getRatio();
                    ui.printMsg("Выиграла ставка : " + bet.getBetType() + "\n" +
                            "Сумма ставки : " + bet.getBetSum() + "$\n" +
                            "Коэфициент : 1к" + bet.getRatio() + "\n" +
                            "Выиграш составил : " + winSum + "$");
                    curUser.addMoney(winSum);
                    isWin = true;
                }
            }
        }
        if(!isWin) {
            ui.printMsg("Выиграшных ставок нет(");
        }
    }


    private int spinOut() {
        int laps = (int) (Math.random() * 3) + 1; //[1-3]
        int result = (int) (Math.random() * 37);// [0-36]
        int total = laps * 36 + result;
        for (int i = 0; i <= total; i++) {
            int sleep = 0;
            if (i > total - 45) sleep = 50;
            if (i > total - 25) sleep = 100;
            if (i > total - 10) sleep = 200;
            if (i > total - 5) sleep = 300;
            if (i > total - 3) sleep = 500;
            if (sleep > 0) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                }
            }
            System.out.printf((i % 36 + 1) + ((i == total) ? "-!" : "->"));

        }


        ui.printMsg("\nВыиграло число " + (total % 36 + 1));
        return total % 36 + 1;
    }


    public void showGameField() {
        for (int i = 0; i < gf.length; i++) {
            if (i == 0) {
                System.out.println("\t \t  1   2   3");
                System.out.println("\t  ____|___|___|___");
            }
            System.out.printf(i + 1 + "\t-|\t");
            for (int j = 0; j < gf[i].length; j++) {

                System.out.printf(((gf[i][j] < 10) ? "  " : " ") + gf[i][j] + " ");
            }
            System.out.println();
        }
    }


}
