app.controller('vantai', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', 'popupBienNhan', function ($scope , $http, $filter, $window, fileUpload, $timeout, $q, popupBienNhan) {
        $scope.searchBienNhan = {basic: "", receiptCode: "", fromDeceipt: "", toDeceipt: "", nhaXe: "", nameStock: ""};
        var searchBienNhan = JSON.stringify($scope.searchBienNhan);
        $scope.listBienNhan = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};

        $scope.idSelected = "";
        $scope.checkLoadData = false;
        $scope.numberPerPage = "5";
        $scope.listBienNhan.numberPerPage = $scope.numberPerPage;

        $scope.checked = [];
        $scope.checkAll = false;
        $scope.selectedItems = [];




// init
//        $http.get(preUrl + "/bienNhan/list-bien-nhan", {params: {searchBienNhan: searchBienNhan, offset: 0, number: $scope.listBienNhan.numberPerPage}})
//                .then(function (response) {
//                    $scope.listBienNhan = response.data;
//                    $scope.listBienNhan.numberPerPage = $scope.numberPerPage;
//                    $scope.listBienNhan.pageCount = getPageCount($scope.listBienNhan);
//                    $scope.listBienNhan.pageList = getPageList($scope.listBienNhan);
//                    $scope.tooltip();
//                });

        $scope.searchData = function () {
            $scope.searchBienNhan.basic = 1;
            $scope.listBienNhan.pageNumber = 1;
            $scope.loadListData();
        };

        /*reload list*/
        $scope.loadListData = function () {
            var searchBienNhan = JSON.stringify($scope.searchBienNhan);
            $http.get(preUrl + "/bienNhan/list-bien-nhan", {params: {searchBienNhan: searchBienNhan, offset: 0, number: $scope.listBienNhan.numberPerPage}})
                    .then(function (response) {
                        $scope.listBienNhan = response.data;

                        $scope.listBienNhan.numberPerPage = $scope.numberPerPage;
                        $scope.listBienNhan.pageCount = getPageCount($scope.listBienNhan);
                        $scope.listBienNhan.pageList = getPageList($scope.listBienNhan);
                        $scope.tooltip();
                    });
        };

        /*reload page*/
        $scope.loadPageData = function (index) {
            var searchBienNhan = JSON.stringify($scope.searchBienNhan);
            $scope.listBienNhan.pageNumber = index;
            $http.get(preUrl + "/bienNhan/list-bien-nhan", {params: {searchBienNhan: searchBienNhan, offset: $scope.listBienNhan.numberPerPage * ($scope.listBienNhan.pageNumber - 1), number: $scope.listBienNhan.numberPerPage}})
                    .then(function (response) {
                        $scope.listBienNhan.items = response.data.items;
                        $scope.listBienNhan.numberPerPage = $scope.numberPerPage;
                        $scope.listBienNhan.pageList = getPageList($scope.listBienNhan);
                        $scope.tooltip();
                    });
        };

        $scope.setNumberPerPage = function (numberPerPage) {
            $scope.listBienNhan.numberPerPage = numberPerPage;
            $scope.searchData();
        };
        
        
        $scope.addListBienNhan = function () {
            alert($scope.selectedItems.length);
            popupBienNhan.setListDataBN($scope.selectedItems);
        };
        
        
       
        
        

        $scope.chooseBienNhan = function (objectBienNhan, check) {
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
            }
            if ($('.onChangeSelectBox_:checked').length == $('.onChangeSelectBox_').length) {
                $scope.checkAll = true;
                $("#select_all").prop('checked', true);
            } else {
                $scope.checkAll = false;
                $("#select_all").prop('checked', false);
            }
        };


        $scope.selectAll = function (checkAll) {
            $scope.selectedItems = [];
            if (checkAll) {
                for (var i = 0; i < $scope.listBienNhan.items.length; i++) {
                    $scope.selectedItems.push($scope.listBienNhan.items[i]);
                    $scope.checked[i] = true;
                }
            } else {
                for (var i = 0; i < $scope.listBienNhan.items.length; i++) {
                    $scope.checked[i] = false;
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
                        $('#fromDeceipt').data("DateTimePicker").maxDate()(moment($('#toDeceipt').val(), "DD-MM-YYYY").toDate());
                }
            });
        });
    }]);