package com.prueba06.service;

import com.prueba06.entity.ProductosEntity;
import com.prueba06.repository.ProductosRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductosService {

    @Autowired
    private ProductosRepository productosRepositorio;

    @Override
    public List<ProductosEntity> listarTodosLosProductos() {
        return productosRepositorio.findAll();
    }

    @Override
    public List<ProductosEntity> listarTodosLosProductosHabilitados(){
        return productosRepositorio.findAllByEstadoTrue();
    }
    
    @Override
    public ProductosEntity guardarProducto(ProductosEntity productosEntity) {
        return productosRepositorio.save(productosEntity);
    }

    @Override
    public ProductosEntity obtenerProductoPorId(Long id) {
        return productosRepositorio.findById(id).get();
    }

    @Override
    public ProductosEntity actualizarProducto(ProductosEntity productosEntity) {
        return productosRepositorio.save(productosEntity);
    }

    @Override
    public void cambiarEstadoFalse(Long id) {
        productosRepositorio.findById(id).ifPresent(productosEntity -> {
            productosEntity.setEstado(Boolean.FALSE);
            productosRepositorio.save(productosEntity);
        });
    }

    @Override
    public void eliminarProductoDefinitivamente(Long id) {
        productosRepositorio.deleteById(id);
    }

}
