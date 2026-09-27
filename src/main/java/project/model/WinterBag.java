package project.model;

public class WinterBag extends AidItem {
    private static final long serialVersionUID = 1L;
    public WinterBag(String code, String description) {
        super(code, description);
    }
    @Override
    public String getCategory() {
        return "Winter Bag";
    }
}
