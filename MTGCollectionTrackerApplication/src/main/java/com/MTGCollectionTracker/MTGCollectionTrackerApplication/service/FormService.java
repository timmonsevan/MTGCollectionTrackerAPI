package com.MTGCollectionTracker.MTGCollectionTrackerApplication.service;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.SearchCard;
import org.springframework.ui.Model;

/**
 * interface for form methods used by controllers
 * @author timmonsevan
 */

public interface FormService {

    public String processSearchCardForm(SearchCard searchCard, Model theModel) throws ClassNotFoundException;

    public String processAddCardForm(SearchCard searchCard, Model theModel) throws ClassNotFoundException;

    public String processUpdateCardForm(SearchCard searchCard, Model theModel) throws ClassNotFoundException;

    public String processRemoveCardForm(SearchCard searchCard, Model theModel);

    public String fillUpdateCardForm(int id, Model theModel);
}
