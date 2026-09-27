package project.model;

public class EmergencyKit extends AidItem {
    private static final long serialVersionUID = 1L;
    public EmergencyKit(String code, String description) {
        super(code, description);
    }
    @Override
    public String getCategory() {
        return "Emergency Kit";
    }
}
