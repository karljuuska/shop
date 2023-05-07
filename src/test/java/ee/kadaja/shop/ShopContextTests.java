package ee.kadaja.shop;


import ee.kadaja.shop.api.ItemApi;
import ee.kadaja.shop.mapper.ItemMapper;
import ee.kadaja.shop.repository.ItemRepository;
import ee.kadaja.shop.statemachine.ItemStateInterceptor;
import ee.kadaja.shop.statemachine.ItemStateMachine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class ShopContextTests {

    @Autowired
    private ItemApi itemApi;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemStateMachine itemStateMachine;

    @Autowired
    private ItemStateInterceptor itemStateInterceptor;

    @Test
    void contextLoads() {
        assertNotNull(itemApi);
        assertNotNull(itemRepository);
        assertNotNull(itemMapper);
        assertNotNull(itemStateMachine);
        assertNotNull(itemStateInterceptor);
    }

}
