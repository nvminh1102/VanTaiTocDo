refineFullNameDeclaration = function(myfield) {
    var fullName = myfield.value;
    fullName = fullName.toUpperCase();
    //cat nhung khoang trang thua
    var last = "";
    var strReturn = "";
    var current;
    for (var i = 0; i < fullName.length; i++) {
        current = fullName.charAt(i).toString();
        if (last == " ") {
            if (current != " ")
                strReturn += current;
        } else {
            strReturn += current;
        }
        last = current;
    }
    document.getElementById(myfield.id).value = strReturn;
}

refineFullName = function(myfield) {

    var lowerChar = new Array(
            "à", "á", "ạ", "ả", "ã", "â", "ầ", "ấ", "ẩ", "ậ", "ẫ", "ă", "ằ", "ắ", "ặ", "ẳ", "ẵ",
            "đ", "è", "é", "ẹ", "ẻ", "ẽ", "ê", "ề", "ế", "ệ", "ể", "ễ", "ì", "í", "ị", "ỉ", "ĩ",
            "ơ", "ớ", "ợ", "ở", "ỡ", "ờ",
            "ò", "ó", "ọ", "ỏ", "õ", "ô", "ồ", "ố", "ộ", "ổ", "ỗ", "ù", "ú", "ụ", "ủ", "ũ", "ư", "ừ", "ứ", "ự", "ử", "ữ",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
            );
    var upperChar = new Array(
            "À", "Á", "Ạ", "Ả", "Ã", "Â", "Ầ", "Ấ", "Ẩ", "Ậ", "Ẫ", "Ă", "Ằ", "Ắ", "Ặ", "Ẳ", "Ẵ",
            "Đ", "È", "É", "Ẹ", "Ẻ", "Ẽ", "Ê", "Ề", "Ế", "Ệ", "Ể", "Ễ", "Ì", "Í", "Ị", "Ỉ", "Ĩ",
            "Ơ", "Ớ", "Ợ", "Ở", "Ỡ", "Ờ",
            "Ò", "Ó", "Ọ", "Ỏ", "Õ", "Ô", "Ồ", "Ố", "Ộ", "Ổ", "Ỗ", "Ù", "Ú", "Ụ", "Ủ", "Ũ", "Ư", "Ừ", "Ứ", "Ự", "Ử", "Ữ",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
            );
    var fullName = myfield.value;
    var newName = "";
    var last = "";
    var current;
    for (var i = 0; i < fullName.length; i++) {
        current = fullName.charAt(i).toString();

        if (last == "") {
            var check = true;
            for (var j = 0; j < lowerChar.length; j++) {
                if (lowerChar[j] == current) {
                    newName += upperChar[j].toString();
                    check = false;
                    break;
                }
            }
            if (check)
                newName += current;
        }
        else if (last == " ") {
            if (current != " ") {
                var check = true;
                for (var j = 0; j < lowerChar.length; j++) {
                    if (lowerChar[j] == current) {
                        newName += upperChar[j].toString();
                        check = false;
                        break;
                    }
                }
                if (check)
                    newName += current;
            }
        } else {
            var check = true;
            for (var j = 0; j < lowerChar.length; j++) {
                if (upperChar[j] == current) {
                    newName += lowerChar[j].toString();
                    check = false;
                    break;
                }
            }
            if (check)
                newName += current;
        }
        last = current;
    }

    document.getElementById(myfield.id).value = newName;
    newName = newName.toProperCase().toPlaces().toStartCase();
    document.getElementById(myfield.id).value = newName;
}

textBoxNull = function(id) {
    var value = document.getElementById(id).value;
    if ($.trim(value) == '') {
        return true;
    }
    return false;
}

selectNull = function(id) {
    var value = document.getElementById(id).value;
    if ($.trim(value) == '' || value == '-1') {
        return true;
    }

    return false;
}
selectValNull = function(idName) {
    var value = $(idName).select2("val"); //document.getElementById(id).value;
    if ($.trim(value) == '' || value == '-1') {
        return true;
    }
    return false;
}
attach3BoxDate = function(formName) {

    var dd = document.getElementById(formName + ".birthDate1").value;
    var mm = document.getElementById(formName + ".birthDate2").value;
    var yyyy = document.getElementById(formName + ".birthDate3").value;

    var full = yyyy;
    if (mm != null && mm != '')
        full = mm + '/' + full;

    if (dd != null && dd != '')
        full = dd + '/' + full;

    return full;
}

