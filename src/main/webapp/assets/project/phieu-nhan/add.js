app.controller('vantai', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', function ($scope, $http, $filter, $window, fileUpload, $timeout, $q, Fact) {
        $scope.search = {basic: "", receiptCode: "", fromDeceipt: "", toDeceipt: "", nhaXe: "", nameStock: ""};
        
        $scope.listBienNhanDaChon = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.listBienNhanDaChon = dataShare.listBienNhanDaChon;
        $scope.numberPerPage = "5";
        $scope.listBienNhanDaChon.numberPerPage = $scope.numberPerPage;
        $scope.changeIdSelected = function (id) {
            $scope.idSelected = id;
            $http.get(preUrl + "/them-moi-thong-tin-bo-nhiem-ccv/searchById", {params: {idNotary: $scope.idSelected}})
                    .then(function (reponse) {
                        $scope.phieuNhan = reponse.data;
                    });
        };
        /*load tooltip*/
        $scope.tooltip = function () {
            var defer = $q.defer();
            $timeout(function () {
                $("[data-toggle=popover]").popover();
                defer.resolve();
            }, 1000);
        };

        $scope.save = function () {
            
            if ($("#formAdd").parsley().validate()) {
                if (typeof $scope.phieuNhan != "undefined" && typeof $scope.phieuNhan.receiptCode != 'undefined') {
                    $scope.call = {
                        vtGoodsReceipt: angular.copy($scope.phieuNhan),
                    };
                    var call_ = JSON.stringify($scope.call);
                    $http.post(preUrl + "/them-moi-thong-tin-bo-nhiem-ccv", call_, {headers: {'Content-Type': 'application/json'}})
                            .then(function (response) {
                                if (response.data.reponseCode == 200 && response.data.success == true) {
                                    window.location.href = preUrl + "/quan-ly-bo-nhiem-ccv?status=1";
                                } else {
                                    switch (response.data.reponseCode){
                                        case 1: 
                                            $scope.thongBao = $("#notaryEmpty").html();
                                            break;
                                        case 2: 
                                            $scope.thongBao = $("#errMsg2").html();
                                            break;
                                        case 3: 
                                            $scope.thongBao = $("#errMsg1").html();
                                            break;
                                        case 4: 
                                            $scope.thongBao = $("#errMsg3").html();
                                            break;
                                        case 5: 
                                            $scope.thongBao = $("#error_2").html();
                                            break;
                                        case 6: 
                                            $scope.thongBao = $("#error_3").html();
                                            break;
                                        case 7: 
                                            $scope.thongBao = $("#error_1").html();
                                            break;
                                        case 500:
                                            $scope.thongBao = "Lỗi trong quá trình xử lý, Vui lòng thử lại sau.";
                                            break;
                                    }
                                }
                            }, function (response) {
                                $("#confirm-error").modal("show");
                            }
                            );
                } else {
                    $scope.thongBao = $("#notaryEmpty").html();
                    $("#popup-message").modal("show");
                }
            }
        };
        $(document).ready(function () {
            $("#dateSign_").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.document.dateSign = $(this).val();
                    $scope.dateSign_ = stringToDate($scope.document.dateSign, "dd-MM-yyyy", "-");
                    if($('#dateSign_').val() != "")
                        $('#effectiveDate_').data("DateTimePicker").minDate(moment($('#dateSign_').val(), "DD-MM-YYYY").toDate());
                }
            });
            $("#effectiveDate_").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.document.effectiveDate = $(this).val();
                    $scope.effectiveDate_ = stringToDate($scope.document.effectiveDate, "dd-MM-yyyy", "-");
                    if($('#effectiveDate_').val() != "")
                        $('#dateSign_').data("DateTimePicker").maxDate(moment($('#effectiveDate_').val(), "DD-MM-YYYY").toDate());
                }
            });
        });

        $scope.removeIndexList = function (index, list) {
            var list_ = [];
            for (var i = 0; i < list.length; i++) {
                if (i != index) {
                    list_.push(list[i]);
                }
            }
            return list_;
        };
    }]);