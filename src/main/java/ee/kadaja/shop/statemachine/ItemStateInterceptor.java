package ee.kadaja.shop.statemachine;


import java.time.OffsetDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import ee.kadaja.shop.repository.ItemRepository;

import static ee.kadaja.shop.statemachine.ItemStateMachineImpl.ITEM_ID_HEADER;


@Slf4j
@Component
@RequiredArgsConstructor
public class ItemStateInterceptor
        extends StateMachineInterceptorAdapter<ItemState, ItemEvent> {
    private final ItemRepository itemRepository;

    @Override
    public void preStateChange(
            State<ItemState, ItemEvent> state,
            Message<ItemEvent> message,
            Transition<ItemState, ItemEvent> transition,
            StateMachine<ItemState, ItemEvent> stateMachine
    ) {
        Optional.ofNullable(message)
                .flatMap(eventMessage -> Optional.ofNullable(
                        eventMessage.getHeaders()
                                    .get(ITEM_ID_HEADER, Integer.class)))
                .flatMap(itemRepository::findById)
                .ifPresentOrElse(item -> {
                    item.setItemState(state.getId());
                    item.setUpdatedOn(OffsetDateTime.now());

                    itemRepository.save(item);
                }, () -> {
                    log.error("Statemachine preStateChange failed! Possible empty message or "
                                      + "invalid item id detected.");
                    throw new IllegalArgumentException("Statemachine preStateChange failed!");
                });
    }
}
