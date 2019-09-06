package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    private final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;


    @InjectMocks
    OwnerController ownerController;

@Captor
ArgumentCaptor<String> stringArgumentCaptor;


    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> {

         List<Owner> owners=new ArrayList<>();

        String name=invocation.getArgument(0);


        if(name.equals("%Iksinski%")){
            owners.add(new Owner(1L,"Iks","Iksinski"));
            return owners;
        }else if(name.equals("%DontFindMe%")) {
            return owners;
        }else if(name.equals("%FindMe%")) {
            owners.add(new Owner(1L,"Iks","Iksinski"));
            owners.add(new Owner(2L,"Iks2","Iksinski2"));
            return owners;
        }

        throw new  RuntimeException("Invalid Argument");
                });
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        //given
        Owner owner = new Owner(null,"Iks","Iksinski");

        //when
        String viewName=ownerController.processFindForm(owner,bindingResult,model);

        //then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Iksinski%");
        assertThat("redirect:/owners/1").isEqualToIgnoringCase(viewName);
        verifyZeroInteractions(model);
    }

    @Test
    void processFindFormWildcardFound() {
        //given
        Owner owner = new Owner(null,"Iks","FindMe");
        InOrder inOrder=inOrder(ownerService,model);


        //when
        String viewName=ownerController.processFindForm(owner,bindingResult, model);

        //then
        assertThat("%FindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/ownersList").isEqualToIgnoringCase(viewName);

        //inorder asserts
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model).addAttribute(anyString(),anyList());
        verifyNoMoreInteractions(model);

    }

    @Test
    void processFindFormWildcardNotFound() {
        //given
        Owner owner = new Owner(null,"Iks","DontFindMe");

        //when
        String viewName=ownerController.processFindForm(owner,bindingResult,model);

        verifyNoMoreInteractions(ownerService);
        //then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%DontFindMe%");
        assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
        verifyZeroInteractions(model);
    }

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




}