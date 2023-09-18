package zhanuzak.dto.response;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

@Getter
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<String> images;
    private String characteristic;
    private boolean isFavorite;
    private Country madeIn;
    private Category category;
    private List<String> comments;
    private int countComment;
    private int countCommentsByUser;
    private List<String>FirstNameCommentsByUser;
    private int countLike;

    public ProductResponse(Long id, String name, BigDecimal price,
                           String characteristic, boolean isFavorite, Country madeIn, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.characteristic = characteristic;
        this.isFavorite = isFavorite;
        this.madeIn = madeIn;
        this.category = category;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }

    public void setFirstNameCommentsByUser(List<String> firstNameCommentsByUser) {
        FirstNameCommentsByUser = firstNameCommentsByUser;
    }

    public void setCountCommentsByUser(int countCommentsByUser) {
        this.countCommentsByUser = countCommentsByUser;
    }

    public int getCountComment() {
        return countComment;
    }

    public void setCountComment(int countComment) {
        this.countComment = countComment;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setMadeIn(Country madeIn) {
        this.madeIn = madeIn;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

