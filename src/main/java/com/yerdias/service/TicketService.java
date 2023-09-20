package com.yerdias.service;

import com.yerdias.entity.Customer;
import com.yerdias.entity.Film;
import com.yerdias.entity.Ticket;

import com.yerdias.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

public class TicketService {


    public static void addTicket(int seatNumber, double price, String movieTitle) {
        Film film = MovieService.getFilmByTitle(movieTitle);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Ticket ticket = new Ticket();
        ticket.setPlace(seatNumber);
        ticket.setPrice(BigDecimal.valueOf(price));
        ticket.setIsPurchased(false);
        ticket.setFilm(film);
        session.save(ticket);
        session.getTransaction().commit();
        session.close();
    }

    public static Ticket readTicket() {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction(); // Начинаем транзакцию

            Query<Ticket> query = session.createQuery("FROM Ticket", Ticket.class);
            List<Ticket> tickets = query.getResultList();

            if (!tickets.isEmpty()) {
                Ticket ticket = tickets.get(0);
                transaction.commit();
                return ticket;
            }

            transaction.commit();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean buySelectedTicket(Long ticketId, Customer currentUser) {
        Ticket selectedTicket = getTicketById(ticketId);
        if (selectedTicket == null) {
            return false;
        }
        if (selectedTicket.getIsPurchased()) {
            return false;
        }
        boolean withdrawStatus = BalanceService.withdrawMoney(selectedTicket.getPrice(), currentUser);
        if (withdrawStatus) {

            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            selectedTicket.setCustumer(currentUser);
            selectedTicket.setIsPurchased(true);
            session.update(selectedTicket);
            session.getTransaction().commit();
            session.close();

            return true;
        }
        return false;
    }

    private static Ticket getTicketById(Long ticketId) {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction();
            Ticket ticket = session.get(Ticket.class, ticketId);
            session.getTransaction().commit();
            session.close();
            return ticket;
        }
    }


    public static int countTickets() {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction(); // Начинаем транзакцию

            Query<Ticket> query = session.createQuery("FROM Ticket", Ticket.class);
            int ticketCount = query.getResultList().size();

            transaction.commit(); // Завершаем транзакцию
            return ticketCount;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Лучше вернуть 0 в случае ошибки
        }
    }

    public static boolean isTicketExistsById(Long ticketId) {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction();
            Ticket ticket = session.get(Ticket.class, ticketId);
            boolean exists = ticket != null;
            session.getTransaction().commit();
            session.close();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
