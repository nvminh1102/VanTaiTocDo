app.controller('vantaivn', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', function ($scope, $http, $filter, $window, fileUpload, $timeout, $q) {

        $scope.search = {basic: 0, receiptCode: "", truckPartner: "", loaiXe: "",  bienSo: "", fromDelivery: "", toDelivery: "", fromReceive: "", toReceive: ""};

        var search = JSON.stringify($scope.search);
        $scope.listData = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
        $scope.checkAll_ = false;

        $scope.clear = function () {
            $scope.search.basic = 0;
            $scope.search.receiptCode = "";
            $scope.search.truckPartner = "";
            $scope.search.loaiXe = "";
            $scope.search.bienSo = "";
            $scope.search.fromDelivery = "";
            $scope.search.toDelivery = "";
            $scope.search.fromReceive = "";
            $scope.search.toReceive = "";
            $("#fromDelivery").data("DateTimePicker").date(null);
            $("#toDelivery").data("DateTimePicker").date(null);
            $("#fromReceive").data("DateTimePicker").date(null);
            $("#toReceive").data("DateTimePicker").date(null);
            $timeout(function () {
                $('#fromDelivery').data("DateTimePicker").maxDate(moment("01/01/9999999999", "DD-MM-YYYY").toDate());
                $('#toDelivery').data("DateTimePicker").minDate(moment("01/01/100", "DD-MM-YYYY").toDate());
                $('#fromReceive').data("DateTimePicker").maxDate(moment("01/01/9999999999", "DD-MM-YYYY").toDate());
                $('#toReceive').data("DateTimePicker").minDate(moment("01/01/100", "DD-MM-YYYY").toDate());
            }, 0);
        };

        $scope.searchSelect = function (event) {
            $scope._custum_searchSuccess = true;
            if (event != null) {
                var div = angular.element(event.currentTarget).parent();
                for (var i = 0; i < $(div)[0].childNodes.length; i++) {
                    if ($(div)[0].childNodes[i].nodeName == "IMG") {
                        $($(div)[0].childNodes[i]).removeClass("hidden");
                        break;
                    }
                }
            }
            $scope.search.basic = 1;
            $scope.listData.pageNumber = 1;
            $scope.loadListData();
        };
        $scope.setNumberPerPage = function (numberPerPage) {
            $scope.listData.numberPerPage = numberPerPage;
            $scope.searchSelect(null);
        };
        $http.get(preUrl + "/quan-ly-bo-nhiem-ccv/search", {params: {search: search, offset: 0, number: $scope.listData.numberPerPage}})
                .then(function (response) {
                    $scope.listData = response.data;
                    $scope.listData.numberPerPage = $scope.numberPerPage;
                    $scope.listData.pageCount = getPageCount($scope.listData);
                    $scope.listData.pageList = getPageList($scope.listData);
                    $scope.tooltip();
                });

        //reload list
        $scope.loadListData = function () {
            var search = JSON.stringify($scope.search);
            $http.get(preUrl + "/quan-ly-bo-nhiem-ccv/search", {params: {search: search, offset: 0, number: $scope.listData.numberPerPage}})
                    .then(function (response) {
                        $scope.listData = response.data;

                        $scope.listData.numberPerPage = $scope.numberPerPage;
                        $scope.listData.pageCount = getPageCount($scope.listData);
                        $scope.listData.pageList = getPageList($scope.listData);
                        $scope.tooltip();
                        $("._custum_searchSuccess").addClass("hidden");
                        $scope._custum_searchSuccess = false;
                    }, function (response) {
                        $("._custum_searchSuccess").addClass("hidden");
                        $scope._custum_searchSuccess = false;
                        toastr.error($("#_custum_error_500").html());
                    });
        };

        //reload page
        $scope.loadPageData = function (index) {
            var search = JSON.stringify($scope.search);
            $scope.listData.pageNumber = index;
            $http.get(preUrl + "/quan-ly-bo-nhiem-ccv/search", {params: {search: search, offset: $scope.listData.numberPerPage * ($scope.listData.pageNumber - 1), number: $scope.listData.numberPerPage}})
                    .then(function (response) {
                        $scope.listData.items = response.data.items;

                        $scope.listData.numberPerPage = $scope.numberPerPage;
                        $scope.listData.pageList = getPageList($scope.listData);
                        $scope.tooltip();
                    }, function (response) {
                        /*call back*/
                        toastr.error($("#_custum_error_500").html());
                    });
        };
        $scope.export = function () {
            window.location.href = preUrl + "/quan-ly-bo-nhiem-ccv/export?fullName=" + $scope.search.fullName + "&dispatchCode=" + $scope.search.dispatchCode
                    + "&type=" + $scope.search.type
                    + "&fromDateSign=" + $scope.search.fromDateSign + "&toDateSign=" + $scope.search.toDateSign;
        };

        $scope.delete = function (idAppoint) {
            $scope.idDelete = idAppoint;
            $("#confirm-delete").modal("show");
        };

        $scope.confirmDelete = function () {

            $http.post(preUrl + "/xoa-thong-tin-bo-nhiem-ccv", $scope.idDelete, {headers: {'Content-Type': 'application/json'}})
                    .then(function (response) {
                        if (response.data.reponseCode == 200 && response.data.success == true) {
                            toastr.success("Xóa thành công!");
                        } else {
                            toastr.error("Có lỗi trong quá trình xử lý, Vui lòng thử lại sau.");
                        }
                        $scope.searchSelect(null);
                        $("#confirm-delete").modal("hide");
                    },
                            function (response) {
                                $("#confirm-delete").modal("hide");
                                $("#confirm-error").modal("show");
                            });

        };
//
//        $scope.documentReappoint = {
//            linkFile: "",
//            fileName: ""
//        };

//        $scope.myFile = {
//            file_MienNhiem: "", listFile_MienNhiem: [],
//        };
//        $scope.countFile = {
//            file_MienNhiem: 0,
//        };
        $scope.notaryRequest = {
            linkFile: "",
            fileName: ""
        };

        //chi tiết thông tin bổ nhiệm, từ chối bổ nhiệm
        $scope.detail_ = function (idAppoint) {
            $scope.idAppoint = idAppoint;
            $("#chi-tiet-bo-nhiem").modal("show");

            $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchBoNhiem", {params: {idAppoint: $scope.idAppoint}})
                    .then(function (response) {
                        $scope.notaryInfo = response.data.notaryInfo;
                        $scope.appoint = response.data.appoint;
                        $scope.document = response.data.document;
                    });

        };

        //Sửa từ chối bổ nhiệm
        $scope.editAppoint_ = function (idAppoint) {
            $scope.idAppoint = idAppoint;
            $http.get(preUrl + "/sua-quyet-dinh-bo-nhiem-ccv", {params: {idAppoint: $scope.idAppoint}})
                    .then(function (response) {
                        $("#tu-choi-bo-nhiem-ccv").modal("show");
                        $scope.documentAppoint = response.data.document;
                        $scope.appoint = response.data.appoint;

                        var listFile = ($scope.documentAppoint.linkFile == null || $scope.documentAppoint.linkFile == "") ? "" : $scope.documentAppoint.linkFile.split(";");
                        var listName = ($scope.documentAppoint.fileName == null || $scope.documentAppoint.fileName == "") ? "" : $scope.documentAppoint.fileName.split(";");
                        for (var i = 0; i < listFile.length; i++) {
                            var obj = {file: {name: listName[i]}};
                            $scope.myFile.listFileDocument.push(obj);
                            $scope.countFile.fileDocument;
                        }

                        var date = "";
                        var yyyy = "";
                        var mm = "";
                        var dd = "";
                        if (isNotUndefined($scope.documentAppoint.dateSign)) {
                            date = new Date($scope.documentAppoint.dateSign);
                            $scope.appoint_dateSign__ = date;
                            yyyy = date.getFullYear();
                            mm = date.getMonth() + 1;
                            dd = date.getDate();
                            $scope.documentAppoint.dateSign = dd + "-" + mm + "-" + yyyy;
                        } else {
                            $scope.appoint_dateSign__ = "";
                        }

                        //set lại lý do
                        //$scope.appoint.reason = $scope.appoint.reason.toString();
                        $http.get(preUrl + "/getResionByType", {params: {type: 6}})
                                .then(function (response) {
                                    $scope.getResionByType = response.data;
                                    $timeout(function () {
                                        $("#reason").select2("val", $scope.appoint.reason);
                                    }, 0);
                                });

                    });
        };

        //Modal quyết định từ chối bổ nhiệm
        $http.get(preUrl + "/getResionByType", {params: {type: 6}})
                .then(function (response) {
                    $scope.getResionByType = response.data;
                });
        $scope.tuchoibonhiem = function (idNotaryInfo) {
            $scope.idNotary = idNotaryInfo;
            $("#tu-choi-bo-nhiem-ccv").modal("show");
        };



        //File bổ nhiệm
        $scope.removeFileDocument = function (index) {
            var count = $scope.countFile.fileDocument;
            var listFile = $scope.myFile.listFileDocument;
            if (count >= 0) {
                $scope.countFile.fileDocument = count - 1;
                $scope.myFile.listFileDocument = $scope.removeIndexList(index, listFile);

                $scope.documentAppoint.linkFile = $scope.removeIndexList(index, $scope.documentAppoint.linkFile.split(";")).join(";");
                $scope.documentAppoint.fileName = $scope.removeIndexList(index, $scope.documentAppoint.fileName.split(";")).join(";");
            }
        };
        $scope.uploadFileDocument = function (event) {
            console.log(event.target.files[0], $scope.myFile.fileDocument);
            if (event.target.files[0] != undefined) {
                var file = $scope.myFile.fileDocument;
                var uploadUrl = preUrl + "/uploadFile";
                fileUpload.uploadFileToUrl(file, uploadUrl)
                        .then(function (response) {
                            if (response.data != null && response.data != undefined && response.status == 200) {
                                if ($scope.documentAppoint.linkFile == undefined || $scope.documentAppoint.linkFile == "") {
                                    $scope.documentAppoint.linkFile = response.data.path;
                                    $scope.documentAppoint.fileName = response.data.name;
                                } else {
                                    $scope.documentAppoint.linkFile = $scope.documentAppoint.linkFile + ";" + response.data.path;
                                    $scope.documentAppoint.fileName = $scope.documentAppoint.fileName + ";" + response.data.name;
                                }

                                $scope.countFile.fileDocument = $scope.countFile.fileDocument + 1;

                                var obj = {file: $scope.myFile.fileDocument};
                                $scope.myFile.listFileDocument.push(obj);
                                /*reset input*/
                                $scope.myFile.fileDocument = "";
                                document.getElementById("fileDocument").value = "";
                                document.getElementById("fileDocument_").value = "";
                            } else {
                                $("#confirm-error").modal("show");
                            }
                        },
                                function (response) {
                                    $("#confirm-error").modal("show");
                                }
                        );
            }
        };
        $scope.download = function (fileName, linkFile) {
            var form = '';
            form += '<input type="hidden" name="name" value="' + fileName + '">';
            form += '<input type="hidden" name="path" value="' + linkFile + '">';
            var _form_ = $('<form id = "custumFormUrl" class="hidden" action="' + preUrl + "/download" + '" method="get">' + form + '</form>');
            $('body').append(_form_);
            $("#custumFormUrl").submit();
            $("#custumFormUrl").remove();
        };
        $scope.downloadFileDocument = function (index) {
            var linkFile = $scope.document.linkFile.split(";")[index];
            var fileName = $scope.document.fileName.split(";")[index];

            var form = '';
            form += '<input type="hidden" name="name" value="' + fileName + '">';
            form += '<input type="hidden" name="path" value="' + linkFile + '">';
            var _form_ = $('<form id = "custumFormUrl" class="hidden" action="' + preUrl + "/download" + '" method="get">' + form + '</form>');
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

        $(document).ready(function () {
            $("#fromDelivery").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.fromDelivery = $(this).val();
                    if ($('#fromDelivery').val() != "")
                        $('#toDelivery').data("DateTimePicker").minDate(moment($('#fromDelivery').val(), "DD-MM-YYYY").toDate());
                }
            });
            $("#toDelivery").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.toDelivery = $(this).val();
                    if ($('#toDelivery').val() != "")
                        $('#fromDelivery').data("DateTimePicker").maxDate(moment($('#toDelivery').val(), "DD-MM-YYYY").toDate());
                }
            });
            $("#fromReceive").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.fromReceive = $(this).val();
                    if ($('#fromReceive').val() != "")
                        $('#toReceive').data("DateTimePicker").minDate(moment($('#fromReceive').val(), "DD-MM-YYYY").toDate());
                }
            });
            $("#toReceive").datetimepicker({
                locale: 'vi-VN',
                format: 'DD-MM-YYYY'
            }).on('dp.change', function (e) {
                if (e != null) {
                    $scope.search.toReceive = $(this).val();
                    if ($('#toReceive').val() != "")
                        $('#fromReceive').data("DateTimePicker").maxDate(moment($('#toReceive').val(), "DD-MM-YYYY").toDate());
                }
            });
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