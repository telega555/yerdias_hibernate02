//package org.example.service;
//
//import jakarta.persistence.Column;
//import org.example.entity.Film;
//import org.example.entity.User;
//import org.example.util.HibernateUtil;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//
//import java.time.LocalTime;
//
//public class MovieService {
//    private static SessionFactory sessionFactory;
//
//    public MovieService(SessionFactory sessionFactory) {
//        this.sessionFactory = HibernateUtil.getSession().getSessionFactory();
//    }
//
//
//    public static Film printMovies() {
//        try (Session session = sessionFactory.openSession()) {
//            Query<Film> query = session.createQuery("FROM Film ", Film.class);
//            return (Film) query.getResultList();
//        }
//    }
//    public static void createMovie(String title, LocalTime filmStartTime, int releaseYear ){
//        try (Session session = sessionFactory.openSession()){
//            Transaction transaction = session.beginTransaction();
//            Film film = new Film();
//            film.setTitle(title);
//            film.setFilmStartTime(filmStartTime);
//            film.setReleaseYear(releaseYear);
//            session.save(film);
//            transaction.commit();
//        }
//    }
//
//    public static void deleteMovieById(Long filmId){
//        try (Session session = sessionFactory.openSession()){
//            Transaction transaction = session.beginTransaction();
//            Film film = session.get(Film.class, filmId);
//            session.detach(film);
//            transaction.commit();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Произошла ошибка при удалении фильма.");
//        }
//
//    }
//    public static void editMovie(Long id, LocalTime filmStartTime, int releaseYear, String title){
//        try (Session session = sessionFactory.openSession()){
//            Transaction transaction = session.beginTransaction();
//            Film film = session.get(Film.class, id);
//            film.setReleaseYear(releaseYear);
//            film.setTitle(title);
//            film.setFilmStartTime(filmStartTime);
//            transaction.commit();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Произошла ошибка при изменении фильма.");
//        }
//
//    }
//}
package com.yerdias.service;

import com.yerdias.entity.Film;
import com.yerdias.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class MovieService {


    public static List<Film> printMovies() {
        try (Session session = HibernateUtil.getSession()) {

            Transaction transaction = session.beginTransaction(); // Начинаем транзакцию

            Query<Film> query = session.createQuery("FROM Film", Film.class);
            List<Film> films = query.getResultList();

            for (int i = 0; i < films.size(); i++) {
                System.out.println(films.get(i).getId() + " " + films.get(i).getTitle() + " " + films.get(i).getFilmStartTime() + " " + films.get(i).getReleaseYear());
            }
            transaction.commit(); // Завершаем транзакцию
            return films;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Лучше вернуть пустой список в случае ошибки
        }
    }


    public static void createMovie(String title, LocalDateTime filmStartTime, int releaseYear) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction(); // Начинаем транзакцию

            Film film = new Film();
            film.setTitle(title);
            film.setFilmStartTime(filmStartTime);
            film.setReleaseYear(releaseYear);

            session.save(film);

            transaction.commit(); // Завершаем транзакцию
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteMovieById(Long filmId) {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction();
            Film film = session.get(Film.class, filmId);
            if (film != null) {
                session.delete(film);
                session.getTransaction().commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Произошла ошибка при удалении фильма.");
        }
    }

    public static void editMovie(Long id, String title, LocalDateTime filmStartTime, int releaseYear) {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction();
            Film film = session.get(Film.class, id);
            if (film != null) {
                film.setTitle(title);
                film.setFilmStartTime(filmStartTime);
                film.setReleaseYear(releaseYear);
                session.getTransaction().commit();

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Произошла ошибка при изменении фильма.");
        }
    }
    public static Film getMovieById(Long filmId) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(Film.class, filmId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Произошла ошибка при получении фильма по ID.");
            return null;
        }
    }

    public static Film getFilmByTitle(String movieTitle) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction =session.beginTransaction();
            Query<Film> query = session.createQuery("FROM Film WHERE title = :movieTitle", Film.class);
            query.setParameter("movieTitle", movieTitle);
            transaction.commit();
            session.close();
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Произошла ошибка при получении фильма по title.");
            return null;
        }
    }


    public static boolean isMovieExistsById(Long movieId) {
        try (Session session = HibernateUtil.getSession()){
            session.getTransaction();
            session.get(Film.class, movieId);
            session.getTransaction().commit();
            session.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int countMovies() {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction(); // Начинаем транзакцию

            Query<Film> query = session.createQuery("FROM Film", Film.class);
            int movieCount = query.getResultList().size();

            transaction.commit(); // Завершаем транзакцию
            return movieCount;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Лучше вернуть 0 в случае ошибки
        }
    }

}
