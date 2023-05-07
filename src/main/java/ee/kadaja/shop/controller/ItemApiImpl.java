package ee.kadaja.shop.controller;


import java.util.List;

import ee.kadaja.shop.api.ItemApi;
import ee.kadaja.shop.dto.ItemDto;
import ee.kadaja.shop.mapper.ItemMapper;
import ee.kadaja.shop.service.ItemService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemApiImpl implements ItemApi {

    private final ItemMapper itemMapper;
    private final ItemService itemService;

    // Lazy fetch interview data in a read-only transaction for the mapper
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<ItemDto>> getItems() {
        log.info("Fetching all items.");
        val items = itemService.getItems();
        log.trace("Fetched items: {}", items);
        return ResponseEntity.ok(itemMapper.entitiesToDtoList(items));
    }

    @Override
    public ResponseEntity<ItemDto> addItem(ItemDto itemDto) {
        val draftItem = itemMapper.dtoToEntity(itemDto);
        val item = itemService.insertItem(draftItem);

        return ResponseEntity.status(CREATED)
                             .body(itemMapper.entityToDto(item));
    }


    //Needs PATCH with proper validation
    @Override
    public ResponseEntity<List<ItemDto>> updateItems(List<ItemDto> itemDtos){
        val draftItems = itemMapper.dtosToEntities(itemDtos);
        val items = itemService.updateItems(draftItems);

        return ResponseEntity.status(ACCEPTED)
                .body(itemMapper.entitiesToDtoList(items));
    }



}
