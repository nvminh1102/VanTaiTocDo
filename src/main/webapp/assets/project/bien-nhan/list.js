app.controller('frameworkCtrl', ['$scope', '$http', 'fileUpload', function ($scope, $http, fileUpload) {
      $scope.fromDateReceipt = '';
      $scope.toDateReceipt = '';
      $scope.page = page;
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

      $http.get(preUrl + "/managerVanTai/bienNhan/search")
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
        $http.get(preUrl + "/managerVanTai/bienNhan/search", {params: {receiptCode: $scope.search.receiptCode, nameStock: $scope.search.nameStock, fromDateReceipt: $scope.search.fromDateReceipt, toDateReceipt: $scope.search.toDateReceipt}})
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
          $http.get(preUrl + "/managerVanTai/bienNhan/search", {params: {p: pageNumber, numberPerPage: $scope.page.numberPerPage, receiptCode: $scope.search.receiptCode, nameStock: $scope.search.nameStock, fromDateReceipt: $scope.search.fromDateReceipt, toDateReceipt: $scope.search.toDateReceipt}})
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
    $scope.search.receiptCode = '';
    $scope.search.nameStock = '';
    $scope.search.fromDateReceipt = '';
    $scope.search.toDateReceipt = '';
  };

  $scope.preXoa = function (item) {
    $scope.delId = item.id;
    $scope.delReceiptCode = item.receiptCode;
    $("#xoaBienNhan").modal("show");
  };

  $scope.xoaBienNhan = function () {
    var call = {id: $scope.delId};
    var param = JSON.parse(JSON.stringify(call));
    $http.post(preUrl + "/managerVanTai/bienNhan/delete", param, {headers: {'Content-Type': 'application/json'}})
    .then(function (response) {
      switch (Number(response.data)) {
        case 0:
          $scope.search();
          $("#xoaBienNhan").modal("hide");
          toastr.success("Xóa thành công!");
          break;
        case 1:
          $("#xoaBienNhan").modal("hide");
          toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
          break;
      }
    });
  };

    $scope.preEdit = function (id) {
        $('#editId').val(id);
        $('#frmEdit').submit();
    }

    $scope.exportPhieuNhanHang = function (giaoHangId) {
        window.open(preUrl+"/managerVanTai/bienNhan/exportExcelPhieuNhan?giaoHangId=" + giaoHangId , '_blank');
    }
    
    $scope.exportPhieuThu = function (giaoHangId) {
        console.log("exportPhieuThu:" + giaoHangId);
        window.open(preUrl+"/managerVanTai/bienNhan/exportPhieuThu?giaoHangId=" + giaoHangId , '_blank');
    }


    }]);