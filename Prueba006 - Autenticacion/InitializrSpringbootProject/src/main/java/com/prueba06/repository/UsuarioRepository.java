package com.prueba06.repository;

import com.prueba06.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{
    
    // public UsuarioEntity findByEmail(String email);
    
    @Query("SELECT u FROM UsuarioEntity u WHERE u.email = :email")
    public UsuarioEntity findUserByEmail(@Param("email") String email);

}
