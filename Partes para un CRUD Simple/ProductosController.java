package com.prueba06.controller;

import com.prueba06.entity.ProductosEntity;
import com.prueba06.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductosController {

    @Autowired
    private ProductosService productosService;

    @GetMapping("/productos")
    public String listarProductos(Model modelo) {
        modelo.addAttribute("productos", productosService.listarTodosLosProductos());
        return "productos.html";
    }

    @GetMapping("/productos/nuevo")
    public String mostrarFormularioDeRegistrarProducto(Model modelo) {
        ProductosEntity productoEntity = new ProductosEntity();
        modelo.addAttribute("productoEntity", productoEntity);
        return "crearproducto.html";
    }

    @PostMapping("/productos/nuevo")
    public String guardarProducto(@ModelAttribute("productoEntity") ProductosEntity productoEntity) {
        productoEntity.setEstado(Boolean.TRUE);
        productosService.guardarProducto(productoEntity);
        return "redirect:/productos";
    }

    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("productoEntity", productosService.obtenerProductoPorId(id));
        return "editarproducto.html";
    }

    @PostMapping("/productos/editar/{id}")
    public String actualizarProducto(@PathVariable Long id,
            @ModelAttribute("productoEntity") ProductosEntity productoEntity,
            Model modelo) {
        ProductosEntity productoExistente = productosService.obtenerProductoPorId(id);
        productoExistente.setId(id);
        productoExistente.setNombre(productoEntity.getNombre());
        productoExistente.setPrecio(productoEntity.getPrecio());
        productoExistente.setEstado(Boolean.TRUE);
        productosService.actualizarProducto(productoExistente);
        return "redirect:/productos";
    }

    @GetMapping("/productos/cambiarestadofalse/{id}")
    public String inhabilitarProducto(@PathVariable Long id) {
        productosService.cambiarEstadoFalse(id);
        return "redirect:/productos";
    }

    @GetMapping("/productos/eliminardefinitivamente/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productosService.eliminarProductoDefinitivamente(id);
        return "redirect:/productos";
    }

}
