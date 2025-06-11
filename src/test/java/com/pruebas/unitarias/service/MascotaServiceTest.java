package com.pruebas.unitarias.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import com.pruebas.unitarias.model.Mascota;
import com.pruebas.unitarias.repository.MascotaRepository;

public class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private MascotaService mascotaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*Test para guardar mascota en la capa servicio*/
    @Test
    void TestGuardarMascota() {
        Mascota mascota = new Mascota(null, "Rex", "Perro", 5);
        Mascota mascotaGuardada = new Mascota(1L, "Rex", "Perro", 5);
        when(mascotaRepository.save(mascota)).thenReturn(mascotaGuardada);

        Mascota resultado = mascotaService.guardarMascota(mascota);
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Rex");
        assertThat(resultado.getTipo()).isEqualTo("Perro");
        assertThat(resultado.getEdad()).isEqualTo(5);
        verify(mascotaRepository).save(mascota);
    }

    /*Test para listar todas las mascotas guardadas en la capa service*/
    @Test
    void testListarMascotas() {
        Mascota m1 = new Mascota(1L, "Rex", "Perro", 5);
        Mascota m2 = new Mascota(2L, "Michi", "Gato", 2);
        when(mascotaRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Mascota> resultado = mascotaService.listarMascotas();
        assertThat(resultado).hasSize(2).contains(m1, m2);
        verify(mascotaRepository).findAll();
    }

    /*Test para obtener mascota por id*/
    @Test
    void testObtenerMascotaPorId() {
        Mascota mascota = new Mascota(1L, "Rex", "Perro", 5);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));

        Optional<Mascota> resultado = mascotaService.obtenerMascotaPorId(1L);

        assertEquals("Rex", resultado.get().getNombre());
        assertThat(resultado.get().getId()).isEqualTo(1L);
        assertThat(resultado.get().getNombre()).isEqualTo("Rex");
        assertThat(resultado.get().getTipo()).isEqualTo("Perro");
        assertThat(resultado.get().getEdad()).isEqualTo(5);
        verify(mascotaRepository).findById(1L);
    }

    /*Test para eliminar mascota por id*/
    @Test
    void testEliminarMascota() {
        when(mascotaRepository.existsById(3L)).thenReturn(false);

        mascotaService.eliminarMascota(3L);

        verify(mascotaRepository, times(1)).deleteById(3L);
    }

    /*Test para actualizar mascota*/
    @Test
    void testActualizarMascota() {
        Mascota mExistente = new Mascota(1L, "Rex", "Perro", 5);
        Mascota mActualizada = new Mascota(2L, "Michi", "Gato", 2);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mExistente));
        when(mascotaRepository.save(mExistente)).thenReturn(mExistente);

        Mascota resultado = mascotaService.actualizarMascota(1L, mActualizada);
        assertEquals("Michi", resultado.getNombre());
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Michi");
        assertThat(resultado.getTipo()).isEqualTo("Gato");
        assertThat(resultado.getEdad()).isEqualTo(2);
        verify(mascotaRepository).findById(1L);

    }
}
