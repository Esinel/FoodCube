package model;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by stefanos on 6.9.16..
 */
public class Meal {
    private int id;
    private String name;
    private String description;
    private double price;
    private Bitmap image;
    private String imgUrl;
    private Restaurant restaurant;
    private List<Tag> tags;
// TODO: Ostale potrebne metode, geteri i seteri


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", tags=" + tags +
                '}';
    }

    public String getTagsBrusceti(){
        String brusceti = "";
        for (Tag tag : tags){
            brusceti += tag.getName() + "; ";
        }
        return brusceti;
    }
}
