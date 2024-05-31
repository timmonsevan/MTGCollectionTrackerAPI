package com.MTGCollectionTracker.MTGCollectionTrackerApplication.entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * pojo for binding data from form submissions
 * (different from Card which is an object imported from the MTG SDK)
 * @author timmonsevan
 */
public class SearchCard {

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    private String cardName;

    @NotNull(message="is required")
    @Min(value=1, message="must be greater than zero")
    @Pattern(regexp="^[0-9]", message="must be whole integer")
    private String cardQuantity;

    @Pattern(regexp="^[a-zA-Z0-9]{3}", message="only 3 characters/digits")
    private String cardSet;

    public SearchCard() {
    }

    public SearchCard(String name) {
        this.cardName = name;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardQuantity() {
        return cardQuantity;
    }

    public void setCardQuantity(String cardQuantity) {
        this.cardQuantity = cardQuantity;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String set) {
        this.cardSet = set;
    }
}
