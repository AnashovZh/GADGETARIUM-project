package zhanuzak.dto.response;

import zhanuzak.enums.Category;
import zhanuzak.enums.Country;

import java.math.BigDecimal;
import java.util.List;

//    private Long id;
//    private String name;
//    private BigDecimal price;
//    private List<String> images;
//    private boolean isFavorite;
//    private Country madeIn;
//    private Category category;
//    private Role role;


public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<String> images;
    private String characteristic;
    private boolean isFavorite;
    private Country madeIn;
    private Category category;

    public ProductResponse(Long id, String name, BigDecimal price, List<String> images,
              String characteristic, boolean isFavorite, Country madeIn, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.images = images;
        this.characteristic = characteristic;
        this.isFavorite = isFavorite;
        this.madeIn = madeIn;
        this.category = category;
    }

    public ProductResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Country getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(Country madeIn) {
        this.madeIn = madeIn;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

