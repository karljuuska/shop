package ee.kadaja.shop.config;


import java.util.EnumSet;

import ee.kadaja.shop.statemachine.ItemState;
import ee.kadaja.shop.statemachine.ItemEvent;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;


@Slf4j
@Configuration
@EnableStateMachineFactory
public class ItemStateMachineConfig
        extends StateMachineConfigurerAdapter<ItemState, ItemEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<ItemState, ItemEvent> states)
            throws Exception {
        states.withStates()
              .initial(ItemState.AVAILABLE)
              .states(EnumSet.allOf(ItemState.class));
    }

    //Needs restructuring
    @Override
    public void configure(
            StateMachineTransitionConfigurer<ItemState, ItemEvent> transitions)
            throws Exception {
                    transitions.withExternal()
                    .source(ItemState.AVAILABLE)
                    .target(ItemState.UNAVAILABLE)
                    .event(ItemEvent.EXCLUDE)

                    .and()
                    .withExternal()
                    .source(ItemState.UNAVAILABLE)
                    .target(ItemState.AVAILABLE)
                    .event(ItemEvent.RESTOCK)

                    .and()
                    .withExternal()
                    .source(ItemState.UNAVAILABLE)
                    .target(ItemState.UNAVAILABLE)
                    .event(ItemEvent.EXCLUDE)

                    .and()
                    .withExternal()
                    .source(ItemState.AVAILABLE)
                    .target(ItemState.AVAILABLE)
                    .event(ItemEvent.RESTOCK);

    }

    @Override
    public void configure(
            StateMachineConfigurationConfigurer<ItemState, ItemEvent> config)
            throws Exception {
        val listenerAdapter =
                new StateMachineListenerAdapter<ItemState, ItemEvent>() {

                    @Override
                    public void stateChanged(
                            State<ItemState, ItemEvent> from,
                            State<ItemState, ItemEvent> to) {
                        log.info("State transition {} -> {}", from, to);
                    }

                    @Override
                    public void eventNotAccepted(Message<ItemEvent> event) {
                        log.error("Event {} not allowed in current state!", event);
                        throw new IllegalStateException("Event not allowed!");
                    }
                };

        config.withConfiguration().listener(listenerAdapter);
    }
}
