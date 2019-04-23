package com.pluralsight.bookstore.repository;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.model.Language;
import com.pluralsight.bookstore.util.IsbnGenerator;
import com.pluralsight.bookstore.util.NumberGenerator;
import com.pluralsight.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.awt.*;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {

    @Inject
    private BookRepository bookRepository;



    @Test(expected = Exception.class)
    public void findWithInvalidID(){
        bookRepository.find(null);
    }


    @Test(expected = Exception.class)
    public void createInvalidBook(){
        Book book = new Book(null, "description", 12F,"isbn", new Date(),123,"http://test", Language.ENGLISH);
        bookRepository.create(book);
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addClass(TextUtil.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
    }

    @Test
    public void create() {

        assertEquals(Long.valueOf(0),bookRepository.countAll());
        assertEquals(0,bookRepository.findAll().size());

        // Create a Book
//        String title, String description, Float unitCost, String isbn, Date publicationDate, Integer nbOfPages, String imageUrl, Language language
         Book book = new Book("a     title", "description", 12F,"isbn", new Date(),123,"http://test", Language.ENGLISH);
         book = bookRepository.create(book);
         Long bookId = book.getId();

         //Check create book
         assertNotNull(bookId);

        //Find a created book
        Book bookFound = bookRepository.find(bookId);

        // Check the found book
        assertEquals("a title",bookFound.getTitle());
        assertTrue(bookFound.getIsbn().startsWith("13"));



        //Test counting books
        assertEquals(Long.valueOf(1), bookRepository. countAll());
        assertEquals(1, bookRepository.findAll().size());

        // Delete the book
        bookRepository.delete(bookId);

        //Test counting books

        assertEquals(Long.valueOf(0),bookRepository.countAll());
        assertEquals(0,bookRepository.findAll().size());


    }
}
