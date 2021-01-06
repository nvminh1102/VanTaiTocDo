package com.osp.web.dao;

import com.osp.model.VtArea;
import java.util.List;

public interface AreaDAO {

    List<VtArea> getAllArea();
    VtArea getVtAreaById(Integer id);

}
