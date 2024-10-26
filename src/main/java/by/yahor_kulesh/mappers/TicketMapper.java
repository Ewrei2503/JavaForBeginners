package by.yahor_kulesh.mappers;

import by.yahor_kulesh.entity.TicketEntity;
import by.yahor_kulesh.entity.UserEntity;
import by.yahor_kulesh.entity.enums.TicketType;
import by.yahor_kulesh.model.tickets.BusTicket;
import by.yahor_kulesh.model.tickets.ConcertTicket;
import by.yahor_kulesh.model.tickets.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.UUID;


@Mapper
public interface TicketMapper extends CommonMapper{

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);


    @ObjectFactory
    default Ticket toModel(TicketEntity ticketEntity){
        if(ticketEntity == null) return null;
        if(TicketType.BUS == ticketEntity.getType()) {
            return toBus(ticketEntity);
        } else if(ticketEntity.getType()==TicketType.CONCERT) {
            return toConcert(ticketEntity);
        } else return toTicket(ticketEntity);
    }

    @Mappings({
            @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime"),
            @Mapping(target = "userId", source="user", qualifiedByName = "userEntityToUUID")
    })
    ConcertTicket toConcert(TicketEntity ticketEntity);

    @Mappings({
            @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime"),
            @Mapping(target = "userId", source="user", qualifiedByName = "userEntityToUUID")
    })
    BusTicket toBus(TicketEntity ticketEntity);

    default Ticket toTicket(TicketEntity ticketEntity){
        if(ticketEntity == null) return null;
        Ticket ticket = new Ticket();

        ticket.setCreationTime( timestampToZonedDateTime( ticketEntity.getCreationTime() ) );
        ticket.setUserId( userEntityToUUID( ticketEntity.getUser() ) );
        ticket.setId( ticketEntity.getId() );

        return ticket;
    }




    @Mappings({
            @Mapping(target = "type", expression = "java(mapTicketType(ticket))"),
            @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "zonedDateTimeToTimestamp"),
            @Mapping(target = "user", source="userId", qualifiedByName = "UUIDToUserEntity")
    })
    TicketEntity toEntity(Ticket ticket);




    default TicketType mapTicketType(Ticket ticket) {
        if(ticket instanceof BusTicket) {
            return TicketType.BUS;
        } else if(ticket instanceof ConcertTicket) {
            return TicketType.CONCERT;
        } else return TicketType.NOT_DEFINED;
    }

    @Named(value = "UUIDToUserEntity")
    default UserEntity UUIDToUserEntity(UUID uuid) {
        if(uuid == null) {
            return null;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(uuid);
            return userEntity;
        }
    }

    @Named(value = "userEntityToUUID")
    default UUID userEntityToUUID(UserEntity userEntity) {
        if(userEntity == null) {
            return null;
        } else {
            return userEntity.getId();
        }
    }




    Set<Ticket> toModelSet(Set<TicketEntity> ticketEntitySet);
    Set<TicketEntity> toEntitySet(Set<Ticket> ticketSet);
}
