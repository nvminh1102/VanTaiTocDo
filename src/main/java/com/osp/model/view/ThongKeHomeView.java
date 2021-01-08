/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.model.view;

import java.util.List;

/**
 *
 * @author Admin
 */
public class ThongKeHomeView {
   private List<ThongKeGiaoNhanView> thongKeGiaoNhanViews ;
   private ThongKeGiaoNhanView thongKeGiaoNhanViewNgay ;
   private ThongKeGiaoNhanView thongKeGiaoNhanViewMonth ;
   private ThongKeGiaoNhanView thongKeGiaoNhanViewYear ;

    public List<ThongKeGiaoNhanView> getThongKeGiaoNhanViews() {
        return thongKeGiaoNhanViews;
    }

    public void setThongKeGiaoNhanViews(List<ThongKeGiaoNhanView> thongKeGiaoNhanViews) {
        this.thongKeGiaoNhanViews = thongKeGiaoNhanViews;
    }

    public ThongKeGiaoNhanView getThongKeGiaoNhanViewNgay() {
        return thongKeGiaoNhanViewNgay;
    }

    public void setThongKeGiaoNhanViewNgay(ThongKeGiaoNhanView thongKeGiaoNhanViewNgay) {
        this.thongKeGiaoNhanViewNgay = thongKeGiaoNhanViewNgay;
    }

    public ThongKeGiaoNhanView getThongKeGiaoNhanViewMonth() {
        return thongKeGiaoNhanViewMonth;
    }

    public void setThongKeGiaoNhanViewMonth(ThongKeGiaoNhanView thongKeGiaoNhanViewMonth) {
        this.thongKeGiaoNhanViewMonth = thongKeGiaoNhanViewMonth;
    }

    public ThongKeGiaoNhanView getThongKeGiaoNhanViewYear() {
        return thongKeGiaoNhanViewYear;
    }

    public void setThongKeGiaoNhanViewYear(ThongKeGiaoNhanView thongKeGiaoNhanViewYear) {
        this.thongKeGiaoNhanViewYear = thongKeGiaoNhanViewYear;
    }
   
   
}
