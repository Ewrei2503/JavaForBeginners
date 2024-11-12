package by.yahor_kulesh.controllers;

import by.yahor_kulesh.entity.TicketEntity;
import by.yahor_kulesh.services.TicketService;
import by.yahor_kulesh.services.UserService;
import by.yahor_kulesh.utils.DataTestClass;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

  private final TicketService ticketService;

  private final UserService userService;

  @PostMapping
  public TicketEntity getTicket(@RequestParam(required = false) UUID id) {
    return ticketService.getOrCreateTicket(id);
  }

  @GetMapping("/test")
  public void testApp() {
    DataTestClass testClass = new DataTestClass(ticketService, userService);
    testClass.testTicketService();
  }
}
