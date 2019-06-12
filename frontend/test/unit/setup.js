import Vue from 'vue'

Vue.config.productionTip = false

global.fetch = require('jest-fetch-mock');
