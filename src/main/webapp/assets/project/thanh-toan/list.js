app.controller('vantai', ['$scope', '$http', 'fileUpload' , '$timeout', function ($scope, $http, fileUpload, $timeout) {
        $scope.fromDateReceipt = '';
        $scope.toDateReceipt = '';
        $scope.page = page;
        $scope.info = {id: '', tienDaTra: ''};
        $(document).ready(function () {
            $("#fromDateReceipt").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.fromDateReceipt = $(this).val();
                }
            });
            $("#toDateReceipt").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.toDateReceipt = $(this).val();
                }
            });
        });

        $http.get(preUrl + "/managerVanTai/thanh-toan/search")
                .then(function (response) {
                    if (response != null && response != 'undefined' && response.status == 200) {
                        $scope.page = response.data;
                        $scope.page.pageCount = getPageCount($scope.page);
                        $scope.page.pageList = getPageList($scope.page);
                        $scope.paramItems = response.data.items;
                    }
                });

        $scope.search = function () {
            $scope.page.pageNumber = 1;
            $http.get(preUrl + "/managerVanTai/thanh-toan/search", {params: {soPhieuNhan: $scope.search.soPhieuNhan, nguoiGui: $scope.search.nguoiGui, typePayment: $scope.search.typePayment, isPayment: $scope.search.isPayment, fromDateReceipt: $scope.search.fromDateReceipt, toDateReceipt: $scope.search.toDateReceipt}})
                    .then(function (response) {
                        if (response != null && response != 'undefined' && response.status == 200) {
                            $scope.page = response.data;
                            $scope.page.pageCount = getPageCount($scope.page);
                            $scope.page.pageList = getPageList($scope.page);
                            $scope.paramItems = response.data.items;
                        }
                    });
        };

        $scope.loadPage = function (pageNumber) {
            if (pageNumber >= 1) {
                $http.get(preUrl + "/managerVanTai/thanh-toan/search", {params: {p: pageNumber, numberPerPage: $scope.page.numberPerPage, soPhieuNhan: $scope.search.soPhieuNhan, nguoiGui: $scope.search.nguoiGui, typePayment: $scope.search.typePayment, isPayment: $scope.search.isPayment, fromDateReceipt: $scope.search.fromDateReceipt, toDateReceipt: $scope.search.toDateReceipt}})
                        .then(function (response) {
                            $scope.page = response.data;
                            $scope.page.pageList = getPageList($scope.page);
                            $scope.page.pageCount = getPageCount($scope.page);
                            $scope.paramItems = response.data.items;
                        });
            }
        };

        $scope.searchSelect = function (event) {
            $scope.page.pageNumber = 1;
            $scope.loadPage(1);
        };
        $scope.setNumberPerPage = function (numberPerPage) {
            $scope.page.numberPerPage = numberPerPage;
            $scope.searchSelect(null);
        };

        $scope.resetValue = function () {
            $scope.search.soPhieuNhan = '';
            $scope.search.nguoiGui = '';
            $scope.search.typePayment = '';
            $scope.search.isPayment = '';
            $scope.search.fromDateReceipt = '';
            $scope.search.toDateReceipt = '';
        };

        $scope.preThanhToan = function (item) {
            $scope.info.tienDaTra = "";
            $scope.info.id = item.id;
            $scope.info.soPhieuHang = item.receiptCode;
            $scope.tienPhaiTra = item.tongTien - item.tienDaTra;
            $scope.info.tienDaTra = item.tongTien - item.tienDaTra;
            $("#thanhToanModal").modal("show");
        };

        $scope.thanhToan = function () {
            $('#thanhToanModal').modal({backdrop: 'static', keyboard: false});
            if ($scope.info.tienDaTra == "") {
                toastr.error("Bạn chưa nhập số tiền trả!");
                document.getElementById("info.tienDaTra").focus();
                return false;
            }
            if ($scope.info.tienDaTra.toString().includes(",")) {
                $scope.info.tienDaTra = Number($scope.info.tienDaTra.toString().replace(/,/g, ""));
            } else {
                $scope.info.tienDaTra = Number($scope.info.tienDaTra);
            }

            if ($scope.info.tienDaTra > $scope.tienPhaiTra) {
                toastr.error("Số tiền trả đang lớn hơn số tiền còn nợ!");
                document.getElementById("info.tienDaTra").focus();
                return false;
            }

            var info = {
                bienNhan: $scope.info
            };

            var infoAuction = JSON.parse(JSON.stringify(info));
            $http.post(preUrl + "/managerVanTai/thanh-toan/update-tien-da-tra", infoAuction, {headers: {'Content-Type': 'application/json'}})
                    .then(function (response) {
                        switch (Number(response.data)) {
                            case 1:
                                toastr.success("Thanh toán thành công!");
                                $("#thanhToanModal").modal("hide");
                                $scope.loadPage(1);
                                break;
                            case 0:
                                toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
                                document.getElementById("btnSaveAuctionInfo").disabled = false;
                                break;
                        }
                        $timeout(function () {
                            $scope.searchSelect(null);
                        }, 1000);
                    });

        }

        $scope.checkShowThanhToan = function (item) {
            $http.get(preUrl + "/managerVanTai/thanh-toan/tong-tien-mat-hang", {params: {id: item.id}})
                    .then(function (response) {
                        $scope.tienPhaiTra = response.data;
                    });
        };
        $scope.search = {
            soPhieuNhan: '',
            nguoiGui: '',
            typePayment: '',
            isPayment: '',
            fromDateReceipt: '',
            toDateReceipt: ''
        };
        $scope.xuatPhieuThanhToan = function () {
            window.open(preUrl + "/managerVanTai/thanh-toan/exportExcelThanhToan?soPhieuNhan=" + $scope.search.soPhieuNhan +
                    "&nguoiGui=" + $scope.search.nguoiGui + "&typePayment=" + $scope.search.typePayment + "&isPayment=" + $scope.search.isPayment +
                    "&fromGenDate=" + $scope.search.fromDateReceipt + "&toGenDate=" + $scope.search.toDateReceipt, '_blank');
        }

    }]);