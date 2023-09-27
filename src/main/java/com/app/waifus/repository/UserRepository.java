package com.app.waifus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.waifus.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  // ---> metodos user aqui

  // metodo para validar el usuario desde la base de datos
  @Query("select u from User u where u.user_name = :user_field and u.password = :password_field")
  public User ValidarUsuario(
      @Param(value = "user_field") String user_field,
      @Param(value = "password_field") String password_field);

}
