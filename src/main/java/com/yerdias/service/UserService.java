package com.yerdias.service;

import com.yerdias.entity.Customer;
import com.yerdias.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserService {

    private static Customer authUser;

//    public static void createUser(String username, String password, Role role) {
//        Session session = HibernateUtil.getSession();
//        session.beginTransaction();
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(passwordEncryption(password));
//        user.setRole(role);
//        user.setAmountOfMoney(1000d);
//        session.save(user);
//        session.getTransaction().commit();
//        session.close();
//    }

    public static void createUser(String name, String password, com.yerdias.entity.Role role) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            Customer user = new Customer();
            user.setUserName(name);
            user.setRole(role);
            user.setPassword(password);

            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            // Обработка исключений, например, логирование или откат транзакции
            e.printStackTrace();
        }
    }



    public static boolean isUserExists(String username) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            // Вместо session.get() используйте session.createQuery() для проверки существования пользователя.
            Query<Customer> query = session.createQuery("FROM Customer WHERE userName = :username", Customer.class);
            query.setParameter("username", username);

            Customer user = query.uniqueResult();

            transaction.commit();

            // Проверяем, был ли найден пользователь.
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static Customer printUsers() {
        try {
            Session session = HibernateUtil.getSession();
            Query<Customer> query = session.createQuery("FROM Customer ", Customer.class);
            return (Customer) query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isUserPasswordCorrect(String username, String password) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            // Создаем запрос HQL для поиска пользователя по username
            Query<Customer> query = session.createQuery("FROM Customer WHERE userName = :username", Customer.class);
            query.setParameter("username", username);

            Customer user = query.uniqueResult(); // Получаем пользователя

            // Проверяем, правильный ли пароль
            boolean passwordCorrect = user != null && user.getPassword().equals(password);

            transaction.commit(); // Завершаем транзакцию
            return passwordCorrect;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае исключения
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }




    public static void deleteUserById(Long userId) {
        try  {
            Session session = HibernateUtil.getSession();
            session.getTransaction();
            Customer user = session.get(Customer.class, userId);
            session.delete(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Произошла ошибка при удалении пользователя.");
        }
    }


    /**
     * Создает сессию для текущего пользователя
     *
     * @param username имя пользователя
     * @return User
     */
    public static Customer setCurrentUser(String username) {
        if (username.equals("null")) {
            authUser = null;
            return null;
        }
        authUser = UserService.getUser(username);
        return authUser;
    }


    /**
     * Возвращает сессию текущего авторизованного пользователя
     *
     * @return User
     */
    public static Customer getCurrentUser() {
        return authUser;
    }

    public static Customer getUser(String username) {
        Session session = null;

        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();

            Query<Customer> getUserQuery = session.createQuery("FROM Customer WHERE userName=:username", Customer.class);
            getUserQuery.setParameter("username", username);

            Customer user = getUserQuery.uniqueResult();

            transaction.commit(); // Завершаем транзакцию
            return user;
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback(); // Откатываем транзакцию в случае исключения
            }
            e.printStackTrace();
            return null; // Лучше вернуть null, если произошла ошибка
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }




    public static void updateUser(Long userId, String username, String newPassword, String role) {
        try {
            Session session = HibernateUtil.getSession();
            session.getTransaction();
            Customer user = session.get(Customer.class, userId);
            user.setUserName(username);
            user.setRole(com.yerdias.entity.Role.valueOf(role));
            user.setPassword(newPassword);
            session.getTransaction().commit();
            session.close();
        } finally {

        }
    }

    public static boolean isUserExistsById(Long userId) {
        try {
            Session session = HibernateUtil.getSession();
            session.getTransaction();
            Customer user = session.get(Customer.class, userId);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Customer printUserInfo(Customer currentUser) {
        try {
            Session session = HibernateUtil.getSession();
            session.getTransaction();
            Customer user = session.get(Customer.class, getCurrentUser().getCustomerId());
            currentUser.setUserName(user.getUserName());
            currentUser.setRole(user.getRole());
            currentUser.setCustomerId(user.getCustomerId());
            currentUser.setAmountOfMoney(user.getAmountOfMoney());
            currentUser.setPassword(user.getPassword());
            session.getTransaction().commit();
            session.close();
            return currentUser;
        } finally {

        }
    }

    public static int countUsers() {
        try {
            Session session =HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            Query<Customer> query = session.createQuery("FROM Customer ", Customer.class);
            transaction.commit();
            session.close();
            return query.getResultList().size();
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}