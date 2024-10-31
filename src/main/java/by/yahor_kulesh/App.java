package by.yahor_kulesh;

import by.yahor_kulesh.config.ApplicationContext;
import by.yahor_kulesh.model.Data;

import by.yahor_kulesh.utils.DataTestClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class App extends Data {

    public static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class);

    public static void main(String[] args) {
        DataTestClass.testTicketService();
    }
}