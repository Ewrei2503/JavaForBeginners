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
public abstract class UserMapper extends CommonMapper{

    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);




    @Mappings({
        @Mapping(target = "role", expression = "java(mapRole(user))"),
        @Mapping(target = "creationTime", source="creationTime", qualifiedByName = "zonedDateTimeToTimestamp"),
        @Mapping(target="tickets", expression = "java(mapTickets(user))")
    })
    public abstract UserEntity toEntity(User user);




    @ObjectFactory
    public User toModel(UserEntity userEntity){
        if(userEntity.getRole().equals(Role.CLIENT)) {
            return toClient(userEntity);
        } else return toAdmin(userEntity);
    }

    @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime")
    protected abstract Admin toAdmin(UserEntity userEntity);

    @Mappings({
          @Mapping(target = "creationTime", source = "creationTime", qualifiedByName = "timestampToZonedDateTime"),
          @Mapping(target = "tickets", expression = "java(mapTickets(userEntity))")
    })
    protected abstract Client toClient(UserEntity userEntity);




    protected Role mapRole(User user) {
        if(user instanceof Client) {
            return Role.CLIENT;
        } else return Role.ADMIN;
    }

    protected Set<TicketEntity> mapTickets(User user) {
        if(user instanceof Client cl) {
            return TicketMapper.INSTANCE.toEntitySet(cl.getTickets());
        } else return null;
    }

    protected Set<Ticket> mapTickets(UserEntity user) {
        if(user.getRole().equals(Role.CLIENT)) {
            return TicketMapper.INSTANCE.toModelSet(user.getTickets());
        } else return null;
    }

}
