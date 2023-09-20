package com.yerdias;

import com.yerdias.controller.MenuController;
import com.yerdias.entity.Role;
import com.yerdias.service.MovieService;
import com.yerdias.service.TicketService;
import com.yerdias.service.UserService;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        initDefaultData();
        MenuController.showAuthMenu();
    }

    /**
     * Метод инициализирует дефолтные данные для работы приложения
     */
    public static void initDefaultData() {
        if (UserService.countUsers() == 0) {
            UserService.createUser("admin", "12345", Role.ADMIN);
            UserService.createUser("user", "12345", Role.USER);
        }
        if (MovieService.countMovies() == 0) {
            MovieService.createMovie("Под солнцем Тосканы", LocalDateTime.now(),2003);
            MovieService.createMovie("Никогда не говори никогда", LocalDateTime.now(),2019);
            MovieService.createMovie("Мемуары гейши", LocalDateTime.now(),2017);
            MovieService.createMovie("Из Парижа с любовью", LocalDateTime.now(),2000);
            MovieService.createMovie("Дом большой мамочки 3", LocalDateTime.now(),2003);
        }
        if (TicketService.countTickets() == 0) {
            TicketService.addTicket(1, 30.0, "Под солнцем Тосканы");
            TicketService.addTicket(2, 30.0, "Под солнцем Тосканы");
            TicketService.addTicket(3, 30.0, "Под солнцем Тосканы");
            TicketService.addTicket(4, 30.0, "Под солнцем Тосканы");
            TicketService.addTicket(5, 30.0, "Под солнцем Тосканы");
            TicketService.addTicket(1, 20.0, "Никогда не говори никогда");
            TicketService.addTicket(2, 20.0, "Никогда не говори никогда");
            TicketService.addTicket(3, 20.0, "Никогда не говори никогда");
            TicketService.addTicket(4, 20.0, "Никогда не говори никогда");
            TicketService.addTicket(5, 20.0, "Никогда не говори никогда");
            TicketService.addTicket(1, 15.0, "Мемуары гейши");
            TicketService.addTicket(2, 15.0, "Мемуары гейши");
            TicketService.addTicket(3, 15.0, "Мемуары гейши");
            TicketService.addTicket(4, 15.0, "Мемуары гейши");
            TicketService.addTicket(5, 15.0, "Мемуары гейши");
            TicketService.addTicket(1, 10.0, "Из Парижа с любовью");
            TicketService.addTicket(2, 10.0, "Из Парижа с любовью");
            TicketService.addTicket(3, 10.0, "Из Парижа с любовью");
            TicketService.addTicket(4, 10.0, "Из Парижа с любовью");
            TicketService.addTicket(5, 10.0, "Из Парижа с любовью");
            TicketService.addTicket(1, 5.0, "Дом большой мамочки 3");
            TicketService.addTicket(2, 5.0, "Дом большой мамочки 3");
            TicketService.addTicket(3, 5.0, "Дом большой мамочки 3");
            TicketService.addTicket(4, 5.0, "Дом большой мамочки 3");
            TicketService.addTicket(5, 5.0, "Дом большой мамочки 3");
        }
    }

}
