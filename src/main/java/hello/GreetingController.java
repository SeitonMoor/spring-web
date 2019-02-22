package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GreetingController {

    private NewsRepository repository;

    @PostConstruct
    public void init() {
        repository = new FileNewsRepository("news.txt");
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/news")
    public String news(Model model, HttpServletRequest request) throws IOException {
        List<News> news = repository.readNews();

        model.addAttribute("news", news);

        return "news";
    }
}