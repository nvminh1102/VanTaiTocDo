package com.osp.model.view;

import com.osp.model.Authority;

import java.util.List;

/**
 * Created by Admin on 12/27/2017.
 */
public class AuthorityView {
    private Authority parent;
    private List<Authority> childrens;

    public Authority getParent() {
        return parent;
    }

    public void setParent(Authority parent) {
        this.parent = parent;
    }

    public List<Authority> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Authority> childrens) {
        this.childrens = childrens;
    }
}
