package Ebamazon.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchParameters {
    private ArrayList<String> searchQuery;
    private boolean showFixed;
    private boolean showAuction;
    private BigDecimal minPrice=BigDecimal.valueOf(0);
    private BigDecimal maxPrice=BigDecimal.valueOf(9999999999.99);

    public SearchParameters(String searchQuery){
        this.searchQuery = parseSearchInput(searchQuery);
    }

    //parse search query
    private ArrayList<String> parseSearchInput(String input){
        String [] auctionKeywords = input.split(" ");
        return new ArrayList<String>(Arrays.asList(auctionKeywords));
    }

    public ArrayList<String> getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(ArrayList<String> searchQuery) {
        this.searchQuery = searchQuery;
    }

    public boolean isShowFixed() {
        return showFixed;
    }

    public void setShowFixed(boolean showFixed) {
        this.showFixed = showFixed;
    }

    public boolean isShowAuction() {
        return showAuction;
    }

    public void setShowAuction(boolean showAuction) {
        this.showAuction = showAuction;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
