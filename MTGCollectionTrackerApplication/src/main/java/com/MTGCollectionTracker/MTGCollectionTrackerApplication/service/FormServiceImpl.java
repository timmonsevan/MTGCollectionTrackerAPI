package com.MTGCollectionTracker.MTGCollectionTrackerApplication.service;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.DatabaseCard;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.SearchCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * service methods for forms
 *
 * @author timmonsevan
 */
@Service
public class FormServiceImpl implements FormService {

    private final CardService cardService;

    @Autowired
    public FormServiceImpl(CardService theCardService) {
        this.cardService = theCardService;
    }

    /**
     * process the search card form and search the collection for the entry
     */
    @Override
    public String processSearchCardForm(SearchCard searchCard, Model theModel) throws ClassNotFoundException {

        theModel.addAttribute("message", cardService.searchCollectionByName(searchCard.getCardName()));

        return "messagetemplate";
    }

    /**
     * process the add card form and add the entry to the collection
     */
    @Override
    public String processAddCardForm(SearchCard searchCard, Model theModel) throws ClassNotFoundException {

        if (searchCard.getCardSet() == null) {
            theModel.addAttribute("message", cardService.addNewCard(searchCard.getCardName(), searchCard.getCardQuantity()));
        } else {
            theModel.addAttribute("message",
                    cardService.addNewCard(searchCard.getCardName(), searchCard.getCardQuantity(), searchCard.getCardSet()));
        }

        return "messagetemplate";
    }

    /**
     * process the update card form and update the entry
     */
    @Override
    public String processUpdateCardForm(SearchCard searchCard, Model theModel) throws ClassNotFoundException {

        if (searchCard.getCardSet() == null) {
            theModel.addAttribute("message", cardService.updateCard(searchCard.getCardName(), searchCard.getCardQuantity()));
        } else {
            theModel.addAttribute("message",
                    cardService.updateCard(searchCard.getCardName(), searchCard.getCardQuantity(), searchCard.getCardSet()));
        }

        return "messagetemplate";
    }

    /**
     * process the remove card form and delete that entry from collection
     */
    @Override
    public String processRemoveCardForm(SearchCard searchCard, Model theModel) {

        theModel.addAttribute("message", cardService.removeCardFromCollection(searchCard.getCardName()));

        return "messagetemplate";
    }

    /**
     * @return the updateCardForm pre-filled with name and quantity in collection
     */
    @Override
    public String fillUpdateCardForm(int id, Model theModel) {

        DatabaseCard databaseCard = cardService.findById(id);
        SearchCard searchCard = new SearchCard(databaseCard.getName());
        searchCard.setCardQuantity(Integer.toString(databaseCard.getQuantity()));
        theModel.addAttribute("searchcard", searchCard);

        return "Forms/MTGTracker-updateCardForm";
    }
}
