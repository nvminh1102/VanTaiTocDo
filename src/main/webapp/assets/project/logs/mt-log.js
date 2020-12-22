/**
 * Created by Admin on 12/22/2017.
 */
app.controller('frameworkCtrl', ['$scope', '$http', function ($scope, $http) {
    $scope.fromGenDate = '';
    $scope.toGenDate = '';
    // $scope.msisdnId = '';
    // $scope.processResult = '';
    $scope.page = page;
    $(document).ready(function () {
        $("#fromGenDate").datetimepicker({
            locale: 'vi-VN',
            format: 'DD-MM-YYYY'
        }).on('dp.change', function (e) {
            if (e != null) {
                $scope.fromGenDate = $(this).val();
            }
        });
        $("#toGenDate").datetimepicker({
            locale: 'vi-VN',
            format: 'DD-MM-YYYY'
        }).on('dp.change', function (e) {
            if (e != null) {
                $scope.toGenDate = $(this).val();
            }
        });
    });

    $http.get(preUrl + "/logs/mtLog/search", {params: {msisdnId: $scope.msisdnId}})
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
        $http.get(preUrl + "/logs/mtLog/search", {params: {msisdnId: $scope.msisdnId, processResult: $scope.processResult, fromGenDate: $scope.fromGenDate, toGenDate: $scope.toGenDate}})
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
            $http.get(preUrl + "/logs/mtLog/search", {params: {p: pageNumber, msisdnId: $scope.msisdnId, processResult: $scope.processResult, fromGenDate: $scope.fromGenDate, toGenDate: $scope.toGenDate}})
                .then(function (response) {
                    $scope.page = response.data;
                    $scope.page.pageList = getPageList($scope.page);
                    $scope.page.pageCount = getPageCount($scope.page);
                    $scope.paramItems = response.data.items;
                });
        }
    };

    }]);