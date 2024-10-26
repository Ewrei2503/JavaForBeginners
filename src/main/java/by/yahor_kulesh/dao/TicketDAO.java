package by.yahor_kulesh.dao;

import by.yahor_kulesh.config.SessionFactoryProvider;
import by.yahor_kulesh.entity.TicketEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;

import java.util.List;
import java.util.UUID;

public class TicketDAO{

    public void saveTicket(TicketEntity ticket){
        try(Session session = SessionFactoryProvider.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.persist(ticket);
            tx.commit();
        } catch(Exception e) {
            System.err.println("Error during saving ticket: " + e.getMessage());
        }
    }

    public TicketEntity getTicketById(UUID id){
        try(Session session = SessionFactoryProvider.getSessionFactory().openSession()){
            return session.get(TicketEntity.class, id);
        } catch(Exception e) {
            System.err.println("Error during getting ticket: " + e.getMessage());
            return null;
        }
    }

    public List<TicketEntity> getTicketByUserId(UUID id) {
        try(Session session = SessionFactoryProvider.getSessionFactory().openSession()){
            SelectionQuery<TicketEntity> q = session.createSelectionQuery("SELECT t FROM TicketEntity t WHERE t.user.id = :id", TicketEntity.class);
            q.setParameter("id", id);
            return q.getResultList();
        } catch(Exception e) {
            System.err.println("Error during getting ticket: " + e.getMessage());
            return null;
        }
    }

    public void updateTicket(TicketEntity ticket){
        try(Session session = SessionFactoryProvider.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.merge(ticket);
            tx.commit();
        } catch(Exception e) {
            System.err.println("Error during updating ticket: " + e.getMessage());
        }
    }

    public void deleteTicketById(TicketEntity ticket){
        try(Session session = SessionFactoryProvider.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.remove(ticket);
            tx.commit();
        }catch(Exception e) {
            System.err.println("Error during deleting ticket: " + e.getMessage());
        }
    }
}
