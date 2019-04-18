package ir.fearefull.wheretoapp.Enum;

@SuppressWarnings("ALL")
public enum StateTypeEnum {
    AzarbayejanSharghi("آذربایجان شرقی"),
    AzarbayejanGharbi("آذربایجان غربی"),
    Ardebil("اردبیل"),
    Isfahan("اصفهان"),
    Alborz("البرز"),
    Ilam("ایلام"),
    Booshehr("بوشهر"),
    Tehran("تهران"),
    ChaharMahal("چهارمحال و بختیاری"),
    SouthKhorasan("خراسان جنوبی"),
    KhorasanRazavi("خراسان رضوی"),
    NorthKhorasan("خراسان شمالی"),
    Khoozestan("خوزستان"),
    Zanjan("زنجان"),
    Semnan("سمنان"),
    Sistan("سیستان و بلوچستان"),
    Fars("فارس"),
    Ghazvin("قزوین"),
    Ghom("قم"),
    Kordestan("کردستان"),
    Kerman("کرمان"),
    KermanShah("کرمانشاه"),
    Kohgilooye("کهگیلویه و بویراحمد"),
    Golestan("گلستان"),
    Gilan("گیلان"),
    Lorestan("لرستان"),
    Mazandaran("مازندران"),
    Markazi("مرکزی"),
    Hormozgan("هرمزگان"),
    Hamedan("همدان"),
    Yazd("یزد");

    private String text;

    StateTypeEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
