package com.yerdias.controller;

import com.yerdias.service.MovieService;


import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.MatchResult;

public class MovieController {
//    public static void editUser() {
//        UserService.printUsers();
//        Long userId = 0L;
//        boolean scannerLoop = true;
//        while (scannerLoop) {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println(
//                    "Выберите пользователя для редактирования. \n" +
//                            "Для выбора пользователя введите его идентификатор:\n" +
//                            "-----------------------------------------------------\n"
//            );
//            userId = scanner.nextLong();
//            if (!UserService.isUserExistsById(userId)) {
//                System.out.println("Вы указали недействительный Id пользователя!");
//                continue;
//            }
//            if (UserService.getCurrentUser().getUserId().equals(userId)) {
//                System.out.println("Вы не можете отредактировать сами себя!");
//                continue;
//            }
//            System.out.println("Введите новое имя пользователя:");
//            scanner.nextLine();
//            String username = scanner.nextLine();
//            System.out.println("Введите новый пароль пользователя:");
//            String newPassword = scanner.nextLine();
//            System.out.println("Введите новую роль пользователя (USER, MANAGER, ADMIN):");
//            String role = scanner.nextLine();
//            UserService.updateUser(userId, username, newPassword, role);
//            System.out.println("Вы успешно отредактировали профиль выбранного пользователя!");
//            scannerLoop = false;
//        }
//        String message = String.format("Вы обновили профиль пользователя с id %s", userId);
//        LoggerService.addMessage(message, UserService.getCurrentUser());
//        MenuController.backToMenu();
//    }
    public static void editMovie() {
        MovieService.printMovies();
        Long userId = 0L;
        boolean scannerLoop = true;
        while (scannerLoop) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(
                    "Выберите пользователя для редактирования. \n" +
                            "Для выбора пользователя введите его идентификатор:\n" +
                            "-----------------------------------------------------\n"
            );
            userId = scanner.nextLong();
            if (!MovieService.isMovieExistsById(userId)) {
                System.out.println("Вы указали недействительный Id пользователя!");
                continue;
            }
            System.out.println("Введите название фильма:");
            String title = scanner.nextLine();
            MatchResult mr = scanner.match();
            System.out.println("введите месяц");
            int month = Integer.parseInt(mr.group(2));
            System.out.println("введите число");
            int day = Integer.parseInt(mr.group(1));
            System.out.println("введите время");
            int hour = Integer.parseInt(mr.group(3));
            System.out.println("введите минуты");
            int minute = Integer.parseInt(mr.group(4));
            LocalDateTime startTime = LocalDateTime.of(2023,month,day,hour,minute);
            int year = scanner.nextInt();
            MovieService.editMovie(userId,title,startTime,year);

        }
    }
//    public static void deleteUser() {
//        UserService.printUsers();
//        boolean scannerLoop = true;
//        Long userId = 0L;
//        while (scannerLoop) {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println(
//                    "Выберите удаляемого пользователя. \n" +
//                            "Для выбора пользователя введите его идентификатор:\n" +
//                            "-----------------------------------------------------\n"
//            );
//            userId = scanner.nextLong();
//            if (UserService.getCurrentUser().getUserId().equals(userId)) {
//                System.out.println("Вы не можете удалить самого себя!");
//                continue;
//            }
//            UserService.deleteUserById(userId);
//            System.out.println("Вы успешно удалили пользователя!");
//            scannerLoop = false;
//        }
//        String message = String.format("Вы удалили пользователя с id %s", userId);
//        LoggerService.addMessage(message, UserService.getCurrentUser());
//        MenuController.backToMenu();
//    }

    public static void deleteMovie() {
        MovieService.printMovies();
        boolean scannerLoop = true;
        Long movieId = 0L;
        while(scannerLoop){
            Scanner scanner = new Scanner(System.in);
            System.out.println(
                    "Выберите удаляемого пользователя. \n" +
                            "Для выбора пользователя введите его идентификатор:\n" +
                            "-----------------------------------------------------\n"
            );
            movieId = scanner.nextLong();
            MovieService.deleteMovieById(movieId);
            System.out.println("Вы успешно уалоиои фильм!");
            scannerLoop = false;
        }
        MenuController.backToMenu();
    }
}
