app.controller('vantai', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', 'popupBienNhan', function ($scope, $http, $filter, $window, fileUpload, $timeout, $q, popupBienNhan) {
        console.log("1121212121");
        $scope.listBienNhanDaChon = {items: "", rowCount: 0, numberPerPage: 5, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.numberPerPage = "5";
        $scope.listBienNhanDaChon.numberPerPage = $scope.numberPerPage;
        $scope.listBienNhanDaChon = popupBienNhan.getListDataBN();
        // load DL nhà xe
        $http.get(preUrl + "/getListPartner", {params: {typePartner: 4}})
                .then(function (response) {
                    $scope.vtPartners = response.data;
                });

        if (id != null && id != '') {
            $http.get(preUrl + "/phieu-nhan-hang/loadDataEdit", {params: {id: id}})
                    .then(function (response) {
                        $scope.phieuNhan = response.vtGoodsReceipt;
                        $scope.listBienNhanDaChon = response.vtGoodsReceiptDetail;
                    });
        }

        $scope.savePhieuNhan = function () {

            console.log("id1:" + id);
            if ($("#formAdd").parsley().validate()) {
                if (typeof $scope.phieuNhan != "undefined" && typeof $scope.phieuNhan.receiptCode != 'undefined') {
                    if (typeof $scope.listBienNhanDaChon != "undefined") {
                        $scope.call = {
                            VtGoodsReceipt: angular.copy($scope.phieuNhan),
                            vtGoodsReceiptDetail: angular.copy($scope.listBienNhanDaChon)
                        };
                        var call_ = JSON.stringify($scope.call);
                        $http.post(preUrl + "/phieu-nhan-hang/add", call_, {headers: {'Content-Type': 'application/json'}})
                                .then(function (response) {
                                    if (response.data.reponseCode == 200 && response.data.success == true) {
                                        window.location.href = preUrl + "/quan-ly-bo-nhiem-ccv?status=1";
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
            $("#dateReceive").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.phieuNhan.dateReceive = $(this).val();
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