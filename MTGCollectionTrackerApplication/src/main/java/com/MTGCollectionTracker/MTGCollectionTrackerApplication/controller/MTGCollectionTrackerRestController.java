package com.MTGCollectionTracker.MTGCollectionTrackerApplication.controller;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller
 * the first path /view returns a list of the cards in the collection
 * the following four paths are for each C R U D functionality
 * @author timmonsevan
 */

@RestController
@RequestMapping(path="/api")
public class MTGCollectionTrackerRestController {

    private final CardService cardService;

    @Autowired
    public MTGCollectionTrackerRestController(CardService theCardService) {
        this.cardService = theCardService;
    }

    @GetMapping(path="/view")
    public String viewCollection() {
        return cardService.viewCollection();
    }

    @GetMapping(path="/search")
    public String searchCollectionByName (@RequestParam String cardName) throws ClassNotFoundException {
        return cardService.searchCollectionByName(cardName);
    }

    @PostMapping(path="/add")
    public String addNewCard (@RequestParam String cardName, @RequestParam String numCards)
            throws ClassNotFoundException {
        return cardService.addNewCard(cardName, numCards);
    }

    @PutMapping(path="/update")
    public String updateCardCount (@RequestParam String cardName, @RequestParam String numCards)
            throws ClassNotFoundException {
        return cardService.updateCard(cardName, numCards);
    }

    @DeleteMapping(path="/remove")
    public String removeCardFromCollection (@RequestParam String cardName) {
        return cardService.removeCardFromCollection(cardName);
    }
}
