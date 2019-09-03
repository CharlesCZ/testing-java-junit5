package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class PersonTest implements ModelTests {


    @Test
    void groupedAssertion() {

        //given
        Person person=new Person(1l,"Joe","Buck");

        //then
        assertAll("Test Prop Set",
                () -> assertEquals("Joe",person.getFirstName()),
                () -> assertEquals("Buck",person.getLastName()));

    }

    @Test
    void groupedAssertionMsgs() {

        //given
        Person person=new Person(1l,"Joe","Buck");

        //then
        assertAll("Test Prop Set",
                () -> assertEquals("Joe",person.getFirstName(),"First Name Failed"),
                () -> assertEquals("Buck",person.getLastName(),"Last Name Failed"));

    }






}