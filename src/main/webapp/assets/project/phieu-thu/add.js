app.controller('vantai', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', 'popupBienNhan', function ($scope, $http, $filter, $window, fileUpload, $timeout, $q, popupBienNhan) {
        $scope.listBienNhanDaChon = {items: "", rowCount: 0, numberPerPage: 5, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.phieuThu = {receiptCode: "", datePushStock: "", cmndNguoiGui: "", tenNguoiGui: "", sdtNguoiGui: "", diaChiGui: "", cmndNguoiNhan: "", tenNguoiNhan: "", sdtNguoiNhan: "", diaChiNhan: "", stockName: "", nguoiThanhToan: ""};
        $scope.numberPerPage = "5";
        $scope.listBienNhanDaChon.numberPerPage = $scope.numberPerPage;
        $scope.listBienNhanDaChon = popupBienNhan.getListDataBN();
        // load DL nhà xe
        if (receiptCode != null && receiptCode != '') {
            $scope.phieuThu.receiptCode = receiptCode;
        }

        if (id != null && id != '') {
            $http.get(preUrl + "/manager/phieu-thu/loadDataEdit", {params: {id: id}})
                    .then(function (response) {
                        $scope.phieuThu = response.data.vtPhieuThuView;
                        if (response.data.vtReceiptViews != "[]" && response.data.vtReceiptViews.length > 0) {
                            $scope.listBienNhanDaChon.items = response.data.vtReceiptViews;
                            $scope.listBienNhanDaChon.rowCount = response.data.vtReceiptViews.length;
                        }
                    });
        }

        $scope.savePhieuThu = function () {
            if ($("#formAdd").parsley().validate()) {
                if (typeof $scope.phieuThu != "undefined" && typeof $scope.phieuThu.receiptCode != 'undefined') {
                    if (typeof $scope.listBienNhanDaChon != "undefined" && $scope.listBienNhanDaChon.items != "" && $scope.listBienNhanDaChon.items.length>0) {
                        if (id != null && id != '') {
                            $scope.phieuThu.id = id;
                        }
                        $scope.call = {
                            vtPhieuThuView: angular.copy($scope.phieuThu),
                            vtReceiptViews: angular.copy($scope.listBienNhanDaChon.items)
                        };
                        var vTGoodsReceiptForm = JSON.stringify($scope.call);
                        console.log(vTGoodsReceiptForm);
                        $http.post(preUrl + "/manager/phieu-thu/add", vTGoodsReceiptForm, {headers: {'Content-Type': 'application/json'}})
                                .then(function (response) {
                                    if (response.data.reponseCode == 200 && response.data.success == true) {
                                        window.location.href = preUrl + "/phieu-thu/list";
                                    } else {
                                        toastr.success(response.data.messageError);
                                    }
                                }, function (response) {
                                    $("#confirm-error").modal("show");
                                }
                                );
                    } else {
                        toastr.success('Chưa chọn danh sách biên nhận!');
                    }
                } else {
                    toastr.success('Chưa nhập mã phiếu nhận hàng!');
                }
            }
        };
        $(document).ready(function () {
            console.log("vào")
            $("#datePushStock").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.phieuThu.strDatePushStock = $(this).val();
                }
            });
        });


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