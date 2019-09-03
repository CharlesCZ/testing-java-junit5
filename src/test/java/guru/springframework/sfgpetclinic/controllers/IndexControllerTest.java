package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.ControllerTests;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


class IndexControllerTest implements ControllerTests {


    IndexController controller;

    @BeforeEach
    void setUp() {
        controller=new IndexController();
    }

    @DisplayName("Test Proper View name is returned for index page")
    @Test
    void index() {
        assertEquals("index",controller.index());
        assertEquals("index",controller.index(),"Wrong view Returned");

        assertEquals("index",controller.index(),()->"Another expensive message"+"Make me only if you have to");

        assertThat(controller.index()).isEqualTo("index");



    }

    @Test
    @DisplayName("Test exception")
    void oupsHandler() {

        assertThrows(ValueNotFoundException.class,() -> {
            controller.oopsHandler();
        });
    }


@Disabled("Demo of timeout")
    @Test
    void testTimeOut() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(2000);

            System.out.println("I got here");
        });



    }

    @Disabled
    @Test
    void testTimeOutPrempt() {

        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(2000);

            System.out.println("I got here 23423423432423");
        });
    }

    @Test
    void testAssumptionTrue() {

        assumeTrue("GURU".equalsIgnoreCase(System.getenv("GURU_RUNTIME")));
    }


    @Test
    void testAssumptionTrueAssumptionIsTrue() {

        assumeTrue("GURU".equalsIgnoreCase("GURU"));
    }


    @EnabledOnOs(OS.MAC)
    @Test
    void testMeOnMacOS() {
    }


    @EnabledOnOs(OS.LINUX)
    @Test
    void testMeOnLinux() {
    }

@EnabledOnJre(JRE.JAVA_8)
    @Test
    void testMeOnJAva8() {
    }

    @EnabledOnJre(JRE.JAVA_11)
    @Test
    void testMeOnJava11() {
    }

    @EnabledIfEnvironmentVariable(named = "USER",matches = "czarek")
    @Test
    void testIfUserJT() {
    }


    @EnabledIfEnvironmentVariable(named = "USER",matches = "fred")
    @Test
    void testIfUserFred() {
    }
}