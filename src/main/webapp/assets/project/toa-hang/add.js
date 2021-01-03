app.controller('vantai', ['$scope', '$http', '$timeout', '$q', 'popupPhieuNhanHang', function ($scope, $http, $timeout, $q, popupPhieuNhanHang) {
        $scope.listBienNhanDaChon = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.listHangHoa = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.toaHang = {toaHangCode: "", noiDi: "", noiDen: "", bienSo: "", tenLaiXe: "", sdtLaiXe: "", nguoiNhan: "", noiNhan: ""};
        $scope.numberPerPage = "5";
        $scope.listBienNhanDaChon = popupPhieuNhanHang.getListDataBN();
        $scope.listHangHoa = popupPhieuNhanHang.getListDataHH();

        $http.get(preUrl + "/bienNhan/danhSachNhaXe")
                .then(function (response) {
                    $scope.nhaXeList = response.data;
                });

        $scope.onChangeBienSo = function () {
            $http.get(preUrl + "/bienNhan/thongTinNhaXe", {params: {bienSo: $scope.toaHang.bienSo}})
                    .then(function (response) {
                        $scope.toaHang.nhaXe = response.data.nhaXe;
                        $scope.toaHang.loaiXe = response.data.loaiXe;
                        $scope.toaHang.tenLaiXe = response.data.tenLaiXe;
                        $scope.toaHang.sdtLaiXe = response.data.sdtLaiXe;
                    });
        }

        if (toaHangCode != null && toaHangCode != '') {
            $scope.toaHang.toaHangCode = toaHangCode;
        }

        if (id != null && id != '') {
            $http.get(preUrl + "/toa-hang/loadDataEdit", {params: {id: id}})
                    .then(function (response) {
                        $scope.toaHang = response.data.vtToaHang;
                        if (response.data.vtReceiptViews != "[]" && response.data.vtReceiptViews.length > 0) {
                            $scope.listBienNhanDaChon.items = response.data.vtReceiptViews;
                        }
                    });
        }

        $scope.boChonBienNhan = function (item) {
            var idRemove = item.id;
            var list_ = [];
            for (var i = 0; i < $scope.listBienNhanDaChon.items.length; i++) {
                if ($scope.listBienNhanDaChon.items[i].id != idRemove) {
                    list_.push($scope.listBienNhanDaChon.items[i]);
                }
            }
            $scope.listBienNhanDaChon.items = list_;

            var list2_ = [];
            for (var i = 0; i < $scope.listHangHoa.items.length; i++) {
                if ($scope.listHangHoa.items[i].receiptId != idRemove) {
                    list2_.push($scope.listHangHoa.items[i]);
                }
            }
            $scope.listHangHoa.items = list2_;
        }
        $scope.saveToaHang = function () {
            if ($("#formAdd").parsley().validate()) {
                if (typeof $scope.toaHang != "undefined" && typeof $scope.toaHang.toaHangCode != 'undefined') {
                    console.log($scope.listBienNhanDaChon);
                    if (typeof $scope.listBienNhanDaChon != "undefined" && typeof $scope.listBienNhanDaChon.items != "undefined" && $scope.listBienNhanDaChon.items.length > 0
                            && typeof $scope.listHangHoa != "undefined" && typeof $scope.listHangHoa.items != "undefined" && $scope.listHangHoa.items.length > 0) {
                        if (id != null && id != '') {
                            $scope.toaHang.id = id;
                        }
                        $scope.call = {
                            vtToaHang: angular.copy($scope.toaHang),
                            vtReceiptViews: angular.copy($scope.listBienNhanDaChon.items),
                            vtReceiptDetail: angular.copy($scope.listHangHoa.items)
                        };
                        var vTGoodsReceiptForm = JSON.stringify($scope.call);
                        console.log(vTGoodsReceiptForm);
                        $http.post(preUrl + "/toa-hang/add", vTGoodsReceiptForm, {headers: {'Content-Type': 'application/json'}})
                                .then(function (response) {
                                    if (response.data.reponseCode == 200 && response.data.success == true) {
                                        toastr.success(response.data.messageError);
                                        window.location.href = preUrl + "/toa-hang/list";
                                    } else {
                                        toastr.success(response.data.messageError);
                                    }
                                }, function (response) {
                                    $("#confirm-error").modal("show");
                                }
                                );
                    } else {
                        toastr.error('Chưa chọn danh sách biên nhận!');
                    }
                } else {
                    toastr.error('Chưa nhập mã toa hàng!');
                }
            }
        };
        /*load tooltip*/
        $scope.tooltip = function () {
            var defer = $q.defer();
            $timeout(function () {
                $("[data-toggle=popover]").popover();
                defer.resolve();
            }, 1000);
        };



        $scope.removeIndexList = function (index, list) {
            var list_ = [];
            for (var i = 0; i < list.length; i++) {
                if (i != index) {
                    list_.push(list[i]);
                }
            }
            return list_;
        };
    }]);