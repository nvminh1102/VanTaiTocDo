
app.controller('frameworkCtrl', ['$scope', '$http', 'fileUpload', function ($scope, $http, fileUpload) {
  $scope.info = {};
  $scope.info.property = [];
  $scope.titleModal = 'ADD';
  var arrIdDelete = [];
  var currentIndexProperty = -1;

  $scope.property = {id: null, name: '', unit: '', numbers: '', weight: '', sizes: '', cost: '', note: ''};

  $scope.onAddProperty = function () {
    $scope.titleModal = 'ADD';
    $scope.press = {word: ''};
    $scope.property = {id: null, name: '', unit: '', numbers: '', weight: '', sizes: '', cost: '', note: ''};
  };
  $scope.editProperty = function (index) {
    $scope.titleModal = 'EDIT';
    $scope.property = {id: null, name: '', unit: '', numbers: '', weight: '', sizes: '', cost: '', note: ''};
    $scope.property = angular.copy($scope.info.property[index]);
    currentIndexProperty = index;
    if ($scope.property.cost != null && $scope.property.cost > 0) {
      $scope.property.cost = formatNumber($scope.property.cost.toString());
    } else {
      $scope.property.cost = '';
    }
    $("#addPropertyInfo").modal('show');
  };

  $scope.deleteProperty = function (index) {
    arrIdDelete.push($scope.info.property[index].id);
    var items = $scope.info.property;
    $scope.info.property = items.slice(0, index).concat(items.slice(index + 1, items.length));
    toastr.success('Xóa thông tin thành công!');
  };

  $scope.saveProperty = function () {
    var check = $scope.validatePropertyForm();
    if (check) {
      if ($scope.property.cost.toString().includes(",")) {
        $scope.property.cost = Number($scope.property.cost.toString().replace(/,/g,""));
      } else {
        $scope.property.cost = Number($scope.property.cost);
      }
      if ($scope.titleModal == 'EDIT') {
        $scope.info.property[currentIndexProperty] = $scope.property;
        $('#addPropertyInfo').modal('hide');
        toastr.success('Cập nhật thông tin thành công!');
      } else if ($scope.titleModal == 'ADD'){
        $scope.info.property.push($scope.property);
        $('#addPropertyInfo').modal('hide');
      }
    }
  };

  $scope.validatePropertyForm = function () {

    if (textBoxNull("name")) {
      document.getElementById("name").focus();
      toastr.error('Bạn chưa nhập tên hàng hóa!');
      return false;
    }

    if (textBoxNull("unit")) {
      document.getElementById("unit").focus();
      toastr.error('Bạn chưa nhập đơn vị tính!');
      return false;
    }

    if (textBoxNull("numbers")) {
      document.getElementById("numbers").focus();
      toastr.error('Bạn chưa nhập số lượng!');
      return false;
    }
    return true;
  };

  function parsePropertyType() {
    var multi = $("#selectProperty").getKendoMultiSelect(),
        multiDataItems = multi.dataItems();
    var name = '';
    for (var i = 0; i < multiDataItems.length; i += 1) {
      var current = multiDataItems[i];
      if (i < multiDataItems.length - 1)
        name += current.name + ", ";
      else
        name += current.name;
    }
    return name;
  }

  $scope.saveAuctionInfo = function () {
    if ($('#receiptCode').val() == '') {
      toastr.error("Số biên nhận không được để trống!");
      document.getElementById("receiptCode").focus();
      return false;
    } else if ($('#nameStock').val() == '') {
      toastr.error("Kho nhận không được để trống!");
      document.getElementById("nameStock").focus();
      return false;
    } else if ($('#nguoiGui.taxCode').val() == '') {
      toastr.error("MST/CMND người gửi không được để trống!");
      document.getElementById("nguoiGui.taxCode").focus();
      return false;
    } else if ($('#nguoiGui.fullName').val() == '') {
      toastr.error("Tên người gửi không được để trống!");
      document.getElementById("nguoiGui.fullName").focus();
      return false;
    } else if ($('#nguoiNhan.taxCode').val() == '') {
      toastr.error("MST/CMND người nhận không được để trống!");
      document.getElementById("nguoiNhan.taxCode").focus();
      return false;
    } else if ($('#nguoiNhan.fullName').val() == '') {
      toastr.error("Tên người nhận không được để trống!");
      document.getElementById("nguoiNhan.fullName").focus();
      return false;
    }
    $scope.info.bienNhan.dateReceipt = stringToDate($("#dateReceipt").val(), "dd/mm/yyyy", "/");
    var info = {
      bienNhan: $scope.info.bienNhan,
      nguoiGui: $scope.info.nguoiGui,
      nguoiNhan: $scope.info.nguoiNhan,
      matHang: $scope.info.property,
      fileUploads: $scope.myFile.listFileDocument
    };

    if (arrIdDelete.length > 0)
      $http.get(preUrl + "/system/auction-info/deleteProperty", {params: {arrIdDelete: arrIdDelete, auctionInfoId: $scope.info.auction.auctionInfoId}})
      .then(function (response) {
        if (response.data == "0")
          toastr.error("Có lỗi trong quá trình xử lý, vui lòng thử lại!");
        return false;
      });

    var infoAuction = JSON.parse(JSON.stringify(info));
    $http.post(preUrl + "/bienNhan/them-moi-bien-nhan", infoAuction, {headers: {'Content-Type': 'application/json'}})
    .then(function (response) {
      $scope.info.bienNhan.dateReceipt = moment($scope.info.bienNhan.dateReceipt).format('DD/MM/YYYY');
      switch (Number(response.data)) {
        case 1:
          document.getElementById("btnSaveAuctionInfo").disabled = true;
          toastr.success("Thêm mới phiếu biên nhận thành công!");
          break;
        case 0:
          toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
          document.getElementById("btnSaveAuctionInfo").disabled = false;
          break;
      }
    });
  }

  $scope.document = {idFile: '', linkFile: '', fileName: ''};
  $scope.myFile = {fileDocument: "", listFileDocument: []};

  $scope.removeFileDocument = function (index) {
    if ($scope.myFile.listFileDocument.length >= 0) {
      $scope.myFile.listFileDocument = $scope.removeIndexList(index, $scope.myFile.listFileDocument);
    }
  };
  $scope.uploadFileDocument = function (event) {
    if (event.target.files[0] != undefined) {
      $scope.document = {idFile: '', linkFile: '', fileName: ''};
      var file = $scope.myFile.fileDocument;
      if (!validateFileUpload(file)) {
        return false;
      }
      var uploadUrl = preUrl + "/common/uploadFiles";
      fileUpload.uploadFileToUrl(file, uploadUrl).then(function (response) {
            if (response.data != null && response.data != undefined && response.status == 200) {
              $scope.document.idFile = response.data.idFile;
              $scope.document.linkFile = response.data.path;
              $scope.document.fileName = response.data.name;
              $scope.myFile.listFileDocument.push($scope.document);
              $scope.myFile.fileDocument = "";
              document.getElementById("fileDocument").value = "";
            } else {
              toastr.error("Có lỗi xảy ra, xin vui lòng thử lại!")
            }
          },
          function (response) {
            toastr.error("Có lỗi xảy ra, xin vui lòng thử lại!");
          }
      );
    } else {
      toastr.error("Có lỗi xảy ra, xin vui lòng thử lại!");
    }
  };


  $scope.savePropertyInfo = function () {
    var fileId = "";
    for (var i = 0; i < $scope.property.listFile.length; i++) {
      fileId += ";" + $scope.property.listFile[i].idFile;
    }
    document.getElementById("fileId").value = fileId;
  };
  $scope.downloadFileDocument = function (index) {
    // $scope.document = $scope.property.listFile[index];
    $scope.document = $scope.myFile.listFileDocument[index];
    var form = '';
    form += '<input type="hidden" name="name" value="' + $scope.document.fileName + '">';
    form += '<input type="hidden" name="path" value="' + $scope.document.linkFile + '">';
    var _form_ = $('<form id = "custumFormUrl" class="hidden" action="' + preUrl + "/common/download" + '" method="get">' + form + '</form>');
    $('body').append(_form_);
    $("#custumFormUrl").submit();
    $("#custumFormUrl").remove();
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

  $scope.downloadFile = function (file) {
    $scope.document = file;
    var form = '';
    form += '<input type="hidden" name="name" value="' + $scope.document.fileName + '">';
    form += '<input type="hidden" name="path" value="' + $scope.document.linkFile + '">';
    var _form_ = $('<form id = "custumFormUrl" class="hidden" action="' + preUrl + "/common/download" + '" method="get">' + form + '</form>');
    $('body').append(_form_);
    $("#custumFormUrl").submit();
    $("#custumFormUrl").remove();
  };
  var arr = [];
  $scope.propertyChange = function(property){
    var valueProperty = JSON.parse(JSON.stringify(property));
    // var multiSelect = $('#selectProperty').data("kendoMultiSelect");
    // multiSelect.open();
    // $('.k-animation-container').css("display","none");
    // multiSelect.toggle();
    if(property != "" && property != "undefined"){
      $http.post(preUrl + "/system/choice-result/getInfoPropertyAuction", valueProperty, {headers: {'Content-Type': 'application/json'}})
      .then(function (response) {
        $scope.property = response.data;
        $scope.property.propertyStartPrice = formatNumber($scope.property.propertyStartPrice.toString());
        $scope.property.fileCost = '';
        // if ($scope.property.propertyType.toString().includes(",")) {
        //     arr = $scope.mySplit($scope.property.propertyType.toString());
        // } else {
        //     $scope.property.propertyType = [$scope.property.propertyType];
        //     arr = [Number($scope.property.propertyType)];
        // }
        // multiSelect.value(arr);
        // $scope.property.propertyType = arr;
      });
    } else {
      $scope.onAddProperty();
    }
  }

  $scope.mySplit = function(string) {
    var array = string.split(',');
    return array;
  }
}]);
