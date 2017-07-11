package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.widget.ImageView;

import com.os.stefanos.foodcube.tools.AppTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import dao.RestaurantDAO;

/**
 * Created by stefanos on 6.9.16..
 */
public class Restaurant {
    private int id;
    private String name;
    private String description;
    private Bitmap smallPhoto;
    private Bitmap largePhoto;
    private Address address;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String phone;
    private String email;
    private URL site;
    private List<Meal> meals;
    private float longitude;
    private float latitude;
    public String photoUrl;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Bitmap getSmallPhoto() {
        return smallPhoto;
    }

    public void setSmallPhoto(Bitmap smallPhoto) {
        this.smallPhoto = smallPhoto;
    }

    public Bitmap getLargePhoto() {
        return largePhoto;
    }

    public void setLargePhoto(Bitmap largePhoto) {
        this.largePhoto = largePhoto;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public URL getSite() {
        return site;
    }

    public void setSite(URL site) {
        this.site = site;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Date getOpeningTime() throws ParseException {
        String concatenator = this.getStartHour() + ":" + this.getStartMinute();
        DateFormat parser = new SimpleDateFormat("k:mm");
        String startDate = "2013-09-25";
        Date parsedDate = parser.parse(concatenator);

        return parsedDate;
    }

    //first parameter should be workingTIme
    public static List<Restaurant> getFiltered(double latitude, double longitude, double distance)
    {
        //List<Restaurant> resultSet = getFilteredByWorkingTme(timeOpening);
        List<Restaurant> resultSet = RestaurantDAO.getRestaurants();
        return filterByDistance(resultSet, latitude, longitude, distance);
    }

    public static List<Restaurant> filterByDistance(List<Restaurant> toFilter, double latitude, double longitude, double distance)
    {
        Iterator<Restaurant> it = toFilter.iterator();
        while(it.hasNext())
        {
            Restaurant revob = it.next();
            if(AppTools.haversineDistance(latitude, longitude,
                    revob.getAddress().getLatitude(), revob.getAddress().getLongitude()) > distance) // ako je udaljen vise od distance
            {
                it.remove(); // izbaci ga
            }
        }
        return toFilter;
    }
/*
    private void loadImageFromStorage(String imgName)
    {
        String path = getAssets();
        String imageName = imgName;
        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    */

    public String getWorkingTime(){
        return startHour+":"+startMinute+"0-"+endHour+":"+endMinute+"0";
    }
    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", smallPhoto=" + smallPhoto +
                ", largePhoto=" + largePhoto +
                ", address=" + address +
                ", startHour=" + startHour +
                ", startMinute=" + startMinute +
                ", endHour=" + endHour +
                ", endMinute=" + endMinute +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", site=" + site +
                ", meals=" + meals +
                '}';
    }
}
