package by.yahor_kulesh.dao;

import by.yahor_kulesh.entity.UserEntity;
import by.yahor_kulesh.mappers.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Repository
public class UserDAO{

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Transactional
    public void insert(UserEntity user){
        jdbcTemplate.update("INSERT INTO usr(id,name,creation_date,role) VALUES (?,?,?,?)",user.getId(),user.getName(), user.getCreationTime(), user.getRole().name());
    }

    @Transactional
    public UserEntity getById(UUID id){
            return jdbcTemplate.query("SELECT * FROM usr where id=?", UserMapper.INSTANCE, id).stream().findAny().orElse(null);
    }

    @Transactional
    public void update(UserEntity user){
        jdbcTemplate.update("UPDATE usr SET id=?,name=?,creation_date=?,role=? WHERE id=?",user.getId(),user.getName(), user.getCreationTime(), user.getRole().name());
    }

    @Transactional
    public void deleteById(UUID id){
        jdbcTemplate.update("DELETE FROM usr WHERE id=?",id);
    }
}
