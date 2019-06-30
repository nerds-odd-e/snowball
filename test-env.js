//require('babel-polyfill');
var $ = require('./src/main/webapp/resources/lib/bootstrap/js/jquery.js');
//var jsdom = require('jsdom');
//const { JSDOM } = jsdom;
//const { document } = (new JSDOM('')).window;
global.document = document;
//var $ = jQuery = require('jquery')(window);
// Make a global instance of jQuery available for tests
global.$ = $;