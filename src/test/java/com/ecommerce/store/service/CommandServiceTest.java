package com.ecommerce.store.service;

import com.ecommerce.store.model.Client;
import com.ecommerce.store.model.Command;
import com.ecommerce.store.repository.CommandRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class CommandServiceTest {

    @InjectMocks
    CommandService commandService;

    @Mock
    CommandRepository commandRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addCommand() {
      /*  Client client_1 = new Client();
        client_1.setId(1L);

        Command command_1 = new Command();
        command_1.setId(1L);
        command_1.setClient(client_1);
        command_1.setDate(new Date());


        when(commandRepository.save(command_1)).thenReturn(command_1);

        Assert.assertThat(commandService.addCommandByClient(client_1).getId(),is(1));

        verify(commandRepository, atLeast(1)).save(command_1);
*/
    }

    @Test
    void testAddCommand() {

        Command command_1 = new Command();
        command_1.setId(1L);
        command_1.setDate(new Date());


        when(commandRepository.save(command_1)).thenReturn(command_1);

        Assert.assertThat(commandService.addCommandByClient(command_1), is(command_1));

        verify(commandRepository, times(1)).save(command_1);

    }
}