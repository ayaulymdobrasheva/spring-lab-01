package kz.iitu.springlab.web;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;
import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.MessageSource;
import java.util.Locale;
@Controller
@RequestMapping("/books")
public class BookPageController {

    private final BookService service;
    private final MessageSource messageSource;

    public BookPageController(
            BookService service,
            MessageSource messageSource
    ) {
        this.service = service;
        this.messageSource = messageSource;
    }
    @GetMapping
    public String list(
            @RequestParam(required = false) String author,
            Model model
    ) {
        model.addAttribute("books", service.findAll(author));
        model.addAttribute("author", author);
        model.addAttribute("title", "Catalogue");

        return "books/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new BookForm());
        model.addAttribute("genres", Genre.values());

        return "books/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("form") BookForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirect,
            Locale locale
    ) {
        if (binding.hasErrors()) {
            model.addAttribute("genres", Genre.values());
            return "books/form";
        }

        Book saved = service.create(
                new Book(
                        null,
                        form.getTitle(),
                        form.getAuthor(),
                        form.getYear()
                )
        );

        redirect.addFlashAttribute(
                "message",
                messageSource.getMessage(
                        "book.added",
                        new Object[]{saved.title()},
                        locale
                )
        );

        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("book", service.getById(id));
        return "books/detail";
    }
}