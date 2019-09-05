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
        Set<Visit> visits=new HashSet<>();
        visits.add(new Visit());
        visits.add(new Visit());

        when(visitRepository.findAll()).thenReturn(visits);

        //when
        Set<Visit> returnedVisits=service.findAll();

        //then
        verify(visitRepository).findAll();
        assertEquals(2l,returnedVisits.size());

    }

    @Test
    void findById() {
        //given
        Visit visit=new Visit(1L);

        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        //when
        Visit returnedVisit=service.findById(1L);

        //then
        verify(visitRepository).findById(1L);
        assertEquals(Long.valueOf(1L),returnedVisit.getId());
    }

    @Test
    void save() {
        //given
        Visit visit=new Visit();

        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        //when
        Visit returnedVisit=service.save(visit);
        //then
        verify(visitRepository).save(any(Visit.class));

        assertThat(returnedVisit).isNotNull();
    }

    @Test
    void delete() {
        //given
        Visit visit=new Visit();

        service.delete(visit);

        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {

        service.deleteById(1L);

        verify(visitRepository).deleteById(anyLong());

    }
}