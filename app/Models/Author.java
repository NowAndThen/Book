package Models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Author extends Model {
    @Id
    public String author;

    public Author(){}
    public Author(String author){
        this.author = author;
    }
    public  static  Finder<String, Author> find = new Finder<>(Author.class);
}
