package com.osp.web.dao;

import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.model.Authority;
import com.osp.model.Group;
import com.osp.model.GroupAuthority;
import com.osp.model.GroupUser;
import com.osp.model.User;
import com.osp.model.view.GroupView;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Admin on 12/27/2017.
 */
@Repository
@Transactional
public class GroupAuthorityDAO {

    private Logger logger = LogManager.getLogger(GroupAuthorityDAO.class);
    @PersistenceContext(unitName = "appAdmin")
    private EntityManager entityManager;
    @Autowired
    LogAccessDAO logAccessDao;

    public Optional<PagingResult> page(String name, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        Long count = (Long) entityManager.createQuery("select count(gr.id) from com.osp.model.Group gr where UPPER(gr.groupName) like :name").setParameter("name", "%" + ((name != null && name.length() > 0) ? name.trim().toUpperCase() : "") + "%").getSingleResult();
        List<Group> list = entityManager.createQuery("select gr from com.osp.model.Group gr where UPPER(gr.groupName) like :name", Group.class).setParameter("name", "%" + ((name != null && name.length() > 0) ? name.trim().toUpperCase() : "") + "%")
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null && count > 0) {
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }

    @Transactional(value = "transactionManager")
    public Optional<Long> add(Group item) {
        entityManager.persist(item);
        entityManager.flush();
        return Optional.of(item.getId());
    }

    @Transactional(value = "transactionManager")
    public Optional<Boolean> saveGroupView(GroupView item, String ip) {
        try {
            Group group = new Group();
            group.setGroupName(item.getGroupName());
            group.setStatus(true);
            group.setDescription(item.getDescription());
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            group.setCreateBy(user.getUsername());
            group.setUpdateBy(user.getUsername());
            group.setGenDate(new Date());
            group.setLastUpdated(new Date());
            add(group);
            genAuthority(item, group.getId(), user.getUsername());
            logAccessDao.addLog("Thêm mới nhóm quyền Id:" + group.getId(), Constants.Log.system, ip);
            return Optional.of(true);
        } catch (Exception e) {
            return Optional.of(false);
        }
    }

    public Optional<GroupView> getGroupView(Long id) {
        try {
            Group group = get(id).orElse(new Group());
            if (group == null || group.getId() == null) {
                return null;
            }
            GroupView item = new GroupView();
            item.setId(group.getId());
            item.setGroupName(group.getGroupName());
            item.setDescription(group.getDescription());
            List<GroupAuthority> groupAuthorities = loadByGroupId(id).orElse(null);
            if (groupAuthorities != null && groupAuthorities.size() > 0) {
                StringBuilder authoritiesString = new StringBuilder("");
                groupAuthorities.stream().forEach(g -> authoritiesString.append(g.getAuthority() + ","));
                item.setListAuthority(authoritiesString.toString());
            }
            return Optional.ofNullable(item);
        } catch (Exception e) {
            return Optional.of(null);
        }
    }

    @Transactional(value = "transactionManager")
    public Optional<Boolean> editGroupView(GroupView item, String ip) {

        Group group = get(item.getId()).orElse(null);
        if (group == null) {
            return Optional.of(false);
        }
        group.setGroupName(item.getGroupName());
        group.setDescription(item.getDescription());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        group.setUpdateBy(user.getUsername());
        group.setLastUpdated(new Date());
        Long groupId = edit(group).orElse(0L);
        if (groupId == 0L) {
            return Optional.of(false);
        }
        genAuthority(item, groupId, user.getUsername());
        logAccessDao.addLog("Sửa thông tin nhóm quyền Id:" + group.getId(), Constants.Log.system, ip);
        return Optional.of(true);
    }

    public Optional<Group> get(Long id) {
        Group item = entityManager.find(Group.class, id);
        return Optional.ofNullable(item);
    }

    public Optional<List<Group>> loadAllGroup() {
        List<Group> items = entityManager.createQuery("Select gr from com.osp.model.Group gr ", Group.class).getResultList();
        return Optional.ofNullable(items);
    }

    public Optional<List<Group>> loadAllGroupOfUser(Long userId) {
        List<Group> items = entityManager.createQuery("SELECT gr FROM com.osp.model.Group gr JOIN GroupUser gu ON gr.id=gu.groupId where gu.userId=:userId and gr.status=1", Group.class)
                .setParameter("userId", userId).getResultList();
        return Optional.ofNullable(items);
    }
    
    
    
    public Optional<List<User>> loadAllUserOfGroup(Long groupId) {
        List<User> items = entityManager.createQuery("SELECT us FROM com.osp.model.User us JOIN GroupUser gu ON us.id=gu.userId where gu.groupId=:groupId and us.status=1", User.class)
                .setParameter("groupId", groupId).getResultList();
        return Optional.ofNullable(items);
    }
    
