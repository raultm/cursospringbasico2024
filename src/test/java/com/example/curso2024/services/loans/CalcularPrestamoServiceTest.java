package com.example.curso2024.services.loans;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Nested;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Loan;
import com.example.curso2024.models.Member;

class CalcularPrestamoServiceTest {

  CalcularPrestamoService calculateNewLoanService;

  @Mock
  Member member;
  @Mock
  Copy copy;

  String dateString = "2024-01-01";

  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    calculateNewLoanService = new CalcularPrestamoService();
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
      assertEquals(CalcularPrestamoService.COPIA_PRESTADA, ex.getMessage());
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiElSocioHaAlcanzadoElLimiteDePrestamos() {
      Mockito.doReturn(true).when(member).haSuperadoElLimiteDePrestamos();

      Exception ex = assertThrows(RuntimeException.class, () -> {
        calculateNewLoanService.execute(member, copy, dateString);
      });
      assertEquals(CalcularPrestamoService.SOCIO_LIMITE_PRESTAMO, ex.getMessage());
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiElSocioTieneUnPrestamoVencido() {
      Mockito.doReturn(true).when(member).tienePrestamoVencido();

      Exception ex = assertThrows(RuntimeException.class, () -> {
        calculateNewLoanService.execute(member, copy, dateString);
      });
      assertEquals(CalcularPrestamoService.SOCIO_PRESTAMO_VENCIDO, ex.getMessage());
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiElSocioEsEstudianteYFinDeSemana() {
      doReturn(true).when(member).isEstudiante();
      dateString = "2024-04-06T18:32"; // Sábado

      Exception ex = assertThrows(RuntimeException.class, () -> {
        calculateNewLoanService.execute(member, copy, dateString);
      });
      assertEquals(CalcularPrestamoService.FECHA_FIN_SEMANA, ex.getMessage());
    }

  }

  @Nested
  @Disabled
  public class FuncionalidadAvanzadaLimitarCreacionPrestamos {

    @Test
    void unPrestamoNoSePuedeRealizarSiElSocioNoTieneAlMenosLaEdadRecomendada() {
      Mockito.doReturn(18).when(copy).getRecommendedAge();
      Mockito.doReturn(17).when(member).getAge();

      Exception ex = assertThrows(RuntimeException.class, () -> {
        calculateNewLoanService.execute(member, copy, dateString);
      });
      assertEquals(CalcularPrestamoService.COPIA_NO_DISPONIBLE_POR_EDAD,
          ex.getMessage());
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiLaCopiaEstaReservada() {
      Mockito.doReturn("demo").when(copy).getReservedBy();

      Exception ex = assertThrows(RuntimeException.class, () -> {
        calculateNewLoanService.execute(member, copy, dateString);
      });
      assertEquals(CalcularPrestamoService.COPIA_RESERVADA, ex.getMessage());

    }
  }

  // @Test
  // void unPrestamoLePoneFechaDeDevolucionADosSemanasSiEsUnaNovedad() {
  // Mockito.doReturn(true).when(copy).isNew();
  // dateString = "2024-04-01";

  // Loan loan = calculateNewLoanService.execute(member, copy, dateString);

  // assertEquals("2024-04-15", loan.getExpiredAt().toString());
  // }

  // @Test
  // void
  // unPrestamoLePoneFechaDeDevolucionACincoSemanasSiUsuarioEsPremiumYNoEsUnaNovedad()
  // {
  // Mockito.doReturn(true).when(member).isPremium();
  // Mockito.doReturn(false).when(copy).isNew();
  // String dateString = "2024-04-01";

  // Loan loan = calculateNewLoanService.execute(member, copy, dateString);

  // assertEquals("2024-05-06", loan.getExpiredAt().toString());
  // }

  // @Test
  // void
  // unPrestamoSePuedeRealizarSiLaCopiaEstaReservadaAlUsuarioQueSeLeVaHaHacerElPrestamo()
  // {
  // Mockito.doReturn("demo").when(copy).getReservedBy();
  // Mockito.doReturn("demo").when(member).getUsername();

  // assertDoesNotThrow(() -> {
  // calculateNewLoanService.execute(member, copy, dateString);
  // });
  // }

  // @Test
  // void
  // unPrestamosSeIncrementaEnUnaSemanaDeLoHabitualSiEsUnLibroEsNovedadYLongitudMayorDe900()
  // {
  // Mockito.doReturn(Item.LIBRO).when(copy).getType();
  // Mockito.doReturn(true).when(copy).isNew();
  // Mockito.doReturn(901).when(copy).getDuration();
  // String dateString = "2024-04-01";

  // Loan loan = calculateNewLoanService.execute(member, copy, dateString);

  // assertEquals("2024-04-22", loan.getExpiredAt().toString());
  // }

  // @Test
  // void unPrestamosDeUnDiscoSoloEsPorUnaSemana() {
  // Mockito.doReturn(Item.DISCO).when(copy).getType();
  // String dateString = "2024-04-01";

  // Loan loan = calculateNewLoanService.execute(member, copy, dateString);

  // assertEquals("2024-04-08", loan.getExpiredAt().toString());
  // }

  // @Test
  // void unPrestamosDeUnDiscoEsDeDosSemanasSiElUsuarioEsPremium() {
  // Mockito.doReturn(true).when(member).isPremium();
  // Mockito.doReturn(Item.DISCO).when(copy).getType();
  // String dateString = "2024-04-01";

  // Loan loan = calculateNewLoanService.execute(member, copy, dateString);

  // assertEquals("2024-04-15", loan.getExpiredAt().toString());
  // }
}
