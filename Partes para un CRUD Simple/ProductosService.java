package com.prueba06.service;

import com.prueba06.entity.ProductosEntity;
import java.util.List;

public interface ProductosService {
    
    public List<ProductosEntity> listarTodosLosProductos();
    public List<ProductosEntity> listarTodosLosProductosHabilitados();
    public ProductosEntity guardarProducto(ProductosEntity productosEntity);
    public ProductosEntity obtenerProductoPorId(Long id);
    public ProductosEntity actualizarProducto(ProductosEntity productosEntity);
    public void cambiarEstadoFalse(Long id);
    public void eliminarProductoDefinitivamente (Long id);
    
}
