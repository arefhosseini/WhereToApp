package ir.fearefull.wheretoapp.model.api.Enum;

public enum PlaceTypeEnum {
    Irani("ایرانی"),
    Italian("ایتالیایی"),
    Cafe("کافه"),
    FastFood("فست فود");

    private String text;

    PlaceTypeEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
