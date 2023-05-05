const { createProxyMiddleware } = require('http-proxy-middleware');
const http = require('http');
const httpProxy = require('http-proxy');
const bodyParser = require('body-parser');

module.exports = function (app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: true,
    })
  )

  app.use('/fetch-url', bodyParser.json(), (req, res) => {
    const { targetUrl } = req.body;

    app.use(
      '/icalendar',
      createProxyMiddleware({
        target: targetUrl,
        changeOrigin: true,
      })
    );

    res.send('URL fetched successfully');
  });
};
