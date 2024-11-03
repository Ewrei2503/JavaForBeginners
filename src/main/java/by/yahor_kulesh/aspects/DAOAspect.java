package by.yahor_kulesh.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Aspect
@Component
public class DAOAspect {

    @Value("${user.insert}")
    public boolean insertUser;
    @Value("${user.delete}")
    public boolean deleteUser;
    @Value("${user.update}")
    public boolean updateUser;
    @Value("${user.get}")
    public boolean getUser;


    @Value("${ticket.insert}")
    public boolean insertTicket;
    @Value("${ticket.delete}")
    public boolean deleteTicket;
    @Value("${ticket.update}")
    public boolean updateTicket;
    @Value("${ticket.get}")
    public boolean getTicket;

    @Around("execution(* by.yahor_kulesh.dao.UserDAO.* (..)) || execution(* by.yahor_kulesh.dao.TicketDAO.* (..))")
    public Object aroundDAOMethods(ProceedingJoinPoint joinPoint){
        try {
            String method = joinPoint.getSignature().getName();
            if(
                    (method.contains("insert") && !insertUser) || (method.contains("delete") && !deleteUser) ||
                    (method.contains("update") && !updateUser) || (method.contains("get") && !getUser) ||
                    (method.contains("insert") && !insertTicket) || (method.contains("delete") && !deleteTicket) ||
                    (method.contains("update") && !updateTicket) || (method.contains("get") && !getTicket)
            ){
                throw new SQLException("This type of operation( " + method + ") is not allowed!");
            }
            return joinPoint.proceed();
        } catch(Throwable e){
            System.err.println(e.getMessage());
        }
        return null;
    }
}
