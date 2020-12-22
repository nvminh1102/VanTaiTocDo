/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.service;

import com.osp.common.PagingResult;
import com.osp.model.Authority;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Vu Van Lich
 */
public interface AuthorityService {
    public Optional<PagingResult> page(PagingResult page, String authKey);

    public boolean addAuthority(Authority authoItem, String ipClient);

    public boolean isExits(Authority authoItem);

    public boolean editAuthority(Authority authoItem, String ipClient);

    public Authority getAuthorityById(Long authid);

    public boolean deleteAuthority(Authority authorityDel, String ipClient);

    public boolean checkAuthorityAssigned(long authid);

    public List<Authority> getListAuthParent(long authid);

    public List<Authority> getAuthorityChildrenById(long id);
}
