const dotenv = require('dotenv');
const webpack = require("webpack");
const HtmlWebpackPlugin = require('html-webpack-plugin');

dotenv.config();

module.exports = {
    entry: './src/index.tsx',
    devtool: 'source-map',
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_module/,
                use:{
                    loader: 'babel-loader'
                }
            },
            {
                test: /\.tsx?$/,
                exclude: /node_module/,
                use: {
                    loader: 'ts-loader'
                }
            }
        ]
    },
    resolve: {
        extensions: [".tsx", ".ts", ".js"]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './public/index.html'
        })
    ]
}