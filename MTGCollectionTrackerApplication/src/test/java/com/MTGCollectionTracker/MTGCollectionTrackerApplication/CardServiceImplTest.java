package com.MTGCollectionTracker.MTGCollectionTrackerApplication;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.dao.CardDAOImpl;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.DatabaseCard;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.exceptions.CardNotFoundException;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.service.CardServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes= MtgCollectionTrackerApplication.class)
public class CardServiceImplTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    static DatabaseCard databaseCard;

    @MockBean
    private CardDAOImpl cardDao;

    @Autowired
    private CardServiceImpl cardService;

    @BeforeAll
    static void testSetup() throws ClassNotFoundException {
        //  databaseCard = new DatabaseCard(Objects.requireNonNull(CardAPI.getCardByName("choke")), 2);
    }

    @Test
    public void cardServiceAddTest() throws ClassNotFoundException {

        assertEquals("choke was added to your collection.", cardService.addNewCard("choke", String.valueOf(2)));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("", String.valueOf(2)));
        assertEquals("Invalid entry, please check that numCards is whole number value > 0", cardService.addNewCard("choke", String.valueOf("a")));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("choke", String.valueOf(-1)));
        //cardService.addNewCard("consider", String.valueOf(1));
        //assertEquals("consider already exists in your collection", cardService.addNewCard("consider", String.valueOf(1)));
        assertThrows(CardNotFoundException.class, () -> cardService.addNewCard("tonker", String.valueOf(1)));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("", String.valueOf(1), "M10"));
        assertEquals("Invalid entry, please check that numCards is whole number value > 0", cardService.addNewCard("ponder", String.valueOf("a"), "M10"));
        assertThrows(IllegalArgumentException.class, () -> cardService.addNewCard("ponder", String.valueOf(-1), "M10"));
        assertEquals("ponder was added to your collection.", cardService.addNewCard("ponder", String.valueOf(1), "M10"));
        assertEquals("ponder from set GTC cannot be found", cardService.addNewCard("ponder", String.valueOf(1), "GTC"));
    }

    @Test
    public void cardServiceSearchTest() {

        assertThrows(IllegalArgumentException.class, () -> cardService.searchCollectionByName(""));
    }
}
