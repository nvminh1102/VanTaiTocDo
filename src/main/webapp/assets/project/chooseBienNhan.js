app.controller('popupPhieuNhan', ['$scope', '$http', '$timeout', '$q', 'popupPhieuNhanHang', function ($scope, $http, $timeout, $q, popupPhieuNhanHang) {
        $scope.searchBienNhan = {basic: "", receiptCode: "", fromDeceipt: "", toDeceipt: "", nhaXe: "", nameStock: "", status: "1"};
        $scope.listBienNhan = [];
        $scope.listHangHoa = [];

        $scope.idSelected = "";
        $scope.checkLoadData = false;
        $scope.numberPerPage = "5";
        $scope.checked = [];
        $scope.checkedHH = [];
        $scope.checkAll = false;
        $scope.checkAllHH = false;

        $scope.selectedItems = [];
        $scope.selectedItemsHH = [];

        $scope.searchBienNhan.status = "1";
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

        /*reload list*/
        $scope.loadListData = function () {
            $scope.searchBienNhan.status = "1";
            var searchBienNhan = JSON.stringify($scope.searchBienNhan);
            $http.get(preUrl + "/managerVanTai/bienNhan/list-bien-nhan", {params: {searchBienNhan: searchBienNhan}})
                    .then(function (response) {
                        $scope.listBienNhan = response.data.items;
                        $scope.tooltip();
                    });
        };

        $scope.addListBienNhan = function () {
            popupPhieuNhanHang.setListDataBN($scope.selectedItems);
            popupPhieuNhanHang.setListDataHH($scope.selectedItemsHH);
//            console.log($scope.listBienNhanDaChon.items.length);
//            popupPhieuNhanHang.setListDataHH($scope.selectedItemsHH);
        };


        $scope.chooseBienNhan = function (objectBienNhan, check) {
            if (check == true) {
//                $scope.idCallBackManage = id;
                // check add luon, validateDL sau
                
                $http.get(preUrl + "/managerVanTai/bienNhan/loadListHangHoa", {params: {id: [objectBienNhan.id]}})
                        .then(function (response) {
                            console.log("response.data:");
                            console.log(response.data);
                            if (response.data !== "undefined" && response.data !== "[]") {
                                for (var i = 0; i < response.data.length; i++) {
                                    $scope.listHangHoa.push(response.data[i]);
                                }
                                $scope.selectedItemsHH = [];
                                for (var i = 0; i < $scope.listHangHoa.length; i++) {
                                    $scope.selectedItemsHH.push($scope.listHangHoa[i]);
                                    $scope.checkedHH[i] = true;
                                }
//                                $(".onChangeHHSelectBox_").prop('checked', true);
//                                $('.onChangeHHSelectBox_').attr('checked',true);
//                                $('.onChangeHHSelectBox_').prop("checked", "checked");
//                                $('.onChangeHHSelectBox_').checked = true;
                            }
                        });

                $scope.selectedItems.push(objectBienNhan);
            } else {
                var idRemove = objectBienNhan.id;
                var list_ = [];
                for (var i = 0; i < $scope.selectedItems.length; i++) {
                    if ($scope.selectedItems[i].id != idRemove) {
                        list_.push($scope.selectedItems[i]);
                    }
                }
                $scope.selectedItems = list_;

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
                $scope.selectedItemsHH.push(hanghoa);
            } else {
                var idRemove = hanghoa.id;
                var list_ = [];
                for (var i = 0; i < $scope.selectedItemsHH.length; i++) {
                    if ($scope.selectedItemsHH[i].id != idRemove) {
                        list_.push($scope.selectedItemsHH[i]);
                    }
                }
                $scope.selectedItemsHH = list_;
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
            $scope.selectedItems = [];
            if (checkAll) {
                var listId = [];
                for (var i = 0; i < $scope.listBienNhan.length; i++) {
                    $scope.selectedItems.push($scope.listBienNhan[i]);
                    $scope.checked[i] = true;
                    listId.push($scope.listBienNhan[i].id)
                }
                $scope.selectedItemsHH = [];
                $scope.listHangHoa = [];
                $http.get(preUrl + "/managerVanTai/bienNhan/loadListHangHoa", {params: {id: listId}})
                        .then(function (response) {
                            console.log("response.data:");
                            console.log(response.data);
                            if (response.data !== "undefined" && response.data !== "[]") {
                                for (var i = 0; i < response.data.length; i++) {
                                    $scope.listHangHoa.push(response.data[i]);
                                }
                                $scope.selectedItemsHH = [];
                                for (var i = 0; i < $scope.listHangHoa.length; i++) {
                                    $scope.selectedItemsHH.push($scope.listHangHoa[i]);
                                    $scope.checkedHH[i] = true;
                                }
                            }
                        });
            } else {
                for (var i = 0; i < $scope.listBienNhan.length; i++) {
                    $scope.checked[i] = false;
                }
                $scope.selectedItemsHH = [];
                $scope.listHangHoa = [];
            }
        };

        $scope.selectAllHH = function (checkAllHH) {
            $scope.selectedItemsHH = [];
            if (checkAllHH) {
                for (var i = 0; i < $scope.listHangHoa.length; i++) {
                    $scope.selectedItemsHH.push($scope.listHangHoa[i]);
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