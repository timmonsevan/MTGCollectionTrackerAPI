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

    @GetMapping(path="/collection")
    public String viewCollection(Model theModel) {

        List<DatabaseCard> databaseCards = cardService.listCollection();
        theModel.addAttribute("cards", databaseCards);

        return "view-collection";
    }

    @GetMapping(path="/remove")
    public String remove(@RequestParam("cardId") int theId) {
        return cardService.removeCardById(theId);
    }
}
