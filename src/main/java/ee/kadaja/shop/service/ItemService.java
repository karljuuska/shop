package ee.kadaja.shop.service;


import java.time.OffsetDateTime;
import java.util.List;

import ee.kadaja.shop.model.Item;
import ee.kadaja.shop.statemachine.ItemStateMachine;
import ee.kadaja.shop.repository.ItemRepository;
import ee.kadaja.shop.statemachine.ItemState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //private final ItemStateMachine itemStateMachine;

    public List<Item> getItems() {
        return itemRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional
    public Item insertItem(Item item) {
        log.info("Inserting item: {}", item);
        item.setId(null);
        if(item.getItemPrice() > 0){
            item.setItemState(ItemState.AVAILABLE);
        }else{
            item.setItemState(ItemState.UNAVAILABLE);
        }
        item.setUpdatedOn(OffsetDateTime.now());

        return itemRepository.save(item);
    }


    @Transactional
    public List<Item> updateItems(List<Item> items){

        List<Item> itemsFromDB = getItems();

        //ItemStateMachine(for fun) chaotic rn
        for (Item item : items) {
            for (Item itemFromDB : itemsFromDB) {
                if (item.getId().equals(itemFromDB.getId())) {
                    if (itemFromDB.getItemAmount() == 0 && item.getItemAmount() > 0) {
                        itemFromDB.setItemState(ItemState.AVAILABLE);
                        //itemStateMachine.restockItem(itemsFromDB.get(i).getId());
                    } else if (itemFromDB.getItemAmount() > 0 && item.getItemAmount() == 0) {
                        itemFromDB.setItemState(ItemState.UNAVAILABLE);
                        //itemStateMachine.excludeItem(itemsFromDB.get(j).getId());
                    }
                    itemFromDB.setItemAmount(item.getItemAmount());
                }
            }
        }

        return itemRepository.saveAll(itemsFromDB);
    }



}
