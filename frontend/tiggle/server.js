import express from "express";
import ViteExpress from "vite-express";

const app = express();
const port = 3001;

ViteExpress.config({
    mode: "production",
});

ViteExpress.listen(app, port, () => console.log(`ViteExpress server open to ${port}`))