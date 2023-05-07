package ee.kadaja.shop.config;


import java.util.UUID;

import ee.kadaja.shop.statemachine.ItemState;
import ee.kadaja.shop.statemachine.ItemEvent;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.config.StateMachineFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class ItemStateMachineImplConfigTest {

    @Autowired
    StateMachineFactory<ItemState, ItemEvent> factory;

    @Test
    public void createNewStateMachine() {
        val stateMachine = factory.getStateMachine(UUID.randomUUID());
        stateMachine.start();
        assertEquals(ItemState.AVAILABLE, stateMachine.getState().getId());

        assertTrue(stateMachine.sendEvent(ItemEvent.EXCLUDE));
        assertEquals(ItemState.UNAVAILABLE, stateMachine.getState().getId());
    }
}
