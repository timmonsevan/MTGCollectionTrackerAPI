package com.MTGCollectionTracker.MTGCollectionTrackerApplication.service;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.DatabaseCard;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.SearchCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * service methods for forms
 * @author timmonsevan
 */
@Service
public class FormServiceImpl implements FormService {

    private final CardService cardService;

    @Autowired
    public FormServiceImpl(CardService theCardService) {
        this.cardService = theCardService;
    }

    @Override
    public String processSearchCardForm(SearchCard searchCard, Model theModel) throws ClassNotFoundException {

        theModel.addAttribute("message", cardService.searchCollectionByName(searchCard.getCardName()));
        return "messagetemplate";
    }

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

    @Override
    public String processRemoveCardForm(SearchCard searchCard, Model theModel) {

        theModel.addAttribute("message", cardService.removeCardFromCollection(searchCard.getCardName()));
        return "messagetemplate";
    }

    @Override
    public String fillUpdateCardForm(int id, Model theModel) {

        DatabaseCard databaseCard = cardService.findById(id);
        SearchCard searchCard = new SearchCard(databaseCard.getName());
        searchCard.setCardQuantity(Integer.toString(databaseCard.getQuantity()));
        theModel.addAttribute("searchcard", searchCard);

        return "MTGTracker-updateCardForm";
    }
}
