app.controller('frameworkCtrl', ['$scope', '$http', 'fileUpload', function ($scope, $http, fileUpload) {
      $scope.page = page;
      // $scope.search.typePartner = '';
      $http.get(preUrl + "/bienNhan/khach-hang/search")
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
        $http.get(preUrl + "/bienNhan/khach-hang/search", {params: {taxCode: $scope.search.taxCode, fullName: $scope.search.fullName, mobile: $scope.search.mobile, address: $scope.search.address, typePartner: $scope.search.typePartner}})
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
          $http.get(preUrl + "/bienNhan/khach-hang/search", {params: {p: pageNumber, numberPerPage: $scope.page.numberPerPage, taxCode: $scope.search.taxCode, fullName: $scope.search.fullName, mobile: $scope.search.mobile, address: $scope.search.address, typePartner: $scope.search.typePartner}})
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
    $scope.search.taxCode = '';
    $scope.search.fullName = '';
    $scope.search.mobile = '';
    $scope.search.address = '';
    $scope.search.typePartner = '';

  };

  $scope.preXoa = function (item) {
    $scope.delId = item.id;
    $scope.delFullName = item.fullName;
    $("#xoaKhachHang").modal("show");
  };

  $scope.xoaKhachHang = function () {
    var call = {id: $scope.delId};
    var param = JSON.parse(JSON.stringify(call));
    $http.post(preUrl + "/bienNhan/khach-hang/delete", param, {headers: {'Content-Type': 'application/json'}})
    .then(function (response) {
      switch (Number(response.data)) {
        case 0:
          $scope.search();
          $("#xoaKhachHang").modal("hide");
          toastr.success("Xóa thành công!");
          break;
        case 1:
          $("#xoaKhachHang").modal("hide");
          toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
          break;
      }
    });
  };

  $scope.resetValueAdd = function () {
    $scope.info.id = '';
    $scope.info.taxCode = '';
    $scope.info.fullName = '';
    $scope.info.mobile = '';
    $scope.info.address = '';
    $scope.info.typePartner = 2;
    $scope.info.email = '';
  };

  $scope.preAddKhachHang = function () {
    $scope.info = {
      id: '',
      taxCode: '',
      fullName: '',
      mobile: '',
      address: '',
      typePartner: 2,
      email: ''
    };
    $scope.titleAddOrEdit = "Thêm mới khách hàng";
    $("#addOrEditKhachHang").modal("show");
  };

  $scope.addKhachHang = function () {
    $('#addOrEditKhachHang').modal({backdrop: 'static', keyboard: false});
    if ($scope.info.taxCode == "") {
      toastr.error("Số CMTND không được để trống!");
      document.getElementById("info.taxCode").focus();
      return false;
    }
    if ($scope.info.fullName == "") {
      toastr.error("Tên khách hàng không được để trống!");
      document.getElementById("info.fullName").focus();
      return false;
    }
    if ($scope.info.email != "" && !validateEmail($scope.info.email)) {
      document.getElementById("info.email").focus();
      toastr.error("Email nhập không đúng định dạng vui lòng nhập đúng (ví dụ: email@gmail.com)!");
      return false;
    }

    var call = {id:  $scope.info.id, taxCode: $scope.info.taxCode, fullName: $scope.info.fullName, mobile: $scope.info.mobile, address: $scope.info.address, typePartner: $scope.info.typePartner, email: $scope.info.email};
    var param = JSON.parse(JSON.stringify(call));
    $http.post(preUrl + "/bienNhan/khach-hang/them-moi-khach-hang", param, {headers: {'Content-Type': 'application/json'}})
    .then(function (response) {
      switch (Number(response.data)) {
        case 0:
          if ($scope.info.id != null && $scope.info.id != "") {
            $scope.search();
            toastr.success("Sửa thành công!");
            $("#addOrEditKhachHang").modal("hide");
          } else {
            $scope.search();
            $("#addOrEditKhachHang").modal("hide");
            toastr.success("Thêm mới thành công!");
          }
          break;
        case 1:
          $("#addOrEditKhachHang").modal("hide");
          toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
          break;
      }
    });
  };

    $scope.preEdit = function (item) {
      $scope.info = {
        id: item.id,
        taxCode: item.taxCode,
        fullName: item.fullName,
        mobile: item.mobile,
        address: item.address,
        typePartner: item.typePartner,
        email: item.email
      };
      $scope.titleAddOrEdit = "Chỉnh sửa khách hàng";
      $("#addOrEditKhachHang").modal("show");
    }

    }]);