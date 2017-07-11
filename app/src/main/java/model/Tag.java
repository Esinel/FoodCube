package model;

import java.util.List;

/**
 * Created by stefanos on 6.9.16..
 */
public class Tag {
    private int id;
    private String name;
    private List<Meal> meals;
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

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return name + ", ";
    }
}
