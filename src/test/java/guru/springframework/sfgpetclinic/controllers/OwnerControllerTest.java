package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    private final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    OwnerController ownerController;

@Captor
ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void processCreationFormHappyPath() {
        //given
        Owner owner = new Owner(null,"Iks","Iksinski");
        Owner savedOwner= new Owner(5L,"Iks","Iksinski");
        given(ownerService.save(owner)).willReturn(savedOwner);
        given(bindingResult.hasErrors()).willReturn(false);

        //when
        String redirectPath=ownerController.processCreationForm(owner,bindingResult);

        //then
        then(ownerService).should().save(owner);
        then(bindingResult).should().hasErrors();
        assertEquals(REDIRECT_OWNERS_5,redirectPath);
    }

    @Test
    void processCreationFormHasErrors() {
        //given
        Owner owner = new Owner(null,"Iks","Iksinski");
        given(bindingResult.hasErrors()).willReturn(true);


        //when
        String redirectPath=ownerController.processCreationForm(owner,bindingResult);

        //then
        then(bindingResult).should().hasErrors();
        assertEquals(VIEWS_OWNER_CREATE_OR_UPDATE_FORM ,redirectPath);
    }

    @Test
    void processFindFormWildcardString() {
        //given
        Owner owner = new Owner(null,"Iks","Iksinski");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor=ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);


        //when
        String viewName=ownerController.processFindForm(owner,bindingResult,null);

        //then
        assertThat(captor.getValue()).isEqualToIgnoringCase("%Iksinski%");
    }


    @Test
    void processFindFormWildcardStringAnnotation() {
        //given
        Owner owner = new Owner(null,"Iks","Iksinski");
        List<Owner> ownerList = new ArrayList<>();
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);


        //when
        String viewName=ownerController.processFindForm(owner,bindingResult,null);

        //then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Iksinski%");
    }
}