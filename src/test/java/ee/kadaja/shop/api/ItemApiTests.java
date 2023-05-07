package ee.kadaja.shop.api;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import ee.kadaja.shop.dto.ItemDto;
import ee.kadaja.shop.dto.ItemStateDto;
import ee.kadaja.shop.dto.ItemTypeDto;
import ee.kadaja.shop.api.ItemApi;

import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemApiTests {

    @Autowired
    private ItemApi itemApi;

    @Test
    @Order(1)
    public void getItemsFromSeededData() {
        val response = itemApi.getItems();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        val items = response.getBody();
        assertNotNull(items);

        assertEquals(9, items.size());

        // Deep validate first only
        val first = items.get(0);
        val expectedFirstItem = ItemDto
                .builder().id(1000)
                .itemName("Brownie")
                .itemState(ItemStateDto.AVAILABLE)
                .itemType(ItemTypeDto.FOOD)
                .itemPrice(0.65)
                .itemAmount(48)
                .updatedOn(OffsetDateTime.of(LocalDate.now(), LocalTime.MIN, ZoneOffset.ofHours(3)))
                .build();
        assertEquals(first, expectedFirstItem);
    }
    //Update ->
}
