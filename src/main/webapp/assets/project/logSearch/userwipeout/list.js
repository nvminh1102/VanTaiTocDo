app.controller('frameworkCtrl',['$scope','$http','$filter','$window','$timeout','$q',function ($scope,$http,$filter,$window,$timeout,$q) {

    //basic
    $scope.flagOpenAdvanceSearch = true;
    $scope.showOrHideAdvanceSearchForm = function () {
        $scope.flagOpenAdvanceSearch = !$scope.flagOpenAdvanceSearch;
    };
    var urlSearch = "/system/user-wipeout/list/search";

    //1 : show, 0 : hiden
    $scope._nameRow = [];
    $scope._tableRow = [];
    $timeout(function(){
        for(var i=0; i <= $scope._numberRow; i++){
            var objRow = [];
            objRow[0] = 1;
            objRow[1] = $scope._nameRow[i];
            $scope._tableRow.push(objRow);
        }
    },0);

    $scope.listData = {items:"",rowCount:0,numberPerPage:10,pageNumber:1,pageList:[],pageCount:0};
    $scope.search={basic:0};
    var search = JSON.stringify($scope.search);

    $http.get(preUrl + urlSearch, {params: {search:search,offset:0,number:$scope.listData.numberPerPage}})
        .then(function (response) {
            $scope.listData=response.data;

            $scope.listData.pageCount=getPageCount($scope.listData);
            $scope.listData.pageList=getPageList($scope.listData);
            $scope.tooltip();

            $("#content").removeClass("hidden");
            $('#tableData').loadingView({'state':false});

        }, function (response) {
            $("#content").removeClass("hidden");
            $('#tableData').loadingView({'state':false});
            toastr.error("Error")
        });

    $scope.clearSearch = function() {
        $scope.laddaClear = initLadda('#ladda-button-clear');

        $scope.search={basic:0};
        $timeout(function () {
            stopLadda($scope.laddaClear);
            $(".search_select2").select2("val","");
            //$('.search-datetime').data("DateTimePicker").date(null);
            $('.search-datetime').data('DateTimePicker').clear();
            console.log($('.search-datetime').data('DateTimePicker'));

        },0);
    };

    //search data
    $scope.searchData = function () {
        if($("#formSearchBasic").parsley().validate()){
            $('#tableData').loadingView({'state':true});
            $scope.laddaSearch = initLadda('#ladda-button-search');

            $scope.listData.pageNumber=1;
            $scope.loadListData();
        }
    };

    //reload list
    $scope.loadListData=function () {
        var search = JSON.stringify($scope.search);
        $http.get(preUrl + urlSearch, {params: {search:search,offset:0,number:$scope.listData.numberPerPage}})
            .then(function (response) {
                $scope.listData=response.data;

                $scope.listData.pageCount=getPageCount($scope.listData);
                $scope.listData.pageList=getPageList($scope.listData);

                $scope.tooltip();
                stopLadda($scope.laddaSearch);
                $('#tableData').loadingView({'state':false});

            },function (response) {
                $('#tableData').loadingView({'state':false});
                toastr.error("Error");
                stopLadda($scope.laddaSearch);
            });
    };

    //reload page
    $scope.loadPageData = function (index) {
        if($("#formSearchBasic").parsley().validate()) {
            $('#tableData').loadingView({'state':true});
            $scope.laddaSearch = initLadda('#ladda-button-search');
            var search = JSON.stringify($scope.search);
            $scope.listData.pageNumber = index;
            $http.get(preUrl + urlSearch, {params: {search: search, offset: $scope.listData.numberPerPage * ($scope.listData.pageNumber - 1), number: $scope.listData.numberPerPage}})
                .then(function (response) {
                    $scope.listData.items = response.data.items;
                    $scope.listData.pageList = getPageList($scope.listData);
                    $scope.tooltip();

                    stopLadda($scope.laddaSearch);
                    $('#tableData').loadingView({'state':false});
                }, function (response) {
                    stopLadda($scope.laddaSearch);
                    $('#tableData').loadingView({'state':false});
                    toastr.error("Error");
                });
        }
    };

    //next page
    $scope.setNumberPerPage = function (numberPerPage) {
        $scope.listData.numberPerPage=numberPerPage;
        $scope.searchData();
    };

    //load tooltip
    $scope.tooltip=function () {
        var defer = $q.defer();
        $timeout(function(){
            $("[data-toggle=popover]").popover();
            defer.resolve();
        },1000);
    };

}]);