package com.nlte.myappportfolio;

import android.test.AndroidTestCase;

import com.nlte.myappportfolio.bean.MoviesSetBean;

import java.io.IOException;

/**
 * NLTE
 * 2016/6/5 0005 19:22
 */
public class TestLoadMovesDate extends AndroidTestCase{

    public void testGetPopMoves(){
        try {
            MoviesSetBean popMovies = LoadMovesDate.getPopMovies();
            System.out.println(popMovies.getPage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void testCasr(){
        assertEquals(4, 2 + 2);
    }

}
