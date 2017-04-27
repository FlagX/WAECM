/**
 * Created by Dominik Schwarz on 18.03.2017.
 */
'use strict';

const React = require('react');
var rest = require('rest');
var defaultRequest = require('rest/interceptor/defaultRequest');
var mime = require('rest/interceptor/mime');
var uriTemplateInterceptor = require('./api/uriTemplateInterceptor');
var errorCode = require('rest/interceptor/errorCode');
var baseRegistry = require('rest/mime/registry');
var oAuth = require('./interceptor/oauthInterceptor');

var registry = baseRegistry.child();

registry.register('text/uri-list', require('./api/uriListConverter'));
registry.register('application/hal+json', require('rest/mime/type/application/hal'));

var SockJS = require('sockjs-client');
require('stompjs');

var registerForNotification = function(registrations) {
    var socket = SockJS('/transactionNotifier' + '?access_token=' + oAuth.getToken());
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        registrations.forEach(function (registration) {
            stompClient.subscribe(registration.route, registration.callback);
        });
    });
}

module.exports.registerForNotification = registerForNotification;
module.exports.rest = rest.wrap(mime, {registry: registry})
    .wrap(uriTemplateInterceptor)
    .wrap(errorCode)
    .wrap(defaultRequest, {headers: {'Accept': 'application/hal+json'}})
    .wrap(oAuth, {});
