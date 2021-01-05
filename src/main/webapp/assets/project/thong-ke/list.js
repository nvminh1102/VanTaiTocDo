app.controller('frameworkCtrl', ['$scope', '$http', 'fileUpload', function ($scope, $http, fileUpload) {
      $scope.fromDate = '';
      $scope.toDate = '';
      $scope.page = page;

      $(document).ready(function () {
        $("#fromDate").datetimepicker({
          locale: 'vi-VN',
          format: 'DD-MM-YYYY'
        }).on('dp.change', function (e) {
          if (e != null) {
            $scope.search.fromDate = $(this).val();
          }
        });
        $("#toDate").datetimepicker({
          locale: 'vi-VN',
          format: 'DD-MM-YYYY'
        }).on('dp.change', function (e) {
          if (e != null) {
            $scope.search.toDate = $(this).val();
          }
        });
      });

    $http.get(preUrl + "/managerVanTai/bienNhan/danhSachNhaXe")
        .then(function (response) {
            $scope.nhaXeList = response.data;
        });

      $http.get(preUrl + "/managerVanTai/thong-ke/search")
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
        $http.get(preUrl + "/managerVanTai/thong-ke/search", {params: {bienSo: $scope.search.bienSo, loaiXe: $scope.search.loaiXe, hinhThucVanChuyen: $scope.search.hinhThucVanChuyen, fromDate: $scope.search.fromDate, toDate: $scope.search.toDate}})
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
          $http.get(preUrl + "/managerVanTai/thong-ke/search", {params: {p: pageNumber, numberPerPage: $scope.page.numberPerPage, bienSo: $scope.search.bienSo, loaiXe: $scope.search.loaiXe, hinhThucVanChuyen: $scope.search.hinhThucVanChuyen, fromDate: $scope.search.fromDate, toDate: $scope.search.toDate}})
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
    $scope.search.bienSo = '';
    $scope.search.loaiXe = '';
    $scope.search.hinhThucVanChuyen = '';
    $scope.search.fromDate = '';
    $scope.search.toDate = '';
  };

    $scope.xuatPhieuThanhToan = function () {
        window.open(preUrl + "/managerVanTai/thanh-toan/exportExcelThanhToan?soPhieuNhan=" + $scope.search.soPhieuNhan +
            "&nguoiGui=" + $scope.search.nguoiGui + "&typePayment=" + $scope.search.typePayment + "&isPayment=" + $scope.search.isPayment +
            "&fromGenDate=" + $scope.search.fromDate + "&toGenDate=" + $scope.search.toDate, '_blank');
    }

}]);