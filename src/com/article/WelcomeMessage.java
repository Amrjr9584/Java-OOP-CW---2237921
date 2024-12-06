package com.article;

public class WelcomeMessage {

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    public static void display() {
        System.out.println(CYAN + "***************************************************************");
        System.out.println(CYAN + "*                                                             *");
        System.out.println(GREEN + "*       _   _                     ______                     *");
        System.out.println(GREEN + "*      | \\ | |                   |  ____|                    *");
        System.out.println(GREEN + "*      |  \\| | _____   _____ _ __| |__   _ __   __ _  ___     *");
        System.out.println(GREEN + "*      | . ` |/ _ \\ \\ / / _ \\ '__|  __| | '_ \\ / _` |/ _ \\    *");
        System.out.println(GREEN + "*      | |\\  |  __/\\ V /  __/ |  | |____| | | | (_| |  __/    *");
        System.out.println(GREEN + "*      |_| \\_|\\___| \\_/ \\___|_|  |______|_| |_|\\__, |\\___|    *");
        System.out.println(GREEN + "*                                              __/ |          *");
        System.out.println(GREEN + "*                                             |___/           *");
        System.out.println(CYAN + "*                                                             *");
        System.out.println(RED + "*                       WELCOME TO NEWZ!                      *");
        System.out.println(CYAN + "*                                                             *");
        System.out.println(CYAN + "***************************************************************" + RESET);
    }
}
