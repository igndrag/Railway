package NATrain.UI;

public enum Lang {
    RU ("Русский"),
    ENG ("English"),
    ;

    private final String description;

    Lang(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}


