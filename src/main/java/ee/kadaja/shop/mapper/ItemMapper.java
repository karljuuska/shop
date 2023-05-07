package ee.kadaja.shop.mapper;


import java.util.List;

import ee.kadaja.shop.dto.ItemDto;
import ee.kadaja.shop.model.Item;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDto entityToDto(Item entity);

    Item dtoToEntity(ItemDto dto);

    List<ItemDto> entitiesToDtoList(List<Item> entities);

    List<Item> dtosToEntities(List<ItemDto> dtos);
}
