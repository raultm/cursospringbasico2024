package com.example.curso2024.interfaces.contextoprestamo;

import java.time.LocalDateTime;

public interface ContextoPrestamo {

    Contexto getContexto();

    boolean aplica(LocalDateTime fechaComienzo);

}
