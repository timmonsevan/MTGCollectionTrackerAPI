package com.MTGCollectionTracker.MTGCollectionTrackerApplication.dao;
import com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity.DatabaseCard;
import java.util.List;

/**
 * CRUD interface for accessing DatabaseCard in local database
 * @author timmonsevan
 */

public interface CardDAO {

    void save(DatabaseCard theDatabaseCard);

    DatabaseCard findById(Integer id);

    List<DatabaseCard> findAll();

    List<DatabaseCard> findByName(String name);

    void update(DatabaseCard theDatabaseCard);

    void delete(String name);

    void delete(int id);
}
