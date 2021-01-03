
app.controller('frameworkCtrl', ['$scope', '$http', 'fileUpload', function ($scope, $http, fileUpload) {
        $scope.info = {};
        $scope.info.bienNhan = {receiptCode: "", nameStock: "", dateReceipt: "", paymentType: "", nhaXe: "", bienSo: "", loaiXe: "", employee: "", nguoiThanhToanId=""};
        $scope.info.nguoiGui = {taxCode: "", fullName: "", mobile: "", address: "", sohopdong: ""};
        $scope.info.nguoiNhan = {taxCode: "", fullName: "", mobile: "", address: "", sohopdong: ""};
        $scope.info.property = [];
        $scope.titleModal = 'ADD';
        var arrIdDelete = [];
        var currentIndexProperty = -1;
        if (receiptCode != null && receiptCode != '') {
            $scope.info.bienNhan.receiptCode = receiptCode;
        }

        $http.get(preUrl + "/bienNhan/danhSachNhaXe")
                .then(function (response) {
                    $scope.nhaXeList = response.data;
                });

        $scope.onChangeBienSo = function () {
            $http.get(preUrl + "/bienNhan/thongTinNhaXe", {params: {bienSo: $scope.info.bienNhan.bienSo}})
                    .then(function (response) {
                        $scope.info.bienNhan.nhaXe = response.data.nhaXe;
                        $scope.info.bienNhan.loaiXe = response.data.loaiXe;
                        $scope.info.bienNhan.employee = response.data.tenLaiXe;
                    });
        }


        $http.get(preUrl + "/bienNhan/danhSachKhachHang")
                .then(function (response) {
                    $scope.khachHangList = response.data;
                });


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
                    $scope.property.cost = Number($scope.property.cost.toString().replace(/,/g, ""));
                } else {
                    $scope.property.cost = Number($scope.property.cost);
                }
                if ($scope.titleModal == 'EDIT') {
                    $scope.info.property[currentIndexProperty] = $scope.property;
                    $('#addPropertyInfo').modal('hide');
                    toastr.success('Cập nhật thông tin thành công!');
                } else if ($scope.titleModal == 'ADD') {
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

        $scope.saveAuctionInfo = function () {
            if ($('#receiptCode').val() == '') {
                toastr.error("Số biên nhận không được để trống!");
                document.getElementById("receiptCode").focus();
                return false;
            } else if ($('#nameStock').val() == '') {
                toastr.error("Kho nhận không được để trống!");
                document.getElementById("nameStock").focus();
                return false;
            } else if ($('#paymentType').val() == '') {
                toastr.error("Bạn chưa chọn loại thanh toán!");
                document.getElementById("paymentType").focus();
                return false;
            } else if ($('#info.bienNhan.nguoiThanhToanId').val() == '') {
                toastr.error("Bạn chưa chọn người thanh toán!");
                document.getElementById("info.bienNhan.nguoiThanhToanId").focus();
                return false;
            } else if ($scope.info.nguoiGui.taxCode == '') {
                toastr.error("MST/CMND người gửi không được để trống!");
                document.getElementById("nguoiGui.taxCode").focus();
                return false;
            } else if ($scope.info.nguoiGui.fullName == '') {
                toastr.error("Tên người gửi không được để trống!");
                document.getElementById("nguoiGui.fullName").focus();
                return false;
            } else if ($scope.info.nguoiNhan.taxCode == '') {
                toastr.error("MST/CMND người nhận không được để trống!");
                document.getElementById("nguoiNhan.taxCode").focus();
                return false;
            } else if ($scope.info.nguoiNhan.fullName == '') {
                toastr.error("Tên người nhận không được để trống!");
                document.getElementById("nguoiNhan.fullName").focus();
                return false;
            } else if ($scope.info.property.length == 0) {
                toastr.error("Bạn chưa nhập mặt hàng!");
                $("#addPropertyInfo").modal('show');
                return false;
            }
            document.getElementById("btnSaveAuctionInfo").disabled = true;
            if ($scope.info.nguoiGui.dateReceipt != '') {
                $scope.info.bienNhan.dateReceipt = stringToDate($("#dateReceipt").val(), "dd/mm/yyyy", "/");

            }
            var info = {
                bienNhan: $scope.info.bienNhan,
                nguoiGui: $scope.info.nguoiGui,
                nguoiNhan: $scope.info.nguoiNhan,
                matHang: $scope.info.property,
                fileUploads: $scope.myFile.listFileDocument
            };

            var infoAuction = JSON.parse(JSON.stringify(info));
            $http.post(preUrl + "/bienNhan/them-moi-bien-nhan", infoAuction, {headers: {'Content-Type': 'application/json'}})
                    .then(function (response) {
                        $scope.info.bienNhan.dateReceipt = moment($scope.info.bienNhan.dateReceipt).format('DD/MM/YYYY');
                        switch (Number(response.data)) {
                            case 1:
                                document.getElementById("btnSaveAuctionInfo").disabled = false;
                                toastr.success("Thêm mới phiếu nhận hàng thành công!");
                                $scope.info = {};
                                setTimeout(function () {
                                    location.reload();
                                }, 2000);
                                break;
                            case 0:
                                toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
                                document.getElementById("btnSaveAuctionInfo").disabled = false;
                                break;
                        }
                    });
        };

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

        $scope.mySplit = function (string) {
            var array = string.split(',');
            return array;
        };

        $(document).on('click', function (e) {
            if (e.target.id != 'nguoiGui.taxCode' && e.target.id != 'btnThemMatHang' && e.target.id != 'btnSaveAuctionInfo') {
                $http.get(preUrl + "/bienNhan/ThongTinNguoiGui", {params: {taxCode: $scope.info.nguoiGui.taxCode, typePartner: 2}})
                        .then(function (response) {
                            if (response.data.fullName != null && response.data.fullName != "") {
                                $scope.info.nguoiGui.fullName = response.data.fullName;
                                $scope.info.nguoiGui.mobile = response.data.mobile;
                                $scope.info.nguoiGui.address = response.data.address;
                                $scope.info.nguoiGui.sohopdong = response.data.sohopdong;
                            }
                        });
            }
        });

        $(document).on('click', function (e) {
            if (e.target.id != 'nguoiNhan.taxCode' && e.target.id != 'btnThemMatHang' && e.target.id != 'btnSaveAuctionInfo') {
                $http.get(preUrl + "/bienNhan/ThongTinNguoiGui", {params: {taxCode: $scope.info.nguoiNhan.taxCode, typePartner: 3}})
                        .then(function (response) {
                            if (response.data.fullName != null && response.data.fullName != "") {
                                $scope.info.nguoiNhan.fullName = response.data.fullName;
                                $scope.info.nguoiNhan.mobile = response.data.mobile;
                                $scope.info.nguoiNhan.address = response.data.address;
                                $scope.info.nguoiNhan.sohopdong = response.data.sohopdong;
                            }
                        });
            }
        });
    }]);
