package com.example.curso2024.services.loans;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Loan;
import com.example.curso2024.models.Member;


class CalculateNewLoanServiceTest {
    
    CalculateNewLoanService calculateNewLoanService;

    @Mock Member member;
    @Mock Copy copy;

    String dateString = "2024-01-01";

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        calculateNewLoanService = new CalculateNewLoanService();
    }

    @Test
    void unPrestamoLePoneFechaDeDevolucionATresSemanas(){
      dateString = "2024-04-01";

      Loan loan = calculateNewLoanService.execute(member, copy, dateString);

      assertEquals("2024-04-22", loan.getExpiredAt().toString());
    }

    @Test
    void unPrestamoLePoneFechaDeDevolucionADosSemanasSiEsUnaNovedad(){
      Mockito.doReturn(true).when(copy).isNew();
      dateString = "2024-04-01";

      Loan loan = calculateNewLoanService.execute(member, copy, dateString);

      assertEquals("2024-04-15", loan.getExpiredAt().toString());
    }

    // @Test
    // void unPrestamoLePoneFechaDeDevolucionADosSemanasSiEsUnaNovedadAunqueUsuarioSeaPremium(){
    //   Mockito.doReturn(true).when(copy).isNovedad();
    //   String dateString = "2024-04-01";

    //   Loan loan = calculateNewLoanService.execute(member, copy, dateString);

    //   assertEquals("2024-04-15", loan.getExpiredAt().toString());
    // }

    @Test
    void unPrestamoLePoneFechaDeDevolucionACincoSemanasSiUsuarioEsPremiumYNoEsUnaNovedad(){
      Mockito.doReturn(true).when(member).isPremium();
      Mockito.doReturn(false).when(copy).isNew();
      String dateString = "2024-04-01";

      Loan loan = calculateNewLoanService.execute(member, copy, dateString);

      assertEquals("2024-05-06", loan.getExpiredAt().toString());
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiLaCopiaEstaPrestada(){
      Mockito.doReturn(true).when(copy).isBorrowed();

      Exception ex = assertThrows(RuntimeException.class, () -> { calculateNewLoanService.execute(member, copy, dateString); });
      assertEquals("La copia ya está prestada", ex.getMessage());
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiElSocioHaAlcanzadoElLimiteDePrestamos(){
      Mockito.doReturn(true).when(member).isLoanLimitReached();

      Exception ex = assertThrows(RuntimeException.class, () -> { calculateNewLoanService.execute(member, copy, dateString); });
      assertEquals("El Socio ha alcanzado el límite de préstamos abiertos", ex.getMessage());
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiElSocioNoTieneAlMenosLaEdadRecomendada(){
      Mockito.doReturn(18).when(copy).getRecommendedAge();
      Mockito.doReturn(17).when(member).getAge();

      Exception ex = assertThrows(RuntimeException.class, () -> { calculateNewLoanService.execute(member, copy, dateString); });
      assertEquals("La Copia no se puede prestar a ese Socio por la edad", ex.getMessage());
    }

    @Test
    void unPrestamoNoSePuedeRealizarSiLaCopiaEstaReservada(){
      Mockito.doReturn("demo").when(copy).getReservedBy();

      Exception ex = assertThrows(RuntimeException.class, () -> { calculateNewLoanService.execute(member, copy, dateString); });
      assertEquals("La copia ya está reservada", ex.getMessage());

    }

    @Test
    void unPrestamoSePuedeRealizarSiLaCopiaEstaReservadaAlUsuarioQueSeLeVaHaHacerElPrestamo(){
      Mockito.doReturn("demo").when(copy).getReservedBy();
      Mockito.doReturn("demo").when(member).getUsername();

      assertDoesNotThrow(() -> { calculateNewLoanService.execute(member, copy, dateString); });
    }
}
