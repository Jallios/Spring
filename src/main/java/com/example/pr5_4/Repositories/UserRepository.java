package com.example.pr5_4.Repositories;


import com.example.pr5_4.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    @Query(value="select * from User where username=:c", nativeQuery=true)
    User findUserByLogin (@Param("c")String username);
}
