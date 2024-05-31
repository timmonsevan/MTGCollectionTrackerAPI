package com.MTGCollectionTracker.MTGCollectionTrackerApplication.service;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.DatabaseCard;
import java.util.List;

/**
 * interface for methods utilized by RESTController
 * @author timmonsevan
 */
public interface CardService {

    public DatabaseCard findById(int id);

    public String viewCollection();

    public List<DatabaseCard> listCollection();

    public String searchCollectionByName(String cardName) throws ClassNotFoundException;

    public String addNewCard(String cardName, String numCards) throws ClassNotFoundException;

    public String addNewCard(String cardName, String numCards, String set) throws ClassNotFoundException;

    public String updateCard(String cardName, String numCards) throws ClassNotFoundException;

    public String updateCard(String cardName, String numCards, String set) throws ClassNotFoundException;

    public String removeCardFromCollection(String cardName);

    public String removeCardById(int id);
}
