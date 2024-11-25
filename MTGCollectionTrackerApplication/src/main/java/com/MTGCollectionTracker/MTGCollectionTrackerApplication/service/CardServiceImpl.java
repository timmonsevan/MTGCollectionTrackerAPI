package com.MTGCollectionTracker.MTGCollectionTrackerApplication.service;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.dao.CardDAO;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.DatabaseCard;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.exceptions.CardNotFoundException;
import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * service methods for controllers
 * @author timmonsevan
 */
@Service
public class CardServiceImpl implements CardService {

    private final CardDAO cardDAO;

    @Autowired
    public CardServiceImpl(CardDAO theCardDAO) {
        this.cardDAO = theCardDAO;
    }

    /**
     * @return a card in collection found by its Id number in stored database
     */
    @Override
    public DatabaseCard findById(long id) {
        return cardDAO.findById(id);
    }
    /**
     * @return a String List of all cards in collection
     */
    @Override
    public String viewCollection() {

        List<DatabaseCard> query = new ArrayList<>(cardDAO.findAll());
        List<String> collectionList = new ArrayList<>();

        if (query.isEmpty()) {
            return "Collection is empty.";
        }

        for (DatabaseCard card : query) {
            if (card.getQuantity() == 1) {
                collectionList.add("You have " + card.getQuantity() + " copy of " + card.getName() + " in your collection\n");
            } else {
                collectionList.add("You have " + card.getQuantity() + " copies of " + card.getName() + " in your collection\n");
            }
        }

        return collectionList.toString();
    }

    /**
     * @return List of all cards in stored database collection
     */
    @Override
    public List<DatabaseCard> listCollection() {
        return new ArrayList<>(cardDAO.findAll());
    }

    /**
     * searches collection for card by name
     * @return a message screen
     */
    @Override
    public String searchCollectionByName(String cardName) throws ClassNotFoundException {

        if (cardName != null) {

            if (cardName.isBlank() || cardName.isEmpty()) {
                throw new IllegalArgumentException("Invalid entry, please check that cardName is entered.");
            }

            List<DatabaseCard> query;

            try {
                query = new ArrayList<>(cardDAO.findByName(cardName));
            } catch (NullPointerException ex) {
                return "Card '" + cardName + "' not found in your collection.";
            }

            query = new ArrayList<>(cardDAO.findByName(cardName));
            List<String> collectionList = new ArrayList<>();

            for (DatabaseCard card : query) {
                if (card.getQuantity() == 1) {
                    collectionList.add("You have " + card.getQuantity() + " copy of " + card.getName() + " in your collection\n");
                } else {
                    collectionList.add("You have " + card.getQuantity() + " copies of " + card.getName() + " in your collection\n");
                }
            }
            return collectionList.toString();
        } else {
            return null;
        }
    }
    /**
     * adds a new card to the collection with a specified quantity
     * @return a message screen
     */
    @Override
    @Transactional
    public String addNewCard(String cardName, String numCards) throws ClassNotFoundException {

        int quantity;

        if (cardName != null && numCards != null) {

            if (cardName.isBlank() || cardName.isEmpty()) {
                throw new IllegalArgumentException("Invalid entry, please check that cardName is entered");
            }

            try {
                quantity = Integer.parseInt(numCards);
            } catch (NumberFormatException ex) {
                return "Invalid entry, please check that numCards is whole number value > 0";
            }

            if (quantity <= 0) {
                throw new IllegalArgumentException("Invalid entry, please check that numCards is whole number value > 0");
            }

            try {
                List<DatabaseCard> query = new ArrayList<>(cardDAO.findByName(cardName));

                if (!query.isEmpty()) {
                    for (DatabaseCard card : query) {
                        if (card.getName().equalsIgnoreCase(cardName)) {
                            return cardName + " already exists in your collection";
                        }
                    }
                }
            } catch (Exception ignored) {
            }

            try {
                DatabaseCard tempDatabaseCard = new DatabaseCard(Objects.requireNonNull(CardAPI.getCardByName(cardName)), quantity);
                cardDAO.save(tempDatabaseCard);
            } catch (Exception ex) {
                throw new CardNotFoundException("Card with name " + cardName + " cannot be found.");
            }
            return cardName + " was added to your collection.";
        } else {
            return null;
        }
    }
    /**
     * adds a new card to the collection with a specified quantity and set indicator
     * @return a message screen
     */
    @Override
    @Transactional
    public String addNewCard(String cardName, String numCards, String set) throws ClassNotFoundException {

        int quantity;

        if (cardName != null && numCards != null && set != null) {


            if (cardName.isBlank() || cardName.isEmpty()) {
                throw new IllegalArgumentException("Invalid entry, please check that cardName is entered");
            }

            try {
                quantity = Integer.parseInt(numCards);
            } catch (NumberFormatException ex) {
                return "Invalid entry, please check that numCards is whole number value > 0";
            }

            if (quantity <= 0) {
                throw new IllegalArgumentException("Invalid entry, please check that numCards is whole number value > 0");
            }

            try {
                List<DatabaseCard> query = new ArrayList<>(cardDAO.findByName(cardName));

                if (!query.isEmpty()) {
                    for (DatabaseCard card : query) {
                        if (card.getName().equalsIgnoreCase(cardName)) {
                            return cardName + " already exists in your collection";
                        }
                    }
                }
            } catch (Exception ignored) {
            }

            List<Card> cards = CardAPI.getCardsByName(cardName);

            for (Card card : cards) {
                if (card.getSet().equalsIgnoreCase(set)) {
                    DatabaseCard tempDatabaseCard = new DatabaseCard(card, quantity);
                    cardDAO.save(tempDatabaseCard);
                    return cardName + " was added to your collection.";
                }
            }

            return cardName + " from set " + set + " cannot be found";
        } else {
            return null;
        }
    }

