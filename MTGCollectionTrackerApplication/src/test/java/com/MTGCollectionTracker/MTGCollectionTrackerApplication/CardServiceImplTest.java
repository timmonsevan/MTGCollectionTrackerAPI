package com.MTGCollectionTracker.MTGCollectionTrackerApplication;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.dao.CardDAOImpl;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.DatabaseCard;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.exceptions.CardNotFoundException;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.service.CardServiceImpl;
import io.magicthegathering.javasdk.api.CardAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * unit tests for CardServiceImpl
 * @author timmonsevan
 */
@SpringBootTest(classes = MtgCollectionTrackerApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CardServiceImplTest {

    @Autowired
    private CardDAOImpl cardDao;

    @Autowired
    private CardServiceImpl cardService;

    /**
     * test add methods
     * @throws ClassNotFoundException
     */
    @Test
    public void cardServiceAddTest() throws ClassNotFoundException {

        assertEquals("choke was added to your collection.", cardService.addNewCard("choke", String.valueOf(2)));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("", String.valueOf(2)));
        assertEquals("Invalid entry, please check that numCards is whole number value > 0",
                cardService.addNewCard("choke", String.valueOf("a")));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("choke", String.valueOf(-1)));
        assertEquals("consider already exists in your collection", cardService.addNewCard("consider", String.valueOf(4)));
        assertEquals("consider already exists in your collection",
                cardService.addNewCard("consider", String.valueOf(4), "MID"));
        assertThrows(CardNotFoundException.class, () -> cardService.addNewCard("tonker", String.valueOf(1)));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("", String.valueOf(1), "M10"));
        assertEquals("Invalid entry, please check that numCards is whole number value > 0",
                cardService.addNewCard("ponder", String.valueOf("a"), "M10"));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("giant growth", String.valueOf(-1), "M11"));
        assertEquals("giant growth was added to your collection.",
                cardService.addNewCard("giant growth", String.valueOf(1), "M11"));
        assertEquals("firebolt from set GTC cannot be found",
                cardService.addNewCard("firebolt", String.valueOf(1), "GTC"));
    }

    /**
     * test search methods
     * @throws ClassNotFoundException
     */
    @Test
    public void cardServiceSearchTest() throws ClassNotFoundException {

        assertThrows(IllegalArgumentException.class, () -> cardService.searchCollectionByName(""));
        assertEquals("Card 'black lotus' not found in your collection.",
                cardService.searchCollectionByName("black lotus"));
        assertEquals("[You have 1 copy of Time Warp in your collection\n]",
                cardService.searchCollectionByName("Time Warp"));
        assertEquals("[You have 2 copies of Snapcaster Mage in your collection\n]",
                cardService.searchCollectionByName("Snapcaster Mage"));

        long id = 4;

        assertEquals("Time Warp", cardService.findById(id).getName());
    }

    /**
     * test update methods
     * @throws ClassNotFoundException
     */
    @Test
    public void cardServiceUpdateTest() throws ClassNotFoundException {

        DatabaseCard databaseCard = new DatabaseCard(Objects.requireNonNull(CardAPI.getCardByName("path to exile")), 4);
        cardService.addNewCard(databaseCard.getName(), String.valueOf(databaseCard.getQuantity()));

        assertThrows(IllegalArgumentException.class, () -> cardService.updateCard("", String.valueOf(1)));
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCard("", String.valueOf(1), "M10"));
        assertEquals("Invalid entry, please check that numCards is whole number value > 0",
                cardService.updateCard("Ponder", String.valueOf('a')));
        assertEquals("Invalid entry, please check that numCards is whole number value > 0",
                cardService.updateCard("Ponder", String.valueOf('a'), "M10"));
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCard("ponder", String.valueOf(-1)));
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCard("ponder", String.valueOf(-1), "M10"));
        assertEquals("Card 'giant growth' not found in your collection.",
                cardService.updateCard("giant growth", String.valueOf(2)));
        assertEquals("Card 'giant growth' not found in your collection.",
                cardService.updateCard("giant growth", String.valueOf(2), "M11"));
        assertEquals("path to exile quantity updated", cardService.updateCard("path to exile", String.valueOf(3)));
        assertEquals("path to exile updated", cardService.updateCard("path to exile", String.valueOf(2), "CMA"));

        cardService.removeCardFromCollection("path to exile");
    }

    /**
     * test deletion methods
     * @throws ClassNotFoundException
     */
    @Test
    public void cardServiceRemoveTest() throws ClassNotFoundException {

        DatabaseCard databaseCard = new DatabaseCard(Objects.requireNonNull(CardAPI.getCardByName("path to exile")), 4);
        cardService.addNewCard(databaseCard.getName(), String.valueOf(databaseCard.getQuantity()));

        assertThrows(IllegalArgumentException.class, () -> cardService.removeCardFromCollection(""));
        assertEquals("Card 'giant growth' not found in your collection.",
                cardService.removeCardFromCollection("giant growth"));
        assertEquals("path to exile removed from collection.", cardService.removeCardFromCollection("path to exile"));
        assertEquals("redirect:/app/collection", cardService.removeCardFromCollection(databaseCard.getId()));

        cardService.removeCardFromCollection("path to exile");
    }

    /**
     * test view method
     */
    @Test
    public void viewCollectionTest() {

        String collection = cardService.viewCollection();

        assertNotEquals("Collection is empty.", cardService.viewCollection());
        assertEquals(collection, cardService.viewCollection());
    }

    /**
     * test list method
     */
    @Test
    public void listCollectionTest() {

        List<DatabaseCard> emptyList = new ArrayList<>();
        String listString = cardService.listCollection().toString();

        assertNotEquals(emptyList, cardService.listCollection());
        assertEquals(listString, cardService.listCollection().toString());
    }
}
