
app.controller('frameworkCtrl', ['$scope', '$http', '$timeout', '$q', function ($scope, $http, $timeout, $q) {
        $scope.info = {};
        $scope.info.bienNhan = {receiptCode: "", nameStock: "", dateReceipt: "", paymentType: "", nhaXe: "", bienSo: "", loaiXe: "", employee: "", nguoiThanhToanId: ""};
        $scope.info.nguoiGui = {taxCode: "", fullName: "", mobile: "", address: "", soHopDong: ""};
        $scope.info.nguoiNhan = {taxCode: "", fullName: "", mobile: "", address: "", soHopDong: ""};
        $scope.info.property = [];
        $scope.titleModal = 'ADD';
        var arrIdDelete = [];
        var currentIndexProperty = -1;
        $scope.id = receiptId;

        $scope.property = {id: null, name: '', unit: '', numbers: '', weight: '', sizes: '', cost: '', note: ''};

        $scope.onAddProperty = function () {
            $scope.titleModal = 'ADD';
            $scope.press = {word: ''};
            $scope.property = {id: null, name: '', unit: '', numbers: '', weight: '', sizes: '', cost: '', note: ''};
        };

        $http.get(preUrl + "/managerVanTai/bienNhan/danhSachKhachHang")
                .then(function (response) {
                    $scope.khachHangList = response.data;
                });


        $(document).on('click', function (e) {
            if (e.target.id != 'nguoiGui.taxCode' && e.target.id != 'btnThemMatHang' && e.target.id != 'btnSaveAuctionInfo') {
                $http.get(preUrl + "/managerVanTai/bienNhan/ThongTinNguoiGui", {params: {taxCode: $scope.info.nguoiGui.taxCode, typePartner: 2}})
                        .then(function (response) {
                            if (response.data.fullName != null && response.data.fullName != "") {
                                $scope.info.nguoiGui.fullName = response.data.fullName;
                                $scope.info.nguoiGui.mobile = response.data.mobile;
                                $scope.info.nguoiGui.address = response.data.address;
                                $scope.info.nguoiGui.soHopDong = response.data.soHopDong;
                            }
                        });
            }
        });

        $(document).on('click', function (e) {
            if (e.target.id != 'nguoiNhan.taxCode' && e.target.id != 'btnThemMatHang' && e.target.id != 'btnSaveAuctionInfo') {
                $http.get(preUrl + "/managerVanTai/bienNhan/ThongTinNguoiGui", {params: {taxCode: $scope.info.nguoiNhan.taxCode, typePartner: 3}})
                        .then(function (response) {
                            if (response.data.fullName != null && response.data.fullName != "") {
                                $scope.info.nguoiNhan.fullName = response.data.fullName;
                                $scope.info.nguoiNhan.mobile = response.data.mobile;
                                $scope.info.nguoiNhan.address = response.data.address;
                                $scope.info.nguoiNhan.soHopDong = response.data.soHopDong;
                            }
                        });
            }
        });

        $http.get(preUrl + "/managerVanTai/bienNhan/ThongTinBienNhan", {params: {id: $scope.id}})
                .then(function (response) {
                    $scope.info = response.data;
                    $scope.info.property = response.data.matHang;
                    var dateReceipt = new Date($scope.info.bienNhan.dateReceipt);
                    if (dateReceipt != null && dateReceipt != '') {
                        $scope.info.bienNhan.dateReceipt = moment(dateReceipt).format('DD/MM/YYYY');
                    }
                    if ($scope.info.bienNhan.nguoiThanhToanId != null && $scope.info.bienNhan.nguoiThanhToanId != '') {
                            $("#nguoiThanhToanId").select2("val", $scope.info.bienNhan.nguoiThanhToanId);
                    }
                    // $scope.myFile.listFileDocument = $scope.info.fileUploads;
                });

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
                toastr.error("Số phiếu nhận hàng không được để trống!");
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
            }
            document.getElementById("btnSaveAuctionInfo").disabled = true;
            if ($scope.info.nguoiGui.dateReceipt != '') {
                $scope.info.bienNhan.dateReceipt = stringToDate($("#dateReceipt").val(), "dd/mm/yyyy", "/");

            }
            var info = {
                bienNhan: $scope.info.bienNhan,
                nguoiGui: $scope.info.nguoiGui,
                nguoiNhan: $scope.info.nguoiNhan,
                matHang: $scope.info.property
                        // fileUploads: $scope.myFile.listFileDocument
            };

            if (arrIdDelete.length > 0)
                $http.get(preUrl + "/managerVanTai/bienNhan/deleteProperty", {params: {arrIdDelete: arrIdDelete, receiptId: $scope.info.bienNhan.id}})
                        .then(function (response) {
                            if (response.data == "0")
                                toastr.error("Có lỗi trong quá trình xử lý, vui lòng thử lại!");
                            return false;
                        });
            var infoAuction = JSON.parse(JSON.stringify(info));
            $http.post(preUrl + "/managerVanTai/bienNhan/chinh-sua-bien-nhan", infoAuction, {headers: {'Content-Type': 'application/json'}})
                    .then(function (response) {
                        $scope.info.bienNhan.dateReceipt = moment($scope.info.bienNhan.dateReceipt).format('DD/MM/YYYY');
                        switch (Number(response.data)) {
                            case 1:
                                document.getElementById("btnSaveAuctionInfo").disabled = false;
                                toastr.success("Chỉnh sửa phiếu nhận hàng thành công!");
                                break;
                            case 0:
                                toastr.error("Có lỗi xảy ra vui lòng thử lại sau!");
                                document.getElementById("btnSaveAuctionInfo").disabled = false;
                                break;
                        }
                    });
        };
    }]);
