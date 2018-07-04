package Models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.SqlRow;
import org.springframework.core.annotation.Order;
import play.data.validation.Constraints;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Volume extends Book{

    @Constraints.Required
    public String authors;

    public Volume(){};

    public Volume(Book book, String authors){
        super(book.id, book.title, book.year);
        this.authors = authors;
    }

    public static Set<Volume> allBooks(){
        Set<Volume> res = new HashSet<>();
        List<Book> books = Book.find.all();
        if (books == null)
            return null;
        for(Book book: books){
            String authors = Volume.getAuthors(book.id);
            res.add(new Volume(book, authors));
        }
        return res;
    }

    public static String getAuthors(Integer id){
        String sql = " select o.author " +
                "from author o " +
                "join book_to_author c on c.author = o.author " +
                "where c.id_book = " + id;
        List<SqlRow> bug = Ebean.createSqlQuery(sql).findList();
        String authors = "";
        for(SqlRow one: bug) {
            authors += one.getString("author") + "; ";
        }
        return authors.substring(0,authors.length());
    }

}
