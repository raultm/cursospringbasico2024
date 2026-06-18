package com.example.curso2024.services.loans;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Nested;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.curso2024.interfaces.noprestable.CopiaEnPrestamo;
import com.example.curso2024.interfaces.noprestable.SocioNoProfesorEnFinDeSemana;
import com.example.curso2024.interfaces.noprestable.SocioSuperaLimitePrestamos;
import com.example.curso2024.interfaces.noprestable.SocioTienePrestamoVencido;
import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Loan;
import com.example.curso2024.models.Member;

@SpringBootTest
class CalcularPrestamoServiceTest {

  @Autowired
  CalcularPrestamoService calculateNewLoanService;

  @Mock
  Member member;
  @Mock
  Copy copy;

  String dateString = "2024-04-01T10:24";

  DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Nested
  public class EnunciadoConFuncionalidadBasica {

    @Test
    void unPrestamoLePoneFechaDeDevolucionATresSemanasSiNoTieneNingunPerfil() {
      dateString = "2024-04-01T10:24";

      Loan loan = calculateNewLoanService.execute(member, copy, dateString);

      assertEquals("2024-04-22", loan.getExpiredAt().format(dateFormatter));
    }

    @Test
    void unPrestamoLePoneFechaDeDevolucionAUnaSemanaSiEsUnVisitante() {
      doReturn(true).when(member).isVisitante();
      dateString = "2024-04-01T12:45";

      Loan loan = calculateNewLoanService.execute(member, copy, dateString);

      assertEquals("2024-04-08", loan.getExpiredAt().format(dateFormatter));
    }

    @Test
    void unPrestamoLePoneFechaDeDevolucionADosSemanasSiEsUnEstudiante() {
      doReturn(true).when(member).isEstudiante();
      dateString = "2024-04-01T18:32";

      Loan loan = calculateNewLoanService.execute(member, copy, dateString);

      assertEquals("2024-04-16", loan.getExpiredAt().format(dateFormatter));
    }

    @Test
    void unPrestamoLePoneFechaDeDevolucionATreintaDiasSiEsUnProfesor() {
      doReturn(true).when(member).isProfesor();
      dateString = "2024-04-01T08:05";

      Loan loan = calculateNewLoanService.execute(member, copy, dateString);

      assertEquals("2024-05-01", loan.getExpiredAt().format(dateFormatter));
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiLaCopiaEstaPrestada() {
      Mockito.doReturn(true).when(copy).estaEnPrestamo();

      Exception ex = assertThrows(RuntimeException.class, () -> {
        calculateNewLoanService.execute(member, copy, dateString);
      });
      assertTrue(ex.getMessage().contains(CopiaEnPrestamo.MENSAJE));
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiElSocioHaAlcanzadoElLimiteDePrestamos() {
      Mockito.doReturn(true).when(member).haSuperadoElLimiteDePrestamos();

      Exception ex = assertThrows(RuntimeException.class, () -> {
        calculateNewLoanService.execute(member, copy, dateString);
      });
      assertTrue(ex.getMessage().contains(SocioSuperaLimitePrestamos.MENSAJE));
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiElSocioTieneUnPrestamoVencido() {
      Mockito.doReturn(true).when(member).tienePrestamoVencido();

      Exception ex = assertThrows(RuntimeException.class, () -> {
        calculateNewLoanService.execute(member, copy, dateString);
      });
      assertTrue(ex.getMessage().contains(SocioTienePrestamoVencido.MENSAJE));
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiElSocioEsEstudianteYFinDeSemana() {
      doReturn(true).when(member).isEstudiante();
      dateString = "2024-04-06T18:32"; // Sábado

      Exception ex = assertThrows(RuntimeException.class, () -> {
        calculateNewLoanService.execute(member, copy, dateString);
      });
      assertTrue(ex.getMessage().contains(SocioNoProfesorEnFinDeSemana.MENSAJE));
      
    }

  }

}
