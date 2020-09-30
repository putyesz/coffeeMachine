package machine;

import java.util.Scanner;

public class CoffeeMachine {
    State state = State.ON;
    int water;
    int milk;
    int coffeeBeans;
    int cups;
    int money;

    public CoffeeMachine() {
        this.water = 400;
        this.milk = 540;
        this.coffeeBeans = 120;
        this.cups = 9;
        this.money = 550;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        String first = scanner.nextLine().replace(" ", "");
        String second = scanner.nextLine().replace(" ", "");
        System.out.println(first);
        System.out.println(second);
        System.out.println(second.equals(first));


        CoffeeMachine coffeeMachine = new CoffeeMachine();

        while (coffeeMachine.state != State.OFF) {
            System.out.println(coffeeMachine.state.text);
            coffeeMachine.handleInput(scanner.next());
        }
    }

    enum State {
        ON("Write action (buy, fill, take, remaining, exit):"),
        BUY("What do you want to buy? 1 - espresso, 2 - latte 3 - cappuccino" +
                ", back - to main menu:"),
        FILLWATER("Write how many ml of water do you want to add:"),
        FILLMILK("Write how many ml of milk do you want to add:"),
        FILLBEANS("Write how many grams of coffee beans do you want to add:"),
        FILLCUPS("Write how many disposable cups do you want to add:"),
        OFF("");

        private final String text;

        State(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    enum Coffee {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);

        private int water;
        private int milk;
        private int beans;
        private int price;

        Coffee (int water, int milk, int beans, int price) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.price = price;
        }

        public int getWater() {
            return water;
        }

        public int getMilk() {
            return milk;
        }

        public int getBeans() {
            return beans;
        }

        public int getPrice() {
            return price;
        }
    }


    private void handleInput(String inp) {
        switch (state) {
            case ON:
                chooseAction(inp);
                break;
            case BUY:
                chooseCoffee(inp);
                break;
            case FILLWATER:
                addWater(inp);
                break;
            case FILLMILK:
                addMilk(inp);
                break;
            case FILLBEANS:
                addBeans(inp);
                break;
            case FILLCUPS:
                addCups(inp);
                break;
        }
    }

    private void chooseAction(String inp) {
        switch (inp) {
            case "buy":
                this.state = State.BUY;
                break;
            case "fill":
                this.state = State.FILLWATER;
                break;
            case "remaining":
                sout();
                break;
            case "take":
                takeMoney();
                break;
            case "exit":
                state = State.OFF;
                break;
            default:
                System.out.println("Unknown command!");
        }
    }

    private void chooseCoffee(String inp) {
        switch (inp) {
            case "1":
                makeCoffee(Coffee.ESPRESSO);
                break;
            case "2":
                makeCoffee(Coffee.LATTE);
                break;
            case "3":
                makeCoffee(Coffee.CAPPUCCINO);
                break;
            case "back":
                break;
        }
        this.state = State.ON;
    }

    private void makeCoffee(Coffee coffee) {
        String bottleneck;
        if (this.water < coffee.water) {
            bottleneck = "water";
        } else if (this.milk < coffee.milk) {
            bottleneck = "milk";
        } else if (this.coffeeBeans < coffee.beans) {
            bottleneck = "beans";
        } else if (this.cups < 1) {
            bottleneck = "cups";
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            this.water -= coffee.water;
            this.milk -= coffee.milk;
            this.coffeeBeans -= coffee.beans;
            this.money += coffee.price;
            this.cups--;
            return;
        }
        System.out.printf("Sorry, not enough %s!\n", bottleneck);
    }

    private void addWater(String inp) {
        this.water += Integer.parseInt(inp);
        this.state = State.FILLMILK;
    }
    private void addMilk(String inp) {
        this.milk += Integer.parseInt(inp);
        this.state = State.FILLBEANS;
    }
    private void addBeans(String inp) {
        this.coffeeBeans += Integer.parseInt(inp);
        this.state = State.FILLCUPS;
    }
    private void addCups(String inp) {
        this.cups += Integer.parseInt(inp);
        this.state = State.ON;
    }

    private void sout() {
        System.out.printf("The coffee machine has:\n%d of water\n%d of " +
            "milk\n%d of coffee beans\n%d of disposable cups\n%d of money\n",
            this.water, this.milk, this.coffeeBeans, this.cups, this.money);
    }

    private void takeMoney() {
        System.out.printf("I gave you $%d\n", this.money);
        this.money = 0;
    }
}
