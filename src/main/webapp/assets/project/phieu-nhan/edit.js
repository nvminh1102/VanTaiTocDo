app.controller('tccc', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', function ($scope, $http, $filter, $window, fileUpload, $timeout, $q) {

        $scope.myFile = {fileDocument: "", listFileDocument: []};
        $scope.countFile = {fileDocument: 0};

        $scope.document = {};
        $scope.document.linkFile = "";
        $scope.document.fileName = "";
        $scope.appoint = {};

        /*start load dữ liệu theo idAppoint*/
        $(document).ready(function () {
            $http.get(preUrl + "/chinh-sua-thong-tin-bo-nhiem-ccv/search", {params: {idAppoint: idAppoint}})
                    .then(function (response) {
                        $scope.notaryInfoView = response.data.notaryInfoView;
                        $scope.document = response.data.document;
                        $scope.appoint = response.data.appoint;

                        var listFile = ($scope.document.linkFile == null || $scope.document.linkFile == "") ? "" : $scope.document.linkFile.split(";");
                        var listName = ($scope.document.fileName == null || $scope.document.fileName == "") ? "" : $scope.document.fileName.split(";");
                        for (var i = 0; i < listFile.length; i++) {
                            var obj = {file: {name: listName[i]}};
                            $scope.myFile.listFileDocument.push(obj);
                            $scope.countFile.fileDocument++;
                        }

                        /*reset date input*/
                        var date = "";
                        var yyyy = "";
                        var mm = "";
                        var dd = "";
                        if (isNotUndefined($scope.document.dateSign)) {
                            date = new Date($scope.document.dateSign);
                            $scope.dateSign_ = date;
                            yyyy = date.getFullYear();
                            mm = date.getMonth() + 1;
                            dd = date.getDate();
                            $scope.document.dateSign = dd + "-" + mm + "-" + yyyy;
                        } else {
                            $scope.dateSign_ = "";
                        }

                        if (isNotUndefined($scope.document.effectiveDate)) {
                            date = new Date($scope.document.effectiveDate);
                            $scope.effectiveDate_ = date;
                            yyyy = date.getFullYear();
                            mm = date.getMonth() + 1;
                            dd = date.getDate();
                            $scope.document.effectiveDate = dd + "-" + mm + "-" + yyyy;
                        } else {
                            $scope.effectiveDate_ = "";
                        }

                    });
        });
        /*end*/

        $scope.update = function () {
           /*if ($scope.document.linkFile == "") {
               $("#fileDocument").attr("data-parsley-trigger", "change");
               $("#fileDocument").attr("data-parsley-required", "true");
               $("#fileDocument").attr("data-parsley-required-message", $("#fileEmpty").html());
           } else {
               $("#fileDocument").removeAttr("data-parsley-trigger");
               $("#fileDocument").removeAttr("data-parsley-required");
               $("#fileDocument").removeAttr("data-parsley-required-message");
           }*/
            if ($scope.document.effectiveDate) {
                $("#effectiveDate_").attr("data-parsley-trigger", "change");
                $("#effectiveDate_").attr("data-parsley-required", "true");
                $("#effectiveDate_").attr("data-parsley-required-message", $("#error_1").html());
            } else {
                $("#effectiveDate_").removeAttr("data-parsley-trigger");
                $("#effectiveDate_").removeAttr("data-parsley-required");
                $("#effectiveDate_").removeAttr("data-parsley-required-message");
            }
            if ($scope.appoint.reason) {
                $("#appointReason").attr("data-parsley-trigger", "change");
                $("#appointReason").attr("data-parsley-required", "true");
                $("#appointReason").attr("data-parsley-required-message", $("#error_2").html());
            } else {
                $("#appointReason").removeAttr("data-parsley-trigger");
                $("#appointReason").removeAttr("data-parsley-required");
                $("#appointReason").removeAttr("data-parsley-required-message");
            }
            
            if ($("#formAdd").parsley().validate()) {
               
                    $scope.call = {
                        document: angular.copy($scope.document),
                        notaryInfoView: angular.copy($scope.notaryInfoView),
                        appoint: angular.copy($scope.appoint)
                    }
                    $scope.call.document.dateSign = $scope.dateSign_;
                    $scope.call.document.effectiveDate = $scope.effectiveDate_;
                    var call_ = JSON.stringify($scope.call);
                    $http.post(preUrl + "/chinh-sua-thong-tin-bo-nhiem-ccv/update", call_, {headers: {'Content-Type': 'application/json'}})
                            .then(function (response) {
                                if (response.data.reponseCode == 200 && response.data.success == true) {
                                    window.location.href = preUrl + "/quan-ly-bo-nhiem-ccv?status=2";
                                } 
                                else {
                                    switch (response.data.reponseCode) {
                                        case 1:
                                            $scope.thongBao = $("#errMsg2").html();
                                            break;
                                        case 2:
                                            $scope.thongBao = $("#errMsg1").html();
                                            break;
                                        case 3:
                                            $scope.thongBao = $("#errMsg3").html();
                                            break;
                                        case 4:
                                            $scope.thongBao = $("#error_2").html();
                                            break;
                                        case 5:
                                            $scope.thongBao = $("#error_3").html();
                                            break;
                                        case 6:
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
        $scope.removeFileDocument = function (index) {
            if ($scope.countFile.fileDocument >= 0) {
                $scope.countFile.fileDocument = $scope.countFile.fileDocument - 1;
                $scope.myFile.listFileDocument = $scope.removeIndexList(index, $scope.myFile.listFileDocument);

                $scope.document.linkFile = $scope.removeIndexList(index, $scope.document.linkFile.split(";")).join(";");
                $scope.document.fileName = $scope.removeIndexList(index, $scope.document.fileName.split(";")).join(";");
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
                                if ($scope.document.linkFile == undefined || $scope.document.linkFile == "") {
                                    $scope.document.linkFile = response.data.path;
                                    $scope.document.fileName = response.data.name;
                                } else {
                                    $scope.document.linkFile = $scope.document.linkFile + ";" + response.data.path;
                                    $scope.document.fileName = $scope.document.fileName + ";" + response.data.name;
                                }

                                $scope.countFile.fileDocument = $scope.countFile.fileDocument + 1;

                                var obj = {file: $scope.myFile.fileDocument};
                                $scope.myFile.listFileDocument.push(obj);
                                /*reset input*/
                                $scope.myFile.fileDocument = "";
                                document.getElementById("fileDocument").value = "";
                            } else {
                                $("#confirm-error").modal("show");
                            }
                        },
                                function (response) {
                                    $("#confirm-error").modal("show");
                                }
                        );
            } else {

            }
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

        /*load tooltip*/
        $scope.tooltip = function () {
            var defer = $q.defer();
            $timeout(function () {
                $("[data-toggle=popover]").popover();
                defer.resolve();
            }, 1000);
        };

    }]);