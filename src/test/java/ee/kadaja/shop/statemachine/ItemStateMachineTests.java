package ee.kadaja.shop.statemachine;


import ee.kadaja.shop.model.Item;
import ee.kadaja.shop.repository.ItemRepository;
import ee.kadaja.shop.service.ItemService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest
public class ItemStateMachineTests {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemStateMachine itemStateMachine;

    @Autowired
    ItemRepository itemRepository;


    //TODO StateMachine
    /*
    @Test
    @Transactional
    public void itemGetsExcluded() {
        val newItem = Item.builder().build();

        val itemSaved = itemService.insertItem(newItem);
        val initialUpdatedOn = itemSaved.getUpdatedOn();
        assertEquals(ItemState.AVAILABLE, itemSaved.getItemState());

        val stateMachine = itemStateMachine.excludeItem(itemSaved.getId());
        assertEquals(ItemState.UNAVAILABLE, stateMachine.getState().getId());

        val optionalExcludedItem = itemRepository.findById(itemSaved.getId());
        assertFalse(optionalExcludedItem.isEmpty());

        val excludedItem = optionalExcludedItem.get();
        assertEquals(ItemState.UNAVAILABLE, excludedItem.getItemState());
        assertNotEquals(initialUpdatedOn, excludedItem.getUpdatedOn());

    }

     */
}
