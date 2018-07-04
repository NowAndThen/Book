package Models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;

@Entity
public class Book extends Model {
    @Id
    public Integer id;
    @Constraints.Required
    public String title;
    @Constraints.Required
    public Integer year;

    public Book(){};
    public Book(String title, Integer year){
        this.title = title;
        this.year = year;
    }
    public Book(Integer id, String title, Integer year){
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public  static  Finder<Integer, Book> find = new Finder<>(Book.class);

}
