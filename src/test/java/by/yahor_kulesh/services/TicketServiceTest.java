package by.yahor_kulesh.services;

import by.yahor_kulesh.entity.TicketEntity;
import by.yahor_kulesh.entity.UserEntity;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.model.users.Client;
import by.yahor_kulesh.repositories.TicketRepository;
import by.yahor_kulesh.repositories.UserRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TicketServiceTest {

  private Ticket ticket;

  @Mock private TicketRepository ticketRepository;

  @Mock private UserRepository userRepository;

  @InjectMocks private TicketService ticketService;

  @Captor ArgumentCaptor<TicketEntity> ticketEntityCaptor;

  @Captor ArgumentCaptor<UUID> uuidArgumentCaptor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ticket = new Ticket();
  }

  @Test
  void insertOrUpdateTicket_TicketAdded_SavesWithCorrectUUID() {
    ticketService.insertOrUpdateTicket(ticket);

    Mockito.verify(ticketRepository, Mockito.times(1)).save(ticketEntityCaptor.capture());
    Assertions.assertNotNull(
        ticketEntityCaptor.getValue(), "Captured TicketEntity should not be null");
    Assertions.assertEquals(
        ticket.getId(),
        ticketEntityCaptor.getValue().getId(),
        "UUID should match the expected value");
  }

  @Test
  void insertOrUpdateTicket_TicketAddedWithNullId_ThrowsException() {
    ticket.setId(null);

    Assertions.assertThrows(
        IllegalArgumentException.class, () -> ticketService.insertOrUpdateTicket(ticket));
    Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.any());
  }

  @Test
  void insertOrUpdateTicket_NullAsAParameter_ThrowsException() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> ticketService.insertOrUpdateTicket(null));
    Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.any());
  }

  @Test
  void insertOrUpdateTicket_RepositoryError_ThrowsException() {
    Mockito.doThrow(new RuntimeException("Database Error"))
        .when(ticketRepository)
        .save(Mockito.isA(TicketEntity.class));

    Assertions.assertThrows(
        RuntimeException.class, () -> ticketService.insertOrUpdateTicket(ticket));
    Mockito.verify(ticketRepository, Mockito.times(1)).save(ticketEntityCaptor.capture());
    Assertions.assertNotNull(
        ticketEntityCaptor.getValue(), "Captured TicketEntity should not be null");
    Assertions.assertEquals(
        ticket.getId(),
        ticketEntityCaptor.getValue().getId(),
        "UUID should match the expected value");
  }

  @Test
  void insertOrUpdateTicket_MinUUID_SavesCorrectly() {
    ticket.setId(new UUID(0L, 0L));

    ticketService.insertOrUpdateTicket(ticket);

    Mockito.verify(ticketRepository, Mockito.times(1)).save(ticketEntityCaptor.capture());
    Assertions.assertNotNull(
        ticketEntityCaptor.getValue(), "Captured TicketEntity should not be null");
    Assertions.assertEquals(
        ticket.getId(),
        ticketEntityCaptor.getValue().getId(),
        "UUID should match the expected value");
  }

  @Test
  void insertOrUpdateTicket_MaxUUID_SavesCorrectly() {
    ticket.setId(new UUID(Long.MAX_VALUE, Long.MAX_VALUE));

    ticketService.insertOrUpdateTicket(ticket);

    Mockito.verify(ticketRepository, Mockito.times(1)).save(ticketEntityCaptor.capture());
    Assertions.assertNotNull(
        ticketEntityCaptor.getValue(), "Captured TicketEntity should not be null");
    Assertions.assertEquals(
        ticket.getId(),
        ticketEntityCaptor.getValue().getId(),
        "UUID should match the expected value");
  }

  @Test
  void deleteTicketById_NullAsParameter_ThrowsException() {
    Mockito.when(ticketRepository.getTicketById(Mockito.isA(UUID.class))).thenReturn(null);

    Assertions.assertThrows(
        IllegalArgumentException.class, () -> ticketService.deleteTicketById(null));
    Mockito.verify(ticketRepository, Mockito.times(1)).getTicketById(null);
    Mockito.verify(ticketRepository, Mockito.never()).deleteById(Mockito.isA(UUID.class));
  }

  @Test
  void deleteTicketById_CorrectUUID_DeletesCorrectly() {
    UUID ticketId = UUID.randomUUID();
    Mockito.when(ticketRepository.getTicketById(Mockito.isA(UUID.class)))
        .thenReturn(new TicketEntity(ticketId));

    ticketService.deleteTicketById(ticketId);

    Mockito.verify(ticketRepository, Mockito.times(1)).getTicketById(Mockito.isA(UUID.class));
    Mockito.verify(ticketRepository, Mockito.times(1)).deleteById(uuidArgumentCaptor.capture());
    Assertions.assertEquals(
        ticketId, uuidArgumentCaptor.getValue(), "UUID should match the expected value");
  }

  @Test
  void deleteTicketById_WrongUUID_ThrowsException() {
    UUID ticketId = new UUID(Long.MAX_VALUE, Long.MAX_VALUE);
    Mockito.when(ticketRepository.getTicketById(Mockito.isA(UUID.class))).thenReturn(null);

    Assertions.assertThrows(
        IllegalArgumentException.class, () -> ticketService.deleteTicketById(ticketId));
    Mockito.verify(ticketRepository, Mockito.times(1)).getTicketById(Mockito.isA(UUID.class));
    Mockito.verify(ticketRepository, Mockito.never()).deleteById(Mockito.isA(UUID.class));
  }

  @Test
  void getTicketById_NullAsParameter_ThrowsException() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> ticketService.getTicketById(null));
    Mockito.verify(ticketRepository, Mockito.never()).getTicketById(Mockito.isA(UUID.class));
  }

  @Test
  void getTicketById_CorrectUUID_ReturnsTicket() {
    UUID ticketId = UUID.randomUUID();
    Mockito.when(ticketRepository.getTicketById(Mockito.isA(UUID.class)))
        .thenReturn(new TicketEntity(ticketId));

    Ticket gotTicket = ticketService.getTicketById(ticketId);

    Mockito.verify(ticketRepository, Mockito.times(1)).getTicketById(Mockito.isA(UUID.class));
    Assertions.assertEquals(ticketId, gotTicket.getId(), "UUID should match the expected value");
  }

  @Test
  void getTicketById_WrongUUID_ReturnsNull() {
    UUID ticketId = new UUID(Long.MAX_VALUE, Long.MAX_VALUE);
    Mockito.when(ticketRepository.getTicketById(Mockito.isA(UUID.class))).thenReturn(null);

    Assertions.assertNull(ticketService.getTicketById(ticketId));
    Mockito.verify(ticketRepository, Mockito.times(1)).getTicketById(Mockito.isA(UUID.class));
    Mockito.verify(ticketRepository, Mockito.never()).deleteById(Mockito.isA(UUID.class));
  }

  @Test
  void getTicketByUserId_NullAsParameter_ThrowsException() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> ticketService.getTicketByUserId(null));
    Mockito.verify(ticketRepository, Mockito.never()).getTicketByUserId(Mockito.isA(UUID.class));
  }

  @Test
  void getTicketByUserId_CorrectUUID_ReturnsList() {
    UUID userId = UUID.randomUUID();
    ArrayList<TicketEntity> ticketEntities = getTicketEntities(userId);
    Mockito.when(ticketRepository.getTicketByUserId(Mockito.isA(UUID.class)))
        .thenReturn(ticketEntities);

    List<Ticket> gotTicket = ticketService.getTicketByUserId(userId);

    Mockito.verify(ticketRepository, Mockito.times(1)).getTicketByUserId(Mockito.isA(UUID.class));
    Assertions.assertNotNull(gotTicket, "Ticket List should not be null");
    Assertions.assertAll(
        () -> Assertions.assertEquals(userId, ticketEntities.get(0).getUser().getId()),
        () -> Assertions.assertEquals(userId, ticketEntities.get(1).getUser().getId()),
        () -> Assertions.assertEquals(userId, ticketEntities.get(2).getUser().getId()));
  }

  @Test
  void getTicketByUserId_WrongUUID_ReturnsEmptyCollection() {
    UUID ticketId = new UUID(Long.MAX_VALUE, Long.MAX_VALUE);
    Mockito.when(ticketRepository.getTicketByUserId(Mockito.isA(UUID.class)))
        .thenReturn(Collections.emptyList());

    Assertions.assertEquals(Collections.emptyList(), ticketService.getTicketByUserId(ticketId));
    Mockito.verify(ticketRepository, Mockito.times(1)).getTicketByUserId(Mockito.isA(UUID.class));
  }

  @Test
  void getOrCreateTicket_NullAsParameter_CreatesNewTicket() {
    ticketService.getOrCreateTicket(null);

    Mockito.verify(ticketRepository, Mockito.times(1)).save(ticketEntityCaptor.capture());
    Mockito.verify(ticketRepository, Mockito.times(1)).getTicketById(uuidArgumentCaptor.capture());
    Assertions.assertNotNull(
        ticketEntityCaptor.getValue(), "Captured TicketEntity should not be null");
    Assertions.assertNotNull(
        uuidArgumentCaptor.getValue(), "Captured TicketEntity.id should not be null");
    Assertions.assertEquals(
        uuidArgumentCaptor.getValue(),
        ticketEntityCaptor.getValue().getId(),
        "UUID should match the expected value");
  }

  @Test
  void getOrCreateTicket_UnknownUUID_CreatesNewTicket() {
    UUID userId = new UUID(Long.MAX_VALUE, Long.MAX_VALUE);
    Mockito.when(ticketRepository.getTicketById(Mockito.isA(UUID.class))).thenReturn(null);

    ticketService.getOrCreateTicket(userId);

    Mockito.verify(ticketRepository, Mockito.times(1)).save(ticketEntityCaptor.capture());
    Assertions.assertNotNull(ticketEntityCaptor, "Ticket should not be null");
    Assertions.assertEquals(
        userId, ticketEntityCaptor.getValue().getId(), "UUID should match the expected value");
  }

  @Test
  void getOrCreateTicket_CorrectUUID_ReturnsTicket() {
    UUID ticketId = UUID.randomUUID();
    Mockito.when(ticketRepository.getTicketById(ticketId))
        .thenReturn(new TicketEntity(ticketId))
        .thenReturn(new TicketEntity(ticketId));

    TicketEntity gotTicket = ticketService.getOrCreateTicket(ticketId);

    Mockito.verify(ticketRepository, Mockito.times(2)).getTicketById(Mockito.isA(UUID.class));
    Assertions.assertEquals(ticketId, gotTicket.getId(), "UUID should match the expected value");
  }

  @Test
  void insertOrUpdateTicketAndUpdateClient_NullAsParameter_ThrowsException() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> ticketService.insertOrUpdateTicketAndUpdateClient(null, null));
    Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.isA(TicketEntity.class));
    Mockito.verify(userRepository, Mockito.never()).updateUserStatusById(Mockito.isA(UUID.class));
    Mockito.verify(userRepository, Mockito.never()).getUserById(Mockito.isA(UUID.class));
  }

  @Test
  void insertOrUpdateTicketAndUpdateClient_NullClient_ThrowsException() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> ticketService.insertOrUpdateTicketAndUpdateClient(ticket, null));
    Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.isA(TicketEntity.class));
    Mockito.verify(userRepository, Mockito.never()).updateUserStatusById(Mockito.isA(UUID.class));
    Mockito.verify(userRepository, Mockito.never()).getUserById(Mockito.isA(UUID.class));
  }

  @Test
  void insertOrUpdateTicketAndUpdateClient_NullAsTicket_ThrowsException() {
    Client client = new Client();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> ticketService.insertOrUpdateTicketAndUpdateClient(null, client));
    Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.isA(TicketEntity.class));
    Mockito.verify(userRepository, Mockito.never()).updateUserStatusById(Mockito.isA(UUID.class));
    Mockito.verify(userRepository, Mockito.never()).getUserById(Mockito.isA(UUID.class));
  }

  @Test
  void insertOrUpdateTicketAndUpdateClient_CorrectTicketAndClient_SavesTicketAndUpdatesUser() {
    Client client = new Client();
    int size = client.getTickets().size();
    Mockito.when(userRepository.getUserById(client.getId()))
        .thenReturn(new UserEntity(client.getId()));

    ticketService.insertOrUpdateTicketAndUpdateClient(ticket, client);

    Mockito.verify(ticketRepository, Mockito.times(1)).save(ticketEntityCaptor.capture());
    Mockito.verify(userRepository, Mockito.times(1))
        .updateUserStatusById(uuidArgumentCaptor.capture());
    Assertions.assertEquals(client.getId(), uuidArgumentCaptor.getValue());
    Assertions.assertEquals(ticket.getId(), ticketEntityCaptor.getValue().getId());
    Assertions.assertEquals(size + 1, client.getTickets().size());
  }

  @Test
  void insertOrUpdateTicketAndUpdateClient_ClientNotFound_ThrowsException() {
    Client client = new Client();
    Mockito.when(userRepository.getUserById(client.getId())).thenReturn(null);

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> ticketService.insertOrUpdateTicketAndUpdateClient(ticket, client));
    Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.isA(TicketEntity.class));
    Mockito.verify(userRepository, Mockito.never()).updateUserStatusById(Mockito.isA(UUID.class));
    Mockito.verify(userRepository, Mockito.times(1)).getUserById(Mockito.isA(UUID.class));
  }

  @Test
  void getTicketsFromFile_FileIsNull_ThrowsException() {
    Assertions.assertThrows(
        NullPointerException.class, () -> ticketService.getTicketsFromFile(null));
  }

  @Test
  void getTicketsFromFile_File_getTickets() {
    List<Ticket> list =
        ticketService.getTicketsFromFile(new File("src/test/resources/ticketData.txt"));

    Assertions.assertEquals(17, list.size());
  }

  @Test
  void getTicketsFromFile_FileNotFound_ThrowsException() {
    File file = new File("resources/ticketData.txt");

    Assertions.assertThrows(
        IllegalArgumentException.class, () -> ticketService.getTicketsFromFile(file));
  }

  private static ArrayList<TicketEntity> getTicketEntities(UUID userId) {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(userId);
    TicketEntity ticketEntity = new TicketEntity(UUID.randomUUID());
    TicketEntity ticketEntity1 = new TicketEntity(UUID.randomUUID());
    TicketEntity ticketEntity2 = new TicketEntity(UUID.randomUUID());
    ticketEntity.setUser(userEntity);
    ticketEntity1.setUser(userEntity);
    ticketEntity2.setUser(userEntity);
    ArrayList<TicketEntity> ticketEntities = new ArrayList<>();
    ticketEntities.add(ticketEntity);
    ticketEntities.add(ticketEntity1);
    ticketEntities.add(ticketEntity2);
    return ticketEntities;
  }
}
