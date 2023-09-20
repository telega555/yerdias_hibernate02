package com.yerdias.controller;

import com.yerdias.service.TicketService;
import com.yerdias.service.UserService;
import java.util.Scanner;

public class TicketController {
    public static void buyTicket() {
        TicketService.readTicket();
        Long ticketId = 0L;
        boolean scannerLoop = true;
        while (scannerLoop) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(
                    "Выберите билет чтобы купить. \n" +
                            "Для выбора билета введите его идентификатор:\n" +
                            "-----------------------------------------------------\n"
            );
            ticketId = scanner.nextLong();
            if (!TicketService.isTicketExistsById(ticketId)) {
                System.out.println("Вы указали недействительный Id пользователя!");
                continue;
            }
            TicketService.buySelectedTicket(ticketId, UserService.getCurrentUser());
        }
    }

    public static void returnTicket(boolean b) {

    }
}
