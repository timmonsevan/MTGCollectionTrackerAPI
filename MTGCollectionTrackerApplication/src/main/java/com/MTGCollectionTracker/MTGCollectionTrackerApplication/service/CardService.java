package com.MTGCollectionTracker.MTGCollectionTrackerApplication.service;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.DatabaseCard;
import java.util.List;

/**
 * interface for CRUD methods to be utilized by controllers
 *
 * @author timmonsevan
 */
public interface CardService {

    DatabaseCard findById(long id);

    String viewCollection();

    List<DatabaseCard> listCollection();

    String searchCollectionByName(String cardName) throws ClassNotFoundException;

    String addNewCard(String cardName, String numCards) throws ClassNotFoundException;

    String addNewCard(String cardName, String numCards, String set) throws ClassNotFoundException;

    String updateCard(String cardName, String numCards) throws ClassNotFoundException;

    String updateCard(String cardName, String numCards, String set) throws ClassNotFoundException;

    String removeCardFromCollection(String cardName);

    String removeCardFromCollection(long id);
}
