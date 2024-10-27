package by.yahor_kulesh.mappers;

import by.yahor_kulesh.entity.TicketEntity;
import by.yahor_kulesh.entity.UserEntity;
import by.yahor_kulesh.entity.enums.Role;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.model.users.Admin;
import by.yahor_kulesh.model.users.Client;
import by.yahor_kulesh.model.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import java.util.Set;


@Mapper
public interface UserMapper extends CommonMapper{

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);




    @Mappings({
            @Mapping(target = "role", expression = "java(mapRole(user))"),
            @Mapping(target = "creationTime", source="creationTime", qualifiedByName = "zonedDateTimeToTimestamp"),
            @Mapping(target="tickets", expression = "java(mapTickets(user))")
    })
    UserEntity toEntity(User user);




    @ObjectFactory
    default User toModel(UserEntity userEntity){
        if(userEntity == null){
            return null;
        } else if(userEntity.getRole().equals(Role.CLIENT)) {
            return toClient(userEntity);
        } else return toAdmin(userEntity);
    }

    @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime")
    Admin toAdmin(UserEntity userEntity);

    @Mappings({
            @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime"),
            @Mapping(target = "tickets", expression = "java(mapTickets(userEntity))")
    })
    Client toClient(UserEntity userEntity);




    default Role mapRole(User user) {
        if(user instanceof Client) {
            return Role.CLIENT;
        } else return Role.ADMIN;
    }

    default Set<TicketEntity> mapTickets(User user) {
        if(user instanceof Client client) {
            return TicketMapper.INSTANCE.toEntitySet(client.getTickets());
        } else return null;
    }

    default Set<Ticket> mapTickets(UserEntity user) {
        if(user.getRole().equals(Role.CLIENT)) {
            return TicketMapper.INSTANCE.toModelSet(user.getTickets());
        } else return null;
    }

}