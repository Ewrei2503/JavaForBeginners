package by.yahor_kulesh.dao;

import by.yahor_kulesh.config.SessionFactoryProvider;
import by.yahor_kulesh.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;

public class UserDAO{

    public void saveUser(UserEntity user){
        try(
                Session session = SessionFactoryProvider.getSessionFactory().openSession()
        ){
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        }catch(Exception e){
            System.err.println("Error during saving user: " + e.getMessage());
        }
    }

    public UserEntity getUserById(UUID id){
        try(
                Session session = SessionFactoryProvider.getSessionFactory().openSession()
        ) {
           return session.get(UserEntity.class, id);
        } catch(Exception e) {
            System.err.println("Error during getting user: " + e.getMessage());
            return null;
        }
    }

    public void updateUser(UserEntity user){
        try(
                Session session = SessionFactoryProvider.getSessionFactory().openSession()
        ){
            Transaction tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
        } catch (Exception e){
            System.err.println("Error during updating user: " + e.getMessage());
        }
    }

    public void deleteUser(UserEntity user){
        try(
                Session session = SessionFactoryProvider.getSessionFactory().openSession()
        ){
            Transaction tx = session.beginTransaction();
            session.remove(user);
            tx.commit();
        } catch(Exception e) {
            System.err.println("Error during deleting user: " + e.getMessage());
        }
    }
}
