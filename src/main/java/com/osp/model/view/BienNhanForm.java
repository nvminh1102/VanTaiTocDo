package com.osp.model.view;
import com.osp.model.VtPartner;
import com.osp.model.VtReceipt;
import com.osp.model.VtReceiptDetail;
import java.util.List;

public class BienNhanForm {
  public VtReceipt bienNhan;
  public VtPartner nguoiGui;
  public VtPartner nguoiNhan;
  public List<VtReceiptDetail> matHang;

  public VtReceipt getBienNhan() {
    return bienNhan;
  }

  public void setBienNhan(VtReceipt bienNhan) {
    this.bienNhan = bienNhan;
  }

  public VtPartner getNguoiGui() {
    return nguoiGui;
  }

  public void setNguoiGui(VtPartner nguoiGui) {
    this.nguoiGui = nguoiGui;
  }

  public VtPartner getNguoiNhan() {
    return nguoiNhan;
  }

  public void setNguoiNhan(VtPartner nguoiNhan) {
    this.nguoiNhan = nguoiNhan;
  }

  public List<VtReceiptDetail> getMatHang() {
    return matHang;
  }

  public void setMatHang(List<VtReceiptDetail> matHang) {
    this.matHang = matHang;
  }
}
