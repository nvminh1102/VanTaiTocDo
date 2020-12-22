package com.osp.model.view;

import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.util.List;

public class ListJSonCount {
    private int count;
    private List<JSONObject> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<JSONObject> getList() {
        return list;
    }

    public void setList(List<JSONObject> list) {
        this.list = list;
    }
}
