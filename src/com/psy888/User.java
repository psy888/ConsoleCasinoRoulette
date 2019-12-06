package com.psy888;


import java.util.ArrayList;

public class User {
    private String name;
    private int money;
    private ArrayList<Bet> bets;
    private UI ui;

    public User(String name, int money) {
        this.name = name;
        this.money = money;
        this.bets = new ArrayList<>();
        this.ui = new UI();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<Bet> getBets() {
        return bets;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void makeBet() {
        if(money<=0){
            this.addMoney(Integer.parseInt(ui.getInputStr("У вас недостаточно денег. " +
                    "Хотите пополнить счет? введите сумму пополнения или 0 для выхода")));
            if(money<=0){
                ui.printMsg("Игра окончена!");
                System.exit(0);
            }
        }
        Bet bet = new Bet();
        if (this.money >= bet.getBetSum()) {
            money -= bet.getBetSum();
        } else {
            bet.setBetSum(money);
            ui.printMsg("У вас недостаточно средств ваша ставка снижена :" + money + " All IN!");
            money = 0;
        }
        bets.add(bet);
    }
}
