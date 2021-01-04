app.controller('frameworkCtrl', ['$scope', '$http', 'fileUpload', function ($scope, $http, fileUpload) {
      $scope.page = page;
      // $scope.search.typePartner = '';
      $http.get(preUrl + "/manager/nha-xe/search")
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
        $http.get(preUrl + "/manager/nha-xe/search", {params: {nhaXe: $scope.search.nhaXe, loaiXe: $scope.search.loaiXe, bienSo: $scope.search.bienSo, tenLaiXe: $scope.search.tenLaiXe}})
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
          $http.get(preUrl + "/manager/nha-xe/search", {params: {p: pageNumber, numberPerPage: $scope.page.numberPerPage, nhaXe: $scope.search.nhaXe, loaiXe: $scope.search.loaiXe, bienSo: $scope.search.bienSo, tenLaiXe: $scope.search.tenLaiXe}})
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
    $scope.search.nhaXe = '';
    $scope.search.loaiXe = '';
    $scope.search.bienSo = '';
    $scope.search.tenLaiXe = '';

  };

  $scope.preXoa = function (item) {
    $scope.delId = item.id;
    $scope.delNhaXe = item.nhaXe;
    $("#xoaNhaXe").modal("show");
  };

  $scope.xoaNhaXe = function () {
    var call = {id: $scope.delId};
    var param = JSON.parse(JSON.stringify(call));
    $http.post(preUrl + "/manager/nha-xe/delete", param, {headers: {'Content-Type': 'application/json'}})
    .then(function (response) {
      switch (Number(response.data)) {
        case 0:
          $scope.search();
          $("#xoaNhaXe").modal("hide");
          toastr.success("Xóa thành công!");
          break;
        case 1:
          $("#xoaNhaXe").modal("hide");
          toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
          break;
      }
    });
  };

  $scope.resetValueAdd = function () {
    $scope.info.id = '';
    $scope.info.nhaXe = '';
    $scope.info.loaiXe = '';
    $scope.info.bienSo = '';
    $scope.info.tenLaiXe = '';
    $scope.info.sdtLaiXe = '';
  };

  $scope.preAddNhaXe = function () {
    $scope.info = {
      id: '',
      nhaXe: '',
      loaiXe: '',
      bienSo: '',
      tenLaiXe: '',
      sdtLaiXe: ''
    };
    $scope.titleAddOrEdit = "Thêm mới nha xe";
    $("#addOrEditNhaXe").modal("show");
  };

  $scope.addNhaXe = function () {
    $('#addOrEditNhaXe').modal({backdrop: 'static', keyboard: false});
    if ($scope.info.nhaXe == "") {
      toastr.error("Tên nhà xe không được để trống!");
      document.getElementById("info.nhaXe").focus();
      return false;
    }
    if ($scope.info.loaiXe == "") {
      toastr.error("Loại xe không được để trống!");
      document.getElementById("info.loaiXe").focus();
      return false;
    }
    if ($scope.info.bienSo == "") {
      toastr.error("Biển số xe không được để trống!");
      document.getElementById("info.bienSo").focus();
      return false;
    }

    var call = {id:  $scope.info.id, nhaXe: $scope.info.nhaXe, loaiXe: $scope.info.loaiXe, bienSo: $scope.info.bienSo, tenLaiXe: $scope.info.tenLaiXe, sdtLaiXe: $scope.info.sdtLaiXe};
    var param = JSON.parse(JSON.stringify(call));
    $http.post(preUrl + "/manager/nha-xe/them-moi-nha-xe", param, {headers: {'Content-Type': 'application/json'}})
    .then(function (response) {
      switch (Number(response.data)) {
        case 0:
          if ($scope.info.id != null && $scope.info.id != "") {
            $scope.search();
            toastr.success("Sửa thành công!");
            $("#addOrEditNhaXe").modal("hide");
          } else {
            $scope.search();
            $("#addOrEditNhaXe").modal("hide");
            toastr.success("Thêm mới thành công!");
          }
          break;
        case 1:
          $("#addOrEditNhaXe").modal("hide");
          toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
          break;
      }
    });
  };

    $scope.preEdit = function (item) {
      $scope.info = {
        id: item.id,
        nhaXe: item.nhaXe,
        loaiXe: item.loaiXe,
        bienSo: item.bienSo,
        tenLaiXe: item.tenLaiXe,
        sdtLaiXe: item.sdtLaiXe
      };
      $scope.titleAddOrEdit = "Chỉnh sửa nhà xe";
      $("#addOrEditNhaXe").modal("show");
    }

    }]);