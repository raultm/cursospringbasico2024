package com.example.curso2024.interfaces.ajustefechaentrega;

import java.time.LocalDateTime;

public interface AjusteFechaEntrega {

    boolean aplica(LocalDateTime fechaEntrega);

    LocalDateTime ajustar(LocalDateTime fechaEntrega);

}
