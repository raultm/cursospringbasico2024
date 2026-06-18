package com.example.curso2024.interfaces.contextoprestamo;

import java.time.LocalDateTime;

public interface ContextoPrestamo {

    String getNombre();

    boolean aplica(LocalDateTime fechaComienzo);

}