extract3BoxDate = function(dateString, formName) {
    var yyyy = dateString.replace(/(\d{2}\/)*/, "");
    var mm = dateString.replace(/(\/)?\d{4}/, "").replace(/\d{2}\//, "");
    var dd = dateString.replace(/(\/)?(\d{2}\/)?\d{4}/, "");
    document.getElementById(formName + ".birthDate1").value = dd;
    document.getElementById(formName + ".birthDate2").value = mm;
    document.getElementById(formName + ".birthDate3").value = yyyy;
}

closeDialog = function(id) {
    $('#' + id).trigger('close');
}

openDialog = function(id) {
    $('#' + id).lightbox_me();
}

//AnhVDN_Start
convertDate = function(input) {
    var id = input.id;
    var date = document.getElementById(id).value;
    if (validateDateBirthDayAlert(date)) {
        return;
    }
    //var id = id;
    var isNav = navigator.appName;
    var now = new Date();
    var ngay = now.getDate();
    if (ngay < 10) {
        ngay = "0" + ngay;
    }
    var thang = now.getMonth() + 1;
    var nam = 0;
    if (thang < 10) {
        thang = "0" + thang;
    }
    var currNgay = "";
    var currThang = "";
    var currNam = "";
    var currDate = "";
    var obj = document.getElementById(id);
    if ((isNav == "Microsoft Internet Explorer") || (isNav == "Opera")) {
        nam = now.getYear();
        //Dung cho Firefox	
    } else if (isNav == "Netscape") {
        nam = now.getYear();
        if (nam < 1900) {
            nam += 1900;
        }
    }
    //Kiem tra xem co ton tai id khong
    var strDate = obj.value;
    if (strDate != "" && (strDate.length < 4 || strDate.length > 7)) { //if strDate co gia tri 
        var index_ = strDate.indexOf("/");
        if (index_ == -1) { //Khong ton tai
            currNgay = strDate.substring(0, 2);
            currThang = strDate.substring(2, 4);
            currNam = strDate.substring(4, 8);

            if (currThang == null || currThang == "") {
                currThang = thang;
            } else {
                if (currThang.length < 2) {
                    currThang = "0" + currThang;
                }
            }

            if (currNam == null || currNam == "") {
                currNam = nam;
            } else if (currNam.length < 4) {
                var addedValue;
                if (currNam.length == 1) {
                    addedValue = nam.toString().substring(0, 3);
                } else if (currNam.length == 2) {
                    addedValue = nam.toString().substring(0, 2);
                    if (currNam > (parseInt(nam.toString().substring(2, 4)) + 5)) {
                        addedValue = (parseInt(addedValue) - 1).toString();
                    } else {
                        addedValue = nam.toString().substring(0, 2);
                    }
                } else {
                    addedValue = nam.toString().substring(0, 1);
                    if (currNam > nam.toString().substring(1, 4)) {
                        addedValue = (parseInt(addedValue) - 1).toString();
                    }
                }
                currNam = addedValue + currNam;

            }

            currDate = currNgay + "/" + currThang + "/" + currNam;
        }	//if (index_==-1)
        else {//Co ton tai
            currNgay = strDate.substring(0, index_);
            if (currNgay.length <= 1)
                currNgay = "0" + currNgay; //Neu ngay co 1 ky tu vi du 8 chuyen thanhg 08
            else if (currNgay.length > 2) {
                currNgay = currNgay.substring(0, 2);
            }
            strDate = strDate.substring(index_ + 1, strDate.length);//strDate gio chi co the chua nam va thang (da tach ngay)
            if (strDate == "") //Truong hop nay la strDate ban dau co dang "12/"
                currDate = currNgay + "/" + thang + "/" + nam;
            else {//Truong hop nay la strDate ban dau co dang "12/11"
                //Xu ly thang va nam 
                var index_ = strDate.indexOf("/");
                if (index_ == -1) {//Khong ton tai co nghia la chi co thang khong 
                    currThang = strDate;
                    if (currThang.length == 1)
                        currThang = "0" + currThang; //Neu ngay co 1 ky tu vi du chuyen thanh 08
                    else if (currThang.length > 2)
                        currThang = currThang.substring(0, 2);
                    currDate = currNgay + "/" + currThang + "/" + nam;
                }	//if (index_ == -1)
                else { //Co ton tai co nghia la co the ton tai nam	
                    currThang = strDate.substring(0, index_);
                    strDate = strDate.substring(index_ + 1, strDate.length); //strDate gio chi co the chua nam
                    if (currThang.length == 1)
                        currThang = "0" + currThang; //Neu ngay co 1 ky tu vi du chuyen thanhg 08
                    else if (currThang.length > 2)
                        currThang = currThang.substring(0, 2);
                    if (strDate == "")//Truong hop nay la strDate ban dau co dang "12/12/"
                        currDate = currNgay + "/" + currThang + "/" + nam;
                    else {//Co ton tai nam 12/12/2008	
                        currNam = strDate;
                        if (currNam.length > 4) {
                            currNam = currNam.substring(0, 4);
                        } else if (currNam.length < 4) {
                            var addedValue;
                            if (currNam.length == 1) {
                                addedValue = nam.toString().substring(0, 3);
                            } else if (currNam.length == 2) {
                                addedValue = nam.toString().substring(0, 2);
                                if (currNam > (parseInt(nam.toString().substring(2, 4)) + 5)) {
                                    addedValue = (parseInt(addedValue) - 1).toString();
                                } else {
                                    addedValue = nam.toString().substring(0, 2);
                                }
                            } else {
                                addedValue = nam.toString().substring(0, 1);
                                if (currNam > nam.toString().substring(1, 4)) {
                                    addedValue = (parseInt(addedValue) - 1).toString();
                                }
                            }
                            currNam = addedValue + currNam;
                        }
                        currDate = currNgay + "/" + currThang + "/" + currNam;

                    }
                }
            }
        }
        var dd = currDate.substring(0, 2);
        var mm = currDate.substring(3, 5);
        var yyyy = currDate.substring(6, currDate.length);
        if (isNaN(dd)) {
            dd = ngay;
        }
        if (isNaN(mm)) {
            mm = thang;
        }
        if (isNaN(yyyy)) {
            yyyy = nam;
        }
        if (validateDateBirthDayAlert(dd + "/" + mm + "/" + yyyy)) {
            //dijit.byId(id).attr("value", dd + "/" + mm + "/" + yyyy);
            document.getElementById(id).value = dd + "/" + mm + "/" + yyyy;
        }
    } else if (strDate != "" && strDate.length == 4) {
        var index_ = strDate.indexOf("/");
        if (index_ == -1) {
            //dijit.byId(id).attr("value", strDate);
            document.getElementById(id).value = strDate;
        } else {
            currThang = strDate.substring(0, index_);
            if (currThang.length == 1) {
                if (currThang == "0") {
                    currThang = thang;
                } else {
                    currThang = "0" + currThang; //Neu ngay co 1 ky tu vi du 8 chuyen thanhg 08
                }
            } else if (currThang.length >= 2) {
                currThang = currThang.substring(0, 2);
            } else if (currThang.length == 0) {
                currThang = thang;
            }
            currNam = strDate.substring(index_ + 1, strDate.length);
            if (currNam == null || currNam == "") {
                currNam = nam;
            }
            else if (currNam.length < 4) {
                var addedValue;
                if (currNam.length == 1) {
                    addedValue = nam.toString().substring(0, 2);
                    currNam = "0" + currNam;
                } else if (currNam.length == 2) {
                    addedValue = nam.toString().substring(0, 2);
                    if (currNam > (parseInt(nam.toString().substring(2, 4)) + 5)) {
                        addedValue = (parseInt(addedValue) - 1).toString();
                    } else {
                        addedValue = nam.toString().substring(0, 2);
                    }
                } else {
                    addedValue = nam.toString().substring(0, 1);
                    if (currNam > nam.toString().substring(1, 4)) {
                        addedValue = (parseInt(addedValue) - 1).toString();
                    }
                }
                currNam = addedValue + currNam;
            }
            if (validateDateBirthDayAlert(currThang + "/" + currNam)) {
                //dijit.byId(id).attr("value", currThang + "/" + currNam);
                document.getElementById(id).value = currThang + "/" + currNam;
            }
        }
    } else if (strDate != "" && strDate.length == 5) {
        var index_ = strDate.indexOf("/");
        if (index_ == -1) {
            currThang = strDate.substring(0, 1);
            if (currThang == "0") {
                currThang = thang;
            } else {
                currThang = "0" + currThang;//Neu ngay co 1 ky tu vi du 8 chuyen thanhg 08
            }
            currNam = strDate.substring(1, 5);
            if (validateDateBirthDayAlert(currThang + "/" + currNam)) {
                //dijit.byId(id).attr("value", currThang + "/" + currNam);
                document.getElementById(id).value = currThang + "/" + currNam;
            }
        } else {
            currThang = strDate.substring(0, index_);
            if (currThang.length == 1) {
                if (currThang == "0") {
                    currThang = thang;
                } else {
                    currThang = "0" + currThang;//Neu ngay co 1 ky tu vi du 8 chuyen thanhg 08
                }
            } else if (currThang.length >= 2) {
                currThang = currThang.substring(0, 2);
            } else if (currThang.length == 0) {
                currThang = thang;
            }
            currNam = strDate.substring(index_ + 1, strDate.length);
            if (currNam.length < 4) {
                var addedValue;
                if (currNam.length == 1) {
                    addedValue = nam.toString().substring(0, 2);
                    currNam = "0" + currNam;
                } else if (currNam.length == 2) {
                    addedValue = nam.toString().substring(0, 2);
                    if (currNam > (parseInt(nam.toString().substring(2, 4)) + 5)) {
                        addedValue = (parseInt(addedValue) - 1).toString();
                    } else {
                        addedValue = nam.toString().substring(0, 2);
                    }
                } else {
                    addedValue = nam.toString().substring(0, 1);
                    if (currNam > nam.toString().substring(1, 4)) {
                        addedValue = (parseInt(addedValue) - 1).toString();
                    }
                }
                currNam = addedValue + currNam;
            }
            if (validateDateBirthDayAlert(currThang + "/" + currNam)) {
                //dijit.byId(id).attr("value", currThang + "/" + currNam);
                document.getElementById(id).value = currThang + "/" + currNam;
            }
        }
    } else if (strDate != "" && strDate.length == 6) {
        var index_ = strDate.indexOf("/");
        if (index_ == -1) {
            currNgay = strDate.substring(0, 2);
            currThang = strDate.substring(2, 4);
            currNam = strDate.substring(4, 6);
            if (currThang.valueOf() > 12) {
                currThang = strDate.substring(0, 2);
                currNam = strDate.substring(2, 6);
                if (validateDateBirthDayAlert(currThang + "/" + currNam)) {
                    //dijit.byId(id).attr("value", currThang + "/" + currNam);
                    document.getElementById(id).value = currThang + "/" + currNam;
                }
            } else {
                currNam = strDate.substring(4, 6);
                if (currNam.length < 4) {
                    var addedValue;
                    addedValue = nam.toString().substring(0, 2);
                    if (currNam > (parseInt(nam.toString().substring(2, 4)) + 5)) {
                        addedValue = (parseInt(addedValue) - 1).toString();
                    } else {
                        addedValue = nam.toString().substring(0, 2);
                    }
                    currNam = addedValue + currNam;
                }
                if (validateDateBirthDayAlert(currNgay + "/" + currThang + "/" + currNam)) {
                    //dijit.byId(id).attr("value", currNgay + "/" + currThang + "/" + currNam);
                    document.getElementById(id).value = currNgay + "/" + currThang + "/" + currNam;
                }
            }
        } else {
            currThang = strDate.substring(0, index_);
            if (currThang.length == 1) {
                if (currThang == "0") {
                    currThang = thang;
                } else {
                    currThang = "0" + currThang;
                }
            } else if (currThang.length >= 2) {
                currThang = currThang.substring(0, 2);
            } else if (currThang.length == 0) {
                currThang = thang;
            }
            currNam = strDate.substring(index_ + 1, strDate.length);
            if (currNam.length < 4) {
                var addedValue;
                if (currNam.length == 1) {
                    addedValue = nam.toString().substring(0, 2);
                    currNam = "0" + currNam;
                } else if (currNam.length == 2) {
                    addedValue = nam.toString().substring(0, 2);
                    if (currNam > (parseInt(nam.toString().substring(2, 4)) + 5)) {
                        addedValue = (parseInt(addedValue) - 1).toString();
                    } else {
                        addedValue = nam.toString().substring(0, 2);
                    }
                } else {
                    addedValue = nam.toString().substring(0, 1);
                    if (currNam > nam.toString().substring(1, 4)) {
                        addedValue = (parseInt(addedValue) - 1).toString();
                    }
                }
                currNam = addedValue + currNam;
            }
            if (validateDateBirthDayAlert(currThang + "/" + currNam)) {
                //dijit.byId(id).attr("value", currThang + "/" + currNam);
                document.getElementById(id).value = currThang + "/" + currNam;
            }
        }
    } else if (strDate != "" && strDate.length == 7) {
        var index_ = strDate.indexOf("/");
        if (index_ == -1) {
            if (validateDateBirthDayAlert(document.getElementById(id).value)) {
                //dijit.byId(id).attr("value", dijit.byId(id).attr("value"));
                document.getElementById(id).value = document.getElementById(id).value;
            }
        } else {
            currNgay = strDate.substring(0, index_);
            currThang = strDate.substring(index_ + 1, strDate.length);
            var indexCheck_ = currThang.indexOf("/");
            if (indexCheck_ == -1) {
                currThang = strDate.substring(0, index_);
                if (currThang.length == 1) {
                    if (currThang == "0") {
                        currThang = thang;
                    } else {
                        currThang = "0" + currThang;
                    }
                } else if (currThang.length >= 2) {
                    currThang = currThang.substring(0, 2);
                } else if (currThang.length == 0) {
                    currThang = thang;
                }
                currNam = strDate.substring(index_ + 1, strDate.length);
                if (currNam.length < 4) {
                    var addedValue;
                    if (currNam.length == 1) {
                        addedValue = nam.toString().substring(0, 2);
                        currNam = "0" + currNam;
                    } else if (currNam.length == 2) {
                        addedValue = nam.toString().substring(0, 2);
                        if (currNam > (parseInt(nam.toString().substring(2, 4)) + 5)) {
                            addedValue = (parseInt(addedValue) - 1).toString();
                        } else {
                            addedValue = nam.toString().substring(0, 2);
                        }
                    } else {
                        addedValue = nam.toString().substring(0, 1);
                        if (currNam > nam.toString().substring(1, 4)) {
                            addedValue = (parseInt(addedValue) - 1).toString();
                        }
                    }
                    currNam = addedValue + currNam;
                }
                if (validateDateBirthDayAlert(currThang + "/" + currNam)) {
                    //dijit.byId(id).attr("value", currThang + "/" + currNam);
                    document.getElementById(id).value = currThang + "/" + currNam;
                }
            } else {
                currNgay = strDate.substring(0, index_);
                if (currNgay.length <= 1)
                    currNgay = "0" + currNgay;
                else if (currNgay.length > 2) {
                    currNgay = currNgay.substring(0, 2);
                }
                strDate = strDate.substring(index_ + 1, strDate.length);
                if (strDate == "")
                    currDate = currNgay + "/" + thang + "/" + nam;
                else {
                    //Xu ly thang va nam 
                    var index_ = strDate.indexOf("/");
                    if (index_ == -1) {
                        currThang = strDate;
                        if (currThang.length == 1)
                            currThang = "0" + currThang;
                        else if (currThang.length > 2)
                            currThang = currThang.substring(0, 2);
                        currDate = currNgay + "/" + currThang + "/" + nam;
                    }
                    else {
                        currThang = strDate.substring(0, index_);
                        strDate = strDate.substring(index_ + 1, strDate.length);
                        if (currThang.length == 1)
                            currThang = "0" + currThang;
                        else if (currThang.length > 2)
                            currThang = currThang.substring(0, 2);
                        if (strDate == "")
                            currDate = currNgay + "/" + currThang + "/" + nam;
                        else {
                            currNam = strDate;
                            if (currNam.length > 4) {
                                currNam = currNam.substring(0, 4);
                            } else if (currNam.length < 4) {
                                var addedValue;
                                if (currNam.length == 1) {
                                    addedValue = nam.toString().substring(0, 3);
                                } else if (currNam.length == 2) {
                                    addedValue = nam.toString().substring(0, 2);
                                    if (currNam > (parseInt(nam.toString().substring(2, 4)) + 5)) {
                                        addedValue = (parseInt(addedValue) - 1).toString();
                                    } else {
                                        addedValue = nam.toString().substring(0, 2);
                                    }
                                } else {
                                    addedValue = nam.toString().substring(0, 1);
                                    if (currNam > nam.toString().substring(1, 4)) {
                                        addedValue = (parseInt(addedValue) - 1).toString();
                                    }
                                }
                                currNam = addedValue + currNam;
                            }
                            currDate = currNgay + "/" + currThang + "/" + currNam;

                        }
                    }
                }
                if (validateDateBirthDayAlert(currNgay + "/" + currThang + "/" + currNam)) {
                    //dijit.byId(id).attr("value", currNgay + "/" + currThang + "/" + currNam);
                    document.getElementById(id).value = currNgay + "/" + currThang + "/" + currNam
                }
            }
        }
    }
}
validateDateBirthDayAlert = function(date) {
    var trimDate = $.trim(date);
    var partern1 = /^\d{2}\/\d{2}\/\d{4}$/;
    var partern2 = /^\d{2}\/\d{4}$/;
    var partern3 = /^\d{4}$/;

    var iDay = null;
    var iMonth = null;
    var iYear = null;
    if (partern3.test(trimDate)) {
        iYear = trimDate;
    } else if (partern2.test(trimDate)) {
        var split = trimDate.split("/");
        iMonth = split[0];
        iYear = split[1];
    } else if (partern1.test(trimDate)) {
        var split = trimDate.split("/");
        iDay = split[0];
        iMonth = split[1];
        iYear = split[2];
    } else {
        return false;
    }

    if (iDay != null) {
        iDay = parseInt(iDay, 10);
        iMonth = parseInt(iMonth, 10);
        iYear = parseInt(iYear, 10);
        var testDate = new Date(iYear, iMonth - 1, iDay);
        if (testDate.getDate() == iDay) {
            if (testDate.getMonth() + 1 == iMonth) {
                if (testDate.getFullYear() == iYear) {

                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    if (iMonth != null && iMonth > 12) {
        return false;
    }
    if (iYear != null && iYear < 1900) {
        return false;
    }
    if (iMonth != null && iMonth == 0) {
        return false;
    }
    if (iDay != null && iDay == 0) {
        return false;
    }
    return true;
}

validateDateInput = function(id) {
    var date = document.getElementById(id).value;
    var trimDate = $.trim(date);
    if(trimDate == '') return true;
    
    var partern1 = /^\d{2}\/\d{2}\/\d{4}$/;
    var partern2 = /^\d{2}\/\d{4}$/;
    var partern3 = /^\d{4}$/;

    var iDay = null;
    var iMonth = null;
    var iYear = null;
    if (partern3.test(trimDate)) {
        iYear = trimDate;
    } else if (partern2.test(trimDate)) {
        var split = trimDate.split("/");
        iMonth = split[0];
        iYear = split[1];
    } else if (partern1.test(trimDate)) {
        var split = trimDate.split("/");
        iDay = split[0];
        iMonth = split[1];
        iYear = split[2];
    } else {
        return false;
    }

    if (iDay != null) {
        iDay = parseInt(iDay, 10);
        iMonth = parseInt(iMonth, 10);
        iYear = parseInt(iYear, 10);
        var testDate = new Date(iYear, iMonth - 1, iDay);
        if (testDate.getDate() == iDay) {
            if (testDate.getMonth() + 1 == iMonth) {
                if (testDate.getFullYear() == iYear) {

                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    if (iMonth != null && iMonth > 12) {
        return false;
    }
    if (iYear != null && iYear < 1900) {
        return false;
    }
    if (iMonth != null && iMonth == 0) {
        return false;
    }
    if (iDay != null && iDay == 0) {
        return false;
    }
    return true;
}

validateDateInputFull = function(id) {
    var date = document.getElementById(id).value;
    var trimDate = $.trim(date);
    if(trimDate == '') return true;
    
    var partern1 = /^\d{2}\/\d{2}\/\d{4}$/;

    var iDay = null;
    var iMonth = null;
    var iYear = null;
    if (partern1.test(trimDate)) {
        var split = trimDate.split("/");
        iDay = split[0];
        iMonth = split[1];
        iYear = split[2];
    } else {
        return false;
    }

    if (iDay != null) {
        iDay = parseInt(iDay, 10);
        iMonth = parseInt(iMonth, 10);
        iYear = parseInt(iYear, 10);
        var testDate = new Date(iYear, iMonth - 1, iDay);
        if (testDate.getDate() == iDay) {
            if (testDate.getMonth() + 1 == iMonth) {
                if (testDate.getFullYear() == iYear) {

                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    if (iMonth != null && iMonth > 12) {
        return false;
    }
    if (iYear != null && iYear < 1900) {
        return false;
    }
    if (iMonth != null && iMonth == 0) {
        return false;
    }
    if (iDay != null && iDay == 0) {
        return false;
    }
    return true;
}

validateBirthDay = function(id) {
    //Validate Birthday
    //var date = dijit.byId(id).attr("value");
    var date = document.getElementById(id).value;
    var name = "Ngày sinh";
    //if (date == null) return null;
    var trimDate = $.trim(date);
    //if (trimDate == "") return null;
    var partern1 = /^\d{2}\/\d{2}\/\d{4}$/;
    var partern2 = /^\d{2}\/\d{4}$/;
    var partern3 = /^\d{4}$/;

    var iDay = null;
    var iMonth = null;
    var iYear = null;
    if (partern3.test(trimDate)) {
        iYear = trimDate;
    } else if (partern2.test(trimDate)) {
        var split = trimDate.split("/");
        iMonth = split[0];
        iYear = split[1];
    } else if (partern1.test(trimDate)) {
        var split = trimDate.split("/");
        iDay = split[0];
        iMonth = split[1];
        iYear = split[2];
    } else if (date != null && date != "") {
        document.getElementById(id).focus();
        msg.alert(name + " phải thuộc một trong các định dạng(dd/mm/yyyy, mm/yyyy, yyyy)!", "<sd:Property>confirm.title</sd:Property>");
        //alert(name + " phải thuộc một trong các định dạng(dd/mm/yyyy, mm/yyyy, yyyy)");
        return false;
    }

    if (iDay != null) {
        iDay = parseInt(iDay, 10);
        iMonth = parseInt(iMonth, 10);
        iYear = parseInt(iYear, 10);
        var testDate = new Date(iYear, iMonth - 1, iDay);
        if (testDate.getDate() == iDay) {
            if (testDate.getMonth() + 1 == iMonth) {
                if (testDate.getFullYear() == iYear) {

                } else {
                    document.getElementById(id).focus();
                    msg.alert(name + " không hợp lệ!", "<sd:Property>confirm.title</sd:Property>");
                    return false;
                }
            } else {

                document.getElementById(id).focus();
                msg.alert(name + " không hợp lệ!", "<sd:Property>confirm.title</sd:Property>");
                return false;
            }
        } else {
            document.getElementById(id).focus();
            msg.alert(name + " không hợp lệ!", "<sd:Property>confirm.title</sd:Property>");
            return false;
        }
    }
    if (iMonth != null && iMonth > 12) {
        document.getElementById(id).focus();
        msg.alert(name + " không hợp lệ!", "<sd:Property>confirm.title</sd:Property>");
        return false;
    }
    //Qlqt_AnhVDN_20082013_Start
    var now = new Date();
    if (iYear > now.getFullYear()) {
        document.getElementById(id).focus();
        msg.alert("Năm sinh không được lớn hơn năm hiện tại!", "<sd:Property>confirm.title</sd:Property>");
        return false;
    }
    if (iMonth != null && iYear == now.getFullYear() && iMonth > (now.getMonth() + 1)) {
        document.getElementById(id).focus();
        msg.alert("Tháng, năm sinh không được lớn hơn tháng năm hiện tại!", "<sd:Property>confirm.title</sd:Property>");
        return false;
    }
    if (iDay != null && iMonth != null && iYear == now.getFullYear() && iMonth == (now.getMonth() + 1) && iDay > now.getDate()) {
        document.getElementById(id).focus();
        msg.alert("Ngày tháng năm sinh không được lớn hơn ngày tháng năm hiện tại!", "<sd:Property>confirm.title</sd:Property>");
        return false;
    }
    if (iYear != null && iYear < 1900) {
        document.getElementById(id).focus();
        msg.alert("Năm sinh không hợp lệ!", "<sd:Property>confirm.title</sd:Property>");
        return false;
    }
    if (iMonth != null && iMonth == 0) {
        document.getElementById(id).focus();
        msg.alert("Tháng sinh không hợp lệ!", "<sd:Property>confirm.title</sd:Property>");
        return false;
    }
    if (iDay != null && iDay == 0) {
        document.getElementById(id).focus();
        msg.alert("Ngày sinh không hợp lệ!", "<sd:Property>confirm.title</sd:Property>");
        return false;
    }
    return true;
}

refineFullName = function(myfield) {
    //var fullName = myfield.value.trim();
    //var newName = "";
    //newName = fullName.replace(/(\s)+/g, ' ');

    var lowerChar = new Array(
            "à", "á", "ạ", "ả", "ã", "â", "ầ", "ấ", "ẩ", "ậ", "ẫ", "ă", "ằ", "ắ", "ặ", "ẳ", "ẵ",
            "đ", "è", "é", "ẹ", "ẻ", "ẽ", "ê", "ề", "ế", "ệ", "ể", "ễ", "ì", "í", "ị", "ỉ", "ĩ",
            "ơ", "ớ", "ợ", "ở", "ỡ", "ờ",
            "ò", "ó", "ọ", "ỏ", "õ", "ô", "ồ", "ố", "ộ", "ổ", "ỗ", "ù", "ú", "ụ", "ủ", "ũ", "ư", "ừ", "ứ", "ự", "ử", "ữ",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
            );
    var upperChar = new Array(
            "À", "Á", "Ạ", "Ả", "Ã", "Â", "Ầ", "Ấ", "Ẩ", "Ậ", "Ẫ", "Ă", "Ằ", "Ắ", "Ặ", "Ẳ", "Ẵ",
            "Đ", "È", "É", "Ẹ", "Ẻ", "Ẽ", "Ê", "Ề", "Ế", "Ệ", "Ể", "Ễ", "Ì", "Í", "Ị", "Ỉ", "Ĩ",
            "Ơ", "Ớ", "Ợ", "Ở", "Ỡ", "Ờ",
            "Ò", "Ó", "Ọ", "Ỏ", "Õ", "Ô", "Ồ", "Ố", "Ộ", "Ổ", "Ỗ", "Ù", "Ú", "Ụ", "Ủ", "Ũ", "Ư", "Ừ", "Ứ", "Ự", "Ử", "Ữ",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
            );
    var fullName = myfield.value;
    var newName = "";
    var last = "";
    var current;
    for (var i = 0; i < fullName.length; i++) {
        current = fullName.charAt(i).toString();

        if (last == "") {
            var check = true;
            for (var j = 0; j < lowerChar.length; j++) {
                if (lowerChar[j] == current) {
                    newName += upperChar[j].toString();
                    check = false;
                    break;
                }
            }
            if (check)
                newName += current;
        }
        else if (last == " ") {
            if (current != " ") {
                var check = true;
                for (var j = 0; j < lowerChar.length; j++) {
                    if (lowerChar[j] == current) {
                        newName += upperChar[j].toString();
                        check = false;
                        break;
                    }
                }
                if (check)
                    newName += current;
            }
        } else {
            var check = true;
            for (var j = 0; j < lowerChar.length; j++) {
                if (upperChar[j] == current) {
                    newName += lowerChar[j].toString();
                    check = false;
                    break;
                }
            }
            if (check)
                newName += current;
        }
        last = current;
    }

    document.getElementById(myfield.id).value = newName;
    newName = newName.toProperCase().toPlaces().toStartCase().toDigitalCase();
    document.getElementById(myfield.id).value = newName;
}

trim = function(myfield) {
    $(myfield).val($(myfield).val().trim());
}

String.prototype.toProperCase = function()
{
    return this.toLowerCase().replace(/^(.)|\s(.)/g, 
    function($1) { return $1.toUpperCase(); });
}
String.prototype.toPlaces= function()    {
    return this.replace(/((,\s)?(Thị Trấn|Công An|Thị Xã|Thành Phố|Khu Phố|Số Nhà|Khu Tập Thể|Ấp|Làng|Xã|Phường|Quận|Huyện|Tỉnh|Nay Là|Đường|\(nay Là))/g, 
    function($1) { return $1.toLowerCase(); });
}
String.prototype.toStartCase= function()    {
    return this.replace(/^./g, 
    function($1) { return $1.toUpperCase(); });
}
String.prototype.toDigitalCase= function()    {
    return this.replace(/^(.)|\s([^ ]*\d[^ ,.&|])/g, 
    function($1) { return $1.toUpperCase(); });
}
//AnhVDN_End