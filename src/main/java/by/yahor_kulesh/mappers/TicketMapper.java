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
public abstract class TicketMapper extends CommonMapper{

    public static TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);




    @ObjectFactory
    public Ticket toModel(TicketEntity ticketEntity){
        if(ticketEntity.getType().equals(TicketType.BUS)) {
            return toBus(ticketEntity);
        } else if(ticketEntity.getType().equals(TicketType.CONCERT)){
            return toConcert(ticketEntity);
        } else return toTicket(ticketEntity);
    }

    @Mappings({
            @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime"),
            @Mapping(target = "userId", source="user", qualifiedByName = "userEntityToUUID")
    })
    protected abstract ConcertTicket toConcert(TicketEntity ticketEntity);

    @Mappings({
            @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime"),
            @Mapping(target = "userId", source="user", qualifiedByName = "userEntityToUUID")
    })
    protected abstract BusTicket toBus(TicketEntity ticketEntity);

    @Mappings({
            @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime"),
            @Mapping(target = "userId", source="user", qualifiedByName = "userEntityToUUID")
    })
    public abstract Ticket toTicket(TicketEntity ticketEntity);




    @Mappings({
            @Mapping(target = "type", expression = "java(mapTicketType(ticket))"),
            @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "zonedDateTimeToTimestamp"),
            @Mapping(target = "user", source="userId", qualifiedByName = "UUIDToUserEntity")
    })
    public abstract TicketEntity toEntity(Ticket ticket);




    protected TicketType mapTicketType(Ticket ticket) {
        if(ticket instanceof BusTicket) {
            return TicketType.BUS;
        } else if(ticket instanceof ConcertTicket) {
            return TicketType.CONCERT;
        } else return TicketType.NOT_DEFINED;
    }

    @Named(value = "UUIDToUserEntity")
    protected UserEntity UUIDToUserEntity(UUID uuid) {
        if(uuid == null) {
            return null;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(uuid);
            return userEntity;
        }
    }

    @Named(value = "userEntityToUUID")
    protected UUID userEntityToUUID(UserEntity userEntity) {
        if(userEntity == null) {
            return null;
        } else {
            return userEntity.getId();
        }
    }




    abstract Set<Ticket> toModelSet(Set<TicketEntity> ticketEntitySet);
    abstract Set<TicketEntity> toEntitySet(Set<Ticket> ticketSet);
}
