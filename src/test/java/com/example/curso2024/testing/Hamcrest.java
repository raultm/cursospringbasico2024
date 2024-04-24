package com.example.curso2024.testing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class Hamcrest {
    
    @Test
    void startsWithExample(){
        assertThat("cadena", startsWith("cad"));
    }

    @Test
    void endsWithExample(){
        assertThat("cadena", endsWith("ena"));
    }

    @Test
    void containsExample(){
        assertThat("cadena", containsString("den"));
    }

    @Test
    void greaterThanExample(){
        assertThat(7, greaterThan(6));
    }

    @Test
    void lessThanExample(){
        assertThat(7, lessThan(8));
    }

    @Test
    void nullExample(){
        assertThat(null, nullValue());
    }

    @Test
    void notNullExample(){
        assertThat("cadena", notNullValue());
    }

    @Test
    void hasPropertyExample(){
        assertThat(new Persona(), hasProperty("nombre", is("Alfonso")));
    }

    public class Persona {
        public String nombre = "Alfonso";

         public String getNombre(){ return nombre; }
    }
}
