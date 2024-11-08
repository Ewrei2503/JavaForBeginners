package by.yahor_kulesh.controllers;

import by.yahor_kulesh.entity.TicketEntity;
import by.yahor_kulesh.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public TicketEntity getTicket(@RequestParam(required = false) UUID id) {
        return ticketService.getOrCreateTicket(id);
    }

    @GetMapping("/test")
    public void testApp() {
        ticketService.testApp();
    }
}
