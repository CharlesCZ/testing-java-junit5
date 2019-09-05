package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;


    @InjectMocks
    SpecialitySDJpaService service;


    @Test
    void testDeleteByObject() {
        Speciality speciality=new Speciality();

        service.delete(speciality);

        verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void deleteById() {
        service.deleteById(1l);
        service.deleteById(1l);
        verify(specialtyRepository,times(2)).deleteById(1l);
    }


    @Test
    void deleteByIdAtLeast() {
        service.deleteById(1l);
        service.deleteById(1l);
        verify(specialtyRepository,atLeastOnce()).deleteById(1l);
    }



    @Test
    void deleteByIdAtMost() {
        service.deleteById(1l);
        service.deleteById(1l);
        verify(specialtyRepository,atMost(5)).deleteById(1l);
    }


    @Test
    void deleteByIdNever() {
        service.deleteById(1l);
        service.deleteById(1l);
        verify(specialtyRepository,never()).deleteById(5l);
    }


    @Test
    void testDelete() {
        service.delete(new Speciality());
    }

    @Test
    void findByIdTest() {
        Speciality speciality=new Speciality();


        when(specialtyRepository.findById(1l)).thenReturn(Optional.of(speciality));

        Speciality foundSpeciality=service.findById(1l);

        assertThat(foundSpeciality).isNotNull();

        verify(specialtyRepository).findById(anyLong());



    }


    @Test
    void findByIdBddTest() {
        Speciality speciality=new Speciality();

        given(specialtyRepository.findById(1l)).willReturn(Optional.of(speciality));

        Speciality foundSpeciality=service.findById(1l);

        assertThat(foundSpeciality).isNotNull();
        verify(specialtyRepository).findById(anyLong());

    }
}