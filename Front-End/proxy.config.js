const PROXY_CONFIG = [{
    context: [
        "/v1/",
    ],
    "target": "http://localhost:8001",
    "secure": false,
    "changeOrigin": true
}]

module.exports = PROXY_CONFIG;