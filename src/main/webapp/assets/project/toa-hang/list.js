app.controller('vantai', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', function ($scope, $http, $filter, $window, fileUpload, $timeout, $q) {

        $scope.search = {basic: 0, receiptCode: "", truckPartner: "", loaiXe: "", bienSo: "", fromDelivery: "", toDelivery: "", fromReceive: "", toReceive: ""};
        var search = JSON.stringify($scope.search);
        $scope.listData = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.checkAll_ = false;

        $scope.clear = function () {
            $scope.search.basic = 0;
            $scope.search.receiptCode = "";
            $scope.search.truckPartner = "";
            $scope.search.loaiXe = "";
            $scope.search.bienSo = "";
            $scope.search.fromDelivery = "";
            $scope.search.toDelivery = "";
            $scope.search.fromReceive = "";
            $scope.search.toReceive = "";
            $("#fromDelivery").data("DateTimePicker").date(null);
            $("#toDelivery").data("DateTimePicker").date(null);
            $("#fromReceive").data("DateTimePicker").date(null);
            $("#toReceive").data("DateTimePicker").date(null);
            $timeout(function () {
                $('#fromDelivery').data("DateTimePicker").maxDate(moment("01/01/9999999999", "DD-MM-YYYY").toDate());
                $('#toDelivery').data("DateTimePicker").minDate(moment("01/01/100", "DD-MM-YYYY").toDate());
                $('#fromReceive').data("DateTimePicker").maxDate(moment("01/01/9999999999", "DD-MM-YYYY").toDate());
                $('#toReceive').data("DateTimePicker").minDate(moment("01/01/100", "DD-MM-YYYY").toDate());
            }, 0);
        };


        $http.get(preUrl + "/toa-hang/load-list", {params: {search: search, offset: 0, number: $scope.listData.numberPerPage}})
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
            $http.get(preUrl + "/toa-hang/load-list", {params: {search: search, offset: 0, number: $scope.listData.numberPerPage}})
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
            $http.get(preUrl + "/toa-hang/load-list", {params: {search: search, offset: $scope.listData.numberPerPage * ($scope.listData.pageNumber - 1), number: $scope.listData.numberPerPage}})
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
            window.location.href = preUrl + "/toa-hang/export?fullName=" + $scope.search.fullName + "&dispatchCode=" + $scope.search.dispatchCode
                    + "&type=" + $scope.search.type
                    + "&fromDateSign=" + $scope.search.fromDateSign + "&toDateSign=" + $scope.search.toDateSign;
        };

        $scope.preXoa = function (item) {
            $scope.delId = item.id;
            $scope.receiptCode = item.receiptCode;
            $("#xoaPhieuNhan").modal("show");
        };

        $scope.xoaPhieuNhan = function () {
            var call = {id: $scope.delId};
            var vtGoodsReceiptBO = JSON.parse(JSON.stringify(call));
            $http.post(preUrl + "/toa-hang/delete", vtGoodsReceiptBO, {headers: {'Content-Type': 'application/json'}})
                    .then(function (response) {
                        switch (Number(response.data)) {
                            case 0:
                                $scope.loadListData();
                                $("#xoaPhieuNhan").modal("hide");
                                toastr.success("Xóa thành công!");
                                break;
                            case 1:
                                $("#xoaPhieuNhan").modal("hide");
                                toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
                                break;
                        }
                    });
        };



        $scope.delete = function (idAppoint) {
            $scope.idDelete = idAppoint;
            $("#confirm-delete").modal("show");
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
            $(".deletePhieuNhan").click(function () {
                alert("delete");
                var id = $(this).data('item.id');
                var name = $(this).data('item.name');
                console.log(id);
                console.log(name);
                $(".info_id").val(id);
                $(".modal-title").text('Xóa phiếu nhận "' + name + '"');
            });


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