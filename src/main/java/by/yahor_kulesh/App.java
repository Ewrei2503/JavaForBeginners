package by.yahor_kulesh;

import by.yahor_kulesh.config.ApplicationContext;
import by.yahor_kulesh.model.Data;

import by.yahor_kulesh.services.TicketService;
import by.yahor_kulesh.services.UserService;
import by.yahor_kulesh.utils.DataTestClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App extends Data {

    public static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class);

    public static void main(String[] args) {
        DataTestClass testClass = new DataTestClass(context.getBean(TicketService.class),context.getBean(UserService.class));
        testClass.testTicketService();
    }
}