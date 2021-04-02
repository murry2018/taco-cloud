package sia.tacocloud.experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/exp")
public class Board {
    
    @Data
    public static class Author {
        @NotBlank(message = "Say something")
        private String name;
    }
    @Data
    public static class Article {
        @Valid
        private Author author;
        @NotBlank(message = "I'm givin' up on you")
        private String content;
    }
    List<Article> articles = new ArrayList<>();
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", articles);
        model.addAttribute("article", new Article());
        return "exp/list";
    }

    @PostMapping("/write")
    public String write(@Valid Article article, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("items", articles);
            model.addAttribute("my-article", new Article());
            
            return "exp/list";
        }
        articles.add(article);
        return "redirect:/exp";
    }

    @GetMapping("/summary")
    public String summary(Model model) {
        	List<String> items = Arrays.asList(
                "Spring",
                "Thymeleaf",
                "validation");
            model.addAttribute("title", "Section Summary");
            model.addAttribute("items", items);
            return "exp/summary";
    }
}
