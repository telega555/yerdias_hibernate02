package com.yerdias.service;

import com.yerdias.entity.Customer;

import com.yerdias.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class BalanceService {

    public static void addBalance(BigDecimal money){
        try (Session session = HibernateUtil.getSession()){
            session.getTransaction().commit();
            Customer user = session.get(Customer.class, UserService.getCurrentUser().getCustomerId());
            session.getTransaction().commit();
            session.close();
            if(user.getAmountOfMoney().equals(null)){
            user.setAmountOfMoney(money);
            }
            else {
                BigDecimal a = user.getAmountOfMoney().add(money);
                user.setAmountOfMoney(a);
            }

        }
    }

    public static BigDecimal getBalance() {
        try (Session session = HibernateUtil.getSession()){
            session.getTransaction();
            Customer user = session.get(Customer.class, UserService.getCurrentUser().getCustomerId());
            session.getTransaction().commit();
            session.close();
            return user.getAmountOfMoney();
        }
    }

    public static boolean withdrawMoney(BigDecimal price, Customer currentUser) {
        try (Session session = HibernateUtil.getSession()){
            session.getTransaction();
            Customer user = session.get(Customer.class,UserService.getCurrentUser());
            currentUser.getAmountOfMoney().divide(price);
            user.setAmountOfMoney(currentUser.getAmountOfMoney());
            session.getTransaction().commit();
            session.close();
            return currentUser.getAmountOfMoney().compareTo(price) >= 0;
        }
    }
}
