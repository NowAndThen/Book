package controllers;

import Models.Author;
import Models.Book;
import Models.BookToAuthor;
import Models.Volume;
import com.avaje.ebean.Ebean;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.book.*;

import javax.inject.Inject;
import java.util.*;

public class BooksController extends Controller {
    @Inject
    FormFactory formFactory;
    public Result index(){
        Set<Volume> volumes = Volume.allBooks();
        return ok(index.render(volumes));
    }

    public Result create(){
        Form<Volume> bookForm = formFactory.form(Volume.class);
        return ok(create.render(bookForm));
    }

    public Result show(Integer id){
        Book book = Book.find.byId(id);
        if( book == null){
            return notFound("Book hasn't found");
        }
        String authors = Volume.getAuthors(id);
        return ok(show.render(new Volume(book, authors)));
    }

    public Result edit(Integer id){
        Book book = Book.find.byId(id);
        if( book == null){
            return notFound("Book hasn't found");
        }
        Form<Book> bookForm = formFactory.form(Book.class).fill(book);
        return ok(edit.render(bookForm));
    }

    public Result save(){
        Form<Volume> volumeForm = formFactory.form(Volume.class).bindFromRequest();
        if (volumeForm.hasErrors()){
            return badRequest(create.render(volumeForm));
        }
        Volume vol = volumeForm.get();
        Book book = new Book(vol.title, vol.year);
        book.save();
        List<String> auths = new ArrayList<String>(Arrays.asList(vol.authors.split(";")));
        for (String person: auths)
        {
            Author author = Author.find.byId(person);
            if (author == null)
                author = new Author(person);
            author.save();
            BookToAuthor relation = new BookToAuthor(book.id, person);
            relation.save();
        }
        return redirect(routes.BooksController.index());
    }

    public Result update(){
        Book newbook = formFactory.form(Book.class).bindFromRequest().get();
        Book oldBook = Book.find.byId(newbook.id);
        if(oldBook == null) {
            return notFound("Book hasn't found");
        }
        oldBook.title = newbook.title;
        oldBook.year = newbook.year;
        oldBook.update();
        return redirect(routes.BooksController.index());
    }

    public  Result authors()
    {
        List<Author> auths = Author.find.all();
        return ok(authors.render(auths));
    }

    public Result destroy(Integer id){
        Book book = Book.find.byId(id);
        if( book == null){
            return notFound("Book hasn't found");
        }
        book.delete();
        // удаление связей между книгой и авторами
        List<BookToAuthor> relations = Ebean.find(BookToAuthor.class).where().eq("id_book",id).findList();
        for(BookToAuthor item: relations){
            Author author = Author.find.byId(item.author);
            if (Ebean.find(BookToAuthor.class).where().eq("author",item.author).findList().isEmpty());
                author.delete();
            item.delete();
        }
        return redirect(routes.BooksController.index());
    }

}
