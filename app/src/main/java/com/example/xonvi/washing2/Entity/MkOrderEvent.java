package com.example.xonvi.washing2.Entity;

import java.util.List;

/**
 * Created by xonvi on 2017/2/8.
 */

public class MkOrderEvent {

    private List<Thing> thingList;
    public MkOrderEvent(List<Thing> list){

        this.thingList = list;
    }

    public List<Thing> getThingList() {
        return thingList;
    }

    public void setThingList(List<Thing> thingList) {
        this.thingList = thingList;
    }
}
