const path = require("path");

const webpack = require("webpack");
const { merge } = require("webpack-merge");

const baseConfig = require("./webpack.config");

module.exports = merge(baseConfig, {
  mode: "development",
  devServer: {
    static: path.join(__dirname, "public"),
    host: "localhost",
    port: 3000,
    historyApiFallback: true,
    open: true,
    allowedHosts: "all",
    liveReload: true,
  },
  plugins: [
    new webpack.DefinePlugin({
      "process.env.NODE_ENV": JSON.stringify("development"),
      "process.env.REACT_APP_API_URL": JSON.stringify(
        process.env.REACT_APP_DEV_API_URL,
      ),
      "process.env.REACT_APP_API_MOCKING": JSON.stringify(
        process.env.REACT_APP_API_MOCKING,
      ),
    }),
    new webpack.ProvidePlugin({
      process: "process/browser",
    }),
  ],
});
