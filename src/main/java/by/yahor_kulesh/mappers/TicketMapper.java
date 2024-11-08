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
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Mapper
public interface TicketMapper extends CommonMapper, RowMapper<TicketEntity> {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);


    default List<Ticket> toModel(List<TicketEntity> ticketEntityList){
        if((Objects.isNull(ticketEntityList)) || ticketEntityList.isEmpty()) return Collections.emptyList();
        List<Ticket> ticketList = new ArrayList<>();
        for(TicketEntity ticketEntity : ticketEntityList){
            ticketList.add(toModel(ticketEntity));
        }
        return ticketList;
    }


    @ObjectFactory
    default Ticket toModel(TicketEntity ticketEntity){
        if(Objects.isNull(ticketEntity)) return null;
        if(TicketType.BUS == ticketEntity.getType()) {
            return toBusTicket(ticketEntity);
        } else if(ticketEntity.getType()==TicketType.CONCERT) {
            return toConcertTicket(ticketEntity);
        } else return toTicket(ticketEntity);
    }

    @Mappings({
            @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime"),
            @Mapping(target = "userId", source="user", qualifiedByName = "userEntityToUUID")
    })
    ConcertTicket toConcertTicket(TicketEntity ticketEntity);

    @Mappings({
            @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime"),
            @Mapping(target = "userId", source="user", qualifiedByName = "userEntityToUUID")
    })
    BusTicket toBusTicket(TicketEntity ticketEntity);

    default Ticket toTicket(TicketEntity ticketEntity){
        if(Objects.isNull(ticketEntity)) return null;
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
        if(Objects.isNull(uuid)) {
            return null;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(uuid);
            return userEntity;
        }
    }

    @Named(value = "userEntityToUUID")
    default UUID userEntityToUUID(UserEntity userEntity) {
        if(Objects.isNull(userEntity)) {
            return null;
        } else {
            return userEntity.getId();
        }
    }



    default TicketEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        TicketEntity ticket = new TicketEntity();
        ticket.setId(UUID.fromString(rs.getString(1)));
        ticket.setUser(new UserEntity((Objects.isNull(rs.getString(2)))?null:UUID.fromString(rs.getString(2))));
        ticket.setType(TicketType.valueOf(rs.getString(3)));
        ticket.setCreationTime(rs.getTimestamp(4));
        return ticket;
    }




    Set<Ticket> toModelSet(Set<TicketEntity> ticketEntitySet);
    Set<TicketEntity> toEntitySet(Set<Ticket> ticketSet);
}