package com.backend.backend_blackcatgurumis.services;

import com.backend.backend_blackcatgurumis.entities.Producto;
import com.backend.backend_blackcatgurumis.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto1;
    private Producto producto2;

    @BeforeEach
    void setUp() {
        // Inicializamos datos de prueba que usaremos en varios tests
        producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");

        producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Producto 2");
    }

    @Test
    void testListarProductos() {
        // Given: Definimos el comportamiento del mock
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto1, producto2));

        // When: Ejecutamos el método a probar
        List<Producto> productos = productoService.listarTodos();

        // Then: Verificamos el resultado
        assertNotNull(productos);
        assertEquals(2, productos.size());
        assertEquals("Producto 1", productos.get(0).getNombre());
        
        // Verificar que el método del repositorio fue llamado
        verify(productoRepository).findAll();
    }

    @Test
    void testBuscarProductoPorId() {
        // Given
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto1));

        // When
        Producto productoEncontrado = productoService.obtenerPorId(1L);

        // Then
        assertNotNull(productoEncontrado);
        assertEquals("Producto 1", productoEncontrado.getNombre());
        verify(productoRepository).findById(1L);
    }
}
