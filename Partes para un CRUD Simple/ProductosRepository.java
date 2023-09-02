package com.prueba06.repository;

import com.prueba06.entity.ProductosEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository extends JpaRepository<ProductosEntity, Long>{
    
    @Query("SELECT p FROM ProductosEntity p WHERE p.estado = true")
    List<ProductosEntity> findAllByEstadoTrue();
    
}
