package com.MTGCollectionTracker.MTGCollectionTrackerApplication.controller;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.SearchCard;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.service.FormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * controller for mapping forms
 * one path for each C R U D function-form to be filled out by user
 * an additional /updateCardFormFilled path autofills the card
 * parameters based on what card line the update button was pressed
 * one processing path for each C R U D function-form
 *
 * @author timmonsevan
 */
@Controller
@RequestMapping(path="/form")
public class MTGCollectionTrackerFormController {

    private final FormService formService;

    @Autowired
    public MTGCollectionTrackerFormController(FormService theFormService) {
        this.formService = theFormService;
    }

    /**
     * path to search card form
     */
    @RequestMapping(path="/searchCardForm")
    public String searchCardForm(Model theModel) {

        theModel.addAttribute("searchcard", new SearchCard());
        return "Forms/MTGTracker-searchCardForm";
    }

    /**
     * path to add card form
     */
    @RequestMapping(path="/addCardForm")
    public String addCardForm(Model theModel) {

        theModel.addAttribute("searchcard", new SearchCard());
        return "Forms/MTGTracker-addCardForm";
    }

    /**
     * path to update card form
     */
    @RequestMapping(path="/updateCardForm")
    public String updateCardForm(Model theModel) {

        theModel.addAttribute("searchcard", new SearchCard());
        return "Forms/MTGTracker-updateCardForm";
    }

    /**
     * path to update card form, with parameters pre-filled out
     */
    @RequestMapping(path="/updateCardFormFilled")
    public String updateCardFormFilled(@RequestParam("cardId") int theId, Model theModel) {
        return formService.fillUpdateCardForm(theId, theModel);
    }

    /**
     * path to remove card form
     */
    @RequestMapping(path="/removeCardForm")
    public String removeCardForm(Model theModel) {

        theModel.addAttribute("searchcard", new SearchCard());
        return "Forms/MTGTracker-removeCardForm";
    }

    /**
     * processes the search card form
     */
    @GetMapping(path="/processSearchCardForm")
    public String processSearchCardForm(
            @Valid @ModelAttribute("searchcard") SearchCard searchCard, BindingResult theBindingResult, Model theModel)
            throws ClassNotFoundException {

        try {
            return formService.processSearchCardForm(searchCard, theModel);
        } catch (Exception ignore) {
        }

        if (theBindingResult.hasErrors()) {
            return "Forms/MTGTracker-searchCardForm";
        } else {
            return formService.processSearchCardForm(searchCard, theModel);
        }
    }

    /**
     * processes the add card form
     */
    @PostMapping(path="/processAddCardForm")
    public String processAddCardForm(
            @Valid @ModelAttribute("searchcard") SearchCard searchCard, BindingResult theBindingResult, Model theModel)
            throws ClassNotFoundException {

        if (theBindingResult.hasErrors()) {
            return "Forms/MTGTracker-addCardForm";
        } else {
            return formService.processAddCardForm(searchCard, theModel);
        }
    }

    /**
     * processes the update card form
     */
    @PostMapping(path="/processUpdateCardForm")
    public String processUpdateCardForm(
            @Valid @ModelAttribute("searchcard") SearchCard searchCard, BindingResult theBindingResult, Model theModel)
            throws ClassNotFoundException {

        if (theBindingResult.hasErrors()) {
            return "Forms/MTGTracker-updateCardForm";
        } else {
            return formService.processUpdateCardForm(searchCard, theModel);
        }
    }

    /**
     * processes the remove card form
     */
    @PostMapping(path="/processRemoveCardForm")
    public String processRemoveCardForm(
            @Valid @ModelAttribute("searchcard") SearchCard searchCard, BindingResult theBindingResult, Model theModel) {

        if (theBindingResult.hasErrors()) {
            return "Forms/MTGTracker-removeCardForm";
        } else {
            return formService.processRemoveCardForm(searchCard, theModel);
        }
    }

    /**
     * this method and annotation trim excess white space from entries
     * as well as coming back isEmpty on null entries
     */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
