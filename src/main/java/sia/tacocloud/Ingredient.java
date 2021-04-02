package sia.tacocloud;

import lombok.Data;
import lombok.RequiredArgsConstructor;

// memo: RequiredArgsConstructor creates a constructor with parameter declared as final.
@Data
@RequiredArgsConstructor
public
class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
