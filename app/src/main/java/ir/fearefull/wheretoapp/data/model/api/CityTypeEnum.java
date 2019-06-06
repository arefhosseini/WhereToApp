package ir.fearefull.wheretoapp.data.model.api;

import java.util.Arrays;
import java.util.List;

import static ir.fearefull.wheretoapp.data.model.api.StateTypeEnum.AzarbayejanSharghi;

@SuppressWarnings("ALL")
public enum CityTypeEnum {
    Tabriz("تبریز"),
    Maraghe("	مراغه"),
    Marand("مرند"),
    Ahar("اهر"),
    Oroomie("ارومیه"),
    Khooy("خوی"),
    MianDoab("میاندوآب"),
    Mahabad("مهاباد"),
    Ardebil("اردبیل"),
    ParsAbad("پارس‌آباد"),
    MeshkinShahr("مشگین‌شهر"),
    Khalkhal("خلخال"),
    Isfahan("اصفهان"),
    Kashan("کاشان"),
    KhemoeiniShahr("خمینی‌شهر"),
    NajafAbad("نجف‌آباد"),
    Karaj("کرج"),
    KamalShahr("کمال‌شهر"),
    NazarAbad("نظرآباد"),
    MohammadShahr("محمدشهر"),
    Ilam("ایلام"),
    Dehloran("دهلران"),
    Ivan("ایوان"),
    Abdanan("آبدانان"),
    Booshehr("بوشهر"),
    Borazjan("برازجان"),
    BandarKangan("بندر کنگان"),
    BandarGonave("بندر گناوه"),
    Tehran("تهران"),
    EslamShahr("اسلامشهر"),
    Malard("ملارد"),
    Ghods("قدس"),
    ShahrKord("شهرکرد"),
    Boroojen("بروجن"),
    Lordegan("لردگان"),
    FarokhShahr("فرخ‌شهر"),
    Birjand("بیرجند"),
    Ghaen("	قائن"),
    Tabas("طبس"),
    Ferdos("فردوس"),
    Mashhad("مشهد"),
    Neishaboor("نیشابور"),
    Sabzevar("سبزوار"),
    Ghoochan("قوچان"),
    TorbatHeidarie("تربت حیدریه"),
    Bojnord("بجنورد"),
    Shiravan("شیروان"),
    Esfarayen("اسفراین"),
    Ashkhane("آشخانه"),
    Ahvaz("اهواز"),
    Andika("	اندیکا"),
    Abadan("آبادان"),
    BandarMahShahr("بندر ماهشهر"),
    Zanjan("زنجان"),
    Abhar("ابهر"),
    Khoramdare("خرمدره"),
    Gheidar("قیدار"),
    Semnan("سمنان"),
    Shahrood("شاهرود"),
    Damghan("دامغان"),
    Garmsar("گرمسار"),
    Zahedan("زاهدان"),
    Zabol("زابل"),
    IranShahr("ایرانشهر"),
    Chabahar("چابهار"),
    Shiraz("شیراز"),
    MarvShahr("مرودشت"),
    Jahrom("جهرم"),
    FiroozAbad("فیروزآباد"),
    Ghazvin("قزوین"),
    Alvand("الوند"),
    Takestan("تاکستان"),
    BooeenZahra("بوئین زهرا"),
    Ghom("قم"),
    Ghanavat("قنوات"),
    Jafarie("جعفریه"),
    Kahak("کهک"),
    Sanandaj("سنندج"),
    Saghez("سقز"),
    Marivan("مریوان"),
    Bane("بانه"),
    Kerman("کرمان"),
    Sirjan("سیرجان"),
    Rafsanjan("رفسنجان"),
    Jiroft("جیرفت"),
    KermanShah("کرمانشاه"),
    EslamAbadGharb("اسلام‌آباد غرب"),
    Kangavar("کنگاور"),
    JavanRood("جوانرود"),
    Yasooj("یاسوج"),
    DoGonbadan("دوگنبدان"),
    Dehdasht("دهدشت"),
    Gorgan("گرگان"),
    GonbadKavoos("گنبد کاووس"),
    AliAbadKatool("علی‌آباد کتول"),
    BandarTorkaman("بندر ترکمن"),
    Rasht("رشت"),
    BandarAnzali("بندر انزلی"),
    Lahijan("لاهیجان"),
    Langerood("لنگرود"),
    KhoramAbad("خرم‌آباد"),
    Boroojerd("بروجرد"),
    Dorood("دورود"),
    KoohDasht("کوهدشت"),
    Sari("ساری"),
    Amol("آمل"),
    Babol("بابل"),
    GhaemShahr("قائم‌شهر"),
    Arak("اراک"),
    Save("ساوه"),
    Khomein("خمین"),
    Mahalat("محلات"),
    BandarAbas("	بندرعباس"),
    Minab("میناب"),
    Dehbarez("دهبارز"),
    BandarLenge("بندر لنگه"),
    Hamedan("همدان"),
    Malayer("ملایر"),
    Nahavand("نهاوند"),
    AsadAbad("اسدآباد"),
    Yazd("یزد"),
    Meibod("میبد"),
    Ardakan("اردکان"),
    Hamidia("حمیدیا");

