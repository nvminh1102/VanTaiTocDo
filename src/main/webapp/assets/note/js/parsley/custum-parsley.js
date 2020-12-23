/*!
 * ParsleyJS-LaraExtras.js
 * Version 0.4.2 - built Sun, Jun 19th 2016, 3:00 pm
 * hhttps://github.com/happyDemon/ParsleyJS-LaraExtras
 * Maxim Kerstens - <maxim.kerstens@gmail.com>
 * MIT Licensed
 */

// The source code below is generated by babel as
// ParsleyJS-LaraExtras is written in ECMAScript 6

(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? module.exports = factory(require('jquery'), require('moment')) : typeof define === 'function' && define.amd ? define(['jquery', 'moment'], factory) : global.laraParsley = factory(global.jQuery, global.moment);
})(this, function (jQuery, moment) {
    'use strict';

    // Check if the value is within a comma-separated list (val1,val2,..)
    window.Parsley.addValidator('in', {
        requirementType: 'string',
        validateString: function validateString(value, parameter) {
            var possibles = parameter.split(',');

            return possibles.indexOf(value) > -1;
        },
        messages: {
            en: 'The value should be one of the following: "%s".'
        }
    });

    // Check if the value is not in a comma-separated list (val1,val2,..)
    window.Parsley.addValidator('notIn', {
        requirementType: 'string',
        validateString: function validateString(value, parameter) {
            var possibles = parameter.split(',');

            return possibles.indexOf(value) == -1;
        },
        messages: {
            en: 'The value should not be one of the following: "%s".'
        }
    });

    // Valid date formats
    window.Parsley.options.dateFormats = ['DD/MM/YY', 'DD/MM/YYYY', 'MM/DD/YY', 'MM/DD/YYYY', 'YY/MM/DD', 'YYYY/MM/DD'];

    // Check if the value is a date
    window.Parsley.addValidator('date', {
        requirementType: 'boolean',
        validateString: function validateString(value, state, parsleyInstance) {
            return moment(value, formatDatePhpToJs.getDateFormatsOption(parsleyInstance), true).isValid();
        },
        messages: {
            en: 'You should provide a valid date.'
        }
    });

    // Check if the value is a date in a specific format
    window.Parsley.addValidator('dateFormat', {
        requirementType: 'string',
        validateString: function validateString(value, parameter) {
            return moment(value, formatDatePhpToJs.convert(parameter), true).isValid();
        },
        messages: {
            en: 'The date you entered is not in the right format (%s).'
        }
    });

    // Check if the value is a date before the specified date
    window.Parsley.addValidator('before', {
        requirementType: 'string',
        validateString: function validateString(value, parameter, parsleyInstance) {
            var dateFormats = formatDatePhpToJs.getDateFormatsOption(parsleyInstance);

            var beforeDate = moment(parameter, dateFormats, true);

            // If it's not a valid date, error
            if (beforeDate === false) return false;

            return moment(value, dateFormats) < beforeDate;
        },
        messages: {
            en: 'The date you entered should be before %s.'
        }
    });

    // Check if the value is a date before the specified date (from another input)
    window.Parsley.addValidator('beforeInput', {
        requirementType: 'string',
        validateString: function validateString(value, parameter, parsleyInstance) {
            var dateFormats = formatDatePhpToJs.getDateFormatsOption(parsleyInstance);
            var beforeInput = jQuery(parameter);

            // If we can't find the input, return true
            if (beforeInput.length == 0) return true;

            var beforeVal = beforeInput.val();

            // If the val is empty, return true
            if (beforeVal == '') return true;

            var beforeDate = moment(beforeVal, dateFormats, true);

            // If the before date isn't valid, error out
            if (beforeDate.isValid() === false) {
                console.warn(parameter + ' input does not contain a valid date');
                return false;
            }

            var thisDate = moment(value, dateFormats, true);

            // If the value's date isn't valid, error out
            if (thisDate.isValid() === false) {
                console.warn('the input being checked does not contain a valid date');
                return false;
            }

            return thisDate < beforeDate;
        },
        messages: {
            en: 'The date you entered should be before %s.'
        }
    });

    // Check if the value is a date before the specified date
    window.Parsley.addValidator('after', {
        requirementType: 'string',
        validateString: function validateString(value, parameter, parsleyInstance) {
            var dateFormats = formatDatePhpToJs.getDateFormatsOption(parsleyInstance);
            var afterDate = moment(parameter, dateFormats, true);

            // If it's not a valid date, error
            if (afterDate === false) return false;

            return moment(value, dateFormats) > afterDate;
        },
        messages: {
            en: 'The date you entered should be after %s.'
        }
    });

    // Check if the value is a date before the specified date (from another input)
    window.Parsley.addValidator('afterInput', {
        requirementType: 'string',
        validateString: function validateString(value, parameter, parsleyInstance) {
            var dateFormats = formatDatePhpToJs.getDateFormatsOption(parsleyInstance);
            var afterInput = jQuery(parameter);

            console.log(this, dateFormats);

            // If we can't find the input, return true
            if (afterInput.length == 0) return true;

            var afterVal = afterInput.val();

            // If the val is empty, return true
            if (afterVal == '') return true;

            var afterDate = moment(afterVal, dateFormats, true);

            // If the after date isn't valid, error out
            if (afterDate.isValid() === false) {
                console.warn(parameter + ' input does not contain a valid date');
                return false;
            }

            var thisDate = moment(value, dateFormats, true);

            // If the value's date isn't valid, error out
            if (thisDate.isValid() === false) {
                console.warn('the input being checked does not contain a valid date');
                return false;
            }

            return thisDate > afterDate;
        },
        messages: {
            en: 'The date you entered should be after %s.'
        }
    });

    // convert PHP date format to moment JS date format
    var formatDatePhpToJs = window.formatDatePhpToJs = {
        mapChars: {
            d: 'DD',
            D: 'ddd',
            j: 'D',
            l: 'dddd',
            N: 'E',
            S: function S() {
                return '[' + this.format('Do', true).replace(/\d*/g, '') + ']';
            },
            w: 'd',
            z: function z() {
                return this.format('DDD', true) - 1;
            },
            W: 'W',
            F: 'MMMM',
            m: 'MM',
            M: 'MMM',
            n: 'M',
            t: function t() {
                return this.daysInMonth();
            },
            L: function L() {
                return this.isLeapYear() ? 1 : 0;
            },
            o: 'GGGG',
            Y: 'YYYY',
            y: 'YY',
            a: 'a',
            A: 'A',
            B: function B() {
                var thisUTC = this.clone().utc(),

                // Shamelessly stolen from http://javascript.about.com/library/blswatch.htm
                swatch = (thisUTC.hours() + 1) % 24 + thisUTC.minutes() / 60 + thisUTC.seconds() / 3600;
                return Math.floor(swatch * 1000 / 24);
            },
            g: 'h',
            G: 'H',
            h: 'hh',
            H: 'HH',
            i: 'mm',
            s: 'ss',
            u: '[u]', // not sure if moment has this
            e: '[e]', // moment does not have this
            I: function I() {
                return this.isDST() ? 1 : 0;
            },
            O: 'ZZ',
            P: 'Z',
            T: '[T]', // deprecated in moment
            Z: function Z() {
                return parseInt(this.format('ZZ', true), 10) * 36;
            },
            c: 'YYYY-MM-DD[T]HH:mm:ssZ',
            r: 'ddd, DD MMM YYYY HH:mm:ss ZZ',
            U: 'X'
        },
        formatEx: /[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g,
        convert: function convert(PHPDateFormat) {
            return PHPDateFormat.replace(this.formatEx, function (phpStr) {
                console.log(formatDatePhpToJs.mapChars[phpStr]);
                return typeof formatDatePhpToJs.mapChars[phpStr] === 'function' ? formatDatePhpToJs.mapChars[phpStr].call(moment()) : formatDatePhpToJs.mapChars[phpStr];
            });
        },
        getDateFormatsOption: function getDateFormatsOption(parsleyInstance) {
            if (typeof parsleyInstance.options.dateFormats == 'undefined') {
                return this.getDateFormatsOption(parsleyInstance.parent);
            }

            return parsleyInstance.options.dateFormats;
        }
    };

    /**
     * Helper functions.
     *
     * @type {{parseArrayStringParameter: larapars.parseArrayStringParameter, bindChangeToOtherElement: larapars.bindChangeToOtherElement, getDateFormatsOption: larapars.getDateFormatsOption}}
     */
    var utils = {
        parseArrayStringParameter: function parseArrayStringParameter(parameter) {
            var m = parameter.match(/^\s*\[(.*)\]\s*$/);

            if (!m) throw 'Requirement is not an array: "' + parameter + '"';

            return m[1].replace(/\'+/g, '').split(',');
        },
        /**
         * This is used by various validation rules that rely on another input for validation.
         *
         * This function adds a 'change' event listener which forces the original to be validated again.
         *
         * @param rule              Name of the rule this change handler is for
         * @param element           Which element to bind this to
         * @param fieldInstance     The ParsleyFieldInstance we can call validate() on
         * @param originalNotEmpty  Should the original element not be empty? (optional, default false)
         */
        bindChangeToOtherElement: function bindChangeToOtherElement(rule, element, fieldInstance, originalNotEmpty) {
            var $elem = jQuery(element);
            var elData = $elem.data('larapars-rules');

            // None were added yet, initialise
            if (elData === undefined) {
                elData = [rule];
                $elem.data('larapars-rules', elData);
            }
            // Initialised, but not present
            else if (elData.indexOf(rule) == -1) {
                    elData.push(rule);
                    $elem.data('larapars-rules', elData);
                }
                // Already bound
                else {
                        return;
                    }

            // If not yet bound
            $elem.on('change', function () {
                if (originalNotEmpty === true && jQuery(fieldInstance.$element.get(0)).val() != '') {
                    fieldInstance.validate();
                } else if (originalNotEmpty !== true) {
                    fieldInstance.validate();
                }
            });
        }
    };

    // Check if the value is different from the specified input's value
    window.Parsley.addValidator('different', {
        requirementType: 'string',
        validateString: function validateString(value, parameter, fieldInstance) {
            if (jQuery(parameter).length == 0) return true;

            utils.bindChangeToOtherElement('different', parameter, fieldInstance, true);

            return jQuery(parameter).val() != value;
        },
        messages: {
            en: 'The value should not be the same as "%s".'
        }
    });

    // Check if the value is greater than min and smaller than max
    window.Parsley.addValidator('between', {
        requirementType: ['integer', 'integer'],
        validateNumber: function validateNumber(value, min, max) {
            return value > min && value < max;
        },
        messages: {
            en: 'The value should be between "%s" and "%s".'
        }
    });

    // Check if the value is equal to the provided value
    window.Parsley.addValidator('sizeNumber', {
        requirementType: 'integer',
        validateNumber: function validateNumber(value, parameter) {
            return value == parameter;
        },
        messages: {
            en: 'The value should be "%s".'
        }
    });

    // Check if the value's length is equal to the provided value
    window.Parsley.addValidator('sizeString', {
        requirementType: 'integer',
        validateString: function validateString(value, parameter) {
            return value.length == parameter;
        },
        messages: {
            en: 'The value should be "%s" characters long.'
        }
    });

    // Check if each value is distinct
    window.Parsley.addValidator('distinct', {
        requirementType: 'boolean',
        validateMultiple: function validateMultiple(values) {
            var storedValues = [];
            var isDistinct = true;

            values.forEach(function (value) {
                if (storedValues.indexOf(value) > -1) {
                    isDistinct = false;
                    return false;
                }

                storedValues.push(value);
            });

            return isDistinct;
        },
        messages: {
            en: 'Not all values are distinct.'
        }
    });

    // The value should be located in one of the checkbox's checked values
    window.Parsley.addValidator('inArray', {
        requirementType: 'string',
        validateString: function validateString(value, otherFieldName, parsleyInstance) {
            var thisElement = jQuery(parsleyInstance.$element.get(0));

            var values = [];

            // Check if we're dealing with a text field
            if (otherFieldName.substring(0, 1) == '#') {
                // Bind a change event
                utils.bindChangeToOtherElement('inArray', otherFieldName, parsleyInstance, true);

                // If it's a text field we're assuming that it's a list of comma separated values
                return jQuery(otherFieldName).val().split(',').indexOf(value) > -1;
            }

            // Bind a change handler to the checkboxes
            jQuery('input:checkbox[name="' + otherFieldName + '"]').each(function () {
                utils.bindChangeToOtherElement('inArray', this, parsleyInstance, true);
            });

            // Get the selected values of a checkbox by it's name
            jQuery('input:checkbox[name="' + otherFieldName + '"]:checked').each(function () {
                values.push(jQuery(this).val());
            });

            // Check if the value is in there
            return values.indexOf(value) > -1;
        },
        messages: {
            en: 'This value is incorrect.'
        }
    });

    // The value is required only if another input's value matched one of the defined ones.
    // the parameter should be formatted as data-parsley-required-if="["#elementValueToCheck", "value1,value2,.."]"
    window.Parsley.addValidator('requiredIf', {
        requirementType: 'string',
        validateString: function validateString(value, parameters, fieldInstance) {
            // Normalise the parameters
            var values = utils.parseArrayStringParameter(parameters);

            // Get the other input's selector
            var field = values[0];

            // Get the values it should contain to mark this one as required
            parameters = values.slice(1);

            // make sure that the other element get's a change event
            utils.bindChangeToOtherElement('requiredIf', field, fieldInstance);

            // Only required to check if the value is empty
            if (value.length == 0) {
                var fieldValue = jQuery(field).val();

                return parameters.indexOf(fieldValue) == -1;
            }

            return true;
        },
        messages: {
            en: 'This field is required.'
        }
    });

    // The value is required if other field does not contain any of the specified values
    // the parameter should be formatted as data-parsley-required-unless="["#elementValueToCheck", "value1,value2,.."]"
    window.Parsley.addValidator('requiredUnless', {
        requirementType: 'string',
        validateString: function validateString(value, parameters, fieldInstance) {
            // Normalise the parameters
            var values = utils.parseArrayStringParameter(parameters);

            // Get the other input's selector
            var field = values[0];

            // Get the values it should contain to mark this one as required
            parameters = values.slice(1);

            // make sure that the other element get's a change event
            utils.bindChangeToOtherElement('requiredUnless', field, fieldInstance);

            // Only required to check if the value is empty
            if (value.length == 0) {

                var fieldValue = jQuery(field).val();

                // It's not required if the input has one of the values
                return parameters.indexOf(fieldValue) > -1;
            }

            return true;
        },
        messages: {
            en: 'This field is required.'
        }
    });

    // The value is required if  any of the inputs are present in the dom
    // the parameter should be formatted as data-parsley-required-with="#elementValueToCheck,#elementValueToCheck,.."
    window.Parsley.addValidator('requiredWith', {
        requirementType: 'string',
        validateString: function validateString(value, parameters, fieldInstance) {
            // Normalise the parameters
            var allElements = utils.parseArrayStringParameter(parameters);

            // Only validate if the char count is 0
            if (value.length == 0) {
                var AnyPresent = false;

                allElements.forEach(function (id) {
                    var $elem = jQuery(id);

                    // Check for changes on this other input
                    utils.bindChangeToOtherElement('requiredWith', id, fieldInstance);

                    // If the element is in the dom and has a value
                    if ($elem.length > 0 && $elem.val() != '') {
                        AnyPresent = true;
                    }
                });

                return !AnyPresent;
            }

            return true;
        },
        messages: {
            en: 'This field is required.'
        }
    });

    // The value is required if all other inputs are present in the dom
    // the parameter should be formatted as data-parsley-required-with-all="#elementValueToCheck,#elementValueToCheck,.."
    window.Parsley.addValidator('requiredWithAll', {
        requirementType: 'string',
        validateString: function validateString(value, parameters, fieldInstance) {
            // Normalise the parameters
            var allElements = utils.parseArrayStringParameter(parameters);

            // Only validate if the char count is 0
            if (value.length == 0) {
                var AllPresent = true;

                allElements.forEach(function (id) {
                    var $elem = jQuery(id);

                    // Check for changes on this other input
                    utils.bindChangeToOtherElement('requiredWithAll', id, fieldInstance);

                    // If the value isn't in the dom or is empty
                    if ($elem.length == 0 || $elem.val() == '') {
                        AllPresent = false;
                    }
                });

                return !AllPresent;
            }

            return true;
        },
        messages: {
            en: 'This field is required.'
        }
    });

    // The value is required if any of the inputs are not present in the dom
    // the parameter should be formatted as data-parsley-required-with="#elementValueToCheck,#elementValueToCheck,.."
    window.Parsley.addValidator('requiredWithout', {
        requirementType: 'string',
        validateString: function validateString(value, parameters, fieldInstance) {
            // Normalise the parameters
            var allElements = utils.parseArrayStringParameter(parameters);

            // Only validate if the char count is 0
            if (value.length == 0) {
                var AnyPresent = false;

                allElements.forEach(function (id) {
                    var $elem = jQuery(id);

                    // Check for changes on this other input
                    utils.bindChangeToOtherElement('requiredWithAll', id, fieldInstance);

                    if ($elem.length == 0 || $elem.val() == '') {
                        AnyPresent = true;
                    }
                });

                return AnyPresent;
            }

            return true;
        },
        messages: {
            en: 'This field is required.'
        }
    });

    // The value is required if all other inputs are not present in the dom
    // the parameter should be formatted as data-parsley-required-with-all="#elementValueToCheck,#elementValueToCheck,.."
    window.Parsley.addValidator('requiredWithoutAll', {
        requirementType: 'string',
        validateString: function validateString(value, parameters, fieldInstance) {
            // Normalise the parameters
            var allElements = utils.parseArrayStringParameter(parameters);

            // Only validate if the char count is 0
            if (value.length == 0) {
                var AllEmpty = true;

                allElements.forEach(function (id) {
                    var $elem = jQuery(id);

                    // Check for changes on this other input
                    utils.bindChangeToOtherElement('requiredWithAll', id, fieldInstance);

                    if ($elem.length == 1 && $elem.val() != '') {
                        AllEmpty = false;
                    }
                });

                return AllEmpty;
            }

            return true;
        },
        messages: {
            en: 'This field is required.'
        }
    });

    var filesSizes = {
        b: 1,
        kb: 1024,
        mb: 1024 * 1024,
        gb: 1024 * 1024 * 1024
    };

    // Make sure all files within the inputs are equal to or smaller than the defined size.
    window.Parsley.addValidator('fileSizeMax', {
        requirementType: ['integer', 'string'],
        validateString: function validateString(value, maxSize, sizeMultiplyer, parsleyFieldInstance) {
            sizeMultiplyer = sizeMultiplyer.toLowerCase();
            var files = parsleyFieldInstance.$element[0].files;

            // Multiply the max file size
            maxSize = maxSize * filesSizes[sizeMultiplyer.toLowerCase()];

            console.log(maxSize);

            // If a file is present in the input
            if (files.length > 0) {
                // Loop over the files
                for (var i = 0; i < files.length; i++) {
                    console.log(files[i].size);
                    if (files[i].size > maxSize) {
                        return false;
                    }
                }
            }

            return true;
        },
        messages: {
            en: 'Your file(s) are too big.'
        }
    });

    // Make sure all files within the inputs are equal to or bigger than the defined size.
    window.Parsley.addValidator('fileSizeMin', {
        requirementType: ['integer', 'string'],
        validateString: function validateString(value, minSize, sizeMultiplyer, parsleyFieldInstance) {
            var files = parsleyFieldInstance.$element[0].files;

            // Multiply the min file size
            minSize = minSize * filesSizes[sizeMultiplyer.toLowerCase()];

            // If a file is present in the input
            if (files.length > 0) {
                // Loop over the files
                for (var i = 0; i < files.length; i++) {
                    if (files[i].size < minSize) {
                        return false;
                    }
                }
            }

            return true;
        },
        messages: {
            en: 'Your file(s) should are too small.'
        }
    });

    // Make sure all files within the inputs are between the defined sizes.
    window.Parsley.addValidator('fileSizeBetween', {
        requirementType: ['integer', 'integer', 'string'],
        validateString: function validateString(value, minSize, maxSize, sizeMultiplyer, parsleyFieldInstance) {
            var files = parsleyFieldInstance.$element[0].files;

            // Multiply the file sizes
            minSize = minSize * filesSizes[sizeMultiplyer.toLowerCase()];
            maxSize = maxSize * filesSizes[sizeMultiplyer.toLowerCase()];

            // If a file is present in the input
            if (files.length > 0) {
                // Loop over the files
                for (var i = 0; i < files.length; i++) {
                    if (files[i].size <= minSize || files[i].size >= maxSize) {
                        return false;
                    }
                }
            }

            return true;
        },
        messages: {
            en: 'Your file(s) should be between %s and %s %s.'
        }
    });

    // Make sure all files within the input are an image
    window.Parsley.addValidator('image', {
        validateString: function validateString(value, param, parsleyFieldInstance) {
            var files = parsleyFieldInstance.$element[0].files;

            // If a file is present in the input
            if (files.length > 0) {
                // Loop over the files
                for (var i = 0; i < files.length; i++) {
                    if (!files[i].type.match('image/*')) {
                        return false;
                    }
                }
            }

            return true;
        },
        messages: {
            en: 'This is not an image.'
        }
    });

    // Make sure all files within the input have one of the defined mimetypes
    window.Parsley.addValidator('fileMimetype', {
        requirementType: 'string',
        validateString: function validateString(value, mimetypes, parsleyFieldInstance) {
            var allMimes = utils.parseArrayStringParameter(mimetypes);

            var files = parsleyFieldInstance.$element[0].files;

            // If a file is present in the input
            if (files.length > 0) {
                // Loop over the files
                for (var i = 0; i < files.length; i++) {
                    if (allMimes.indexOf(files[i].type) == -1) {
                        return false;
                    }
                }
            }

            return true;
        },
        messages: {
            en: 'This file does not have the correct mimetype "%s".'
        }
    });

    // Make sure all files within the input have one of the defined extensions
    window.Parsley.addValidator('fileExt', {
        requirementType: 'string',
        validateString: function validateString(value, extensions, parsleyFieldInstance) {
            var allExts = utils.parseArrayStringParameter(extensions);

            var files = parsleyFieldInstance.$element[0].files;

            // If a file is present in the input
            if (files.length > 0) {
                // Loop over the files
                for (var i = 0; i < files.length; i++) {
                    var explodeNames = files[i].name.split('.');

                    if (allExts.indexOf(explodeNames[explodeNames.length - 1]) == -1) {
                        return false;
                    }
                }
            }

            return true;
        },
        messages: {
            en: 'This file does not have the correct extensions.'
        }
    });

    // Make sure all images within the input have specific dimensions
    window.Parsley.addValidator('dimensions', {
        requirementType: {
            '': 'boolean',
            min_width: 'number', // Specify the minimum width the image should have
            max_width: 'number', // Specify the maximum width the image should have
            min_height: 'number', // Specify the minimum height the image should have
            max_height: 'number', // Specify the maximum height the image should have
            width: 'number', // Specify the  width the image should have
            height: 'number', // Specify the height the image should have
            ratio: 'string' },
        // Specify the ratio the image should have
        validateString: function validateString(value, param, parsleyFieldInstance) {
            var files = parsleyFieldInstance.$element[0].files;

            var options = parsleyFieldInstance.domOptions.dimensionsOptions;

            // If a file is present in the input
            if (files.length > 0) {
                var defer = jQuery.Deferred();
                var _URL = window.URL || window.webkitURL;

                var image = new Image();

                // Validate once t he image is loaded
                image.onload = function () {
                    var width = this.width;
                    var height = this.height;

                    // Check min width, if defined
                    if (typeof options.min_width != 'undefined') {
                        if (width < options.min_width) {
                            defer.reject(image);
                            return true;
                        }
                    }

                    // Check max width, if defined
                    if (typeof options.max_width != 'undefined') {
                        if (width > options.max_width) {
                            defer.reject(image);
                            return true;
                        }
                    }

                    // Check min height, if defined
                    if (typeof options.min_height != 'undefined') {
                        if (height < options.min_height) {
                            defer.reject(image);
                            return true;
                        }
                    }

                    // Check max height, if defined
                    if (typeof options.max_height != 'undefined') {
                        if (height > options.max_height) {
                            defer.reject(image);
                            return true;
                        }
                    }

                    // Check width, if defined
                    if (typeof options.width != 'undefined') {
                        if (width != options.width) {
                            defer.reject(image);
                            return true;
                        }
                    }

                    // Check height, if defined
                    if (typeof options.height != 'undefined') {
                        if (height != options.height) {
                            defer.reject(image);
                            return true;
                        }
                    }

                    // Check ratio, if defined
                    if (typeof options.ratio != 'undefined') {
                        var splitRatio = options.ratio.split(':');
                        if (splitRatio[0] / splitRatio[1] != width / height) {
                            defer.reject(image);
                            return true;
                        }
                    }

                    defer.resolve(image);
                };

                // On error, reject the promise
                image.onerror = function () {
                    console.warn('image load error');
                    defer.reject();
                };

                image.src = _URL.createObjectURL(files[0]);

                return defer.promise().then(function (image) {
                    // Clean up
                    image = null;

                    return true;
                }, function (image) {
                    // Clean up
                    image = null;

                    return false;
                });
            }

            return true;
        }
    });

    /**
     * Overwrite core Parsley methods.
     *
     * @type {{_isRequired: Window.ParsleyExtend._isRequired}}
     */
    window.ParsleyExtend = jQuery.extend({}, window.ParsleyExtend, {
        // Normally this was intended Internal only.
        // Field is required if have required constraint without `false` value
        _isRequired: function _isRequired() {

            var requiredRules = [
            // This one comes out of the box with parsley
            'required',

            // These ones were added with this library
            'requiredIf', 'requiredUnless', 'requiredWith', 'requiredWithAll', 'requiredWithout', 'requiredWithoutAll'];

            var requiredRulesFound = [];

            // Loop over the list to check if they're defined on the field.
            requiredRules.forEach(function (rule) {
                if ('undefined' !== typeof this.constraintsByName[rule]) {
                    requiredRulesFound.push(rule);
                }
            }, this);

            // If there's not one required rule, return false
            if (requiredRulesFound.length == 0) return false;

            // If parsley's on required rule was found
            if (requiredRulesFound.indexOf('required') >= 0) {
                // Check if the flag is set to true
                return false !== this.constraintsByName.required.requirements;
            }

            return true;
        }
    });

    var main = utils;

    return main;
});
//# sourceMappingURL=laravel-parsley.js.map