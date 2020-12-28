app.controller('vantai', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', 'popupBienNhan', function ($scope, $http, $filter, $window, fileUpload, $timeout, $q, popupBienNhan) {
        $scope.listBienNhanDaChon = {items: "", rowCount: 0, numberPerPage: 5, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.toaHang = {toaHangCode: "", noiDi: "", noiDen: "", bienSo: "", tenLaiXe: "", sdtLaiXe: "", nguoiNhan: "", noiNhan:""};
        $scope.numberPerPage = "5";
        $scope.listBienNhanDaChon.numberPerPage = $scope.numberPerPage;
        $scope.listBienNhanDaChon = popupBienNhan.getListDataBN();
        // load DL nhà xe
        $http.get(preUrl + "/getListPartner", {params: {typePartner: 4}})
                .then(function (response) {
                    $scope.vtPartners = response.data;
                });
        if (toaHangCode != null && toaHangCode != '') {
            $scope.toaHang.toaHangCode = toaHangCode;
        }

        if (id != null && id != '') {
            $http.get(preUrl + "/toa-hang/loadDataEdit", {params: {id: id}})
                    .then(function (response) {
                        $scope.toaHang = response.data.vtGoodsReceiptBO;
                        if (response.data.vtReceiptViews != "[]" && response.data.vtReceiptViews.length > 0) {
                            $scope.listBienNhanDaChon.items = response.data.vtReceiptViews;
                            $scope.listBienNhanDaChon.rowCount = response.data.vtReceiptViews.length;
                        }
                    });
        }

        $scope.saveToaHang = function () {
            if ($("#formAdd").parsley().validate()) {
                if (typeof $scope.toaHang != "undefined" && typeof $scope.toaHang.toaHangCode != 'undefined') {
                    console.log($scope.listBienNhanDaChon);
                    if (typeof $scope.listBienNhanDaChon != "undefined" && $scope.listBienNhanDaChon.items != "" && $scope.listBienNhanDaChon.items.length>0) {
                        if (id != null && id != '') {
                            $scope.toaHang.id = id;
                        }
                        $scope.call = {
                            vtGoodsReceiptBO: angular.copy($scope.toaHang),
                            vtReceiptViews: angular.copy($scope.listBienNhanDaChon.items)
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
                    toastr.error('Chưa nhập mã phiếu nhận hàng!');
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