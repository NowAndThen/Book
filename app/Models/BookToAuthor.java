package Models;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BookToAuthor extends Model {
    @Id
    Integer id;
    public Integer id_book;
    public String author;

    public  BookToAuthor(){};
    public  BookToAuthor(Integer id_book, String author){
        this.id_book = id_book;
        this.author = author;
    }
}
