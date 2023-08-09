const path = require("path");

const webpack = require("webpack");
const { merge } = require("webpack-merge");

const baseConfig = require("./webpack.config");

module.exports = merge(baseConfig, {
  mode: "development",
  devServer: {
    host: "localhost",
    port: 3000,
    historyApiFallback: true,
    open: true,
    static: path.join(__dirname, "public"),
  },
  plugins: [
    new webpack.DefinePlugin({
      "process.env.NODE_ENV": JSON.stringify("development"),
      "process.env.REACT_APP_API_URL": JSON.stringify(
        process.env.REACT_APP_DEV_API_URL,
      ),
    }),
  ],
});
