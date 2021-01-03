package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.VtInPhieuThu;
import java.util.Optional;

public interface InPhieuThuDAO {
    Optional<PagingResult> search(VtInPhieuThu item, PagingResult page);
    Boolean add(VtInPhieuThu vtInPhieuThu, User user);
    Boolean delete(Integer id, User user, String ip);
    VtInPhieuThu findById(Integer id);
    Integer getMaxId();

}
