package com.example.curso2024.suites;

import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.example.curso2024.testing.AssertsList;
import com.example.curso2024.testing.Doubles;
import com.example.curso2024.testing.Hamcrest;

@DisplayName("Suite de Pruebas")
@Suite
@SelectClasses({AssertsList.class, Doubles.class, Hamcrest.class})
public class SuiteClasses {
    
}
