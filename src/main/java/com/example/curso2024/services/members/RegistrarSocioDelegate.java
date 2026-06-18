package com.example.curso2024.services.members;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.curso2024.api.SociosApiDelegate;
import com.example.curso2024.dto.MemberCreate;
import com.example.curso2024.dto.Socio;
import com.example.curso2024.dto.SocioRegistro;
import com.example.curso2024.models.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrarSocioDelegate implements SociosApiDelegate {

    private final SaveMemberService saveMemberService;

    @Override
    public ResponseEntity<Socio> registrarSocio(SocioRegistro socioRegistro) {
        MemberCreate memberCreate = MemberCreate.builder()
                .username(socioRegistro.getNombre())
                .email(socioRegistro.getEmail())
                .perfil(socioRegistro.getPerfil())
                .birthDate(socioRegistro.getNacimiento() != null ? LocalDate.parse(socioRegistro.getNacimiento()) : null)
                .build();

        Member member = saveMemberService.execute(memberCreate);

        return ResponseEntity.ok(toSocio(member));
    }

    private Socio toSocio(Member member) {
        Socio socio = new Socio();
        socio.setId((int) member.getId());
        socio.setNombre(member.getUsername());
        socio.setEmail(member.getEmail());
        socio.setPerfil(member.getPerfil());
        socio.setNacimiento(member.getBirthDate() != null ? member.getBirthDate().toString() : null);
        return socio;
    }

}
