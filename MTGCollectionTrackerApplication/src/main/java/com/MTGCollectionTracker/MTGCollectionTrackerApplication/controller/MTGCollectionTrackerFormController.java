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

    @RequestMapping(path="/searchCardForm")
    public String searchCardForm(Model theModel) {

        theModel.addAttribute("searchcard", new SearchCard());
        return "MTGTracker-searchCardForm";
    }

    @RequestMapping(path="/addCardForm")
    public String addCardForm(Model theModel) {

        theModel.addAttribute("searchcard", new SearchCard());
        return "MTGTracker-addCardForm";
    }

    @RequestMapping(path="/updateCardForm")
    public String updateCardForm(Model theModel) {

        theModel.addAttribute("searchcard", new SearchCard());
        return "MTGTracker-updateCardForm";
    }

    @RequestMapping(path="/updateCardFormFilled")
    public String updateCardFormFilled(@RequestParam("cardId") int theId, Model theModel) {
        return formService.fillUpdateCardForm(theId, theModel);
    }

    @RequestMapping(path="/removeCardForm")
    public String removeCardForm(Model theModel) {

        theModel.addAttribute("searchcard", new SearchCard());
        return "MTGTracker-removeCardForm";
    }

    @GetMapping(path="/processSearchCardForm")
    public String processSearchCardForm(
            @Valid @ModelAttribute("searchcard") SearchCard searchCard, BindingResult theBindingResult, Model theModel)
            throws ClassNotFoundException {

        try {
            return formService.processSearchCardForm(searchCard, theModel);
        } catch (Exception ignore) {
        }

        if (theBindingResult.hasErrors()) {
            return "MTGTracker-searchCardForm";
        } else {
            return formService.processSearchCardForm(searchCard, theModel);
        }
    }

    @PostMapping(path="/processAddCardForm")
    public String processAddCardForm(
            @Valid @ModelAttribute("searchcard") SearchCard searchCard, BindingResult theBindingResult, Model theModel)
            throws ClassNotFoundException {

        if (theBindingResult.hasErrors()) {
            return "MTGTracker-addCardForm";
        } else {
            return formService.processAddCardForm(searchCard, theModel);
        }
    }

    @PostMapping(path="/processUpdateCardForm")
    public String processUpdateCardForm(
            @Valid @ModelAttribute("searchcard") SearchCard searchCard, BindingResult theBindingResult, Model theModel)
            throws ClassNotFoundException {

        if (theBindingResult.hasErrors()) {
            return "MTGTracker-updateCardForm";
        } else {
            return formService.processUpdateCardForm(searchCard, theModel);
        }
    }

    @PostMapping(path="/processRemoveCardForm")
    public String processRemoveCardForm(
            @Valid @ModelAttribute("searchcard") SearchCard searchCard, BindingResult theBindingResult, Model theModel) {

        if (theBindingResult.hasErrors()) {
            return "MTGTracker-removeCardForm";
        } else {
            return formService.processRemoveCardForm(searchCard, theModel);
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}