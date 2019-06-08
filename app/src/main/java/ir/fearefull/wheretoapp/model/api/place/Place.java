package ir.fearefull.wheretoapp.model.api.place;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ir.fearefull.wheretoapp.model.api.Enum.CityTypeEnum;
import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;
import ir.fearefull.wheretoapp.model.api.Enum.StateTypeEnum;

@SuppressWarnings("serial")
public class Place implements Serializable {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("price_degree")
    private int price_degree;

    @SerializedName("address")
    private String address;

    @SerializedName("open_hours")
    private String open_hours;

    @SerializedName("price")
    private String price;

    @SerializedName("features")
    private String features;

    @SerializedName("state")
    private StateTypeEnum state;

    @SerializedName("city")
    private CityTypeEnum city;

    @SerializedName("place_image")
    private String place_image;

    @SerializedName("place_types")
    private List<PlaceTypeEnum> place_types;

    @SerializedName("place_images")
    private List<PlaceImage> place_images;

    @SerializedName("overall_score")
    private float overall_score;

    @SerializedName("all_scores_count")
    private int all_scores_count;

    @SerializedName("one_score_count")
    private int one_score_count;

    @SerializedName("two_score_count")
    private int two_score_count;

    @SerializedName("three_score_count")
    private int three_score_count;

    @SerializedName("four_score_count")
    private int four_score_count;

    @SerializedName("five_score_count")
    private int five_score_count;

    @SerializedName("food_score_average")
    private float food_score_average;

    @SerializedName("service_score_average")
    private float service_score_average;

    @SerializedName("ambiance_score_average")
    private float ambiance_score_average;

    @SerializedName("coordinate_place")
    private List<CoordinatePlace> coordinate_place;

    @SerializedName("phones_place")
    private List<String> phones_place;

    public Place(long id, String name, int price_degree, String address, String open_hours, String price,
                 String features, StateTypeEnum state, CityTypeEnum city, String place_image,
                 float overall_score, List<PlaceTypeEnum> place_types, List<PlaceImage> place_images,
                 int all_scores_count, int one_score_count, int two_score_count, int three_score_count,
                 int four_score_count, int five_score_count, float food_score_average, float service_score_average,
                 float ambiance_score_average, List<CoordinatePlace> coordinate_place, List<String> phones_place) {
        this.id = id;
        this.name = name;
        this.price_degree = price_degree;
        this.address = address;
        this.open_hours = open_hours;
        this.price = price;
        this.features = features;
        this.state = state;
        this.city = city;
        this.place_image = place_image;
        this.place_types = place_types;
        this.place_images = place_images;
        this.overall_score = overall_score;
        this.all_scores_count = all_scores_count;
        this.one_score_count = one_score_count;
        this.two_score_count = two_score_count;
        this.three_score_count = three_score_count;
        this.four_score_count = four_score_count;
        this.five_score_count = five_score_count;
        this.food_score_average = food_score_average;
        this.service_score_average = service_score_average;
        this.ambiance_score_average = ambiance_score_average;
        this.coordinate_place = coordinate_place;
        this.phones_place = phones_place;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriceDegree() {
        return price_degree;
    }

    public String getAddress() {
        return address;
    }

    public String getOpenHours() {
        return open_hours;
    }

    public String getPrice() {
        return price;
    }

    public String getFeatures() {
        return features;
    }

    public StateTypeEnum getState() {
        return state;
    }

    public CityTypeEnum getCity() {
        return city;
    }

    public String getPlaceImage() {
        return place_image;
    }

    public List<PlaceTypeEnum> getPlaceTypes() {
        return place_types;
    }

    public List<PlaceImage> getPlaceImages() {
        return place_images;
    }

    public float getOverallScore() {
        return overall_score;
    }

    public int getAllScoresCount() {
        return all_scores_count;
    }

    public int getOneScoresCount() {
        return one_score_count;
    }

    public int getTwoScoresCount() {
        return two_score_count;
    }

    public int getThreeScoresCount() {
        return three_score_count;
    }

    public int getFourScoresCount() {
        return four_score_count;
    }

    public int getFiveScoresCount() {
        return five_score_count;
    }

    public float getFoodScoreAverage() {
        return food_score_average;
    }

    public float getServiceScoreAverage() {
        return service_score_average;
    }

    public float getAmbianceScoreAverage() {
        return ambiance_score_average;
    }

    public List<CoordinatePlace> getCoordinatePlace() {
        return coordinate_place;
    }

    public List<String> getPhonesPlace() {
        return phones_place;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriceDegree(int price_degree) {
        this.price_degree = price_degree;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOpenHours(String open_hours) {
        this.open_hours = open_hours;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setState(StateTypeEnum state) {
        this.state = state;
    }

    public void setCity(CityTypeEnum city) {
        this.city = city;
    }

    public void setPlaceImage(String place_image) {
        this.place_image = place_image;
    }

    public void setPlaceTypes(List<PlaceTypeEnum> place_types) {
        this.place_types = place_types;
    }

    public void setPlaceImages(List<PlaceImage> place_images) {
        this.place_images = place_images;
    }

    public void setOverallScore(float overall_score) {
        this.overall_score = overall_score;
    }

    public void setAllScoresCount(int all_scores_count) {
        this.all_scores_count = all_scores_count;
    }

    public void setOneScoresCount(int one_score_count) {
        this.one_score_count = one_score_count;
    }

    public void setTwoScoresCount(int two_score_count) {
        this.two_score_count = two_score_count;
    }

    public void setThreeScoresCount(int three_score_count) {
        this.three_score_count = three_score_count;
    }

    public void setFourScoresCount(int four_score_count) {
        this.four_score_count = four_score_count;
    }

    public void setFiveScoresCount(int five_score_count) {
        this.five_score_count = five_score_count;
    }

    public void setFoodScoreAverage(float food_score_average) {
        this.food_score_average = food_score_average;
    }

    public void setServiceScoreAverage(float service_score_average) {
        this.service_score_average = service_score_average;
    }

    public void setAmbianceScoreAverage(float ambiance_score_average) {
        this.ambiance_score_average = ambiance_score_average;
    }

    public void setCoordinatePlace(List<CoordinatePlace> coordinate_place) {
        this.coordinate_place = coordinate_place;
    }

    public void setPhonesPlace(List<String> phones_place) {
        this.phones_place = phones_place;
    }
}
