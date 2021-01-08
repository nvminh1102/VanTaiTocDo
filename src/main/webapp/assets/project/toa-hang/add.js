app.controller('vantai', ['$scope', '$http', '$timeout', '$q', function ($scope, $http, $timeout, $q) {
        $scope.listBienNhanDaChon = [];
        $scope.listHangHoaDaChon = [];
        $scope.toaHang = {toaHangCode: "", noiDi: "", noiDen: "", bienSo: "", tenLaiXe: "", sdtLaiXe: "", nguoiNhan: "", noiNhan: ""};

        $http.get(preUrl + "/managerVanTai/bienNhan/danhSachNhaXe")
                .then(function (response) {
                    $scope.nhaXeList = response.data;
                });

        $scope.onChangeBienSo = function () {
            $http.get(preUrl + "/managerVanTai/bienNhan/thongTinNhaXe", {params: {bienSo: $scope.toaHang.bienSo}})
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
        var listIdvtReceiptDangTrenToa = [];
        if (id != null && id != '') {
            $http.get(preUrl + "/managerVanTai/toa-hang/loadDataEdit", {params: {id: id}})
                    .then(function (response) {
                        $scope.toaHang = response.data.vtToaHang;
                        if (response.data.vtReceiptViews != "[]" && response.data.vtReceiptViews.length > 0) {
                            $scope.listBienNhanDaChon = response.data.vtReceiptViews;
                            if (typeof $scope.listBienNhanDaChon !== "undefined" && $scope.listBienNhanDaChon.length > 0) {
                                for (var j = 0; j < $scope.listBienNhanDaChon.length; j++) {
                                    listIdvtReceiptDangTrenToa.push($scope.listBienNhanDaChon[j].id);
                                }
                            }
                        }
                        if (response.data.vtReceiptDetail != "[]" && response.data.vtReceiptDetail.length > 0) {
                            $scope.listHangHoaDaChon = response.data.vtReceiptDetail;
                        }
                        if ($scope.toaHang.bienSo != null && $scope.toaHang.bienSo != '') {
                            $timeout(function () {
                                $("#idBienSo").select2("val", $scope.toaHang.bienSo);
                            }, 0);
                        }
                    });
        }

        $scope.boChonBienNhan = function (item) {
            var idRemove = item.id;
            var list_ = [];
            for (var i = 0; i < $scope.listBienNhanDaChon.length; i++) {
                if ($scope.listBienNhanDaChon[i].id != idRemove) {
                    list_.push($scope.listBienNhanDaChon[i]);
                }
            }
            $scope.listBienNhanDaChon = list_;

            var list2_ = [];
            for (var i = 0; i < $scope.listHangHoaDaChon.length; i++) {
                if ($scope.listHangHoaDaChon[i].receiptId != idRemove) {
                    list2_.push($scope.listHangHoaDaChon[i]);
                }
            }
            $scope.listHangHoaDaChon = list2_;

            if (typeof $scope.listBienNhan !== "undefined" && $scope.listBienNhan.length > 0) {
                console.log("$scope.listBienNhan.length:" + $scope.listBienNhan.length);
                for (var i = 0; i < $scope.listBienNhan.length; i++) {
                    var boolenCheck = true;
                    for (var j = 0; j < $scope.listBienNhanDaChon.length; j++) {
                        if ($scope.listBienNhan[i].id === $scope.listBienNhanDaChon[j].id) {
                            console.log("Phieu check:" + i);
                            $scope.checked[i] = true;
                            boolenCheck = false;
                            break;
                        }
                    }
                    if (boolenCheck) {
                        $scope.checked[i] = false;
                    }
                }
            }

            if (typeof $scope.listHangHoa !== "undefined" && $scope.listHangHoa.length > 0) {
                console.log("$scope.listHangHoa.length:" + $scope.listHangHoa.length);
                for (var i = 0; i < $scope.listHangHoa.length; i++) {
                    var boolenCheck = true;
                    for (var j = 0; j < $scope.listHangHoaDaChon.length; j++) {
                        if ($scope.listHangHoa[i].id === $scope.listHangHoaDaChon[j].id) {
                            console.log("HH check:" + i);
                            $scope.checkedHH[i] = true;
                            boolenCheck = false;
                            break;
                        }
                    }
                    if (boolenCheck) {
                        $scope.checkedHH[i] = false;
                    }
                }
            }

        }

        $scope.boChonHangHoa = function (item) {
            var idRemove = item.id;
            var list_ = [];
            for (var i = 0; i < $scope.listHangHoaDaChon.length; i++) {
                if ($scope.listHangHoaDaChon[i].id != idRemove) {
                    list_.push($scope.listHangHoaDaChon[i]);
                }
            }
            $scope.listHangHoaDaChon = list_;

            if (typeof $scope.listHangHoa !== "undefined" && $scope.listHangHoa.length > 0) {
                console.log("$scope.listHangHoa.length:" + $scope.listHangHoa.length);
                for (var i = 0; i < $scope.listHangHoa.length; i++) {
                    var boolenCheck = true;
                    for (var j = 0; j < $scope.listHangHoaDaChon.length; j++) {
                        if ($scope.listHangHoa[i].id === $scope.listHangHoaDaChon[j].id) {
                            console.log("HH check:" + i);
                            $scope.checkedHH[i] = true;
                            boolenCheck = false;
                            break;
                        }
                    }
                    if (boolenCheck) {
                        $scope.checkedHH[i] = false;
                    }
                }
            }

        }


        $scope.saveToaHang = function () {
            if ($("#formAdd").parsley().validate()) {
                if (typeof $scope.toaHang != "undefined" && typeof $scope.toaHang.toaHangCode != 'undefined') {
                    console.log($scope.listBienNhanDaChon);
                    console.log($scope.listHangHoaDaChon);
                    if (typeof $scope.listBienNhanDaChon != "undefined" && $scope.listBienNhanDaChon.length > 0
                            && typeof $scope.listHangHoaDaChon != "undefined" && $scope.listHangHoaDaChon.length > 0) {
                        if (id != null && id != '') {
                            $scope.toaHang.id = id;
                        }
                        $scope.call = {
                            vtToaHang: angular.copy($scope.toaHang),
                            vtReceiptViews: angular.copy($scope.listBienNhanDaChon),
                            vtReceiptDetail: angular.copy($scope.listHangHoaDaChon)
                        };
                        var vTGoodsReceiptForm = JSON.stringify($scope.call);
                        console.log(vTGoodsReceiptForm);
                        $http.post(preUrl + "/managerVanTai/toa-hang/add", vTGoodsReceiptForm, {headers: {'Content-Type': 'application/json'}})
                                .then(function (response) {
                                    if (response.data.reponseCode == 200 && response.data.success == true) {
                                        toastr.success(response.data.messageError);
                                        window.location.href = preUrl + "/managerVanTai/toa-hang/list";
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


// popup

        $scope.searchBienNhan = {receiptCode: "", fromDeceipt: "", toDeceipt: "", nhaXe: "", nameStock: "", status: "1"};
        $scope.listBienNhan = [];
        $scope.listHangHoa = [];

        $scope.checked = [];
        $scope.checkedHH = [];
        $scope.checkAll = false;
        $scope.checkAllHH = false;

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

        /*reload list*/
        $scope.loadListData = function () {
            $scope.searchBienNhan.status = "1";
            var searchBienNhan = JSON.stringify($scope.searchBienNhan);
            $http.get(preUrl + "/managerVanTai/bienNhan/list-bien-nhan", {params: {searchBienNhan: searchBienNhan}})
                    .then(function (response) {
                        $scope.listBienNhan = response.data.items;
                        $timeout(function () {
                            if (typeof $scope.listBienNhanDaChon !== "undefined" && $scope.listBienNhanDaChon.length > 0) {
                                if (typeof $scope.listBienNhan !== "undefined" && $scope.listBienNhan.length > 0) {
                                    var checkDulicate = false;
                                    console.log("$scope.listBienNhanDaChon.length:" + $scope.listBienNhanDaChon.length);
                                    for (var j = 0; j < $scope.listBienNhanDaChon.length; j++) {
                                        checkDulicate = false;
                                        for (var i = 0; i < response.data.items.length; i++) {
                                            if (response.data.items[i].id === $scope.listBienNhanDaChon[j].id) {
                                                checkDulicate = true;
                                                break;
                                            }
                                        }
                                        if (!checkDulicate) {
                                            $scope.listBienNhan.push($scope.listBienNhanDaChon[j]);
                                        }
                                    }
                                } else {
                                    $scope.listBienNhan = $scope.listBienNhanDaChon;
                                }
                                if (typeof $scope.listBienNhan !== "undefined" && $scope.listBienNhan.length > 0) {
                                    console.log("$scope.listBienNhan.length:" + $scope.listBienNhan.length);
                                    for (var i = 0; i < $scope.listBienNhan.length; i++) {
                                        for (var j = 0; j < $scope.listBienNhanDaChon.length; j++) {
                                            if ($scope.listBienNhan[i].id === $scope.listBienNhanDaChon[j].id) {
                                                console.log("Phieu check:" + i);
                                                $scope.checked[i] = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (typeof $scope.listHangHoaDaChon !== "undefined" && $scope.listHangHoaDaChon.length > 0) {
                                if (typeof $scope.listHangHoa !== "undefined" && $scope.listHangHoa.length > 0) {
                                    var checkDulicate = false;
                                    console.log("$scope.listHangHoaDaChon.length:" + $scope.listHangHoaDaChon.length);
                                    for (var j = 0; j < $scope.listHangHoaDaChon.length; j++) {
                                        checkDulicate = false;
                                        for (var i = 0; i < response.data.items.length; i++) {
                                            if (response.data.items[i].id === $scope.listHangHoaDaChon[j].id) {
                                                checkDulicate = true;
                                                break;
                                            }
                                        }
                                        if (!checkDulicate) {
                                            $scope.listHangHoa.push($scope.listHangHoaDaChon[j]);
                                        }
                                    }
                                } else {
                                    $scope.listHangHoa = $scope.listHangHoaDaChon;
                                }

                                if (typeof $scope.listHangHoa !== "undefined" && $scope.listHangHoa.length > 0) {
                                    console.log("$scope.listHangHoa.length:" + $scope.listHangHoa.length);
                                    for (var i = 0; i < $scope.listHangHoa.length; i++) {
                                        for (var j = 0; j < $scope.listHangHoaDaChon.length; j++) {
                                            if ($scope.listHangHoa[i].id === $scope.listHangHoaDaChon[j].id) {
                                                console.log("HH check:" + i);
                                                $scope.checkedHH[i] = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }, 100);

                        $scope.tooltip();
                    });
        };


        $scope.loadListData();


//        $scope.addListBienNhan = function () {
//            $scope.listBienNhanDaChon = $scope.selectedItems;
//            $scope.listHangHoaDaChon = $scope.selectedItemsHH;
//        };


        $scope.chooseBienNhan = function (objectBienNhan, check) {
            if (check == true) {
//                $scope.idCallBackManage = id;
                // check add luon, validateDL sau
                var status = 1;// mặc định lấy các mặt hàng chưa lên toa.
                if (listIdvtReceiptDangTrenToa.length > 0) {
                    for (var i = 0; i < listIdvtReceiptDangTrenToa.length; i++) {
                        if (listIdvtReceiptDangTrenToa[i] === objectBienNhan.id) {
                            status = 2;// Trường hợp edit và id chọn = id
                            break;
                        }
                    }
                }
                $http.get(preUrl + "/managerVanTai/bienNhan/loadListHangHoa", {params: {id: [objectBienNhan.id], status: status}})
                        .then(function (response) {
                            console.log("response.data:");
                            console.log(response.data);
                            if (response.data !== "undefined" && response.data !== "[]") {
                                for (var i = 0; i < response.data.length; i++) {
                                    $scope.listHangHoa.push(response.data[i]);
                                }
                                $scope.listHangHoaDaChon = [];
                                for (var i = 0; i < $scope.listHangHoa.length; i++) {
                                    $scope.listHangHoaDaChon.push($scope.listHangHoa[i]);
                                    $scope.checkedHH[i] = true;
                                }
                            }
                        });

                $scope.listBienNhanDaChon.push(objectBienNhan);
            } else {
                var idRemove = objectBienNhan.id;
                var list_ = [];
                for (var i = 0; i < $scope.listBienNhanDaChon.length; i++) {
                    if ($scope.listBienNhanDaChon[i].id != idRemove) {
                        list_.push($scope.listBienNhanDaChon[i]);
                    }
                }
                $scope.listBienNhanDaChon = list_;

                var list2_ = [];
                for (var i = 0; i < $scope.listHangHoa.length; i++) {
                    if ($scope.listHangHoa[i].receiptId != idRemove) {
                        list2_.push($scope.listHangHoa[i]);
                    }
                }
                $scope.listHangHoa = list2_;
            }
            if ($('.onChangeBNSelectBox_:checked').length == $('.onChangeBNSelectBox_').length) {
                $scope.checkAll = true;
                $("#select_allBN").prop('checked', true);
            } else {
                $scope.checkAll = false;
                $("#select_allBN").prop('checked', false);
            }
        };

        $scope.chonHH = function (hanghoa, check) {
            if (check == true) {
                $scope.listHangHoaDaChon.push(hanghoa);
            } else {
                var idRemove = hanghoa.id;
                var list_ = [];
                for (var i = 0; i < $scope.listHangHoaDaChon.length; i++) {
                    if ($scope.listHangHoaDaChon[i].id != idRemove) {
                        list_.push($scope.listHangHoaDaChon[i]);
                    }
                }
                $scope.listHangHoaDaChon = list_;
            }
            if ($('.onChangeHHSelectBox_:checked').length == $('.onChangeHHSelectBox_').length) {
                $scope.checkAll = true;
                $("#select_allHH").prop('checked', true);
            } else {
                $scope.checkAll = false;
                $("#select_allHH").prop('checked', false);
            }
        };


        $scope.selectAll = function (checkAll) {
            $scope.listBienNhanDaChon = [];
            if (checkAll) {
                var listId = [];
                for (var i = 0; i < $scope.listBienNhan.length; i++) {
                    $scope.listBienNhanDaChon.push($scope.listBienNhan[i]);
                    $scope.checked[i] = true;
                    listId.push($scope.listBienNhan[i].id)
                }
                $scope.listHangHoaDaChon = [];
                $scope.listHangHoa = [];
                $http.get(preUrl + "/managerVanTai/bienNhan/loadListHangHoa", {params: {id: listId, status: 2}})
                        .then(function (response) {
                            console.log("response.data:");
                            console.log(response.data);
                            if (response.data !== "undefined" && response.data !== "[]") {
                                for (var i = 0; i < response.data.length; i++) {
                                    $scope.listHangHoa.push(response.data[i]);
                                }
                                $scope.listHangHoaDaChon = [];
                                for (var i = 0; i < $scope.listHangHoa.length; i++) {
                                    $scope.listHangHoaDaChon.push($scope.listHangHoa[i]);
                                    $scope.checkedHH[i] = true;
                                }
                            }
                        });
            } else {
                for (var i = 0; i < $scope.listBienNhan.length; i++) {
                    $scope.checked[i] = false;
                }
                $scope.listHangHoaDaChon = [];
                $scope.listHangHoa = [];
            }
        };

        $scope.selectAllHH = function (checkAllHH) {
            $scope.listHangHoaDaChon = [];
            if (checkAllHH) {
                for (var i = 0; i < $scope.listHangHoa.length; i++) {
                    $scope.listHangHoaDaChon.push($scope.listHangHoa[i]);
                    $scope.checkedHH[i] = true;
                }
            } else {
                for (var i = 0; i < $scope.listHangHoa.length; i++) {
                    $scope.checkedHH[i] = false;
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


    }]);