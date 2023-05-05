const { createProxyMiddleware } = require('http-proxy-middleware');
const bodyParser = require('body-parser');
const httpProxy = require('http-proxy');

module.exports = function (app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: true,
    })
  )

  const proxy = createProxyMiddleware('/icalendar', {
    target: '', // Inserir o URL do Calendário 
    changeOrigin: true,
    pathRewrite: {
      '^/icalendar': '',
    },
    onError: function(err, req, res) {
      console.error('Proxy error:', err);
      res.status(500).send('Proxy error occurred');
    },
    onProxyRes: function(proxyRes, req, res) {
      proxyRes.headers['Access-Control-Allow-Origin'] = '*';
    },
  });
  
  app.use('/icalendar', proxy);
  
  app.post('/fetch-url', bodyParser.json(), (req, res) => {
    const { targetUrl } = req.body;
  
    if (!targetUrl) {
      res.status(400).send('Missing targetUrl parameter');
      return;
    }
  
    proxy.options.target = targetUrl;
  
    res.send('URL fetched successfully');
  });

  app.listen(3000, () => {
    console.log('Proxy server listening on port 3000');
  });
};