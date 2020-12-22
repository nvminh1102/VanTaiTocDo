package com.osp.web.dao;

import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.model.User;
import java.util.ArrayList;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Admin on 12/26/2017.
 */
@Repository
//@Transactional
public class UserDAO extends EntityDAOImpl{

    private Logger logger = LogManager.getLogger(UserDAO.class);
    @PersistenceContext(unitName = "appAdmin")
//    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    LogAccessDAO logAccessDao;

//    @Transactional
    public Optional<PagingResult> pageUser(String username, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        Long count = (Long) entityManager.createQuery("select count(u.id) from User u where u.username like :username").setParameter("username", "%" + username + "%").getSingleResult();
        List<User> list = entityManager.createQuery("select u from User u where u.username like :username", User.class).setParameter("username", "%" + username + "%")
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null && count > 0) {
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }

    @Transactional(value = "transactionManager")
    public Optional<Boolean> addUser(User item, String ipClient) {
        try {
            item.setStatus(1);
            item.setGenDate(new Date());
            item.setLastUpdated(new Date());
            item.setPassword(encoder.encode(item.getPassword()));
            //insert log
            logAccessDao.addLog("Thêm người dùng " + item.getUsername(), Constants.Log.system, ipClient);
            entityManager.persist(item);
            entityManager.flush();
            return Optional.of(Boolean.valueOf(true));
        } catch (Exception e) {
            logger.error("Have error in UserDaoImpl.addUser: " + e.getMessage());
            return Optional.of(Boolean.valueOf(false));
        }
    }
    @Transactional(value = "transactionManager")
    public Optional<User> get(Long id) {
        User user = new User();
        try {
            user = entityManager.find(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error("Have error in UserDaoImpl.get: " + e.getMessage());
            return Optional.ofNullable(user);
        }

    }

    @Transactional(value = "transactionManager")
    public Optional<Boolean> editUserFromView(User item, String ipClient) {
        try {
            User itemDB = get(item.getId()).orElse(null);
            if (itemDB != null) {
                itemDB.setFullName(item.getFullName().trim());
                itemDB.setDescription(item.getDescription());
                //insert log
                logAccessDao.addLog("Sửa người dùng:" + item.getUsername(), Constants.Log.system, ipClient);
                entityManager.merge(item);
                entityManager.flush();
                return Optional.ofNullable(true);
            } else {
                return Optional.ofNullable(false);
            }

        } catch (Exception e) {
            logger.error("Have error in UserDaoImpl.editUser: " + e.getMessage());
            return Optional.ofNullable(false);
        }
    }

    public Optional<User> getByUsername(String username) {
        User user = null;
        try {
            user = entityManager.createQuery("SELECT u from User u where u.username like :username", User.class).setParameter("username", username).getSingleResult();
        } catch (Exception e) {
        }
        return Optional.ofNullable(user);
    }

    @Transactional(value = "transactionManager")
    public Optional<Integer> changeMyPass(String passwordCurrent, String passNew, User user, String ipClient) {
        PasswordEncoder passwordEnocder = new BCryptPasswordEncoder();
        if (!passwordEnocder.matches(passwordCurrent, user.getPassword())) {
            return Optional.of(2);
        }
        passNew = encoder.encode(passNew);
        user.setPassword(passNew);
        Query query = entityManager.createQuery("update User u set u.password=:pass where u.id=:id").setParameter("pass", passNew).setParameter("id", user.getId());
        query.executeUpdate();
        logAccessDao.addLog("Người dùng đổi password: " + user.getUsername(), Constants.Log.user, ipClient);
        return Optional.of(1);
    }

    @Transactional(value = "transactionManager")
    public Optional<Boolean> editUser(User item) {
        try {
            entityManager.merge(item);
            entityManager.flush();
            return Optional.ofNullable(true);
        } catch (Exception e) {
            logger.error("Have error in UserDaoImpl.editUser: " + e.getMessage());
            return Optional.ofNullable(false);
        }
    }

    @Transactional(value = "transactionManager")
    public boolean restorePassword(User userRs, String ipClient) {
        try {
            Query query = entityManager.createQuery("update User u set u.password=:pass where u.id=:id").setParameter("pass", encoder.encode("12345678")).setParameter("id", userRs.getId());
            query.executeUpdate();
            logAccessDao.addLog("Khôi phục mật khẩu tài khoản: " + userRs.getUsername(), Constants.Log.user, ipClient);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
