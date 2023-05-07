package ee.kadaja.shop.statemachine;


import org.springframework.statemachine.StateMachine;


public interface ItemStateMachine {


    StateMachine<ItemState, ItemEvent> excludeItem(
            Integer itemId);

    StateMachine<ItemState, ItemEvent> restockItem(
            Integer itemId);
}
