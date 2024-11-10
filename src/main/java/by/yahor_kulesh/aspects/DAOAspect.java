package by.yahor_kulesh.aspects;

import java.sql.SQLException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

  @Around(
      "(execution(* by.yahor_kulesh.services.UserService.*(..)) || execution(* by.yahor_kulesh.services.TicketService.* (..))) && @annotation(org.springframework.transaction.annotation.Transactional) ")
  public Object checkMethodProhibition(ProceedingJoinPoint joinPoint) {
    try {
      String method = joinPoint.getSignature().getName();
      if (isMethodProhibited(method)) {
        throw new SQLException("This type of operation( " + method + ") is not allowed!");
      }
      return joinPoint.proceed();
    } catch (Throwable e) {
      System.err.println(e.getMessage());
    }
    return null;
  }

  private boolean isMethodProhibited(String method) {
    return (method.contains("insert") && !insertUser)
        || (method.contains("delete") && !deleteUser)
        || (method.contains("update") && !updateUser)
        || (method.contains("get") && !getUser)
        || (method.contains("insert") && !insertTicket)
        || (method.contains("delete") && !deleteTicket)
        || (method.contains("update") && !updateTicket)
        || (method.contains("get") && !getTicket);
  }
}
