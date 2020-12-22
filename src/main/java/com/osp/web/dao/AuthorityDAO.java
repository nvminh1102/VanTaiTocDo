/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.Authority;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Vu Van Lich
 */
public interface AuthorityDAO {
    
    public Optional<PagingResult> page(PagingResult page, String authKey);

    public boolean addAuthority(Authority authItem);

    public boolean isExits(Authority authItem);

    public boolean editAuthority(Authority authItem);

    public Authority getAuthorityById(Long id);

    public boolean deleteAuthority(Authority authDel);

    public boolean checkAuthorityAssigned(long authId);

    public List<Authority> getListAuthParent(long authid);

    public List<Authority> getAuthorityChildrenById(long authId);
    
}
