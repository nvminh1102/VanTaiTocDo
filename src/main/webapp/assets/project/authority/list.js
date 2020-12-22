/**
 * Created by Admin on 12/22/2017.
 */
app.controller('frameworkCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.authKey = "";
        $scope.labelTitle = "Add";
        $scope.dataLoaded = false;
        $scope.authItem = {id: null, authKey: "", authority: "", description: "", fid: null, orderId: null};
        $scope.authItems = [];
        $scope.authParent = [];
        $scope.authKeyRegex = "^[A-Za-z0-9_]+$";
        $scope.page = page;
        $http.get(preUrl + "/system/authority/search", {params: {authKey: $scope.authKey}})
                .then(function (response) {
                    if (response != null && response != 'undefined' && response.status == 200) {
                        $scope.page = response.data;
                        $scope.page.pageCount = getPageCount($scope.page);
                        $scope.page.pageList = getPageList($scope.page);
                        $scope.authItems = response.data.items;
                    }
                });

        $scope.search = function () {
            $scope.page.pageNumber = 1;
            $http.get(preUrl + "/system/authority/search", {params: {authKey: $scope.authKey}})
                    .then(function (response) {
                        if (response != null && response != 'undefined' && response.status == 200) {
                            $scope.page = response.data;
                            $scope.page.pageCount = getPageCount($scope.page);
                            $scope.page.pageList = getPageList($scope.page);
                            $scope.authItems = response.data.items;
                        }
                    });
        };

        $scope.loadPage = function (pageNumber) {
            if (pageNumber >= 1) {
                $http.get(preUrl + "/system/authority/search", {params: {p: pageNumber, authKey: $scope.authKey}})
                        .then(function (response) {
                            $scope.page = response.data;
                            $scope.page.pageList = getPageList($scope.page);
                            $scope.page.pageCount = getPageCount($scope.page);
                            $scope.authItems = response.data.items;
                        });
            }
        };

        $scope.getListAuthParent = function () {
            $http.get(preUrl + "/system/authority/get-list-auth-parent", {params: {authId: $scope.authItem.id}})
                    .then(function (response) {
                        if (response != null && response != 'undefined' && response.status == 200) {
                            $scope.authParent = response.data;
                        }
                    });
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
            $scope.authKey_valid = "";
            $scope.authName_valid = "";
            $scope.authDescription_valid = "";
            var focus = 0;
            
            if ($scope.labelTitle === 'Add' && (typeof item === "undefined" || typeof item.authKey === "undefined" || item.authKey === null || item.authKey === "" || item.authKey.length > 20 || !$scope.checkRegex(item.authKey, $scope.authKeyRegex) )) {
                check = false;
                $scope.authKey_valid = "Mã chức năng không được để trống!";
                if (typeof item != "undefined" && typeof item.authKey != "undefined" && item.authKey != null && item.authKey != "" && item.authKey.length > 100) {
                    $scope.authKey_valid = "Độ dài mã tham số không quá 200 kí tự!";
                }
                if (!$scope.checkRegex(item.authKey, $scope.authKeyRegex)) {
                    $scope.authKey_valid = "Mã chức năng không hợp lệ (Mã chức năng chỉ được gồm các kí tự A - z, số và dấu gạch dưới.)";
                }
                if (focus === 0) {
                    focus = 1;
                    $("#authKeyUpdate").focus();
                }
            }
            
            if (typeof item === "undefined" || typeof item.authority === "undefined" || item.authority === null || item.authority === "" || item.authority.length > 200) {
                check = false;
                $scope.authName_valid = "Tên chức năng không được để trống!";
                if (typeof item != "undefined" && typeof item.authority != "undefined" && item.authority != null && item.authority != "" && item.authority.length > 200) {
                    $scope.authName_valid = "Độ dài tên chức năng không quá 200 kí tự!";
                }
                if (focus === 0) {
                    focus = 1;
                    $("#authDescriptionUpdate").focus();
                }
            }
            
            if (typeof item != "undefined" && typeof item.description != "undefined" && item.description != null && item.description != "" && item.description.length > 500) {
                $scope.authDescription_valid = "Độ mô tả không quá 500 kí tự!";
                check = false;
                if (focus === 0) {
                    focus = 1;
                    $("#authDescriptionUpdate").focus();
                }
            }
            return check;

        };

        $scope.addOrUpdateAuthority = function () {
            var check = true;
            check = $scope.validateData($scope.authItem);
            if ($scope.labelTitle === 'Edit' && $scope.authItem.id !== "") {
                if (check) {
                    var addAuthority = JSON.parse(JSON.stringify($scope.authItem));
                    $http.post(preUrl + "/system/authority/edit", addAuthority, {headers: {'Content-Type': 'application/json'}})
                            .then(function (response) {
                                switch (Number(response.data)) {
                                    case 0:
                                        $("#updateAuthorityModal").modal("hide");
                                        $scope.search();
                                        CommonFunction.showPopUpMsg("Thông báo", "Sửa thông tin thành công!", "success");
                                        $scope.authItem.id = "";
                                        $scope.authItem.authKey = '';
                                        $scope.authItem.description = '';
                                        break;
                                    case 1:
                                        $("#updateAuthorityModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Có lỗi trong quá trình xử lý, vui lòng thử lại!", "error");
                                        break;
                                    case 2:
//                                        $("#updateAuthorityModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Bạn cần nhập đầy đủ các thông tin bắt buộc !", "error");
                                        break;
                                    case 3:
//                                        $("#updateAuthorityModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Chức năng không tồn tại!", "error");

                                        break;
                                    case 4:
//                                        $("#updateAuthorityModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Không thể thay đổi thông tin chức năng cha. Do chức năng này đã có chức năng con!", "error");

                                        break;
                                }
                            }
                            );
                }
            } else {
                if (check) {
                    var addAuthority = JSON.parse(JSON.stringify($scope.authItem));
                    $http.post(preUrl + "/system/authority/add", addAuthority, {headers: {'Content-Type': 'application/json'}})
                            .then(function (response) {
                                switch (Number(response.data)) {
                                    case 0:
                                        $("#updateAuthorityModal").modal("hide");
                                        $scope.search();
                                        CommonFunction.showPopUpMsg("Thông báo", "Thêm mới thành công!", "success");
                                        $scope.authItem.id = "";
                                        $scope.authItem.authKey = '';
                                        $scope.authItem.description = '';
                                        break;
                                    case 1:
                                        $("#updateAuthorityModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Có lỗi trong quá trình xử lý, vui lòng thử lại!", "error");
                                        break;
                                    case 2:
//                                        $("#updateAuthorityModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Bạn cần nhập đầy đủ các thông tin bắt buộc !", "error");

                                        break;
                                    case 3:
//                                        $("#updateAuthorityModal").modal("hide");
                                        CommonFunction.showPopUpMsg("Thông báo", "Mã chức năng đã tồn tại!", "error");

                                        break;
                                }
                            });
                }

            }

        };


        $scope.onEditAuthority = function (auth) {
            $scope.labelTitle = "Edit";
            $scope.authItem.id = auth[0];
            $scope.authItem.authKey = auth[1];
            $scope.authItem.authority = auth[2];
            $scope.authItem.description = auth[3];
            $scope.getListAuthParent();
            $("#authParentUpdate").select2();
            $scope.authItem.fid = auth[5];
            $("#authParentUpdate").select2("val", '' + $scope.authItem.fid);
            
            
        };

        $scope.onAddAuthority = function () {
            $scope.labelTitle = "Add";
            $scope.authItem.id = "";
            $scope.authItem.authKey = '';
            $scope.authItem.authority = '';
            $scope.authItem.description = '';
            $scope.getListAuthParent();
            $("#authParentUpdate").select2();
            $("#authParentUpdate").select2("val", '0');
        };

        $scope.onDeleteAuthority = function (id) {
            $scope.search.basic = 1;
            $scope.deleteId = id;
        };
        $scope.deleteAuthority = function () {
            var call = {id: $scope.deleteId};
            var auth = JSON.parse(JSON.stringify(call));
            $http.post(preUrl + "/system/authority/delete", auth, {headers: {'Content-Type': 'application/json'}})
                    .then(function (response) {
                        switch (Number(response.data)) {
                            case 0:
                                $("#deleteAuthorityModal").modal("hide");
                                $scope.search();
                                CommonFunction.showPopUpMsg("Thông báo", "Xóa thành công!", "success");
                                break;
                            case 1:
                                $("#deleteAuthorityModal").modal("hide");
                                CommonFunction.showPopUpMsg("Thông báo", "Có lỗi trong quá trình xử lý, vui lòng thử lại!", "error");
                                break;
                            case 3:
                                $("#deleteAuthorityModal").modal("hide");
                                CommonFunction.showPopUpMsg("Thông báo", "Chức năng không tồn tại!", "error");
                                break;
                            case 4:
                                $("#deleteAuthorityModal").modal("hide");
                                CommonFunction.showPopUpMsg("Thông báo", "Không thể xóa chức năng do chức năng đã được gán vào nhóm quyền!", "error");
                                break;
                            case 5:
                                $("#deleteAuthorityModal").modal("hide");
                                CommonFunction.showPopUpMsg("Thông báo", "Không thể xóa chức năng do chức năng trên có chức năng phụ thuộc!", "error");
                                break;
                        }
                    });
        };

    $(document).ready(function () {
        $timeout(function(){
            $("#authParentUpdate").select2();
            $("#authParentUpdate").select2("val", '0');
        },0);
    });

    }]);