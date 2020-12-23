/**
 * Created by Admin on 12/21/2017.
 */
var app = angular.module('FrameworkBase', ["ngSanitize","autocomplete"]);

//Bat ky tu
var digitsOnly = /[1234567890]/g;
var digitsAndSemicolon = /[1234567890;]/g;
var digitsAndAsterisk = /[1234567890\\*]/g;
var digitsAndSlash =/[1234567890/]/g;

function restrictCharacters(myfield, e, restrictionType) {
    if (!e) var e = window.event
    if (e.keyCode) code = e.keyCode;
    else if (e.which) code = e.which;
    var character = String.fromCharCode(code);

    // if they pressed esc... remove focus from field...
    if (code==27) { this.blur(); return false; }

    // ignore if they are press other keys
    // strange because code: 39 is the down key AND ' key...
    // and DEL also equals .
    if (!e.ctrlKey && code!=9 && code!=8 && code!=36 && code!=37 && code!=38 && (code!=39 || (code==39 && character=="'")) && code!=40) {
        if (character.match(restrictionType)) {
            return true;
        } else {
            return false;
        }

    }
}
function updatePage($scope, response) {
debugger;
    if (response != null && response != 'undefined' && response.status == 200) {
        $scope.page = response.data;
        $scope.page.pageList = getPageList($scope.page);
        $scope.page.pageCount = getPageCount($scope.page);
    }
}
/*ap dung voi dinh dang 1,000,000 con neu muon 1.000.000 thi duoi'*/
app.directive('format', ['$filter', function ($filter) {
    return {
        require: '?ngModel',
        link: function (scope, elem, attrs, ctrl) {
            if (!ctrl) return;

            ctrl.$formatters.unshift(function (a) {
                return $filter(attrs.format)(ctrl.$modelValue)
            });

            ctrl.$parsers.unshift(function (viewValue) {
                var plainNumber = viewValue.replace(/[^\d|\-+|\.+]/g, '');
                plainNumber=plainNumber.replace(",",".");
                elem.val($filter(attrs.format)(plainNumber));
                return plainNumber;
            });
        }
    };
}]);

/*KHU VỰC PHÂN TRANG JAVASCRIPT*/
 var page={items:"",rowCount:0,numberPerPage:20,pageNumber:1,pageList:[],pageCount:0};
/*load tong trang va danh sach trang*/
function getPageCount(pageResult) {
    var pageCount=Math.ceil(pageResult.rowCount/pageResult.numberPerPage);
    return pageCount;
}

/*TRợ giúp tính toán số trang hiển thị khi hiện page*/
function getPageList(pagingResult) {
    var pages=[];
    var from = pagingResult.pageNumber  - 3;
    var to = pagingResult.pageNumber + 5;
    if (from < 0) {
        to -= from;
        from = 1;
    }

    if (from < 1) {
        from = 1;
    }

    if (to > pagingResult.pageCount) {
        to = pagingResult.pageCount;
    }

    for (var i=from; i<=to; i++ ) {
        pages.push(i);
    }
    return pages;
}


//convert date dd/mm/yyyy sang date cua he thong.
function formatDate(strDate) {
    if(strDate==null || strDate.length!=10) return null;
    var dateArray = strDate.split("/");
    var date = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
    // alert(moment(moment(date).format("YYYY/MM/DD"),"YYYY/MM/DD",true).isValid());
    if(moment(date,"YYYY-MM-DD",true).isValid()){
        return new Date(date);
    }else{
        return null;
    }

}

/*When click my-enter*/
app.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.myEnter);
                });

                event.preventDefault();
            }
        });
    };
});

