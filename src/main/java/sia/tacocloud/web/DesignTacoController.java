package sia.tacocloud.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.Ingredient;
import sia.tacocloud.Ingredient.Type;
import sia.tacocloud.Order;
import sia.tacocloud.Taco;
import sia.tacocloud.data.IngredientRepository;
import sia.tacocloud.data.TacoRepository;

// memo : spring devtools reloader doesn't recognize newly created sub-package
//        (I don't know why??)
//        therefore, when you created new subpackage you should restart the application.
@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository designRepo;

    @Autowired
    public DesignTacoController(
        IngredientRepository ingredientRepository,
        TacoRepository tacoRepository) {
        this.ingredientRepo = ingredientRepository;
        this.designRepo = tacoRepository;
    }

    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute("taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                               filterByType(ingredients, type));
        }
    }
    
    @GetMapping
    public String showDesignForm(Model model) {
        return "design";
    }
    @PostMapping
    public String processDesign(
        @Valid Taco design,  Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }
    // memo: https://stackoverflow.com/a/54203484
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
            .filter(x -> x.getType().equals(type))
            .collect(Collectors.toList());
	}
}
