package ee.kadaja.shop.statemachine;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ee.kadaja.shop.repository.ItemRepository;


@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class ItemStateMachineImpl implements ItemStateMachine {

    public static final String ITEM_ID_HEADER = "item_id";

    private final ItemRepository itemRepository;
    private final StateMachineFactory<ItemState, ItemEvent> stateMachineFactory;
    private final ItemStateInterceptor itemStateInterceptor;


    @Override
    public StateMachine<ItemState, ItemEvent> excludeItem(
            Integer itemId) {
        val stateMachine = build(itemId);
        sendEvent(itemId, stateMachine, ItemEvent.EXCLUDE);

        return stateMachine;
    }

    @Override
    public StateMachine<ItemState, ItemEvent> restockItem(
            Integer itemId) {
        val stateMachine = build(itemId);
        sendEvent(itemId, stateMachine, ItemEvent.RESTOCK);

        return stateMachine;
    }


    private void sendEvent(Integer itemId,
                           StateMachine<ItemState, ItemEvent> stateMachine,
                           ItemEvent itemEvent) {
        val message = MessageBuilder
                .withPayload(itemEvent)
                .setHeader(ITEM_ID_HEADER, itemId)
                .build();

        stateMachine.sendEvent(message);
    }


    private StateMachine<ItemState, ItemEvent> build(Integer itemId) {
        val item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> {
                    log.error("Couldn't find the item with given id {}", itemId);
                    return new IllegalArgumentException("Invalid item id");
                });

        val stateMachine = stateMachineFactory.getStateMachine(String.valueOf(item.getId()));
        stateMachine.stop();

        stateMachine.getStateMachineAccessor()
                    .doWithAllRegions(sma -> {
                        sma.addStateMachineInterceptor(itemStateInterceptor);
                        sma.resetStateMachine(new DefaultStateMachineContext<>(
                                item.getItemState(), null, null, null));
                    });

        stateMachine.start();
        return stateMachine;
    }
}
