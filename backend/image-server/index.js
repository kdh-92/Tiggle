import multer from 'multer';
import express from 'express';
import dotenv from 'dotenv';
import { v4 as uuid } from 'uuid';
import * as path from "node:path";

dotenv.config()
switch (process.env.NODE_ENV) {
    case 'dev': dotenv.config({ path: ".env.dev" }); break;
    default: dotenv.config({ path: ".env.prod" }); break;
}

let app = express()
let port = process.env.PORT

let savePath = process.env.FILE_DIRECTORY
console.log(savePath)

let storage = multer.diskStorage({
    // 저장 경로
    destination: function (req, file, cb) {
        cb(null, savePath)
    },
    // 저장 파일 명
    filename: function (req, file, cb) {
        let ext = path.extname(file.originalname)
        cb(null, `${uuid()}${ext}`)
    },
    limits: {
        fileSize: 1024 * 1024 * 32 // 32 mb
    }
})
let upload = multer({storage: storage})

app.post("/upload", upload.single("image"),
    function (req, res) {
        console.log(req.file);
        return res.status(200).json({})
    }
)

app.get("/image/:imageName", function (req, res) {
    res.sendFile(path.join(savePath, req.params.imageName))
})

app.listen(port, ()=>{
    console.log(`Server started on port ${port}`)
})