package com.nlte.myappportfolio;

import com.nlte.myappportfolio.bean.MoviesSetBean;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testCasr(){
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testGetPopMoves(){
        try {
            MoviesSetBean popMovies = LoadMovesDate.getPopMovies();
            System.out.println(popMovies.getPage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}