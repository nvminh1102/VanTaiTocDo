app.controller('vantai', ['$scope', '$http', '$timeout', '$q', function ($scope, $http, $timeout, $q) {

        $scope.thongKeGiaoNhanViews = [];
        $scope.thongKeGiaoNhanViewNgay = {};
        $scope.thongKeGiaoNhanViewMonth = {};
        $scope.thongKeGiaoNhanViewYear = {};

        $http.get(preUrl + "/managerVanTai/thong-ke/thongKeChiTiet")
                .then(function (response) {
                    $scope.thongKeGiaoNhanViews = response.data.thongKeGiaoNhanViews;
                    $scope.thongKeGiaoNhanViewNgay = response.data.thongKeGiaoNhanViewNgay;
                    $scope.thongKeGiaoNhanViewMonth = response.data.thongKeGiaoNhanViewMonth;
                    $scope.thongKeGiaoNhanViewYear = response.data.thongKeGiaoNhanViewYear;
                    $scope.tooltip();
                });

        /*load tooltip*/
        $scope.tooltip = function () {
            var defer = $q.defer();
            $timeout(function () {
                $("[data-toggle=popover]").popover();
                defer.resolve();
            }, 1000);
        };

    }]);