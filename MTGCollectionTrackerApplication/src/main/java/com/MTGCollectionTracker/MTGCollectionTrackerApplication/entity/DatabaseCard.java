package com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity;
import io.magicthegathering.javasdk.resource.Card;
import jakarta.persistence.*;

/**
 * pojo representing a card saved to local database
 * (different from Card which is an object imported from the MTG SDK)
 *
 * @author timmonsevan
 */

@Entity(name = "card")
@Table(name = "card")
public class DatabaseCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private long id;

    @Column(name = "`mana_cost`")
    private String manaCost;

    @Column(name = "`multiverse_id`")
    private long multiverseId;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`quantity`")
    private int quantity;

    @Column(name = "`rarity`")
    private String rarity;

    @Column(name = "`set`")
    private String set;

    @Column(name = "`set_name`")
    private String setName;

    @Column(name = "`text`")
    private String text;

    @Column(name = "`type`")
    private String type;


    public DatabaseCard() {
    }

    public DatabaseCard(Card card, int quantity) {

        this.manaCost = card.getManaCost();
        this.multiverseId = card.getMultiverseid();
        this.name = card.getName();
        this.quantity = quantity;
        this.rarity = card.getRarity();
        this.set = card.getSet();
        this.setName = card.getSetName();
        this.text = card.getText();
        this.type = card.getType();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getMultiverseId() {
        return multiverseId;
    }

    public void setMultiverseId(long multiverseId) {
        this.multiverseId = multiverseId;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    @Override
    public String toString() {
        return "DatabaseCard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manaCost='" + manaCost + '\'' +
                ", type='" + type + '\'' +
                ", rarity='" + rarity + '\'' +
                ", text='" + text + '\'' +
                ", multiverseId=" + multiverseId +
                ", set='" + set + '\'' +
                ", setName='" + setName + '\'' +
                '}';
    }
}