    /**
     * updates a card quantity
     * @return a message screen
     */
    @Override
    @Transactional
    public String updateCard(String cardName, String numCards) throws ClassNotFoundException {

        int quantity;

        if (cardName != null && numCards != null) {

            if (cardName.isBlank() || cardName.isEmpty()) {
                throw new IllegalArgumentException("Invalid entry, please check that cardName is entered");
            }

            try {
                quantity = Integer.parseInt(numCards);
            } catch (NumberFormatException ex) {
                return "Invalid entry, please check that numCards is whole number value > 0";
            }

            if (quantity <= 0) {
                throw new IllegalArgumentException("Invalid entry, please check that numCards is whole number value > 0");
            }

            List<DatabaseCard> query;

            try {
                query = new ArrayList<>(cardDAO.findByName(cardName));
            } catch (NullPointerException ex) {
                return "Card '" + cardName + "' not found in your collection.";
            }

            query = new ArrayList<>(cardDAO.findByName(cardName));

            try {
                for (DatabaseCard card : query) {
                    if (card.getName().equalsIgnoreCase(cardName)) {
                        if (card.getMultiverseId() == Objects.requireNonNull(CardAPI.getCardByName(cardName)).getMultiverseid()) {
                            card.setQuantity(quantity);
                            cardDAO.update(card);
                        }
                    }
                }

                return cardName + " quantity updated";

            } catch (Exception ex) {
                return "Update error.";
            }
        } else {
            return null;
        }
    }
    /**
     * update a card's quantity and/or set
     * @return a message screen
     */
    @Override
    @Transactional
    public String updateCard(String cardName, String numCards, String set) throws ClassNotFoundException {

        int quantity;

        if (cardName != null && numCards != null && set != null) {

            if (cardName.isBlank() || cardName.isEmpty()) {
                throw new IllegalArgumentException("Invalid entry, please check that cardName is entered");
            }

            try {
                quantity = Integer.parseInt(numCards);
            } catch (NumberFormatException ex) {
                return "Invalid entry, please check that numCards is whole number value > 0";
            }

            if (quantity <= 0) {
                throw new IllegalArgumentException("Invalid entry, please check that numCards is whole number value > 0");
            }

            List<DatabaseCard> query;

            try {
                query = new ArrayList<>(cardDAO.findByName(cardName));
            } catch (NullPointerException ex) {
                return "Card '" + cardName + "' not found in your collection.";
            }

            query = new ArrayList<>(cardDAO.findByName(cardName));

            try {
                for (DatabaseCard card : query) {
                    if (card.getName().equalsIgnoreCase(cardName)) {
                        if (card.getMultiverseId() == Objects.requireNonNull(CardAPI.getCardByName(cardName)).getMultiverseid()) {
                            card.setQuantity(quantity);
                            card.setSet(set);
                            cardDAO.update(card);
                        }
                    }
                }

                return cardName + " updated";

            } catch (Exception ex) {
                return "Update error.";
            }
        } else {
            return null;
        }
    }

    /**
     * removes card from collection based off card name
     * @return a message screen
     */
    @Override
    @Transactional
    public String removeCardFromCollection(String cardName) {

        if (cardName != null) {

            if (cardName.isBlank() || cardName.isEmpty()) {
                throw new IllegalArgumentException("Invalid entry, please check that cardName is entered.");
            }

            List<DatabaseCard> query;

            try {
                query = new ArrayList<>(cardDAO.findByName(cardName));
            } catch (NullPointerException ex) {
                return "Card '" + cardName + "' not found in your collection.";
            }

            query = new ArrayList<>(cardDAO.findByName(cardName));

            try {
                for (DatabaseCard card : query) {
                    if (card.getName().equalsIgnoreCase(cardName)) {
                        if (card.getMultiverseId() == Objects.requireNonNull(CardAPI.getCardByName(cardName)).getMultiverseid()) {
                            cardDAO.delete(card.getName());
                        }
                    }
                }

                return cardName + " removed from collection.";

            } catch (Exception ex) {
                return "Deletion error.";
            }
        } else {
            return null;
        }
    }
    /**
     * removes a card from the collection based on card Id number in stored database
     * @return refreshes the main page
     */
    @Override
    @Transactional
    public String removeCardFromCollection(long id) {

        cardDAO.delete(id);

        return "redirect:/app/collection";
    }
}