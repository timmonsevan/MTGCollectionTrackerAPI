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

        assertNotEquals("Collection is empty.", cardService.viewCollection());
        assertEquals("[You have 2 copies of Snapcaster Mage in your collection\n, You have 4 copies of Spirebluff Canal in your collection\n, You have 1 copy of Time Warp in your collection\n, You have 4 copies of Lightning Bolt in your collection\n, You have 1 copy of Prismari Command in your collection\n, You have 4 copies of Monastery Swiftspear in your collection\n, You have 3 copies of Impulse in your collection\n, You have 4 copies of Slickshot Show-Off in your collection\n, You have 2 copies of Ledger Shredder in your collection\n, You have 4 copies of Play with Fire in your collection\n, You have 4 copies of Consider in your collection\n, You have 4 copies of Lightning Strike in your collection\n, You have 3 copies of Murktide Regent in your collection\n, You have 4 copies of Dragon's Rage Channeler in your collection\n, You have 4 copies of Delver of Secrets // Insectile Aberration in your collection\n, You have 3 copies of Questing Druid // Seek the Beast in your collection\n, You have 4 copies of Ponder in your collection\n, You have 4 copies of Brainstorm in your collection\n, You have 2 copies of Scalding Tarn in your collection\n, You have 2 copies of Misty Rainforest in your collection\n, You have 1 copy of Brazen Borrower // Petty Theft in your collection\n]",
                cardService.viewCollection());
    }

    /**
     * test list method
     */
    @Test
    public void listCollectionTest() {

        List<DatabaseCard> emptyList = new ArrayList<>();

        assertNotEquals(emptyList, cardService.listCollection());
        assertEquals("[DatabaseCard{id=1, name='Snapcaster Mage', manaCost='{1}{U}', type='Creature — Human Wizard', rarity='Rare', text='Flash\nWhen Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost. (You may cast that card from your graveyard for its flashback cost. Then exile it.)', multiverseId=227676, set='ISD', setName='Innistrad'}, DatabaseCard{id=2, name='Spirebluff Canal', manaCost='null', type='Land', rarity='Rare', text='Spirebluff Canal enters the battlefield tapped unless you control two or fewer other lands.\n{T}: Add {U} or {R}.', multiverseId=417822, set='KLD', setName='Kaladesh'}, DatabaseCard{id=4, name='Time Warp', manaCost='{3}{U}{U}', type='Sorcery', rarity='Mythic', text='Target player takes an extra turn after this one.', multiverseId=439354, set='E02', setName='Explorers of Ixalan'}, DatabaseCard{id=5, name='Lightning Bolt', manaCost='{R}', type='Instant', rarity='Common', text='Lightning Bolt deals 3 damage to any target.', multiverseId=806, set='2ED', setName='Unlimited Edition'}, DatabaseCard{id=6, name='Prismari Command', manaCost='{1}{U}{R}', type='Instant', rarity='Rare', text='Choose two —\n• Prismari Command deals 2 damage to any target.\n• Target player draws two cards, then discards two cards.\n• Target player creates a Treasure token.\n• Destroy target artifact.', multiverseId=640575, set='LCC', setName='The Lost Caverns of Ixalan Commander'}, DatabaseCard{id=7, name='Monastery Swiftspear', manaCost='{R}', type='Creature — Human Monk', rarity='Common', text='Haste\nProwess (Whenever you cast a noncreature spell, this creature gets +1/+1 until end of turn.)', multiverseId=571452, set='2X2', setName='Double Masters 2022'}, DatabaseCard{id=8, name='Impulse', manaCost='{1}{U}', type='Instant', rarity='Common', text='Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.', multiverseId=446087, set='BBD', setName='Battlebond'}, DatabaseCard{id=9, name='Slickshot Show-Off', manaCost='{1}{R}', type='Creature — Bird Wizard', rarity='Rare', text='Flying, haste\nWhenever you cast a noncreature spell, Slickshot Show-Off gets +2/+0 until end of turn.\nPlot {1}{R} (You may pay {1}{R} and exile this card from your hand. Cast it as a sorcery on a later turn without paying its mana cost. Plot only as a sorcery.)', multiverseId=655087, set='OTJ', setName='Outlaws of Thunder Junction'}, DatabaseCard{id=10, name='Ledger Shredder', manaCost='{1}{U}', type='Creature — Bird Advisor', rarity='Rare', text='Flying\nWhenever a player casts their second spell each turn, Ledger Shredder connives. (Draw a card, then discard a card. If you discarded a nonland card, put a +1/+1 counter on this creature.)', multiverseId=555247, set='SNC', setName='Streets of New Capenna'}, DatabaseCard{id=12, name='Play with Fire', manaCost='{R}', type='Instant', rarity='Uncommon', text='Play with Fire deals 2 damage to any target. If a player is dealt damage this way, scry 1. (Look at the top card of your library. You may put that card on the bottom of your library.)', multiverseId=534933, set='MID', setName='Innistrad: Midnight Hunt'}, DatabaseCard{id=16, name='Consider', manaCost='{U}', type='Instant', rarity='Common', text='Surveil 1. (Look at the top card of your library. You may put it into your graveyard.)\nDraw a card.', multiverseId=651819, set='CLU', setName='Ravnica: Clue Edition'}, DatabaseCard{id=17, name='Lightning Strike', manaCost='{1}{R}', type='Instant', rarity='Common', text='Lightning Strike deals 3 damage to any target.', multiverseId=574617, set='DMU', setName='Dominaria United'}, DatabaseCard{id=18, name='Murktide Regent', manaCost='{5}{U}{U}', type='Creature — Dragon', rarity='Mythic', text='Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)\nFlying\nMurktide Regent enters the battlefield with a +1/+1 counter on it for each instant and sorcery card exiled with it.\nWhenever an instant or sorcery card leaves your graveyard, put a +1/+1 counter on Murktide Regent.', multiverseId=522128, set='MH2', setName='Modern Horizons 2'}, DatabaseCard{id=19, name='Dragon's Rage Channeler', manaCost='{R}', type='Creature — Human Shaman', rarity='Uncommon', text='Whenever you cast a noncreature spell, surveil 1. (Look at the top card of your library. You may put that card into your graveyard.)\nDelirium — As long as there are four or more card types among cards in your graveyard, Dragon's Rage Channeler gets +2/+2, has flying, and attacks each combat if able.', multiverseId=522197, set='MH2', setName='Modern Horizons 2'}, DatabaseCard{id=20, name='Delver of Secrets // Insectile Aberration', manaCost='{U}', type='Creature — Human Wizard', rarity='Common', text='At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets.', multiverseId=226749, set='ISD', setName='Innistrad'}, DatabaseCard{id=21, name='Questing Druid // Seek the Beast', manaCost='{1}{G}', type='Creature — Human Druid', rarity='Rare', text='Whenever you cast a spell that's white, blue, black, or red, put a +1/+1 counter on Questing Druid.', multiverseId=629735, set='WOE', setName='Wilds of Eldraine'}, DatabaseCard{id=22, name='Ponder', manaCost='{U}', type='Sorcery', rarity='Common', text='Look at the top three cards of your library, then put them back in any order. You may shuffle.\nDraw a card.', multiverseId=244313, set='M12', setName='Magic 2012'}, DatabaseCard{id=23, name='Brainstorm', manaCost='{U}', type='Instant', rarity='Common', text='Draw three cards, then put two cards from your hand on top of your library in any order.', multiverseId=489717, set='2XM', setName='Double Masters'}, DatabaseCard{id=24, name='Scalding Tarn', manaCost='null', type='Land', rarity='Mythic', text='{T}, Pay 1 life, Sacrifice Scalding Tarn: Search your library for an Island or Mountain card, put it onto the battlefield, then shuffle.', multiverseId=405107, set='EXP', setName='Zendikar Expeditions'}, DatabaseCard{id=25, name='Misty Rainforest', manaCost='null', type='Land', rarity='Mythic', text='{T}, Pay 1 life, Sacrifice Misty Rainforest: Search your library for a Forest or Island card, put it onto the battlefield, then shuffle.', multiverseId=405102, set='EXP', setName='Zendikar Expeditions'}, DatabaseCard{id=26, name='Brazen Borrower // Petty Theft', manaCost='{1}{U}{U}', type='Creature — Faerie Rogue', rarity='Mythic', text='Flash\nFlying\nBrazen Borrower can block only creatures with flying.', multiverseId=473001, set='ELD', setName='Throne of Eldraine'}]",
                cardService.listCollection().toString());
    }
}
