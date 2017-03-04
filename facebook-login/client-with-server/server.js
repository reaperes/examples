const path = require('path');
const express = require('express');
const passport = require('passport');
const FacebookStrategy = require('passport-facebook').Strategy;

const app = express();

passport.use(new FacebookStrategy({
  clientID: process.env.FB_APP_ID,
  clientSecret: process.env.FB_APP_SECRET,
  callbackURL: '/auth/facebook/callback'
}, function (accessToken, refreshToken, profile, callback) {
  console.log(accessToken);
  callback(null, profile);

  // GET long-lived access token
  //
  // const request = require('request');
  // const qs = require('querystring');
  //
  // request.get({
  //   url: 'https://graph.facebook.com/oauth/access_token',
  //   qs: {
  //     grant_type: 'fb_exchange_token',
  //     client_id: process.env.FB_APP_ID,
  //     client_secret: process.env.FB_APP_SECRET,
  //     fb_exchange_token: accessToken
  //   }
  // }, function (error, response, body) {
  //   console.log('error:', error);
  //   console.log('statusCode:', response && response.statusCode);
  //   console.log('body:', qs.parse(body));
  // });
}));

app.get('/', function (req, res) {
  res.sendFile(path.resolve(__dirname, './index.html'));
});

app.get('/auth/facebook', passport.authenticate('facebook'));

app.get('/auth/facebook/callback',
  passport.authenticate('facebook', {
    session: false,
    successRedirect: '/',
    failureRedirect: '/'
  })
);

app.listen(8080, function () {
  console.log('Example app listening on port 8080!');
});
