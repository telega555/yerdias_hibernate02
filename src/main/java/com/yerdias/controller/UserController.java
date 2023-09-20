package com.yerdias.controller;


import com.yerdias.entity.Role;
import com.yerdias.service.UserService;


import java.util.Scanner;


public class UserController{
    /**
     * Регистрация нового заказчика
     */
    public static void userRegistration() {
        boolean scannerLoop = true;
        String username = null, password = null;
        while (scannerLoop) {
            System.out.println("Регистрация на сайте:");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите имя пользователя:");
            username = scanner.nextLine();
            if (username == null || UserService.isUserExists(username)) {
                System.out.println("Указанный вами пользователь существует!");
                continue;
            }
            System.out.println("Введите пароль:");
            password = scanner.nextLine();
            scannerLoop = false;
        }
        UserService.createUser(username, password, Role.USER);
        String congrats = String.format(
                "Поздравляем! Вы успешно зарегистрировались в системе!\n" +
                        "-----------------------------------------------------\n" +
                        "Данные для входа в приложение:\n" +
                        "Ваш логин: %s, пароль: (указанный при регистрации)\n" +
                        "-----------------------------------------------------\n", username
        );
        System.out.println(congrats);
        String message = String.format("Пользователь %s зарегистрировался", username);
        userLogin();
    }

    /**
     * Метод для авторизации пользователя
     */
    public static void userLogin() {
        boolean scannerLoop = true;
        String username = null;
        while (scannerLoop) {
            System.out.println("Авторизация на сайте:");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите логин:");
            username = scanner.nextLine();
            if (username == null || !UserService.isUserExists(username)) {
                System.out.println("Указанный вами пользователь не существует!");
                continue;
            }
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();
            if (password == null || !UserService.isUserPasswordCorrect(username, password)) {
                System.out.println("Введенный вами пароль некорректен!");
                continue;
            }
            System.out.println("Вы успешно авторизовались в системе! ");
            UserService.setCurrentUser(username);
            scannerLoop = false;
        }
        MenuController.showMainMenu();
    }

    /**
     * Метод для удаления пользователя
     */
    public static void deleteUser() {
        UserService.printUsers();
        boolean scannerLoop = true;
        Long userId = 0L;
        while (scannerLoop) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(
                    "Выберите удаляемого пользователя. \n" +
                            "Для выбора пользователя введите его идентификатор:\n" +
                            "-----------------------------------------------------\n"
            );
            userId = scanner.nextLong();
            if (UserService.getCurrentUser().getCustomerId().equals(userId)) {
                System.out.println("Вы не можете удалить самого себя!");
                continue;
            }
            UserService.deleteUserById(userId);
            System.out.println("Вы успешно удалили пользователя!");
            scannerLoop = false;
        }
        String message = String.format("Вы удалили пользователя с id %s", userId);
        MenuController.backToMenu();
    }

    /**
     * Метод для выхода из системы
     */
    public static void logout() {
        UserService.setCurrentUser("null");
        userLogin();
    }

    /**
     * Метод для редактирования пользователя
     */
    public static void editUser() {
        UserService.printUsers();
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
            if (!UserService.isUserExistsById(userId)) {
                System.out.println("Вы указали недействительный Id пользователя!");
                continue;
            }
            if (UserService.getCurrentUser().getCustomerId().equals(userId)) {
                System.out.println("Вы не можете отредактировать сами себя!");
                continue;
            }
            System.out.println("Введите новое имя пользователя:");
            scanner.nextLine();
            String username = scanner.nextLine();
            System.out.println("Введите новый пароль пользователя:");
            String newPassword = scanner.nextLine();
            System.out.println("Введите новую роль пользователя (USER, MANAGER, ADMIN):");
            String role = scanner.nextLine();
            UserService.updateUser(userId, username, newPassword, role);
            System.out.println("Вы успешно отредактировали профиль выбранного пользователя!");
            scannerLoop = false;
        }
        String message = String.format("Вы обновили профиль пользователя с id %s", userId);
        MenuController.backToMenu();
    }


}

