package com.MTGCollectionTracker.MTGCollectionTrackerApplication.service;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.SearchCard;
import org.springframework.ui.Model;

/**
 * interface for form methods used by controllers
 *
 * @author timmonsevan
 */

public interface FormService {

    String processSearchCardForm(SearchCard searchCard, Model theModel) throws ClassNotFoundException;

    String processAddCardForm(SearchCard searchCard, Model theModel) throws ClassNotFoundException;

    String processUpdateCardForm(SearchCard searchCard, Model theModel) throws ClassNotFoundException;

    String processRemoveCardForm(SearchCard searchCard, Model theModel);

    String fillUpdateCardForm(int id, Model theModel);
}