    private String text;

    CityTypeEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public StateTypeEnum getState() {
        return AzarbayejanSharghi;
    }

    public List<CityTypeEnum> getCityTypeEnums(StateTypeEnum stateTypeEnum) {
        List<CityTypeEnum> cityTypeEnumList;
        switch (stateTypeEnum) {
            case AzarbayejanSharghi:
                cityTypeEnumList = Arrays.asList(Tabriz, Maraghe, Marand, Ahar);
                break;
            case AzarbayejanGharbi:
                cityTypeEnumList = Arrays.asList(Oroomie, Khooy, MianDoab, Mahabad);
                break;
            case Ardebil:
                cityTypeEnumList = Arrays.asList(Ardebil, ParsAbad, MeshkinShahr, Khalkhal);
                break;
            case Isfahan:
                cityTypeEnumList = Arrays.asList(Isfahan, Kashan, KhemoeiniShahr, NajafAbad);
                break;
            case Alborz:
                cityTypeEnumList = Arrays.asList(Karaj, KamalShahr, NazarAbad, MohammadShahr);
                break;
            case Ilam:
                cityTypeEnumList = Arrays.asList(Ilam, Dehloran, Ivan, Abdanan);
                break;
            case Booshehr:
                cityTypeEnumList = Arrays.asList(Booshehr, Borazjan, BandarKangan, BandarGonave);
                break;
            case Tehran:
                cityTypeEnumList = Arrays.asList(Tehran, EslamShahr, Malard, Ghods);
                break;
            case ChaharMahal:
                cityTypeEnumList = Arrays.asList(ShahrKord, Boroojen, Lordegan, FarokhShahr);
                break;
            case SouthKhorasan:
                cityTypeEnumList = Arrays.asList(Birjand, Ghaen, Tabas, Ferdos);
                break;
            case KhorasanRazavi:
                cityTypeEnumList = Arrays.asList(Mashhad, Neishaboor, Sabzevar, Ghoochan, TorbatHeidarie);
                break;
            case NorthKhorasan:
                cityTypeEnumList = Arrays.asList(Bojnord, Shiravan, Esfarayen, Ashkhane);
                break;
            case Khoozestan:
                cityTypeEnumList = Arrays.asList(Ahvaz, Andika, Abadan, BandarMahShahr);
                break;
            case Zanjan:
                cityTypeEnumList = Arrays.asList(Zanjan, Abhar, Khoramdare, Gheidar);
                break;
            case Semnan:
                cityTypeEnumList = Arrays.asList(Semnan, Shahrood, Damghan, Garmsar);
                break;
            case Sistan:
                cityTypeEnumList = Arrays.asList(Zahedan, Zabol, IranShahr, Chabahar);
                break;
            case Fars:
                cityTypeEnumList = Arrays.asList(Shiraz, MarvShahr, Jahrom, FiroozAbad);
                break;
            case Ghazvin:
                cityTypeEnumList = Arrays.asList(Ghazvin, Alvand, Takestan, BooeenZahra);
                break;
            case Ghom:
                cityTypeEnumList = Arrays.asList(Ghom, Ghanavat, Jafarie, Kahak);
                break;
            case Kordestan:
                cityTypeEnumList = Arrays.asList(Sanandaj, Saghez, Marivan, Bane);
                break;
            case Kerman:
                cityTypeEnumList = Arrays.asList(Kerman, Sirjan, Rafsanjan, Jiroft);
                break;
            case KermanShah:
                cityTypeEnumList = Arrays.asList(KermanShah, EslamAbadGharb, Kangavar, JavanRood);
                break;
            case Kohgilooye:
                cityTypeEnumList = Arrays.asList(Yasooj, DoGonbadan, Dehdasht);
                break;
            case Golestan:
                cityTypeEnumList = Arrays.asList(Gorgan, GonbadKavoos, AliAbadKatool, BandarTorkaman);
                break;
            case Gilan:
                cityTypeEnumList = Arrays.asList(Rasht, BandarAnzali, Lahijan, Langerood);
                break;
            case Lorestan:
                cityTypeEnumList = Arrays.asList(KhoramAbad, Boroojerd, Dorood, KoohDasht);
                break;
            case Mazandaran:
                cityTypeEnumList = Arrays.asList(Sari, Amol, Babol, GhaemShahr);
                break;
            case Markazi:
                cityTypeEnumList = Arrays.asList(Arak, Save, Khomein, Mahalat);
                break;
            case Hormozgan:
                cityTypeEnumList = Arrays.asList(BandarAbas, Minab, Dehbarez, BandarLenge);
                break;
            case Hamedan:
                cityTypeEnumList = Arrays.asList(Hamedan, Malayer, Nahavand, AsadAbad);
                break;
            default:
                cityTypeEnumList = Arrays.asList(Yazd, Meibod, Ardakan, Hamidia);
                break;
        }

        return cityTypeEnumList;
    }
}