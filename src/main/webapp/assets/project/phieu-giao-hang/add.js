app.controller('vantai', ['$scope', '$http', '$timeout', '$q', function ($scope, $http, $timeout, $q) {
        $scope.listBienNhanDaChon = {items: [], rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.phieuGiao = {maPhieuGiao: "", nhaXe: "", loaiXe: "", bienSo: "", tenLaiXe: "", sdtLaiXe: ""};

        $scope.searchBienNhan = {basic: "", receiptCode: "", fromDeceipt: "", toDeceipt: "", nhaXe: "", nameStock: "", status: "2"};
        $scope.listBienNhan = [];
        $scope.idSelected = "";
        $scope.checkLoadData = false;
        $scope.checked = [];
        $scope.checkAll = false;


        $scope.searchBienNhan.status = "2";
        var searchBienNhan = JSON.stringify($scope.searchBienNhan);
        $http.get(preUrl + "/managerVanTai/bienNhan/list-bien-nhan", {params: {searchBienNhan: searchBienNhan, offset: 0}})
                .then(function (response) {
                    $scope.listBienNhan = response.data.items;
                    $scope.tooltip();
                });

        $scope.clear = function () {
            $scope.searchBienNhan.receiptCode = "";
            $scope.searchBienNhan.fromDeceipt = "";
            $scope.searchBienNhan.toDeceipt = "";
            $scope.searchBienNhan.nhaXe = "";
            $scope.searchBienNhan.nameStock = "";
            $("#fromDeceipt").data("DateTimePicker").date(null);
            $("#toDeceipt").data("DateTimePicker").date(null);
            $timeout(function () {
                $('#fromDeceipt').data("DateTimePicker").maxDate(moment("01/01/9999999999", "DD-MM-YYYY").toDate());
                $('#toDeceipt').data("DateTimePicker").minDate(moment("01/01/100", "DD-MM-YYYY").toDate());
            }, 0);
        };

        $scope.loadListData = function () {
            $scope.searchBienNhan.status = "2";
            var searchBienNhan = JSON.stringify($scope.searchBienNhan);
            $http.get(preUrl + "/managerVanTai/bienNhan/list-bien-nhan", {params: {searchBienNhan: searchBienNhan}})
                    .then(function (response) {
                        $scope.listBienNhan = response.data.items;
                        $scope.tooltip();
                    });
        };
        
        $scope.exportPhieuThu = function (idPhieuThu) {
            window.open(preUrl + "/managerVanTai/phieu-giao-hang/exportPhieuThu?idPhieuThu=" + idPhieuThu, '_blank');
        }

        $scope.chooseBienNhan = function (objectBienNhan, check) {
            if (check == true) {
                $scope.listBienNhanDaChon.items.push(objectBienNhan);
            } else {
                var idRemove = objectBienNhan.id;
                var list_ = [];
                for (var i = 0; i < $scope.listBienNhanDaChon.items.length; i++) {
                    console.log($scope.listBienNhanDaChon.items.length);
                    if ($scope.listBienNhanDaChon.items[i].id != idRemove) {
                        console.log("add các id:" + $scope.listBienNhanDaChon.items[i]);
                        list_.push($scope.listBienNhanDaChon.items[i]);
                    }
                    console.log("i:" + i + " - " + $scope.listBienNhanDaChon.items[i])
                }
                $scope.listBienNhanDaChon.items = list_;
            }
            if ($('.onChangeBNSelectBox_:checked').length == $('.onChangeBNSelectBox_').length) {
                $scope.checkAll = true;
                $("#select_allBN").prop('checked', true);
            } else {
                $scope.checkAll = false;
                $("#select_allBN").prop('checked', false);
            }
        };

        $scope.selectAll = function (checkAll) {
            $scope.listBienNhanDaChon.items = [];
            if (checkAll) {
                for (var i = 0; i < $scope.listBienNhan.length; i++) {
                    $scope.listBienNhanDaChon.items.push($scope.listBienNhan[i]);
                    $scope.checked[i] = true;
                }
            } else {
                for (var i = 0; i < $scope.listBienNhan.length; i++) {
                    $scope.checked[i] = false;
                }
            }
        };

        $http.get(preUrl + "/managerVanTai/bienNhan/danhSachNhaXe")
                .then(function (response) {
                    $scope.nhaXeList = response.data;
                });

        $scope.onChangeBienSo = function () {
            $http.get(preUrl + "/managerVanTai/bienNhan/thongTinNhaXe", {params: {bienSo: $scope.phieuGiao.bienSo}})
                    .then(function (response) {
                        $scope.phieuGiao.nhaXe = response.data.nhaXe;
                        $scope.phieuGiao.loaiXe = response.data.loaiXe;
                        $scope.phieuGiao.tenLaiXe = response.data.tenLaiXe;
                        $scope.phieuGiao.sdtLaiXe = response.data.sdtLaiXe;
                    });
        }

        if (maPhieuGiao != null && maPhieuGiao != '') {
            $scope.phieuGiao.maPhieuGiao = maPhieuGiao;
        }

        if (id != null && id != '') {
            $http.get(preUrl + "/managerVanTai/phieu-giao-hang/loadDataEdit", {params: {id: id}})
                    .then(function (response) {
                        $scope.phieuGiao = response.data.vtPhieuGiaoHang;
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
        }

        $scope.savePhieu = function () {
            if ($("#formAdd").parsley().validate()) {
                if (typeof $scope.phieuGiao != "undefined" && typeof $scope.phieuGiao.maPhieuGiao != 'undefined') {
                    console.log($scope.listBienNhanDaChon);
                    if (typeof $scope.listBienNhanDaChon != "undefined" && typeof $scope.listBienNhanDaChon.items != "undefined" && $scope.listBienNhanDaChon.items.length > 0) {
                        if (id != null && id != '') {
                            $scope.phieuGiao.id = id;
                        }
                        $scope.call = {
                            vtPhieuGiaoHang: angular.copy($scope.phieuGiao),
                            vtReceiptViews: angular.copy($scope.listBienNhanDaChon.items),
                        };
                        var vTGoodsReceiptForm = JSON.stringify($scope.call);
                        console.log(vTGoodsReceiptForm);
                        $http.post(preUrl + "/managerVanTai/phieu-giao-hang/add", vTGoodsReceiptForm, {headers: {'Content-Type': 'application/json'}})
                                .then(function (response) {
                                    if (response.data.reponseCode == 200 && response.data.success == true) {
                                        toastr.success(response.data.messageError);
                                        $timeout(function () {
                                            window.location.href = preUrl + "/managerVanTai/phieu-giao-hang/list";
                                        }, 2000);
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
                    toastr.error('Chưa nhập mã phiếu giao hàng!');
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
        
        $(document).ready(function () {
            $("#fromDeceipt").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.searchBienNhan.fromDeceipt = $(this).val();
                    $scope.searchBienNhan.fromDeceiptStr = stringToDate($scope.searchBienNhan.fromDeceipt, "dd-MM-yyyy", "-");
                    if ($('#fromDeceipt').val() != "")
                        $('#toDeceipt').data("DateTimePicker").minDate(moment($('#fromDeceipt').val(), "DD-MM-YYYY").toDate());
                }
            });
            $("#toDeceipt").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.searchBienNhan.toDeceipt = $(this).val();
                    $scope.searchBienNhan.toDeceiptStr = stringToDate($scope.searchBienNhan.toDeceipt, "dd-MM-yyyy", "-");
                    if ($('#toDeceipt').val() != "")
                        $('#fromDeceipt').data("DateTimePicker").maxDate(moment($('#toDeceipt').val(), "DD-MM-YYYY").toDate());
                }
            });
        });

    }]);