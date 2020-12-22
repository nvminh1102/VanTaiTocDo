/**
 * Created by Admin on 12/22/2017.
 */
app.controller('frameworkCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.paramKey = "";
        $scope.labelTitle = "Add";
        $scope.dataLoaded = false;
        $scope.paramItem = {id: null, key: "", value: "", description: ""};
        $scope.paramItems = [];
        $scope.paramKeyRegex = "^[A-Za-z0-9_-]+$";
        $scope.page = page;
        $http.get(preUrl + "/system/parameter/search", {params: {paramKey: $scope.paramKey}})
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
            $http.get(preUrl + "/system/parameter/search", {params: {paramKey: $scope.paramKey}})
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
                $http.get(preUrl + "/system/parameter/search", {params: {p: pageNumber, paramKey: $scope.paramKey}})
                        .then(function (response) {
                            $scope.page = response.data;
                            $scope.page.pageList = getPageList($scope.page);
                            $scope.page.pageCount = getPageCount($scope.page);
                            $scope.paramItems = response.data.items;
                        });
            }
        };
        $scope.checkRegex = function (input, regex) {
            var result = false;
            if (input !== '') {
                var _pattern = regex;
                var _test = new RegExp(_pattern);
                if (_test.test(input))
                    result = true;
            } else {
                result = true;
            }
            return result;
        };

        $scope.validateData = function (item) {
            var check = true;
            $scope.paramKey_valid = "";
            $scope.paramValue_valid = "";
            $scope.paramDescription_valid = "";
            var focus = 0;
            if (typeof item === "undefined" || typeof item.key === "undefined" || item.key === null || item.key === "" || item.key.length > 20 || !$scope.checkRegex(item.key, $scope.paramKeyRegex)) {
                check = false;
                $scope.paramKey_valid = "Mã tham số không được để trống!";
                if (typeof item != "undefined" && typeof item.key != "undefined" && item.key != null && item.key != "" && item.key.length > 20) {
                    $scope.paramKey_valid = "Độ dài mã tham số không quá 20 kí tự!";
                }
                if(!$scope.checkRegex(item.key, $scope.paramKeyRegex)){
                     $scope.paramKey_valid = "Mã tham số không hợp lệ (Mã tham số chỉ được gồm các kí tự A - z, số, dấu gạch dưới và dấu gạch ngang.)";
                }
                if (focus === 0) {
                    focus = 1;
                    $("#paramKeyUpdate").focus();
                }
            }
            if (typeof item === "undefined" || typeof item.value === "undefined" || item.value === null || item.value === "" || item.value.length > 100) {
                check = false;
                $scope.paramValue_valid = "Giá trị tham số không được để trống!";
                if (typeof item != "undefined" && typeof item.value != "undefined" && item.key != null && item.value != "" && item.value.length > 100) {
                    $scope.paramValue_valid = "Độ dài giá trị tham số không quá 100 kí tự!";
                }
                if (focus === 0) {
                    focus = 1;
                    $("#paramValueUpdate").focus();
                }
            }
            if (typeof item != "undefined" && typeof item.description != "undefined" && item.description != null && item.description != "" && item.key.description > 500) {
                $scope.paramDescription_valid = "Độ dài mô tả thông tin tham số không quá 500 kí tự!";
            }
            return check;

        };

        $scope.addOrUpdateParam = function () {
            var check = true;
            check = $scope.validateData($scope.paramItem);
            if ($scope.labelTitle === 'Edit' && $scope.paramItem.id !== "") {
                if (check) {
                    var addParam = JSON.parse(JSON.stringify($scope.paramItem));
                    $http.post(preUrl + "/system/parameter/edit", addParam, {headers: {'Content-Type': 'application/json'}})
                            .then(function (response) {
                                switch (Number(response.data)) {
                                    case 0:
                                        $("#updateParamModal").modal("hide");
                                        $scope.search();
                                        CommonFunction.showPopUpMsg("Thông báo", "Sửa thông tin thành công!", "success");
                                        $scope.paramItem.id = "";
                                        $scope.paramItem.key = '';
                                        $scope.paramItem.value = '';
                                        $scope.paramItem.description = '';
                                        break;
                                    case 1:
                                        $("#updateParamModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Có lỗi trong quá trình xử lý, vui lòng thử lại!", "error");
                                        break;
                                    case 2:
//                                        $("#updateParamModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Bạn cần nhập đầy đủ các thông tin bắt buộc !", "error");
                                        break;
                                    case 3:
//                                        $("#updateParamModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Tham số không tồn tại!", "error");

                                        break;
                                }
                            }
                            );
                }
            } else {
                if (check) {
                    var addParam = JSON.parse(JSON.stringify($scope.paramItem));
                    $http.post(preUrl + "/system/parameter/add", addParam, {headers: {'Content-Type': 'application/json'}})
                            .then(function (response) {
                                switch (Number(response.data)) {
                                    case 0:
                                        $("#updateParamModal").modal("hide");
                                        $scope.search();
                                        CommonFunction.showPopUpMsg("Thông báo", "Thêm mới thành công!", "success");

                                        $scope.paramItem.id = "";
                                        $scope.paramItem.key = '';
                                        $scope.paramItem.value = '';
                                        $scope.paramItem.description = '';
                                        break;
                                    case 1:
                                        $("#updateParamModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Có lỗi trong quá trình xử lý, vui lòng thử lại!", "error");
                                        break;
                                    case 2:
//                                        $("#updateParamModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Bạn cần nhập đầy đủ các thông tin bắt buộc !", "error");

                                        break;
                                    case 3:
//                                        $("#updateParamModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Mã tham số đã tồn tại!", "error");

                                        break;
                                }
                            });
                }

            }

        };


        $scope.onEditParam = function (param) {
            $scope.labelTitle = "Edit";
            $scope.paramItem.id = param[0];
            $scope.paramItem.key = param[1];
            $scope.paramItem.value = param[2];
            $scope.paramItem.description = param[3];
        };

        $scope.onAddParam = function () {
            $scope.labelTitle = "Add";
            $scope.paramItem.id = "";
            $scope.paramItem.key = '';
            $scope.paramItem.value = '';
            $scope.paramItem.description = '';
        };

        $scope.onDeleteParam = function (id) {
            $scope.search.basic = 1;
            $scope.deleteId = id;
        };
        $scope.deleteParam = function () {
            var call = {id: $scope.deleteId};
            var param = JSON.parse(JSON.stringify(call));
            $http.post(preUrl + "/system/parameter/delete", param, {headers: {'Content-Type': 'application/json'}})
                    .then(function (response) {
                        switch (Number(response.data)) {
                            case 0:
                                $("#deleteParamModal").modal("hide");
                                $scope.search();
                                CommonFunction.showPopUpMsg("Thông báo", "Xóa thành công!", "success");
                                break;
                            case 1:
                                $("#deleteParamModal").modal("hide");
                                CommonFunction.showPopUpMsg("Thông báo", "Có lỗi trong quá trình xử lý, vui lòng thử lại!", "error");
                                break;
                            case 3:
                                $("#deleteParamModal").modal("hide");
                                CommonFunction.showPopUpMsg("Thông báo", "Tham số không tồn tại!", "error");

                                break;
                        }
                    });
        };

    }]);