/**
 * Created by stefan on 20.04.17.
 */
(function (define) {
    'use strict';

    define(function (require) {

        var interceptor, oAuth, when;
        var authProvider;

        interceptor = require('rest/interceptor');
        oAuth = require('@zalando/oauth2-client-js');
        when = require('when');

        function urlContainsToken(url) {
            let h;
            if (url) {
                if (url.indexOf('#') === -1) return false;
                h = url.substring(url.indexOf('#'));
            }

            /*
             * Start with checking if there is a token in the hash
             */
            if (h.length < 2) return false;
            if (h.indexOf("access_token") === -1) return false;
            return true;
        }

        function authorize() {
            let r = new oAuth.Request({
                client_id: 'group6',  // required
                redirect_uri: 'http://localhost:8080',
                scope: 'openid'
            });

            // Give it to the provider
            let uri = authProvider.requestToken(r);

            // Later we need to check if the response was expected
            // so save the request
            authProvider.remember(r);

            // Do the redirect
            window.location.href = uri;
        }

        return interceptor({
            init: function (config) {
                authProvider = new oAuth.Provider({
                    id: 'openid',   // required
                    authorization_url: 'http://localhost:8080/oauth/authorize' // required
                });

                if (urlContainsToken(window.location.href)) {
                    authProvider.parse(window.location.hash);
                }

                return config;
            },
            request: function (request) {
                if (request.path === '/logout'){
                    authProvider.deleteTokens();
                    return request;
                }

                if (!authProvider.hasAccessToken()) {
                    authorize();
                }

                let token = authProvider.getAccessToken();
                request.headers = request.headers || {};
                request.headers['Authorization'] = 'Bearer ' + token;
                return request;
            },
            response: function (response) {
                if (response.status.code === 401) {
                    // token probably expired, reauthorize
                    authorize();
                }
                else if (response.status.code === 403) {
                    return when.reject(response);
                }
                return response;
            }
        });

    });

}(
    typeof define === 'function' && define.amd ? define : function (factory) { module.exports = factory(require); },
    typeof window !== 'undefined' ? window : typeof global !== 'undefined' ? global : void 0
    // Boilerplate for AMD and Node
));