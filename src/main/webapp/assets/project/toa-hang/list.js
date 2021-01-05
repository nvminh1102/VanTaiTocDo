app.controller('vantai', ['$scope', '$http', '$timeout', '$q', function ($scope, $http, $timeout, $q) {

        $scope.search = {toaHangCode: "", bienSo: "", noiDen: "", noiDi: "", fromGenDate: "", toGenDate: ""};
        var search = JSON.stringify($scope.search);
        $scope.listData = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.checkAll_ = false;

        $scope.clear = function () {
            $scope.search.toaHangCode = "";
            $scope.search.bienSo = "";
            $scope.search.noiDen = "";
            $scope.search.noiDi = "";
            $scope.search.fromGenDate = "";
            $scope.search.toGenDate = "";
            $("#fromGenDate").data("DateTimePicker").date(null);
            $("#toGenDate").data("DateTimePicker").date(null);
            $timeout(function () {
                $('#fromGenDate').data("DateTimePicker").maxDate(moment("01/01/9999999999", "DD-MM-YYYY").toDate());
                $('#toGenDate').data("DateTimePicker").minDate(moment("01/01/100", "DD-MM-YYYY").toDate());
            }, 0);
        };

        $http.get(preUrl + "/manager/bienNhan/danhSachNhaXe")
                .then(function (response) {
                    $scope.nhaXeList = response.data;
                });


        $http.get(preUrl + "/manager/toa-hang/load-list", {params: {search: search, offset: 0, number: $scope.listData.numberPerPage}})
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
            $http.get(preUrl + "/manager/toa-hang/load-list", {params: {search: search, offset: 0, number: $scope.listData.numberPerPage}})
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
            $http.get(preUrl + "/manager/toa-hang/load-list", {params: {search: search, offset: $scope.listData.numberPerPage * ($scope.listData.pageNumber - 1), number: $scope.listData.numberPerPage}})
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
        $scope.exportPhieuBienNhan = function (toaHangId) {
            window.open(preUrl + "/manager/toa-hang/exportPhieuBienNhan?idToaHang=" + toaHangId, '_blank');
        }

        $scope.preXoa = function (item) {
            $scope.delId = item.id;
            $scope.toaHangCode = item.toaHangCode;
            $("#xoaToaHang").modal("show");
        };

        $scope.xoaToaHang = function () {
            var call = {id: $scope.delId};
            var vtToaHang = JSON.parse(JSON.stringify(call));
            $http.post(preUrl + "/manager/toa-hang/delete", vtToaHang, {headers: {'Content-Type': 'application/json'}})
                    .then(function (response) {
                        switch (Number(response.data)) {
                            case 0:
                                $scope.loadListData();
                                $("#xoaToaHang").modal("hide");
                                toastr.success("Xóa thành công!");
                                break;
                            case 1:
                                $("#xoaToaHang").modal("hide");
                                toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
                                break;
                        }
                    });
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
            $("#fromGenDate").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.fromGenDate = $(this).val();
                    if ($('#fromGenDate').val() != "")
                        $('#toGenDate').data("DateTimePicker").minDate(moment($('#fromGenDate').val(), "DD-MM-YYYY").toDate());
                }
            });
            $("#toGenDate").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.toGenDate = $(this).val();
                    if ($('#toGenDate').val() != "")
                        $('#fromGenDate').data("DateTimePicker").maxDate(moment($('#toGenDate').val(), "DD-MM-YYYY").toDate());
                }
            });
        });

    }]);