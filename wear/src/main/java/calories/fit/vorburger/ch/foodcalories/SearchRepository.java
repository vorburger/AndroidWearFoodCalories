package calories.fit.vorburger.ch.foodcalories;

import java.util.List;

public interface SearchRepository<T> {

    List<T> find(String partialName);

}
