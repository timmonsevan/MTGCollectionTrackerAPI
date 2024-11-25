package com.MTGCollectionTracker.MTGCollectionTrackerApplication;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.dao.CardDAOImpl;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.exceptions.CardNotFoundException;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.service.CardServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MtgCollectionTrackerApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CardServiceImplTest {

    @Autowired
    private CardDAOImpl cardDao;

    @Autowired
    private CardServiceImpl cardService;

    @BeforeEach
    public void testSetup() throws ClassNotFoundException {

        //DatabaseCard databaseCard = new DatabaseCard(Objects.requireNonNull(CardAPI.getCardByName("consider")), 4);
        //cardService.addNewCard(databaseCard.getName(), String.valueOf(databaseCard.getQuantity()));
    }

    @AfterEach
    public void testTakedown() {

        //cardService.removeCardFromCollection("consider");
    }

    @Test
    public void cardServiceAddTest() throws ClassNotFoundException {

        assertEquals("choke was added to your collection.", cardService.addNewCard("choke", String.valueOf(2)));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("", String.valueOf(2)));
        assertEquals("Invalid entry, please check that numCards is whole number value > 0", cardService.addNewCard("choke", String.valueOf("a")));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("choke", String.valueOf(-1)));
        assertEquals("consider already exists in your collection", cardService.addNewCard("consider", String.valueOf(4)));
        assertEquals("consider already exists in your collection", cardService.addNewCard("consider", String.valueOf(4), "MID"));
        assertThrows(CardNotFoundException.class, () -> cardService.addNewCard("tonker", String.valueOf(1)));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("", String.valueOf(1), "M10"));
        assertEquals("Invalid entry, please check that numCards is whole number value > 0", cardService.addNewCard("ponder", String.valueOf("a"), "M10"));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("giant growth", String.valueOf(-1), "M11"));
        assertEquals("giant growth was added to your collection.", cardService.addNewCard("giant growth", String.valueOf(1), "M11"));
        assertEquals("firebolt from set GTC cannot be found", cardService.addNewCard("firebolt", String.valueOf(1), "GTC"));
    }

    @Test
    public void cardServiceSearchTest() throws ClassNotFoundException {

        assertThrows(IllegalArgumentException.class, () -> cardService.searchCollectionByName(""));
        assertEquals("Card 'black lotus' not found in your collection.", cardService.searchCollectionByName("black lotus"));
        assertEquals("[You have 1 copy of Time Warp in your collection\n]", cardService.searchCollectionByName("Time Warp"));
        assertEquals("[You have 2 copies of Snapcaster Mage in your collection\n]", cardService.searchCollectionByName("Snapcaster Mage"));
    }

    @Test
    public void viewCollectionTest() {

        //assertEquals("Collection is empty.", cardService.viewCollection());
        //assertEquals("");
    }

    @Test
    public void listCollectionTest() {

        //List<DatabaseCard> list = new ArrayList<>();

        //assertEquals(list, cardService.listCollection());
    }
}
