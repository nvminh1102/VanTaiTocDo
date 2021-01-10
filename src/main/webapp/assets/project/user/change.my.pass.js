/**
 * Created by Admin on 12/22/2017.
 */
app.controller('frameworkCtrl',['$scope','$http','$timeout' ,function ($scope,$http,$timeout) {
    $scope.passwordCurrent="";
    $scope.passwordNew="";
    $scope.confirmPassword="";

    $scope.change=function () {
        if(validate()){
            $('#btn-check').button('complete');
            $http.post(preUrl+"/system/user/change-my-pass",{},{params: {passwordCurrent:$scope.passwordCurrent,passwordNew:$scope.passwordNew}})
                .then(function (response) {
                    if(response!=null && response!='underfined' && response.status==200){
                        switch(response.data) {
                            case 1:
                                toastr.success($("#changsuccess").text());
                                $timeout(function () {
                                    window.location.href=preUrl+"/login";
                                }, 1000);
                                break;
                            case 2:
                                toastr.error($("#checkoldpass").text());
                                break;
                            default:
                                $scope.messageStatus="Có lỗi xảy ra, hãy thử lại sau!";
                                $("#Message").modal('show');
                                break;
                        }

                    }else{
                        $scope.messageStatus="Có lỗi xảy ra, hãy thử lại sau!";
                        $("#Message").modal('show');
                    }

                },
                function(response){
                    $scope.messageStatus="Có lỗi xảy ra, hãy thử lại sau!";
                    $("#Message").modal('show');
                });

        }
        $('#btn-check').button('complete');
    };

    function validate() {
        var mediumRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        $scope.messageCurrent="";
        $scope.messageNew="";
        $scope.messageConfirm="";
        if($scope.passwordCurrent==null || $scope.passwordCurrent=='undefined' || $scope.passwordCurrent.length==0){
            $scope.messageCurrent=$("#detrong").text();

        }
        if($scope.passwordNew==null || $scope.passwordNew=='undefined' || $scope.passwordNew.length==0){
            $scope.messageNew=$("#detrong").text();

        }
        if($scope.confirmPassword==null || $scope.confirmPassword=='undefined' || $scope.confirmPassword.length==0){
            $scope.messageConfirm=$("#detrong").text();

        }else if(!isUndefined($scope.passwordCurrent)&&!(isUndefined($scope.passwordNew))){
//            if(!mediumRegex.test($scope.passwordNew)){
//                $scope.messageNew=$("#passwordvalidate").text();
//            }else 
                if($scope.confirmPassword!==$scope.passwordNew){
                $scope.messageConfirm=$("#comfirmpassword").text();
                return false;
            }
            else {
               return true;

            }
        }

    }

}]);