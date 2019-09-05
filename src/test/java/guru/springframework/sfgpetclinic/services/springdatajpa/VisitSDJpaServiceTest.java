package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import guru.springframework.sfgpetclinic.services.Dog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;


    @DisplayName("Test Find All")
    @Test
    void findAll() {
        //given
        Set<Visit> visits=new HashSet<>();
        visits.add(new Visit());

        given(visitRepository.findAll()).willReturn(visits);

        //when
        Set<Visit> returnedVisits=service.findAll();

        //then
        then(visitRepository).should().findAll();
        assertEquals(1l,returnedVisits.size());

    }

    @Test
    void findById() {
        //given
        Visit visit=new Visit(1L);
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));

        //when
        Visit returnedVisit=service.findById(1L);

        //then
        then(visitRepository).should().findById(1L);
        assertEquals(Long.valueOf(1L),returnedVisit.getId());
    }

    @Test
    void save() {
        //given
        Visit visit=new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(visit);

        //when
        Visit returnedVisit=service.save(visit);

        //then
        then(visitRepository).should().save(any(Visit.class));
        assertThat(returnedVisit).isNotNull();
    }

    @Test
    void delete() {
        //given
        Visit visit=new Visit();

        //when
        service.delete(visit);

        //then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {

        //when
        service.deleteById(1L);

        //then
        then(visitRepository).should().deleteById(anyLong());

    }
}