/*<input type="text" datetime ng-model="defaultDate" dp-format="DD/MM/YYYY" min-date="minDate" max-date="maxDate"/>*/
/*$('#fromDateSign_').data("DateTimePicker").minDate(moment($('#toDateSign_').val(), "DD-MM-YYYY").toDate());*/
/*$('#toDateSign_').data("DateTimePicker").minDate(moment($('#fromDateSign_').val(), "DD-MM-YYYY").toDate());*/
app.directive('datetime', function() {
    return {
        restrict: 'A',
        require: 'ngModel',
        compile: function() {
            return {
                pre: function(scope, element, attrs, ngModelCtrl) {
                    var obj = {locale: 'vi-VN'};
                    var format = (!attrs.dpFormat) ? 'DD/MM/YYYY HH:mm:ss' : attrs.dpFormat;
                    obj.format = format;
                    obj.showClear = true;

                    var actualScopyThingy = element.scope();
                    if (isNotUndefined(attrs.minDate)) {
                        obj.minDate = actualScopyThingy.minDate;
                        if (isUndefined(obj.minDate)) {
                            obj.minDate = moment(attrs.minDate, format).toDate();
                        }
                    }
                    if (isNotUndefined(attrs.maxDate)) {
                        obj.maxDate = actualScopyThingy.maxDate;
                        if (isUndefined(obj.maxDate)) {
                            obj.maxDate = moment(attrs.maxDate, format).toDate();
                        }
                    }

                    // Initialize the date-picker
                    $(element).datetimepicker(obj).on('dp.change', function (e) {
                        if (e != null) {
                            scope.$apply(function () {
                                if(isNotUndefined($(element).val())){
                                    ngModelCtrl.$setViewValue(moment($(element).val(),format).toDate());
                                } else {
                                    ngModelCtrl.$setViewValue("");
                                }
                            });
                        }
                    });
                }
            }
        }
    }
});

function getWeekNumber(d) {
    // Copy date so don't modify original
    d = new Date(Date.UTC(d.getFullYear(), d.getMonth(), d.getDate()));
    // Set to nearest Thursday: current date + 4 - current day number
    // Make Sunday's day number 7
    d.setUTCDate(d.getUTCDate() + 4 - (d.getUTCDay()||7));
    // Get first day of year
    var yearStart = new Date(Date.UTC(d.getUTCFullYear(),0,1));
    // Calculate full weeks to nearest Thursday
    var weekNo = Math.ceil(( ( (d - yearStart) / 86400000) + 1)/7);
    // Return array of year and week number
    return weekNo;
}

function isUndefined(value){
    if(typeof value == "undefined" || value == null || value == "" || value.length == 0){
        return true;
    }
    return false;
}
function isUndefined(value){
    if(typeof value == "undefined" || value == null || value == "" || value.length == 0){
        return true;
    }
    return false;
}
function isNotUndefined(value){
    if(typeof value == "undefined" || value == null || value == "" || value.length == 0){
        return false;
    }
    return true;
}

function initLadda(selector) {
    var ladda = Ladda.create( document.querySelector(selector));
    ladda.start();
    return ladda;
}

function stopLadda(ladda) {
    ladda.stop();
    ladda.remove();
}

function stringToDate(_date, _format, _delimiter) {
    if(typeof _date == "undefined"|| _date == "" || _date == null) return "";
    var timenow = new Date();
    var formatLowerCase = _format.toLowerCase();
    var formatItems = formatLowerCase.split(_delimiter);
    var dateItems = _date.split(_delimiter);
    var monthIndex = formatItems.indexOf("mm");
    var dayIndex = formatItems.indexOf("dd");
    var yearIndex = formatItems.indexOf("yyyy");
    var month = parseInt(dateItems[monthIndex]);
    month -= 1;
    var formatedDate = new Date(dateItems[yearIndex], month, dateItems[dayIndex], timenow.getHours(), timenow.getMinutes(), timenow.getSeconds(), '00');
    return formatedDate;
}
angular.module('FrameworkBase').filter('filter', function() {
    return function(value, stringdate) {
        var stringDate=stringDate;
        var convertedDate=new Date(stringDate);
        return convertedDate
    };
});

app.service('fileUpload', ['$http', function ($http) {
  this.uploadFileToUrl = function (file, uploadUrl) {
    var fd = new FormData();
    fd.append('file', file);
    return $http.post(uploadUrl, fd, {
      transformRequest: angular.identity,
      headers: {'Content-Type': undefined}
    })
  }
}]);

function validateFileUpload(_file) {
  var text = _file.name;
  var idxDot = text.lastIndexOf(".") + 1;
  var extFile = text.substr(idxDot, text.length).toLowerCase();
  if (text.lastIndexOf(".") <= 0) {
    toastr.error("File không hợp lệ!");
    return false;
  }
  if (extFile == "jpg" || extFile == "jpeg" || extFile == "png" || extFile == "pdf" || extFile == "doc" || extFile == "docx" || extFile == "xls" || extFile == "xlsx" || extFile == "txt" || extFile == "rar" || extFile == "zip") {
    //TO DO
    //check file size max 5mb
    if (_file.size / 1024 / 1024 > 5) {
      toastr.error("Chỉ cho phép upload file có dung lượng nhỏ hơn 5mb!");
      return false;
    }
  } else {
    toastr.error("Chỉ cho phép upload file định dạng ảnh và tài liệu văn bản!");
    return false;
  }
  return true;
}
;