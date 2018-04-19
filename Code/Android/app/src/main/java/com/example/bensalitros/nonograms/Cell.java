package com.example.bensalitros.nonograms;

/**
 * Created by Ben Salitros on 4/12/2018.
 */

public class Cell {

    private long id;
    private boolean isClicked;
    private Integer value = 0;

    public Cell(long id) {
        this.id = id;
        this.isClicked = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        if(clicked)  {
            value = 1;
        }
        else  {
            value = 0;
        }
        isClicked = clicked;
    }

    public Integer getValue() {
        return value;
    }
}
