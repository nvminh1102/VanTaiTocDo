package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.NhaXe;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value = "transactionManager")
public class NhaXeDAOImpl implements NhaXeDAO {
  @PersistenceContext(unitName = "appAdmin")
  @Qualifier(value = "transactionManager")
  private EntityManager entityManager;

  @Override
  public NhaXe add(NhaXe item) {
    try {
      entityManager.persist(item);
      entityManager.flush();
      return item;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public NhaXe getById(Integer id) {
    NhaXe info = new NhaXe();
    try {
      info = entityManager.find(NhaXe.class, id);
      return info;
    } catch (Exception e) {
      e.printStackTrace();
      return info;
    }
  }

  @Override
  public boolean edit(NhaXe item) {
    try {
      entityManager.merge(item);
      entityManager.flush();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public Optional<PagingResult> page(PagingResult page, String nhaXe, String loaiXe, String bienSo,
      String tenLaiXe) {
    try {
      int offset = 0;
      if (page.getPageNumber() > 0) {
        offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
      }
      StringBuffer strWhere = new StringBuffer();
      if (nhaXe != null && !"".equals(nhaXe)) {
        strWhere.append(" and UPPER(t.nha_xe) like :nhaXe");
      }
      if (loaiXe != null && !"".equals(loaiXe)) {
        strWhere.append(" and UPPER(t.loai_xe) like :loaiXe");
      }
      if (bienSo != null && !"".equals(bienSo)) {
        strWhere.append(" and UPPER(t.bien_so) like :bienSo");
      }
      if (tenLaiXe != null && !"".equals(tenLaiXe)) {
        strWhere.append(" and UPPER(t.ten_lai_xe) like :tenLaiXe");
      }

      StringBuffer sqlBuffer = new StringBuffer("SELECT t.ID,t.nha_xe,t.loai_xe,t.bien_so,t.ten_lai_xe,t.sdt_lai_xe,t.gen_date,t.created_by,t.last_update,t.updated_by "
          + "from vt_nha_xe t "
          + " where 1=1 ");
      sqlBuffer.append(strWhere.toString());
      sqlBuffer.append(" order by t.GEN_DATE DESC");

      StringBuffer sqlBufferCount = new StringBuffer("SELECT count(t.id) "
          + "from vt_nha_xe t "
          + " where 1=1 ");
      sqlBufferCount.append(strWhere.toString());

      Query query = entityManager.createNativeQuery(sqlBuffer.toString(), NhaXe.class);
      if (nhaXe != null && !"".equals(nhaXe)) {
        query.setParameter("nhaXe", "%" +nhaXe.trim().toUpperCase()+ "%");
      }
      if (loaiXe != null && !"".equals(loaiXe)) {
        query.setParameter("loaiXe", "%" +loaiXe.trim().toUpperCase()+ "%");
      }
      if (bienSo != null && !"".equals(bienSo)) {
        query.setParameter("bienSo", "%" +bienSo.trim().toUpperCase()+ "%");
      }
      if (tenLaiXe != null && !"".equals(tenLaiXe)) {
        query.setParameter("tenLaiXe", "%" +tenLaiXe.trim().toUpperCase()+ "%");
      }

      List<NhaXe> list = query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

      if (list != null && list.size() > 0) {
        page.setItems(list);
      }

      Query queryCount = entityManager.createNativeQuery(sqlBufferCount.toString());

      if (nhaXe != null && !"".equals(nhaXe)) {
        queryCount.setParameter("nhaXe", "%" +nhaXe.trim().toUpperCase()+ "%");
      }
      if (loaiXe != null && !"".equals(loaiXe)) {
        queryCount.setParameter("loaiXe", "%" +loaiXe.trim().toUpperCase()+ "%");
      }
      if (bienSo != null && !"".equals(bienSo)) {
        queryCount.setParameter("bienSo", "%" +bienSo.trim().toUpperCase()+ "%");
      }
      if (tenLaiXe != null && !"".equals(tenLaiXe)) {
        queryCount.setParameter("tenLaiXe", "%" +tenLaiXe.trim().toUpperCase()+ "%");
      }
      List resultList = queryCount.getResultList();
      if (resultList.size() > 0) {
        BigInteger count = (BigInteger) resultList.get(0);
        page.setRowCount(count.longValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.ofNullable(page);
  }

  @Override
  public boolean delete(int id) {
    try {
      String sql = "delete from vt_nha_xe where ID = :id";
      Query query = entityManager.createNativeQuery(sql).setParameter("id", id);
      query.executeUpdate();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public List<NhaXe> danhSachNhaXe() {
    List<NhaXe> lst = new ArrayList<>();
    try {
      StringBuffer sqlBuffer = new StringBuffer("SELECT a from NhaXe a");
      Query query = entityManager.createQuery(sqlBuffer.toString());
      lst = query.getResultList();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return lst;
  }

  @Override
  public NhaXe getByBienSo(String bienSo) {
    try {
      String sql = " select a from NhaXe a where a.bienSo=:bienSo ";
      Query query = entityManager.createQuery(sql, NhaXe.class);
      query.setParameter("bienSo", bienSo);
      List<Object> objects = query.getResultList();
      if(objects!=null && objects.size()>0){
        return (NhaXe) objects.get(0);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return null;
  }
}
