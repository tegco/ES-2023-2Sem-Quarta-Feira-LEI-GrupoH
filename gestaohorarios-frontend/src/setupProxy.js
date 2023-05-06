const { createProxyMiddleware } = require('http-proxy-middleware');
const http = require('http');
const WebSocket = require('ws');

module.exports = function (app) {
 const server = http.createServer(app);
 const wss = new WebSocket.Server({ server });

  wss.on('connection', (ws) => {
    console.log('WebSocket connection established');
  
    // Handle messages received on the WebSocket connection
    ws.on('message', (message) => {
      console.log(`WebSocket message received: ${message}`);
    });
  
    // Handle WebSocket connection close event
    ws.on('close', () => {
      console.log('WebSocket connection closed');
    });
  });
  
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: true,
    })
  )
  const bodyParser = require('body-parser');

  // Add the following middleware to parse the request body
  app.use(bodyParser.urlencoded({ extended: false }));
  app.use(bodyParser.json());

  const proxy = createProxyMiddleware('/icalendar', {
    target: 'https://fenix.iscte-iul.pt',
    changeOrigin: true,
    pathRewrite: {
      '^/icalendar': '',
    },
    onError: function(err, req, res) {
      console.error('Proxy error:', err);
      res.status(500).send('Proxy error occurred');
    },
    onProxyReq: function (proxyReq, req, res) {
      const targetUrl = req.query.targetUrl; 
 
      if (targetUrl) {
        proxyReq.path = targetUrl.replace('webcal://fenix.iscte-iul.pt', '');
        proxyReq.protocol = "https://";
      }
    },
    onProxyRes: function(proxyRes, req, res) {
      proxyRes.headers['Access-Control-Allow-Origin'] = '*';
    },
  });
  
  app.use('/icalendar', proxy);
  
  server.listen(3000, () => {
    console.log('Proxy server listening on port 3000');
  });
};
