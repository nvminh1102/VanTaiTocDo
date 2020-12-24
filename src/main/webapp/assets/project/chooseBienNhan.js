app.controller('vantai', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', function ($scope, $http, $filter, $window, fileUpload, $timeout, $q) {
        $scope.search = {basic: "", receiptCode: "", fromDeceipt: "", toDeceipt: "", nhaXe: "", nameStock: ""};
        var search = JSON.stringify($scope.search);

        $scope.listData = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};

        $scope.idSelected = "";
        $scope.checkLoadData = false;
        $scope.numberPerPage = "5";
        $scope.listData.numberPerPage = $scope.numberPerPage;

        $scope.checkAll = false;
        $scope.selectedItems = [];

// init
        $http.get(preUrl + "/them-moi-thong-tin-bo-nhiem-ccv/search", {params: {search: search, offset: 0, number: $scope.listData.numberPerPage}})
                .then(function (response) {
                    $scope.listData = response.data;
                    $scope.listData.numberPerPage = $scope.numberPerPage;
                    $scope.listData.pageCount = getPageCount($scope.listData);
                    $scope.listData.pageList = getPageList($scope.listData);
                    $scope.tooltip();
                });

        $scope.searchData = function () {
            $scope.search.basic = 1;
            $scope.listData.pageNumber = 1;
            $scope.loadListData();
        };

        /*reload list*/
        $scope.loadListData = function () {
            var search = JSON.stringify($scope.search);
            $http.get(preUrl + "/them-moi-thong-tin-bo-nhiem-ccv/search", {params: {search: search, offset: 0, number: $scope.listData.numberPerPage}})
                    .then(function (response) {
                        $scope.listData = response.data;

                        $scope.listData.numberPerPage = $scope.numberPerPage;
                        $scope.listData.pageCount = getPageCount($scope.listData);
                        $scope.listData.pageList = getPageList($scope.listData);
                        $scope.tooltip();
                    });
        };

        /*reload page*/
        $scope.loadPageData = function (index) {
            var search = JSON.stringify($scope.search);
            $scope.listData.pageNumber = index;
            $http.get(preUrl + "/them-moi-thong-tin-bo-nhiem-ccv/search", {params: {search: search, offset: $scope.listData.numberPerPage * ($scope.listData.pageNumber - 1), number: $scope.listData.numberPerPage}})
                    .then(function (response) {
                        $scope.listData.items = response.data.items;
                        $scope.listData.numberPerPage = $scope.numberPerPage;
                        $scope.listData.pageList = getPageList($scope.listData);
                        $scope.tooltip();
                    });
        };

        $scope.setNumberPerPage = function (numberPerPage) {
            $scope.listData.numberPerPage = numberPerPage;
            $scope.searchData();
        };

        $scope.chooseBienNhan = function (id, check) {
            if (check == true) {
//                $scope.idCallBackManage = id;
                /* check add luon, validateDL sau
                 $http.get(preUrl + "/quan-ly-dang-ki-hhcc-va-cap-the-ccv/checkNotaryManage", {params: {idNotary: id}})
                 .then(function (response) {
                 if (response.data.success == true) {
                 $scope.thongBao = response.data.messageError;
                 $("#popup-message").modal("show");
                 }
                 $scope.selectedItems.push($scope.idCallBackManage);
                 });
                 */
                $scope.selectedItems.push(id);
            } else {
                var idRemove = id;
                var list_ = [];
                for (var i = 0; i < $scope.selectedItems.length; i++) {
                    if ($scope.selectedItems[i] != idRemove) {
                        list_.push($scope.selectedItems[i]);
                    }
                }
                $scope.selectedItems = list_;
            }
            if ($('.onChangeSelectBox_:checked').length == $('.onChangeSelectBox_').length) {
                $scope.checkAll = true;
                $("#select_all").prop('checked', true);
            } else {
                $scope.checkAll = false;
                $("#select_all").prop('checked', false);
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

        $(document).ready(function () {
            $("#fromDeceipt").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.fromDeceipt = $(this).val();
                    $scope.search.fromDeceiptStr = stringToDate($scope.search.fromDeceipt, "dd-MM-yyyy", "-");
                    if ($('#fromDeceipt').val() != "")
                        $('#toDeceipt').data("DateTimePicker").minDate(moment($('#fromDeceipt').val(), "DD-MM-YYYY").toDate());
                }
            });
            $("#toDeceipt").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.toDeceipt = $(this).val();
                    $scope.search.toDeceiptStr = stringToDate($scope.search.toDeceipt, "dd-MM-yyyy", "-");
                    if ($('#toDeceipt').val() != "")
                        $('#fromDeceipt').data("DateTimePicker").maxDate()(moment($('#toDeceipt').val(), "DD-MM-YYYY").toDate());
                }
            });
        });
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