     public Optional<PagingResult> pageGroupByUser(long userId, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        Long count = (Long) entityManager.createQuery("SELECT count(gr) FROM com.osp.model.Group gr JOIN GroupUser gu ON gr.id=gu.groupId where gu.userId=:userId and gr.status=1").setParameter("userId", userId).getSingleResult();
        List<Group> list = entityManager.createQuery("SELECT gr FROM com.osp.model.Group gr JOIN GroupUser gu ON gr.id=gu.groupId where gu.userId=:userId and gr.status=1").setParameter("userId", userId)
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null && count > 0) {
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }
     public Optional<PagingResult> pageUserOfGroup(Long groupId, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        Long count = (Long) entityManager.createQuery("SELECT count(us) FROM com.osp.model.User us JOIN GroupUser gu ON us.id=gu.userId where gu.groupId=:groupId and us.status=1").setParameter("groupId", groupId).getSingleResult();
        List<User> list = entityManager.createQuery("SELECT us FROM com.osp.model.User us JOIN GroupUser gu ON us.id=gu.userId where gu.groupId=:groupId and us.status=1").setParameter("groupId", groupId)
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null && count > 0) {
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }
    public Optional<PagingResult> pageAuthorityOfGroup(long groupId, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        Long count = (Long) entityManager.createQuery("SELECT count(au) FROM com.osp.model.Authority au JOIN GroupAuthority ga ON au.id=ga.authority where ga.groupId=:groupId ").setParameter("groupId", groupId).getSingleResult();
        List<Authority> list = entityManager.createQuery("SELECT au FROM com.osp.model.Authority au JOIN GroupAuthority ga ON au.id=ga.authority where ga.groupId=:groupId").setParameter("groupId", groupId)
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null && count > 0) {
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }

    public Optional<List<GroupUser>> loadAllGroupUserByGroupId(Long groupId) {
        List<GroupUser> items = entityManager.createQuery("Select gu from GroupUser gu where gu.groupId=:groupId", GroupUser.class).setParameter("groupId", groupId).getResultList();
        return Optional.ofNullable(items);
    }

    public Optional<Long> edit(Group item) {
        entityManager.merge(item);
        entityManager.flush();
        return Optional.of(item.getId());
    }

    /*AUTHORITY*/
    public Optional<List<Authority>> loadAllAuthority() {
        List<Authority> list = entityManager.createQuery("select a from Authority a order by a.orderId asc", Authority.class).getResultList();
        return Optional.ofNullable(list);
    }

    public Optional<Boolean> addListGroupAuthority(List<GroupAuthority> items) {
        items.stream().forEach(item -> entityManager.persist(item));
        entityManager.flush();
        return Optional.of(true);
    }

    /*GROUP AUTHORITY*/
    public Optional<List<GroupAuthority>> loadByGroupId(Long groupId) {
        List<GroupAuthority> list = entityManager.createQuery("select a from GroupAuthority a WHERE a.groupId=:groupId", GroupAuthority.class).setParameter("groupId", groupId).getResultList();
        return Optional.ofNullable(list);
    }

    public Optional<Boolean> deleteGroupAuthority(Long groupId) {
        Query query = entityManager.createQuery("delete from GroupAuthority a WHERE a.groupId=:groupId").setParameter("groupId", groupId);
        query.executeUpdate();
        return Optional.ofNullable(true);
    }

    public Optional<Boolean> deleteGroup(Long id) {
        Query query = entityManager.createQuery("delete from com.osp.model.Group gr where gr.id=:id").setParameter("id", id);
        query.executeUpdate();
        return Optional.ofNullable(true);
    }

    @Transactional(value = "transactionManager")
    public Optional<Boolean> addListGroupUser(List<GroupUser> items, Long userId) {
        deleteListGroupOfUser(userId);
        if (items.size() > 0) {
            items.stream().forEach(item -> entityManager.persist(item));
            entityManager.flush();
            return Optional.of(true);
        }
        return Optional.of(false);
    }

    @Transactional(value = "transactionManager")
    public Optional<Long> deleteGroup(Long id, String ip) {
        List<GroupUser> listGU = loadAllGroupUserByGroupId(id).orElse(new ArrayList<>());
        if (listGU.size() > 0) {
            return Optional.of(2L);
        }
        deleteGroup(id);
        deleteGroupAuthority(id);
        logAccessDao.addLog("Xóa nhóm quyền Id:" + id, Constants.Log.system, ip);
        return Optional.of(1L);
    }

    public Optional<Boolean> deleteListGroupOfUser(Long userId) {
        Query query = entityManager.createQuery("delete from GroupUser gu where gu.userId=:userId").setParameter("userId", userId);
        query.executeUpdate();
        return Optional.of(true);
    }

    public Optional<List<String>> loadListAuthorityOfUserByUsername(String username) {
        List<String> items = entityManager.createQuery("SELECT au.authKey FROM Authority au JOIN GroupAuthority ga ON au.id=ga.authority JOIN com.osp.model.Group gr ON gr.id=ga.groupId JOIN GroupUser gu ON gr.id=gu.groupId JOIN User us ON us.id=gu.userId WHERE us.username=:username")
                .setParameter("username", username).getResultList();

        return Optional.ofNullable(items);
    }

    @Transactional(value = "transactionManager")
    public void genAuthority(GroupView item, Long groupId, String username) {
        List<String> authorities = new ArrayList<>(Arrays.asList(item.getListAuthority().split(",")));
        List<GroupAuthority> groupAuthorities = new ArrayList<>();
        if (authorities.size() > 0) {
            authorities.stream().forEach(au -> groupAuthorities.add(new GroupAuthority(groupId, Long.valueOf(au), username, new Date())));
            if (item.getId() != null) {
                deleteGroupAuthority(item.getId()).orElse(false);
            }
            addListGroupAuthority(groupAuthorities).orElse(false);
        }
    }

   
}
