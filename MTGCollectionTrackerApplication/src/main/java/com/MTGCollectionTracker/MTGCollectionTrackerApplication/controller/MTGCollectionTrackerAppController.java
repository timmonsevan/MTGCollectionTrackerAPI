package com.MTGCollectionTracker.MTGCollectionTrackerApplication.controller;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.DatabaseCard;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * controller for web app
 */
@Controller
@RequestMapping(path="/app")
public class MTGCollectionTrackerAppController {

    private final CardService cardService;

    @Autowired
    public MTGCollectionTrackerAppController(CardService theCardService) {
        this.cardService = theCardService;
    }

    /**
     * Default webpage for app
     * Displays contents of collection listed in order of id asc
     * also hosts UI for add, searching, updating and deleting entries
     * @return the HTML path to view our collection and UI
     */
    @GetMapping(path="/collection")
    public String viewCollection(Model theModel) {

        List<DatabaseCard> databaseCards = cardService.listCollection();
        theModel.addAttribute("cards", databaseCards);

        return "view-collection";
    }

    /**
     * this path removes a card based on theId
     * @param theId retrieved by pressing the delete key on the associated card line
     * @return the returned path refreshes the /app/collection page
     */
    @GetMapping(path="/remove")
    public String remove(@RequestParam("cardId") int theId) {
        return cardService.removeCardFromCollection(theId);
    }
}
