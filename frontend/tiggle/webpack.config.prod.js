const { merge } = require("webpack-merge");
const path = require("path");
const webpack = require("webpack");
const baseConfig = require("./webpack.config");

module.exports = merge(baseConfig, {
  mode: "production",
  output: {
    path: path.join(__dirname, "/dist"),
    filename: "index_bundle.js",
    clean: true,
  },
  plugins: [
    new webpack.DefinePlugin({
      "process.env.NODE_ENV": JSON.stringify("production"),
      "process.env.REACT_APP_API_URL": JSON.stringify(
        process.env.REACT_APP_PROD_API_URL,
      ),
    }),
  ],
});
