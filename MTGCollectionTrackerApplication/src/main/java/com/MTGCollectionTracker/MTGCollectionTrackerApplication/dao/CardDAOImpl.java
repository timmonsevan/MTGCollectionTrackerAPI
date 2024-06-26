package com.MTGCollectionTracker.MTGCollectionTrackerApplication.dao;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.DatabaseCard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * implementation of CRUD methods
 * @author timmonsevan
 */

@Repository
public class CardDAOImpl implements CardDAO{

    private final EntityManager entityManager;

    @Autowired
    public CardDAOImpl (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * add a card to stored database
     */
    @Override
    public void save(DatabaseCard theDatabaseCard) {

        try {
            entityManager.persist(theDatabaseCard);
        } catch (Exception ex) {
            System.out.println("Save error");
        }
    }

    /**
     * find a card by its Id in stored database
     */
    @Override
    public DatabaseCard findById(Integer id) {
        return entityManager.find(DatabaseCard.class, id);
    }

    /**
     * return a List of all cards in collection
     */
    @Override
    public List<DatabaseCard> findAll() {

        TypedQuery<DatabaseCard> theQuery = entityManager.createQuery("FROM card", DatabaseCard.class);

        return theQuery.getResultList();
    }

    /**
     * searches collection for card by card name
     */
    @Override
    public List<DatabaseCard> findByName(String name) {

        TypedQuery<DatabaseCard> theQuery = entityManager.createQuery("FROM card WHERE name=:theData", DatabaseCard.class);
        String formattedName = toTitleCase(name);
        theQuery.setParameter("theData", formattedName);

        return theQuery.getResultList();
    }

    /**
     * updates a card in collection
     */
    @Override
    public void update(DatabaseCard theDatabaseCard) {
        entityManager.merge(theDatabaseCard);
    }

    /**
     * remove a card from collection via the card's name
     */
    @Override
    public void delete(String name) {

        List<DatabaseCard> cards = findByName(name);

        for (DatabaseCard card : cards) {
            DatabaseCard theDatabaseCard = entityManager.find(DatabaseCard.class, card.getId());
            entityManager.remove(theDatabaseCard);
        }
    }

    /**
     * remove a card from collection via the card's Id number in the stored database
     */
    @Override
    public void delete(int id) {

        DatabaseCard card = findById(id);
        entityManager.remove(card);
    }

    /**
     * convert card name to title case formatted name
     * developed by a user on StackOverflow
     */
    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String s : arr) {
            sb.append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
