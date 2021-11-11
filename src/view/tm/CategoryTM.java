package view.tm;

public class CategoryTM {
    private String categoryId;
    private String categoryName;

    public CategoryTM() {
    }

    public CategoryTM(String categoryId, String categoryName) {
        this.setCategoryId(categoryId);
        this.setCategoryName(categoryName);
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoryTM{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
