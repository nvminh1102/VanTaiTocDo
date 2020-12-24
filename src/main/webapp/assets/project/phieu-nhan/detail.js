app.controller('tccc', ['$scope', '$http', '$filter', '$window', 'fileUpload', '$timeout', '$q', function ($scope, $http, $filter, $window, fileUpload, $timeout, $q) {

    $(document).ready(function () {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/search", {params: {idNotary: idNotary}})
                .then(function (response) {
                    $scope.notaryInfoView = response.data;
                });
    });

    $scope.tab = tab;

    $scope.listData_bonhiem_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_tuchoibonhiem_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_miennhiem_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_bonhiemlai_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_dky_hncc_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_dinhchi_hncc_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_caplaithe_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_tuchoicaplaithe_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_thuhoithe_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_xuphat_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_tcmiennhiem_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_tuchoibonhiemlai_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};
    $scope.listData_huy_dinhchi_hncc_ccv = {items: "", rowCount: 0, numberPerPage: 25, pageNumber: 1, pageList: [], pageCount: 0};

    $scope.searchbonhiem = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 1, count: count}})
                .then(function (response) {
                    $scope.listData_bonhiem_ccv = response.data;
                }
                );
    };
    $scope.searchtuchoibonhiem = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 2, count: count}})
                .then(function (response) {
                    $scope.listData_tuchoibonhiem_ccv = response.data;
                });
    };
    $scope.searchmiennhiem = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 3, count: count}})
                .then(function (response) {
                    $scope.listData_miennhiem_ccv = response.data;
                });
    };

    $scope.searchbonhiemlai = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 4, count: count}})
                .then(function (response) {
                    $scope.listData_bonhiemlai_ccv = response.data;
                }
                );
    };
    $scope.searchdkyhncc = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 5, count: count}})
                .then(function (response) {
                    $scope.listData_dky_hncc_ccv = response.data;
                }
                );
    };
    $scope.searchdinhchihncc = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 6, count: count}})
                .then(function (response) {
                    $scope.listData_dinhchi_hncc_ccv = response.data;
                }
                );
    };
    $scope.searchcaplaithehncc = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 7, count: count}})
                .then(function (response) {
                    $scope.listData_caplaithe_ccv = response.data;
                }
                );
    };

    $scope.searchthuhoithehncc = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 8, count: count}})
                .then(function (response) {
                    $scope.listData_thuhoithe_ccv = response.data;
                }
                );
    };
    $scope.searchxuphatccv = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 9, count: count}})
                .then(function (response) {
                    $scope.listData_xuphat_ccv = response.data;
                }
                );
    };

    $scope.searchtcmiennhiem = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 10, count: count}})
                .then(function (response) {
                    $scope.listData_tcmiennhiem_ccv = response.data;
                }
                );
    };

    $scope.searchtccaplaithehncc = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 11, count: count}})
                .then(function (response) {
                    $scope.listData_tuchoicaplaithe_ccv = response.data;
                }
                );
    };

    $scope.search_tuchoibonhiemlai = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 12, count: count}})
                .then(function (response) {
                    $scope.listData_tuchoibonhiemlai_ccv = response.data;
                }
                );
    };

    $scope.search_huy_tam_dinh_chi_hncc = function (count) {
        $http.get(preUrl + "/chi-tiet-thong-tin-ccv/searchCCV", {params: {idNotary: idNotary, tab: 13, count: count}})
                .then(function (response) {
                    $scope.listData_huy_dinhchi_hncc_ccv = response.data;
                }
                );
    };

    switch (tab) {
        case "1":/*bổ nhiệm*/
            $('.nav-tabs a[href="#bonhiem_ccv"]').tab('show');
            $scope.searchbonhiem(false);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-bo-nhiem-ccv").addClass("active");
            break;

        case "2":/*tc bo nhiem*/
            $('.nav-tabs a[href="#tuchoibonhiem_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(false);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-bo-nhiem-ccv").addClass("active");
            break;

        case "3":/*mien nhiem*/
            $('.nav-tabs a[href="#miennhiem_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(false);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-mien-nhiem-ccv").addClass("active");
            break;

        case "4":/*bn lại*/
            $('.nav-tabs a[href="#bonhiemlai_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(false);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-bo-nhiem-lai-ccv").addClass("active");
            break;

        case "5":
            $('.nav-tabs a[href="#dky_hncc_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(false);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-hncc-va-cap-the-ccv").addClass("active");
            break;

        case "6":
            $('.nav-tabs a[href="#dinhchi_hncc_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(false);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-tam-dinh-chi-hncc").addClass("active");
            break;

        case "7":
            $('.nav-tabs a[href="#caplaithe_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(false);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-cap-lai-the-ccv").addClass("active");
            break;

        case "8":
            $('.nav-tabs a[href="#thuhoithe_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(false);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-xoa-dang-ki-hncc").addClass("active");
            break;

        case "9":
            $('.nav-tabs a[href="#xuphat_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(false);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-thong-tin-xu-phat-vi-pham-hanh-chinh").addClass("active");
            break;

        case "10":/*tc mien nhiem*/
            $('.nav-tabs a[href="#tuchoimiennhiem_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(false);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-mien-nhiem-ccv").addClass("active");
            break;

        case "11":/* tc cap lai the*/
            $('.nav-tabs a[href="#tuchoicaplaithe_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(false);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-cap-lai-the-ccv").addClass("active");
            break;

        case "12":/* tu choi bo nhiem lai*/
            $('.nav-tabs a[href="#tuchoibonhiemlai_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(false);
            $scope.search_huy_tam_dinh_chi_hncc(true);

            $("#quan-ly-bo-nhiem-lai-ccv").addClass("active");
            break;

        case "13":/* huy tam dinh chi*/
            $('.nav-tabs a[href="#huy_dinhchi_hncc_ccv"]').tab('show');
            $scope.searchbonhiem(true);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(false);

            $("#quan-ly-tam-dinh-chi-hncc").addClass("active");
            break;

        default:/*bo nhiem*/
            $('.nav-tabs a[href="#bonhiem_ccv"]').tab('show');
            $scope.searchbonhiem(false);
            $scope.searchtuchoibonhiem(true);
            $scope.searchmiennhiem(true);
            $scope.searchbonhiemlai(true);
            $scope.searchdkyhncc(true);
            $scope.searchdinhchihncc(true);
            $scope.searchcaplaithehncc(true);
            $scope.searchthuhoithehncc(true);
            $scope.searchxuphatccv(true);
            $scope.searchtcmiennhiem(true);
            $scope.searchtccaplaithehncc(true);
            $scope.search_tuchoibonhiemlai(true);
            $scope.search_huy_tam_dinh_chi_hncc(true);
            $("#quan-ly-bo-nhiem-ccv").addClass("active");
            break;
    }
    $("#quan-ly-cong-chung-vien").addClass("active");

    $scope.whenClick_bonhiem_ccv = function () {
        $('.nav-tabs a[href="#bonhiem_ccv"]').tab('show');
        $scope.tab = "1";
        $scope.searchbonhiem(false);
    };

    $scope.whenClick_tuchoibonhiem_ccv = function () {
        $('.nav-tabs a[href="#tuchoibonhiem_ccv"]').tab('show');
        $scope.tab = "2";
        $scope.searchtuchoibonhiem(false);
    };

    $scope.whenClick_miennhiem_ccv = function () {
        $('.nav-tabs a[href="#miennhiem_ccv"]').tab('show');
        $scope.tab = "3";
        $scope.searchmiennhiem(false);
    };

    $scope.whenClick_bonhiemlai_ccv = function () {
        $('.nav-tabs a[href="#bonhiemlai_ccv"]').tab('show');
        $scope.tab = "4";
        $scope.searchbonhiemlai(false);
    };
    $scope.whenClick_dky_hncc_ccv = function () {
        $('.nav-tabs a[href="#dky_hncc_ccv"]').tab('show');
        $scope.tab = "5";
        $scope.searchdkyhncc(false);
    };
    $scope.whenClick_dinhchi_hncc_ccv = function () {
        $('.nav-tabs a[href="#dinhchi_hncc_ccv"]').tab('show');
        $scope.tab = "6";
        $scope.searchdinhchihncc(false);
    };
    $scope.whenClick_caplaithe_ccv = function () {
        $('.nav-tabs a[href="#caplaithe_ccv"]').tab('show');
        $scope.tab = "7";
        $scope.searchcaplaithehncc(false);
    };
    $scope.whenClick_thuhoithe_ccv = function () {
        $('.nav-tabs a[href="#thuhoithe_ccv"]').tab('show');
        $scope.tab = "8";
        $scope.searchthuhoithehncc(false);
    };
    $scope.whenClick_xuphat_ccv = function () {
        $('.nav-tabs a[href="#xuphat_ccv"]').tab('show');
        $scope.tab = "9";
        $scope.searchxuphatccv(false);
    };
    $scope.whenClick_tuchoimiennhiem_ccv = function () {
        $('.nav-tabs a[href="#tuchoimiennhiem_ccv"]').tab('show');
        $scope.tab = "10";
        $scope.searchtcmiennhiem(false);
    };
    $scope.whenClick_tuchoicaplaithe_ccv = function () {
        $('.nav-tabs a[href="#tuchoicaplaithe_ccv"]').tab('show');
        $scope.tab = "11";
        $scope.searchtccaplaithehncc(false);
    };
    $scope.whenClick_tuchoibonhiemlai_ccv = function () {
        $('.nav-tabs a[href="#tuchoibonhiemlai_ccv"]').tab('show');
        $scope.tab = "12";
        $scope.search_tuchoibonhiemlai(false);
    };
    $scope.whenClick_huy_dinhchi_hncc_ccv = function () {
        $('.nav-tabs a[href="#huy_dinhchi_hncc_ccv"]').tab('show');
        $scope.tab = "13";
        $scope.search_huy_tam_dinh_chi_hncc(false);
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
    /*load tooltip*/
    $scope.tooltip = function () {
        var defer = $q.defer();
        $timeout(function () {
            $("[data-toggle=popover]").popover();
            defer.resolve();
        }, 1000);
    };

    $('#thong-tin-ccv-lich-su').on('hidden.bs.modal', function (e) {
        $timeout(function () {
            $scope._hisInfo = {};
        }, 0)
    });
    $("#thong-tin-ccv-lich-su").on("shown.bs.modal", function () {});

    $scope.getNotaryHisByType = function(idObj,idNotary,tab, status_prac){
        $http.get(preUrl + "/quan-ly-chi-tiet-ho-so-ccv/getNotaryHisByType", {params: {idObj:idObj, idNotary:idNotary, tab:tab, status_prac: status_prac}})
            .then(function (response) {
                $scope._hisInfo=response.data;
                $("#thong-tin-ccv-lich-su").modal("show");
            });
    };

}]);