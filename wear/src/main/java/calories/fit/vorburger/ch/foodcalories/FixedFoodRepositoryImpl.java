package calories.fit.vorburger.ch.foodcalories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FixedFoodRepositoryImpl implements FoodRepository {

    private List<Food> allFoods = Arrays.asList( new Food[] {
            new Food("a", "small Apple", 40),
            new Food("a", "medium Apple", 70),
            new Food("a", "big Apple", 90),
            new Food("an", "Egg", 70),
            new Food("a", "Toast", 70),
            new Food("an", "Almond", 7),
    } );

    @Override
    public List<Food> find(String foodName) {
        final String lowerCaseFoodName = foodName.toLowerCase();
        List<Food> results = new ArrayList<>(7);
        for (Food aFood : allFoods) {
            if (aFood.name.toLowerCase().contains(lowerCaseFoodName))
                results.add(aFood);
        }
        return results;
    }

}
