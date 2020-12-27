app.controller('vantai', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', function ($scope, $http, $filter, $window, fileUpload, $timeout, $q) {

        $scope.search = {receiptCode: "", nameStock: "", fromPushStock: "", toPushStock: ""};
        var search = JSON.stringify($scope.search);
        $scope.listData = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.checkAll_ = false;

        $scope.clear = function () {
            $scope.search.receiptCode = "";
            $scope.search.nameStock = "";
            $scope.search.fromPushStock = "";
            $scope.search.toPushStock = "";
            $("#fromPushStock").data("DateTimePicker").date(null);
            $("#toPushStock").data("DateTimePicker").date(null);
            $timeout(function () {
                $('#fromPushStock').data("DateTimePicker").maxDate(moment("01/01/9999999999", "DD-MM-YYYY").toDate());
                $('#toPushStock').data("DateTimePicker").minDate(moment("01/01/100", "DD-MM-YYYY").toDate());
            }, 0);
        };


        $http.get(preUrl + "/phieu-thu/load-list", {params: {search: search, offset: 0, number: $scope.listData.numberPerPage}})
                .then(function (response) {
                    $scope.listData = response.data;
                    $scope.listData.numberPerPage = $scope.numberPerPage;
                    $scope.listData.pageCount = getPageCount($scope.listData);
                    $scope.listData.pageList = getPageList($scope.listData);
                    $scope.tooltip();
                });


        $scope.searchSelect = function (event) {
            $scope._custum_searchSuccess = true;
            if (event != null) {
                var div = angular.element(event.currentTarget).parent();
                for (var i = 0; i < $(div)[0].childNodes.length; i++) {
                    if ($(div)[0].childNodes[i].nodeName == "IMG") {
                        $($(div)[0].childNodes[i]).removeClass("hidden");
                        break;
                    }
                }
            }
            $scope.search.basic = 1;
            $scope.listData.pageNumber = 1;
            $scope.loadListData();
        };
        $scope.setNumberPerPage = function (numberPerPage) {
            $scope.listData.numberPerPage = numberPerPage;
            $scope.searchSelect(null);
        };
        //reload list
        $scope.loadListData = function () {
            var search = JSON.stringify($scope.search);
            $http.get(preUrl + "/phieu-thu/load-list", {params: {search: search, offset: 0, number: $scope.listData.numberPerPage}})
                    .then(function (response) {
                        $scope.listData = response.data;
                        $scope.listData.numberPerPage = $scope.numberPerPage;
                        $scope.listData.pageCount = getPageCount($scope.listData);
                        $scope.listData.pageList = getPageList($scope.listData);
                        $scope.tooltip();
                        $("._custum_searchSuccess").addClass("hidden");
                        $scope._custum_searchSuccess = false;
                    }, function (response) {
                        $("._custum_searchSuccess").addClass("hidden");
                        $scope._custum_searchSuccess = false;
                        toastr.error($("#_custum_error_500").html());
                    });
        };

        //reload page
        $scope.loadPageData = function (index) {
            var search = JSON.stringify($scope.search);
            $scope.listData.pageNumber = index;
            $http.get(preUrl + "/phieu-thu/load-list", {params: {search: search, offset: $scope.listData.numberPerPage * ($scope.listData.pageNumber - 1), number: $scope.listData.numberPerPage}})
                    .then(function (response) {
                        $scope.listData.items = response.data.items;
                        $scope.listData.numberPerPage = $scope.numberPerPage;
                        $scope.listData.pageList = getPageList($scope.listData);
                        $scope.tooltip();
                    }, function (response) {
                        /*call back*/
                        toastr.error($("#_custum_error_500").html());
                    });
        };
        $scope.export = function () {
            window.location.href = preUrl + "/phieu-nhan-hang/export?fullName=" + $scope.search.fullName + "&dispatchCode=" + $scope.search.dispatchCode
                    + "&type=" + $scope.search.type
                    + "&fromDateSign=" + $scope.search.fromDateSign + "&toDateSign=" + $scope.search.toDateSign;
        };

        $scope.delete = function (idAppoint) {
            $scope.idDelete = idAppoint;
            $("#confirm-delete").modal("show");
        };

        $scope.confirmDelete = function () {

            $http.post(preUrl + "/xoa-thong-tin-bo-nhiem-ccv", $scope.idDelete, {headers: {'Content-Type': 'application/json'}})
                    .then(function (response) {
                        if (response.data.reponseCode == 200 && response.data.success == true) {
                            toastr.success("Xóa thành công!");
                        } else {
                            toastr.error("Có lỗi trong quá trình xử lý, Vui lòng thử lại sau.");
                        }
                        $scope.searchSelect(null);
                        $("#confirm-delete").modal("hide");
                    },
                            function (response) {
                                $("#confirm-delete").modal("hide");
                                $("#confirm-error").modal("show");
                            });

        };

        //chi tiết thông tin bổ nhiệm, từ chối bổ nhiệm
        $scope.detail_ = function (idAppoint) {
            $scope.idAppoint = idAppoint;
            $("#chi-tiet-bo-nhiem").modal("show");

            $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchBoNhiem", {params: {idAppoint: $scope.idAppoint}})
                    .then(function (response) {
                        $scope.notaryInfo = response.data.notaryInfo;
                        $scope.appoint = response.data.appoint;
                        $scope.document = response.data.document;
                    });

        };


        $scope.tuchoibonhiem = function (idNotaryInfo) {
            $scope.idNotary = idNotaryInfo;
            $("#tu-choi-bo-nhiem-ccv").modal("show");
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

        /*load tooltip*/
        $scope.tooltip = function () {
            var defer = $q.defer();
            $timeout(function () {
                $("[data-toggle=popover]").popover();
                defer.resolve();
            }, 1000);
        };

        $(document).ready(function () {
            $("#fromDelivery").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.fromDelivery = $(this).val();
                    if ($('#fromDelivery').val() != "")
                        $('#toDelivery').data("DateTimePicker").minDate(moment($('#fromDelivery').val(), "DD-MM-YYYY").toDate());
                }
            });
            $("#toDelivery").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.toDelivery = $(this).val();
                    if ($('#toDelivery').val() != "")
                        $('#fromDelivery').data("DateTimePicker").maxDate(moment($('#toDelivery').val(), "DD-MM-YYYY").toDate());
                }
            });
            $("#fromReceive").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.fromReceive = $(this).val();
                    if ($('#fromReceive').val() != "")
                        $('#toReceive').data("DateTimePicker").minDate(moment($('#fromReceive').val(), "DD-MM-YYYY").toDate());
                }
            });
            $("#toReceive").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.toReceive = $(this).val();
                    if ($('#toReceive').val() != "")
                        $('#fromReceive').data("DateTimePicker").maxDate(moment($('#toReceive').val(), "DD-MM-YYYY").toDate());
                }
            });
        });

    }]);